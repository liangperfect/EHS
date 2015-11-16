package com.example.ehs.im;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;

import com.example.ehs.R;
import com.example.ehs.db.IMMessageDb;
import com.example.ehs.model.Friends;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.Byte2KB;
import com.example.ehs.utils.ComPressUtils;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.utils.ExpressionUtil;
import com.example.ehs.utils.GridviewUtils;
import com.example.ehs.xmpphelper.AChatActivity;
import com.example.ehs.xmpphelper.AudioEncoder;
import com.example.ehs.xmpphelper.MakeAudio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * 聊天界面
 * */
public class ChatActivity extends AChatActivity implements OnClickListener{
	
    ListView listView = null;
    List<Map<String, Object>> list;
    IMMessage message ;
    String style=null;
    String name=null;
    Friends friends=null;
    
    private TextView titleView;//标题
    private Button messageView;//返回消息界面
    private Button imgView;//好友信息
    
    private ImageView bqView;//表情
    private ImageView addView;//发送方式
    private EditText chatInputView;//文字输入
    private Button speakView;//按住说话
    private Button chatSendView;//语音、键盘按钮
    
    private MessageListAdapter adapter = null;
    
    private int[] imageIds = new int[107];//表情ID
	private Dialog builder; //表情弹出框
	
	private int input_type=0;//输入方式  INPUT_TYPE:0---文字输入，1---语音输入
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int FILE_RESULT_CODE = 2;
    
    ByteArrayOutputStream stream=null;
    String filename=null;
    int i1 =0;//未长按键（0），长按键（1）
    String record_call=null;
	
	private static int MAX_TIME = 15; // 最长录制时间，单位秒，0为无时间限制
	private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1

	private static int RECORD_NO = 0; // 不在录音
	private static int RECORD_ING = 1; // 正在录音
	private static int RECODE_ED = 2; // 完成录音

	private static int RECODE_STATE = 0; // 录音的状态

	private static float recodeTime = 0.0f; // 录音的时间
	private static double voiceValue = 0.0; // 麦克风获取的音量值
	MakeAudio mr;//录制音频生成文件
	private Dialog dialog;
	private Thread recordThread;
	private ImageView dialog_img;
	AudioEncoder encoder=new AudioEncoder();//编码的类
	boolean isPlay = false;
	MediaPlayer mediaPlayer;
	int i=1;
	int j = 0;//0--表示发送，1--表示接受
	ImageView voice_volumn=null;
	
	//群聊
	String crowd_jid=null;
	String  crowd_name=null;
	
	private ProgressBar pb=null;
	private IMMessageDb messageDb=null;
	private UserInfo userInfo=null;
	List<IMMessage> msgList=null;
	TextView receiveState=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		
		messageDb = new IMMessageDb(ChatActivity.this);
		userInfo = new UserInfo(ChatActivity.this);
		
		System.out.println("userInfo="+userInfo.getAccount());
		
		Bundle b = getIntent().getBundleExtra("info");
		style = b.getString("style");
		System.out.println("style="+style);
		
		if(style.equals("crowd")){
			crowd_jid = b.getString("jid");
			crowd_name = b.getString("name");
			System.out.println("crowd_jid="+crowd_jid);
			System.out.println("crowd_name="+crowd_name);
		}else{
			friends = b.getParcelable("friends");
			//name = friends.getName();
			name = friends.getJID();
			System.out.println("name="+name);
		}
		this.pb = (ProgressBar) findViewById(R.id.formclient_pb_1);
	    setupView();
	}
	private void setupView() {
    	listView = (ListView) findViewById(R.id.chatlist);
    	
    	titleView = (TextView)this.findViewById(R.id.title);
    	if(style.equals("crowd")){
    		titleView.setText(crowd_name);
    	}else{
    		titleView.setText(name);
    	}
    	
    	msgList = getMessages();
    	adapter = new MessageListAdapter(ChatActivity.this, msgList,listView);
		listView.setAdapter(adapter);
		
    	messageView = (Button)findViewById(R.id.bt_left);
    	messageView.setVisibility(View.VISIBLE);
    	messageView.setOnClickListener(this);
    	
    	imgView = (Button)findViewById(R.id.bt_right);
    	imgView.setVisibility(View.VISIBLE);
    	imgView.setOnClickListener(this);
    	
    	bqView = (ImageView)findViewById(R.id.chat_bq);
    	bqView.setOnClickListener(this);
    	
    	addView = (ImageView)findViewById(R.id.chat_add);
    	addView.setOnClickListener(this);
    	
    	chatInputView = (EditText)findViewById(R.id.chat_input);
    	
    	speakView = (Button)findViewById(R.id.chat_speak);
    	 
    	chatSendView = (Button)findViewById(R.id.chat_send);
    	//每次进来都是默认文字输入
    	input_type = 0;   
    	chatSendView.setText("语音");
    	chatSendView.setOnClickListener(this);
    	
    	//监听说话还是文字输入
    	chatInputView.addTextChangedListener(new TextWatcher() {		
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				String content = chatInputView.getText().toString();
				if(content.length()>0){
					input_type = 2;
					chatSendView.setText("发送");
				}else{
					input_type = 0;
					chatSendView.setText("语音");
				}
			}
		});
    	
    	
    	FileTransferManager fileTransferManager = new FileTransferManager(ConnectionUtils.getConnection(ChatActivity.this));
		fileTransferManager.addFileTransferListener(new RecFileTransferListener());
    }

	@Override
	protected void receiveNewMessage(IMMessage message) {
		
	}
	@Override
	protected void refreshMessage(List<IMMessage> messages) {
		adapter.refreshList(messages);
		System.out.println("33");
	}
	
//消息容器	
private class MessageListAdapter extends BaseAdapter {
		private List<IMMessage> items;
		private Context context;
		private ListView adapterList;
		public MessageListAdapter(Context context, List<IMMessage> items,
				ListView adapterList) {
			this.context = context;
			this.items = items;
			this.adapterList = adapterList;
		}
		public void refreshList(List<IMMessage> items) {
			System.out.println("44");
			this.items = items;
			this.notifyDataSetChanged();
			adapterList.setSelection(items.size() - 1);
		}
		@Override
		public int getCount() {
			return items.size();
		}
		@Override
		public Object getItem(int position) {
			return items.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final IMMessage message = items.get(position);
			TextView timeView=null;
			ImageView avaterView=null;
			TextView contentView=null;
			
			ImageView conImageView=null;
			
			LinearLayout fileLayout=null;
			ImageView floder_image = null;
			TextView floder_name=null;
			TextView floder_size=null;
		
			Button receiveBt=null;
			Button rejectBt=null;
			
			LinearLayout voiceLayout=null;
			TextView voice_time=null;
			//ImageView voice_volumn=null;
			View  view1=null;
			//接收的信息
			if(style.equals("crowd")){
				
			}else if(style.equals("from")){
				if (message.getFromSubJid() != null && message.getMsgType() != 1){
					//接收的消息
					view1 = ChatActivity.this.getLayoutInflater().inflate(R.layout.item_message, null);//消息
					timeView = (TextView)view1.findViewById(R.id.messageTime);//消息发送 时间
					avaterView = (ImageView) view1.findViewById(R.id.senderAvatar);//消息发送者头像
					//发送消息
					contentView = (TextView)view1.findViewById(R.id.messageContent);//消息内容
					contentView.setBackgroundResource(R.drawable.balloon_l_selector);
					//发送图片
					conImageView = (ImageView)view1.findViewById(R.id.messageImg);//图片
					conImageView.setBackgroundResource(R.drawable.balloon_l_selector);
					//发送文件
					fileLayout = (LinearLayout)view1.findViewById(R.id.send_folder_1);//发送文件
					fileLayout.setBackgroundResource(R.drawable.balloon_l_selector);
					//发送语音
					voiceLayout = (LinearLayout)view1.findViewById(R.id.voice_layout_1);//发送语音
					voiceLayout.setBackgroundResource(R.drawable.balloon_l_selector);
				}else{
					//发送的消息
					view1 = ChatActivity.this.getLayoutInflater().inflate(R.layout.item_news, null);//新闻
					timeView = (TextView)view1.findViewById(R.id.newsTime);//新闻发送 时间
					avaterView = (ImageView) view1.findViewById(R.id.newsIcon);//新闻发送者图标
					//发送消息
					contentView = (TextView)view1.findViewById(R.id.newsTitle);//新闻标题
					contentView.setBackgroundResource(R.drawable.balloon_r_selector);
					//发送图片
					conImageView = (ImageView)view1.findViewById(R.id.newsImg);//图片
					conImageView.setBackgroundResource(R.drawable.balloon_r_selector);
					//发送文件
					fileLayout = (LinearLayout)view1.findViewById(R.id.send_folder_2);//发送文件
					fileLayout.setBackgroundResource(R.drawable.balloon_r_selector);
					//发送语音
					voiceLayout = (LinearLayout)view1.findViewById(R.id.voice_layout);//发送语音
					voiceLayout.setBackgroundResource(R.drawable.balloon_r_selector);
				}
			}else{//发送消息
				//如果是接收的消息，接受的消息显示在左边
				if (message.getFromSubJid() != null && message.getMsgType() != 1){
					System.out.println("接收消息");
					//发送文字和表情
					view1 = ChatActivity.this.getLayoutInflater().inflate(R.layout.item_message, null);//消息
					timeView = (TextView)view1.findViewById(R.id.messageTime);//消息发送 时间
					avaterView = (ImageView) view1.findViewById(R.id.senderAvatar);//消息发送者头像
					contentView = (TextView)view1.findViewById(R.id.messageContent);//消息内容
					contentView.setBackgroundResource(R.drawable.balloon_l_selector);
					//发送图片
					conImageView = (ImageView)view1.findViewById(R.id.messageImg);
					conImageView.setBackgroundResource(R.drawable.balloon_l_selector);
					//发送文件
					fileLayout = (LinearLayout)view1.findViewById(R.id.send_folder_1);
					fileLayout.setBackgroundResource(R.drawable.balloon_l_selector);
					//发送语音
					voiceLayout = (LinearLayout)view1.findViewById(R.id.voice_layout_1);
					voiceLayout.setBackgroundResource(R.drawable.balloon_l_selector);
				}else{//发送的消息显示在右边
					System.out.println("发送消息");
					//发送文字和表情
					view1 = ChatActivity.this.getLayoutInflater().inflate(R.layout.item_news, null);//新闻
					timeView = (TextView)view1.findViewById(R.id.newsTime);//新闻发送 时间
					avaterView = (ImageView) view1.findViewById(R.id.newsIcon);//新闻发送者图标
					contentView = (TextView)view1.findViewById(R.id.newsTitle);//新闻标题
					contentView.setBackgroundResource(R.drawable.balloon_r_selector);
					//发送图片
					conImageView = (ImageView)view1.findViewById(R.id.newsImg);
					conImageView.setBackgroundResource(R.drawable.balloon_r_selector);
					//发送文件
					fileLayout = (LinearLayout)view1.findViewById(R.id.send_folder_2);
					fileLayout.setBackgroundResource(R.drawable.balloon_r_selector);
					//发送语音
					voiceLayout = (LinearLayout)view1.findViewById(R.id.voice_layout);
					voiceLayout.setBackgroundResource(R.drawable.balloon_r_selector);
				}
			}
			timeView.setText(message.getTime());
			String tempStr = message.getContent();//消息具体内容
			//System.out.println("message="+message.toString());
			System.out.println("chatMode="+message.getChatMode());
			//广播消息时出错，得判断是不是广播消息，广播消息
			System.out.println("hhh="+message.getChatMode());
			//int chatMode=message.getChatMode();
			int chatMode = message.getChatMode();
			String str=tempStr;
			if(tempStr.startsWith(";")){
				chatMode = Integer.parseInt(tempStr.substring(tempStr.lastIndexOf(";")+1,2));
				str=tempStr.substring(2);
				System.out.println("chaMode="+chatMode);
				System.out.println("tempStr="+tempStr);
			}
			//发送文字、表情
			if((chatMode==0) || (chatMode==4)){
				contentView.setVisibility(View.VISIBLE);
				conImageView.setVisibility(View.GONE);
				String zhengze = "f0[0-9]{2}|f10[0-7]";	
				//正则表达式，用来判断消息内是否有表情
				try {
					System.out.println("000");
					SpannableString spannableString = ExpressionUtil.getExpressionString(context, str, zhengze);
					contentView.setText(spannableString);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}else if(chatMode==1){//发送图片
				System.out.println("111");
				contentView.setVisibility(View.GONE);
				conImageView.setVisibility(View.VISIBLE);
				final byte[] b=Base64.decode(str, Base64.DEFAULT);
				 if(b.length!=0) {
					 final Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);  
					 //将得到的bitmap还原才行
					 conImageView.setImageBitmap(bitmap);
						conImageView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent i = new Intent();
								i.setClass(ChatActivity.this, PictureActivity.class);
								Bundle bd = new Bundle();
								if (message.getFromSubJid() != null && message.getMsgType() != 1){
									bd.putString("from", "from");
								}else{
									bd.putString("from", "to");
									bd.putString("url", message.getInfoUrl());
									System.out.println("infoUrl="+message.getInfoUrl());
								}
								bd.putByteArray("bit", b);
								i.putExtra("pic",bd);
								startActivity(i);
							}
						});
				 }
			}else if(chatMode == 2){//文件传输
				System.out.println("222");
				contentView.setVisibility(View.GONE);
				conImageView.setVisibility(View.GONE);
				fileLayout.setVisibility(View.VISIBLE);
				//文件里的
				floder_image = (ImageView)fileLayout.findViewById(R.id.send_file_pic);
				floder_name = (TextView)fileLayout.findViewById(R.id.file_name);
				floder_size = (TextView)fileLayout.findViewById(R.id.file_size);
				
				receiveState = (TextView)fileLayout.findViewById(R.id.receive_state);
				receiveBt = (Button)fileLayout.findViewById(R.id.send_file_ok);
				rejectBt = (Button)fileLayout.findViewById(R.id.send_file_reject);
				//pb = (ProgressBar) fileLayout.findViewById(R.id.formclient_pb);
				
				String fileName = tempStr.substring(tempStr.indexOf(";")+1);
				String fileSize = tempStr.substring(0,tempStr.indexOf(";"));
				
				System.out.println("fileName="+fileName);
				floder_name.setText(fileName);
				floder_size.setText(fileSize);
				if(fileName.endsWith(".mp3")){
					floder_image.setImageResource(R.drawable.music);
				}else if(fileName.endsWith(".mp4")){
					floder_image.setImageResource(R.drawable.mp4);
				}else if(fileName.endsWith(".jpg")||fileName.endsWith(".jpeg")||fileName.endsWith(".png")){
					floder_image.setImageResource(R.drawable.capture_image);
				}else if(fileName.endsWith(".txt")||fileName.endsWith(".lrc")){
					floder_image.setImageResource(R.drawable.txt);
				}else if(fileName.endsWith(".doc")||fileName.endsWith(".docx")){
					floder_image.setImageResource(R.drawable.word);
				}else if(fileName.endsWith(".pdf")){
					floder_image.setImageResource(R.drawable.pdf);
				}else{
					floder_image.setImageResource(R.drawable.unknown);
				}
				if (message.getFromSubJid() != null && message.getMsgType() != 1){//接收文件
						//receiveBt.setVisibility(View.VISIBLE);
						//rejectBt.setVisibility(View.VISIBLE); 
					}
				
			}else if(chatMode == 3){//语音
				System.out.println("333"); 
				contentView.setVisibility(View.GONE);
				conImageView.setVisibility(View.GONE);
				fileLayout.setVisibility(View.GONE);
				voiceLayout.setVisibility(View.VISIBLE);
				voice_time = (TextView)voiceLayout.findViewById(R.id.voice_time);
				voice_time.setText("5'");
				voice_volumn = (ImageView)voiceLayout.findViewById(R.id.voice_volumn);//播放时需要控制
				final String url = message.getInfoUrl();
				System.out.println("url="+url);
				boolean isSave=false;
				String saveFileStr=null;
				if (message.getFromSubJid() != null && message.getMsgType() != 1){
					j = 1;//接收
					if(!isSave){
						System.out.println("接收的消息");
						String sdStatus = Environment.getExternalStorageState();  
				        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用   
							File f=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EHS/receive/audio"+"/"+message.getFromSubJid());
							if (!f.exists()) {//不存在创建
								 f.mkdirs();
							}
							SimpleDateFormat  sim=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS");
							String film=sim.format(new Timestamp(System.currentTimeMillis()));
							saveFileStr = Environment.getExternalStorageDirectory().getAbsolutePath()+"/EHS/receive/audio"+"/"+message.getFromSubJid()+"/"+film+".amr";
							System.out.println("录音文件接收地址="+saveFileStr);
							//String byteStr = str.substring(0,str.lastIndexOf(";"));
							String byteStr = str;
							byte[] bytes = Base64.decode(byteStr, Base64.DEFAULT);
							System.out.println("bytes 长度="+bytes.length);
							//bitmap  将比特转为bitmap
			                FileOutputStream b1 = null;  
			                try {
								b1 = new FileOutputStream(saveFileStr);
				                try {
									b1.write(bytes);
									b1.close();
								} catch (IOException e) {
									e.printStackTrace();
									System.out.println("IO异常");
								}
							} catch (FileNotFoundException e) {
								System.out.println("没找到文件");
								e.printStackTrace();
							} 
						}
		                final File saveFile = new File(saveFileStr);
		                if(saveFile.exists()){
		                	isSave=true;
		                }
		                voiceLayout.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								
								isPlay = false;
								if (!isPlay) {
									if (mediaPlayer != null && mediaPlayer.isPlaying()) {
										mediaPlayer.stop();
									}
									mediaPlayer = new MediaPlayer();
									try {
										System.out.println("path="+saveFile.getPath());
										mediaPlayer.setDataSource(saveFile.getPath());
										mediaPlayer.prepare();
										mediaPlayer.start();
										isPlay = true;
										mediaPlayer
												.setOnCompletionListener(new OnCompletionListener() {
													@Override
													public void onCompletion(MediaPlayer mp) {
														if (isPlay) {
															isPlay = false;
														}
		
													}
												});
		
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (SecurityException e) {
										e.printStackTrace();
									} catch (IllegalStateException e) {
										e.printStackTrace();
									} catch (IOException e) {
										e.printStackTrace();
									}
								} else {
									if (mediaPlayer.isPlaying()) {
										mediaPlayer.stop();
										isPlay = false;
									} else {
										isPlay = false;
									}
								}
							}
						});
		                mythread1();
					}else{
						Toast.makeText(ChatActivity.this, "没有SD卡", Toast.LENGTH_SHORT).show();
					}
				}else{
					j=0;//发送
					voiceLayout.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
							isPlay = false;
							if (!isPlay) {
								if (mediaPlayer != null && mediaPlayer.isPlaying()) {
									mediaPlayer.stop();
								}
								mediaPlayer = new MediaPlayer();
								try {
									mediaPlayer.setDataSource(url);
									mediaPlayer.prepare();
									mediaPlayer.start();
									isPlay = true;
									mediaPlayer
											.setOnCompletionListener(new OnCompletionListener() {
												@Override
												public void onCompletion(MediaPlayer mp) {
													if (isPlay) {
														isPlay = false;
													}
	
												}
											});
	
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (SecurityException e) {
									e.printStackTrace();
								} catch (IllegalStateException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							} else {
								if (mediaPlayer.isPlaying()) {
									mediaPlayer.stop();
									isPlay = false;
								} else {
									isPlay = false;
								}
							}
						}
					});
					mythread1();
				}
			}
			return view1;
		}

	}
//点击事件	
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.bt_left:
		this.finish();
		break;
		case R.id.bt_right:
			Intent i = new Intent();
			i.putExtra("his_info", name);
			i.setClass(ChatActivity.this, FriendsInfoActivity.class);
			startActivity(i);			
		break;
		case R.id.chat_bq:
			createExpressionDialog();
			break;
		case R.id.chat_add:
			createMoreDialog();
			break;
		case R.id.chat_send:
			System.out.println("type="+input_type);
			String message = chatInputView.getText().toString();
			//判断方式:默认为0---显示“语音”,语音点击为1---显示“键盘”,有文字输入为2---显示“发送”
			if(input_type == 0){//原本显示“语音”，点击一下为“键盘”
				input_type = 1;
				chatSendView.setText("键盘");
				chatInputView.setVisibility(View.GONE);
				speakView.setVisibility(View.VISIBLE);
				speakView.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							System.out.println("按下");
							if (RECODE_STATE != RECORD_ING) {// 不等于正在录音
								mr = new MakeAudio("audio");
								RECODE_STATE = RECORD_ING;
								showVoiceDialog();
								try {
									if(mr.path.equals("没有sd卡")){
										Toast.makeText(ChatActivity.this, mr.path, Toast.LENGTH_SHORT).show();
									}else{
										mr.start();// 开始录音
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
								mythread();
							}
							break;
						case MotionEvent.ACTION_UP:
							System.out.println("弹起");
							if (RECODE_STATE == RECORD_ING) {
								RECODE_STATE = RECODE_ED;
								if (dialog.isShowing()) {
									dialog.dismiss();
								}
								try {
									mr.stop();
									voiceValue = 0.0;
								} catch (IOException e) {
									e.printStackTrace();
								}
								if (recodeTime < MIX_TIME) {
									showWarnToast();// 显示录音时间太短对话框
									RECODE_STATE = RECORD_NO;
								} else {
									//比特编码后发送
									if(mr.path.equals("没有sd卡")){
										Toast.makeText(ChatActivity.this, mr.path, Toast.LENGTH_SHORT).show();
									}else{
										String messageStr = ComPressUtils.getAudoiToStr(mr.path);
										System.out.println("path="+mr.path);
										sendMessage(";3"+messageStr, mr.path, 3);
									}
								}
							}
							break;
						default:
							break;
						}
						return false;
					}
				});
			}else if(input_type == 1){
				input_type = 0;
				chatInputView.setVisibility(View.VISIBLE);
				speakView.setVisibility(View.GONE);
				chatSendView.setText("语音");
			}else{
				sendMessage(";0"+message,"",0);
				chatInputView.setText("");
			}
			break;
		default:
			break;
		}
		
	}
/*
 * 
 * 更多操作对话框
 * */
	private void createMoreDialog() {
		builder = new Dialog(ChatActivity.this);
		GridView gridView = GridviewUtils.getMoreGridView(ChatActivity.this);
		builder.setContentView(gridView);
		builder.show();	
		gridView.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			switch(position){
			case 0:
				//跳转到图片选择界面
				 Intent intentFromGallery = new Intent();
                 intentFromGallery.setType("image/*"); // 设置文件类型
                 intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                 startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
                 builder.dismiss();
				break;
			case 1:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
                startActivityForResult(intent, 1);  
				builder.dismiss();
				break;
			case 2:
				//视频通话模块
				Intent i = new Intent();
				i.setClass(ChatActivity.this, VedioChatActivity.class);
				Bundle bd = new Bundle();
				bd.putParcelable("friends", friends);
				i.putExtra("bd", bd);
				startActivity(i);
				builder.dismiss();
				break;
			case 3:
				//语音通话
				Intent i1 = new Intent();
				i1.setClass(ChatActivity.this, VoiceChatActivity.class);
				Bundle bd1 = new Bundle();
				bd1.putParcelable("friends", friends);
				i1.putExtra("bd", bd1);
				startActivity(i1);
				builder.dismiss();
				break;
			case 4:
				//文件传输
				Intent i4 = new Intent();
				i4.setClass(ChatActivity.this, MyFileManager.class);
				startActivityForResult(i4,FILE_RESULT_CODE);
				builder.dismiss();
				break;
				default:break;
			}
		}
	});
}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String picPath=null;
		if (resultCode != RESULT_CANCELED){
			switch (requestCode) {
            case IMAGE_REQUEST_CODE:
            	Uri uri = data.getData();   
                String scheme = uri.getScheme();   
                if(scheme.equalsIgnoreCase("file")) {   
                    picPath = uri.getPath();   
                    System.out.println("picPath="+picPath);   
                    file = new File(picPath);   
                    String messageStr = ComPressUtils.bitmapToString(picPath);
                    sendMessage(";1"+messageStr, picPath ,1);
                } else if (scheme.equalsIgnoreCase("content")) {   
                    Cursor cursor = getContentResolver().query(uri, null, null,null, null);   
                    cursor.moveToFirst();   
                    picPath = cursor.getString(1);   
                    file = new File(picPath);   
                    String messageStr = ComPressUtils.bitmapToString(picPath);
                    sendMessage(";1"+messageStr, picPath, 1);
                }   
               break;
            case CAMERA_REQUEST_CODE:
                String sdStatus = Environment.getExternalStorageState();  
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用   
                    return;  
                }  
                String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
                Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
                Bundle bundle1 = data.getExtras();  
                Bitmap bitmap2 = (Bitmap) bundle1.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式   
                FileOutputStream b1 = null;  
                File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EHS/Image");  
                file1.mkdirs();// 创建文件夹   
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/EHS/Image/"+name;  
                try {  
                    b1 = new FileOutputStream(fileName);  
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, b1);// 把数据写入文件   
                    File files = new File(fileName);
                    String  messageStr = ComPressUtils.bitmapToString(fileName);
                    sendMessage(";1"+messageStr, fileName,1);
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                } finally {  
                    try {  
                        b1.flush();  
                        b1.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
                break;
            case FILE_RESULT_CODE:
            	 Bundle bd = null;  
                 if(data!=null&&(bd=data.getExtras())!=null){  
                	 bd = data.getExtras();
                	 String filePath = bd.getString("file");
                     System.out.println("filePath="+filePath);
                     File file2 = new File(filePath);
                     //文件
                     System.out.println("fileSize="+file2.length());
                   //  sendFile(friends.getJID(),file2,ConnectionUtils.getConnection(ChatActivity.this));
                     sendFile(filePath,friends.getJID());
                 }  
            	break;
                default:break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

//表情对话框
private void createExpressionDialog() {
		builder = new Dialog(ChatActivity.this);
		GridView gridView = GridviewUtils.createGridView(ChatActivity.this, imageIds);
		builder.setContentView(gridView);
		builder.setTitle("默认表情");
		builder.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(), imageIds[arg2 % imageIds.length]);
				ImageSpan imageSpan = new ImageSpan(ChatActivity.this, bitmap);
				String str = null;
				if(arg2<10){
					str = "f00"+arg2;
				}else if(arg2<100){
					str = "f0"+arg2;
				}else{
					str = "f"+arg2;
				}
				SpannableString spannableString = new SpannableString(str);
				spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				chatInputView.append(spannableString);
				builder.dismiss();
			}
		});
		
	}
	
// 录音时显示Dialog
void showVoiceDialog() {
dialog = new Dialog(ChatActivity.this, R.style.DialogStyle);
dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
	dialog.setContentView(R.layout.my_dialog);
	dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
	dialog.show();
}

// 录音计时线程
void mythread() {
	recordThread = new Thread(ImgThread);
	recordThread.start();
}
// 录音线程
private Runnable ImgThread = new Runnable() {
	@Override
	public void run() {
		recodeTime = 0.0f;
		while (RECODE_STATE == RECORD_ING) {
			if (recodeTime >= MAX_TIME && MAX_TIME != 0) {// 录制时间过长时
				imgHandle.sendEmptyMessage(0);
			} else {
				try {
					Thread.sleep(200);
					recodeTime += 0.2;
					if (RECODE_STATE == RECORD_ING) {
						voiceValue = mr.getAmplitude();// 返回振幅
						imgHandle.sendEmptyMessage(1);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
Handler imgHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// 录音超过15秒自动停止
				if (RECODE_STATE == RECORD_ING) {
					RECODE_STATE = RECODE_ED;
					if (dialog.isShowing()) {
						dialog.dismiss();
					}
					try {
						mr.stop();// 停止录音
						voiceValue = 0.0;
					} catch (IOException e) {
						e.printStackTrace();
					}

					if (recodeTime < 1.0) {
						showWarnToast();// 现实录音时间太短对话框
						RECODE_STATE = RECORD_NO;
					} else {
						encoder.startEncoder(mr.path);//开始编码
					}
				}
				break;
			case 1:// 显示振幅图片
				setDialogImage();
				break;
			default:
				break;
			}

		}
	};
};

// 录音Dialog图片随声音大小切换
void setDialogImage() {
	if (voiceValue < 200.0) {
		dialog_img.setImageResource(R.drawable.record_animate_01);
	} else if (voiceValue > 200.0 && voiceValue < 400) {
		dialog_img.setImageResource(R.drawable.record_animate_02);
	} else if (voiceValue > 400.0 && voiceValue < 800) {
		dialog_img.setImageResource(R.drawable.record_animate_03);
	} else if (voiceValue > 800.0 && voiceValue < 1600) {
		dialog_img.setImageResource(R.drawable.record_animate_04);
	} else if (voiceValue > 1600.0 && voiceValue < 3200) {
		dialog_img.setImageResource(R.drawable.record_animate_05);
	} else if (voiceValue > 3200.0 && voiceValue < 5000) {
		dialog_img.setImageResource(R.drawable.record_animate_06);
	} else if (voiceValue > 5000.0 && voiceValue < 7000) {
		dialog_img.setImageResource(R.drawable.record_animate_07);
	} else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_08);
	} else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_09);
	} else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_10);
	} else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_11);
	} else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_12);
	} else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_13);
	} else if (voiceValue > 28000.0) {
		dialog_img.setImageResource(R.drawable.record_animate_14);
	}
}

// 录音时间太短时Toast显示
void showWarnToast() {
	Toast toast = new Toast(ChatActivity.this);
	LinearLayout linearLayout = new LinearLayout(ChatActivity.this);
	linearLayout.setOrientation(LinearLayout.VERTICAL);
	linearLayout.setPadding(20, 20, 20, 20);

	// 定义一个ImageView
	ImageView imageView = new ImageView(ChatActivity.this);
	imageView.setImageResource(R.drawable.voice_to_short); // 图标

	TextView mTv = new TextView(ChatActivity.this);
	mTv.setText("时间太短   录音失败");
	mTv.setTextSize(14);
	mTv.setTextColor(Color.WHITE);// 字体颜色
	// mTv.setPadding(0, 10, 0, 0);

	// 将ImageView和ToastView合并到Layout中
	linearLayout.addView(imageView);
	linearLayout.addView(mTv);
	linearLayout.setGravity(Gravity.CENTER);// 内容居中
	linearLayout.setBackgroundResource(R.drawable.record_bg);// 设置自定义toast的背景

	toast.setView(linearLayout);
	toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
	toast.show();
}
FileTransferManager fileTransferManager=null;
//發送文件
protected void sendFile(String filepath,String jid) {
	fileTransferManager = new FileTransferManager(ConnectionUtils.getConnection(ChatActivity.this));
	//final OutgoingFileTransfer fileTransfer = fileTransferManager.createOutgoingFileTransfer(jid+"/Spark 2.6.3");
	final OutgoingFileTransfer fileTransfer = fileTransferManager.createOutgoingFileTransfer(jid+"/Smack");
	final File file = new File(filepath);
	try {
		fileTransfer.sendFile(file, "Sending");
	} catch (Exception e) {
		e.printStackTrace();
	}
	File file1 = new File(filepath);
	String kbs = Byte2KB.bytes2kb(file1.length());
	System.out.println("kbs="+kbs);
	String messageContent = kbs+";"+filepath.substring(filepath.lastIndexOf("/")+1);
	String time = ConnectionUtils.getStringTime();
	
	IMMessage message = new IMMessage();
	message.setChatMode(2);//發送文件
	message.setContent(messageContent);
	message.setMsgType(1);
	message.setTime(time);
	message.setInfoUrl(filepath);  
	System.out.println("message="+message.toString());
	System.out.println("jid="+jid);
	System.out.println("userAccount="+userInfo.getAccount());
	messageDb.saveMessage(message, jid, userInfo.getAccount());
	msgList.add(message);
	adapter.notifyDataSetChanged();
	
	new Thread(new Runnable() {
		@Override
		public void run() {
			try{					
				while (true) {
					Thread.sleep(500L);
					Status status = fileTransfer.getStatus();								
					if ((status == FileTransfer.Status.error)
							|| (status == FileTransfer.Status.complete)
							|| (status == FileTransfer.Status.cancelled)
							|| (status == FileTransfer.Status.refused)) {
						handler.sendEmptyMessage(4);
						break;
					}else if(status == FileTransfer.Status.negotiating_transfer){
						//..
					}else if(status == FileTransfer.Status.negotiated){							
						//..
					}else if(status == FileTransfer.Status.initial){
						//..
					}else if(status == FileTransfer.Status.negotiating_stream){							
						//..
					}else if(status == FileTransfer.Status.in_progress){
						handler.sendEmptyMessage(2);
						long p = fileTransfer.getBytesSent() * 100L / fileTransfer.getFileSize();													
						android.os.Message message = handler.obtainMessage();
						message.arg1 = Math.round((float) p);
						message.what = 3;
						message.sendToTarget();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}).start();
}

private FileTransferRequest request;
private File file;

class RecFileTransferListener implements FileTransferListener {
	@Override
	public void fileTransferRequest(FileTransferRequest prequest) {	
		NotificationManager mgr = (NotificationManager) ChatActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification nt = new Notification();
		  nt.defaults = Notification.DEFAULT_SOUND;
		  int soundId = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
		  mgr.notify(soundId, nt);
		  
		System.out.println("aaa");
		System.out.println("The file received from: " + prequest.getRequestor());
		File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EHS/receive");
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		System.out.println("bbb");
		System.out.println("filename="+prequest.getFileName());
		file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/EHS/receive/" + prequest.getFileName());
		request = prequest;
		handler.sendEmptyMessage(5);
		System.out.println("ccc");
		//監聽到有文件發送過來
		String time = ConnectionUtils.getStringTime();
		IMMessage message = new IMMessage();
		message.setChatMode(2);//發送文件
		message.setContent(Byte2KB.bytes2kb(prequest.getFileSize())+";"+prequest.getFileName());
		message.setMsgType(0);//發送還是接收,0接收
		message.setTime(time);
		message.setFromSubJid(friends.getJID());
		System.out.println("message="+message.toString());
		System.out.println("userAccount="+userInfo.getAccount());
		messageDb.saveMessage(message, friends.getJID(), userInfo.getAccount());
		System.out.println("111");
		msgList.add(message);
		System.out.println("222");
		refreshMessage(msgList);
	}
}
private Handler handler = new Handler() {
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
		case 1:
			String[] args = (String[]) msg.obj;
			break;			
		case 2:
			//if(pb.getVisibility()==View.GONE){
			System.out.println("1");
				pb.setMax(100);
				pb.setProgress(0);
				pb.setVisibility(View.VISIBLE);
			//}
			break;
		case 3:
			pb.setVisibility(View.VISIBLE);
			pb.setProgress(msg.arg1);
			break;
		case 4:
			pb.setVisibility(View.GONE);
			//receiveState.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();
			break;
		case 5:
			final IncomingFileTransfer infiletransfer = request.accept();
			AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);
			builder.setTitle("是否接受对方传来的文件")
					.setCancelable(false)
					.setPositiveButton("接收",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									try {
										infiletransfer.recieveFile(file);
									} catch (XMPPException e) {
										e.printStackTrace();
									}
									handler.sendEmptyMessage(2);
									Timer timer = new Timer();
									TimerTask updateProgessBar = new TimerTask() {
										public void run() {
											if ((infiletransfer.getAmountWritten() >= request.getFileSize())
													|| (infiletransfer.getStatus() == FileTransfer.Status.error)
													|| (infiletransfer.getStatus() == FileTransfer.Status.refused)
													|| (infiletransfer.getStatus() == FileTransfer.Status.cancelled)
													|| (infiletransfer.getStatus() == FileTransfer.Status.complete)) {
												cancel();
												handler.sendEmptyMessage(4);
											} else {
												long p = infiletransfer.getAmountWritten() * 100L / infiletransfer.getFileSize();													
												android.os.Message message = handler.obtainMessage();
												message.arg1 = Math.round((float) p);
												message.what = 3;
												message.sendToTarget();
											}
										}
									};
									timer.scheduleAtFixedRate(updateProgessBar, 10L, 10L);
									dialog.dismiss();
								}
							})
					.setNegativeButton("拒绝",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									request.reject();
									dialog.cancel();
								}
							}).show();
			break;
		default:
			break;
		}
	};
};


//录音计时线程
void mythread1() {
	recordThread = new Thread(ImgThread1);
	recordThread.start();
}
//录音线程
private Runnable ImgThread1 = new Runnable() {
	@Override
	public void run() {
		while (isPlay) {
			try {
				Thread.sleep(200);
				if(i<3){
					i = i+1;
				}else{
					i=1;
				}
				imgHandle1.sendEmptyMessage(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
Handler imgHandle1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if(j==0){//发送
					voice_volumn.setImageResource(R.drawable.velumn_right_1);
				}else{
					voice_volumn.setImageResource(R.drawable.volumn_left_1);
				}
				break;
			case 2:
				if(j==0){
					voice_volumn.setImageResource(R.drawable.velumn_right_2);
				}else{
					voice_volumn.setImageResource(R.drawable.velumn_left_2);
				}
				break;
			case 3:
				if(j==0){
					voice_volumn.setImageResource(R.drawable.velumn_right_3);
				}else{
					voice_volumn.setImageResource(R.drawable.velumn_left_3);
				}
				break;
			default:
				break;
			}

		}
	};
};
}
