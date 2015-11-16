package com.example.ehs.im;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInformation;

import com.example.ehs.R;
import com.example.ehs.model.Friends;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.ComPressUtils;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.utils.ExpressionUtil;
import com.example.ehs.utils.GridviewUtils;
import com.example.ehs.xmpphelper.AudioEncoder;
import com.example.ehs.xmpphelper.ContacterManager;
import com.example.ehs.xmpphelper.MakeAudio;
import com.example.ehs.xmpphelper.XmppFileManager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CrowdChatActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
	private Button rightBtView;
	
	private ImageView bqView;//表情
    private ImageView addView;//发送方式
    private EditText chatInputView;//文字输入
    private Button speakView;//按住说话
    private Button chatSendView;//语音、键盘按钮
	
	String crowd_id=null;
	String crowd_name=null;
	private int input_type=0;//输入方式  INPUT_TYPE:0---文字输入，1---语音输入
	UserInfo userInfo = null;
	String currentAccount=null;
	String currentName=null;
	MultiUserChat muc =null;
	
	static final int RECEIVE=0;
	static final int SEND=1;
	static final int INVITE=2;
	static final int ACCEPT=3;
	static final int MEMBER=4;
	
	ListView msgListView=null;
	private CrowdMessageAdapter adapter=null;
	//private SimpleAdapter msgAdapter=null;
	//private List<HashMap<String, Object>> msgList=null;
	//private HashMap<String, Object> msgMap=null;
	private List<IMMessage> msgList=null;
	IMMessage imMessage = null;
	
	//判断接收还是发送消息
	boolean sendOrAccept=true;//发送消息
	String body = null;
	String from = null;
	
	private int[] imageIds = new int[107];//表情ID
	private Dialog builder; //表情弹出框
	
    /* 请求码*/
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int FILE_RESULT_CODE = 2;
    
    Friends friends=null;
    boolean isPlay = false;
	MediaPlayer mediaPlayer;
	MakeAudio mr;//录制音频生成文件
	private static int RECODE_STATE = 0; // 录音的状态
	private static int RECORD_NO = 0; // 不在录音
	private static int RECORD_ING = 1; // 正在录音
	private static int RECODE_ED = 2; // 完成录音
	private Dialog dialog;
	private Thread recordThread;
	private ImageView dialog_img;
	private static float recodeTime = 0.0f; // 录音的时间
	private static double voiceValue = 0.0; // 麦克风获取的音量值
	
	private static int MAX_TIME = 15; // 最长录制时间，单位秒，0为无时间限制
	private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1
	AudioEncoder encoder=new AudioEncoder();//编码的类
	ByteArrayOutputStream stream=null;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		Intent i = this.getIntent();
		Bundle b = i.getBundleExtra("crow");
		crowd_id = b.getString("jid");
		crowd_name = b.getString("name");
		System.out.println("jid="+crowd_id);
		System.out.println("name="+crowd_name);
		userInfo = new UserInfo(this);
		currentAccount = userInfo.getAccount();
		currentName = userInfo.getUserName();
		System.out.println("currentAccount="+currentAccount);
		System.out.println("currentName="+currentName);
		init();
		try {  
			muc = new MultiUserChat(ConnectionUtils.getConnection(this), crowd_id);  
            // 创建聊天室,进入房间后的nickname(昵称)  
            muc.join(currentAccount);  
            System.out.println("加入成功！");
        } catch (XMPPException e) {  
            e.printStackTrace();  
        }
		ChatPacketListener chatListener = new ChatPacketListener(muc);
		muc.addMessageListener(chatListener);
		//ContacterManager.doTest(ConnectionUtils.getConnection(CrowdChatActivity.this),crowd_id);
	}
	
	public Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case RECEIVE: 
            	sendOrAccept = false;
            	body = msg.getData().getString("body");
                from = msg.getData().getString("from");
                System.out.println("body="+body);
                System.out.println("from="+from);
               
                imMessage = new IMMessage();
                imMessage.setContent(body);
                imMessage.setFromSubJid(from);
                msgList.add(imMessage);
                break;
            case SEND:
            	sendOrAccept = true;
            	imMessage = new IMMessage();
            	imMessage.setContent(chatInputView.getText().toString());
            	break;
            default:
                break;
            }
        }
    };
    
	class ChatPacketListener implements PacketListener {
        private String _number;
        private Date _lastDate;
        private MultiUserChat _muc;
        private String _roomName;

        public ChatPacketListener(MultiUserChat muc) {
            _number = "0";
            _lastDate = new Date(0);
            _muc = muc;
            _roomName = muc.getRoom();
        }

        @Override
        public void processPacket(Packet packet) {
            Message message = (Message) packet;
            String from = message.getFrom();

            if (message.getBody() != null) {
                DelayInformation inf = (DelayInformation) message.getExtension(
                        "x", "jabber:x:delay");
                Date sentDate;
                if (inf != null) {
                    sentDate = inf.getStamp();
                } else {
                    sentDate = new Date();
                }

                System.out.println("Receive old message: date="
                        + sentDate.toLocaleString() + " ; message="
                        + message.getBody());

                android.os.Message msg = new android.os.Message();
                msg.what = RECEIVE;
                Bundle bd = new Bundle();
                bd.putString("from", from.substring(from.lastIndexOf("/")+1));
                bd.putString("body", message.getBody());
                msg.setData(bd);
                handler.sendMessage(msg);
            }
        }
    }
	
	private void init() {
		//标题和按钮
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		rightBtView = (Button)findViewById(R.id.bt_right);//添加好友和加入群	
		
		titleView.setText(crowd_name);
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		rightBtView.setBackgroundResource(R.drawable.btn_add);
		rightBtView.setVisibility(View.VISIBLE);
		rightBtView.setOnClickListener(this);
		
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
    	
    	msgList = new ArrayList<IMMessage>();
    	
    	msgListView = (ListView)findViewById(R.id.chatlist);
    	
    	adapter = new CrowdMessageAdapter(this, msgList,msgListView);
		msgListView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_right:
			Intent i = new Intent();
			i.setClass(CrowdChatActivity.this, CrowdInfoActivity.class);
			Bundle b = new Bundle();
			b.putString("jid", crowd_id);
			i.putExtra("bd", b);
			startActivity(i);
			break;
		case R.id.chat_bq:
			createExpressionDialog();
			//Toast.makeText(CrowdChatActivity.this, "群聊表情", Toast.LENGTH_SHORT).show();
			break;
		case R.id.chat_add:
			createMoreDialog();
			//Toast.makeText(CrowdChatActivity.this, "群聊更多", Toast.LENGTH_SHORT).show();
			break;
		case R.id.chat_send:
			System.out.println("type="+input_type);
			String message = chatInputView.getText().toString();
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
										Toast.makeText(CrowdChatActivity.this, mr.path, Toast.LENGTH_SHORT).show();
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
										Toast.makeText(CrowdChatActivity.this, mr.path, Toast.LENGTH_SHORT).show();
									}else{
										String messageStr = getAudoiToStr(mr.path);
										System.out.println("path="+mr.path);
										//sendMessage("3"+messageStr+";"+mr.path, 3);
										try {
											muc.sendMessage("3"+messageStr);
										} catch (XMPPException e) {
											e.printStackTrace();
										}
										chatInputView.setText("");
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
				try {
					muc.sendMessage("0"+message);
				} catch (XMPPException e) {
					e.printStackTrace();
				}
				chatInputView.setText("");
			}
			Toast.makeText(CrowdChatActivity.this, "群聊发送", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	private class CrowdMessageAdapter extends BaseAdapter {
		private List<IMMessage> items;
		private Context context;
		private ListView adapterList;

		public CrowdMessageAdapter(Context context,List<IMMessage> items,
				ListView adapterList) {
			this.context = context;
			this.items = items;
			this.adapterList = adapterList;
		}

		public void refreshList(List<IMMessage> items) {
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
			View view1=null;
			TextView timeView=null;
			ImageView avaterView=null;
			TextView contentView=null;
			
			ImageView conImageView=null;
			
			LinearLayout fileLayout=null;
			ImageView floder_image = null;
			TextView floder_name=null;
			TextView floder_size=null;
			TextView floder_send=null;
			
			LinearLayout voiceLayout=null;
			TextView voice_time=null;
			ImageView voice_volumn=null;
			if(message.getFromSubJid().equals(currentAccount)){
				//发送消息
				view1 = CrowdChatActivity.this.getLayoutInflater().inflate(R.layout.item_news, null);//新闻
				timeView = (TextView)view1.findViewById(R.id.newsTime);//新闻发送 时间
				avaterView = (ImageView) view1.findViewById(R.id.newsIcon);//新闻发送者图标
				contentView = (TextView)view1.findViewById(R.id.newsTitle);//新闻标题
				contentView.setBackgroundResource(R.drawable.balloon_r_selector);
				timeView.setText(currentAccount);
				timeView.setGravity(Gravity.RIGHT);
				//发送图片
				conImageView = (ImageView)view1.findViewById(R.id.newsImg);
				conImageView.setBackgroundResource(R.drawable.balloon_r_selector);
				//发送文件
				fileLayout = (LinearLayout)view1.findViewById(R.id.send_folder_2);
				fileLayout.setBackgroundResource(R.drawable.balloon_r_selector);
				//发送语音
				voiceLayout = (LinearLayout)view1.findViewById(R.id.voice_layout);
				voiceLayout.setBackgroundResource(R.drawable.balloon_r_selector);
			}else{
				//接收消息
				view1 = CrowdChatActivity.this.getLayoutInflater().inflate(R.layout.item_message, null);//消息
				timeView = (TextView)view1.findViewById(R.id.messageTime);//消息发送 时间
				avaterView = (ImageView) view1.findViewById(R.id.senderAvatar);//消息发送者头像
				contentView = (TextView)view1.findViewById(R.id.messageContent);//消息内容
				contentView.setBackgroundResource(R.drawable.balloon_l_selector);
				timeView.setText(message.getFromSubJid());
				timeView.setGravity(Gravity.LEFT);
				//发送图片
				conImageView = (ImageView)view1.findViewById(R.id.messageImg);
				conImageView.setBackgroundResource(R.drawable.balloon_l_selector);
				//发送文件
				fileLayout = (LinearLayout)view1.findViewById(R.id.send_folder_1);
				fileLayout.setBackgroundResource(R.drawable.balloon_l_selector);
				//发送语音
				voiceLayout = (LinearLayout)view1.findViewById(R.id.voice_layout_1);
				voiceLayout.setBackgroundResource(R.drawable.balloon_l_selector);
			}
			String tempStr = message.getContent();//消息具体内容
			System.out.println("message="+message.toString());
			//广播消息时出错，得判断是不是广播消息，广播消息
			System.out.println("hhh="+message.getChatMode());
			int chatMode=message.getChatMode();
			String str=tempStr;
			/*if(tempStr.substring(0, 1).equals("0")||tempStr.substring(0, 1).equals("1")||tempStr.substring(0,1).equals("2")
					|| tempStr.substring(0,1).equals("3")){
				chatMode = Integer.parseInt(tempStr.substring(0, 1));
				str = tempStr.substring(1);
				System.out.println("str="+str);
				System.out.println("chatMode="+chatMode);
			}*/
			if((chatMode==0) || (chatMode==4)){
				contentView.setVisibility(View.VISIBLE);
				String zhengze = "f0[0-9]{2}|f10[0-7]";	
				//正则表达式，用来判断消息内是否有表情
				try {
					SpannableString spannableString = ExpressionUtil.getExpressionString(context, str, zhengze);
					contentView.setText(spannableString);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				//contentView.setText(str);
			}else if(chatMode==1){
				contentView.setVisibility(View.GONE);
				conImageView.setVisibility(View.VISIBLE);
				final byte[] b=Base64.decode(str, Base64.DEFAULT);
				
				/*byte[] d=null;
	    		if (str!=null) {  
		    	   str = str.toUpperCase();  
		    	    int length = str.length() / 2;  
		    	    char[] hexChars = str.toCharArray();  
		    	    d = new byte[length];  
		    	    for(int i = 0; i < length; i++){  
		    	        int pos = i * 2;  
		    	        d[i] = (byte) ((byte) "0123456789ABCDEF".indexOf(hexChars[pos]) << 4 | (byte) "0123456789ABCDEF".indexOf(hexChars[pos + 1]));  
		    	    } 
	    		}*/
				//将比特流转化为图片
				//final Bitmap bitmap=null;
				 if(b.length!=0) {
					 final Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);  
					 conImageView.setImageBitmap(bitmap);
						conImageView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent i = new Intent();
								i.setClass(CrowdChatActivity.this, PictureActivity.class);
								//i.putExtra("pic",bitmap);
								Bundle bd = new Bundle();
								if (message.getFromSubJid() != null && message.getMsgType() != 1){
									bd.putString("from", "from");
								}else{
									bd.putString("from", "to");
								}
								bd.putByteArray("bit", b);
								i.putExtra("pic",bd);
								startActivity(i);
							}
						});
				 }
			}else if(chatMode == 2){
				contentView.setVisibility(View.GONE);
				conImageView.setVisibility(View.GONE);
				fileLayout.setVisibility(View.VISIBLE);
				//文件里的
				floder_image = (ImageView)fileLayout.findViewById(R.id.send_file_pic);
				floder_name = (TextView)fileLayout.findViewById(R.id.file_name);
				floder_size = (TextView)fileLayout.findViewById(R.id.file_size);
				floder_send = (TextView)fileLayout.findViewById(R.id.send_file_ok);
				String fileName = tempStr.substring(tempStr.indexOf(";")+1);
				String fileSize = tempStr.substring(tempStr.indexOf(";"));
				System.out.println("fileName="+fileName);
				floder_name.setText(fileName);
				floder_size.setText(fileSize);
				if (message.getFromSubJid() != null && message.getMsgType() != 1){
					floder_send.setText("等待接受");
				}else{
					floder_send.setText("发送中...");
				}
				final String accept = floder_send.getText().toString();
				System.out.println("accept="+accept);
				fileLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(accept.equals("等待接受")){
							Toast.makeText(CrowdChatActivity.this, "接受中...", Toast.LENGTH_SHORT).show();
							FileTransferManager manager = new FileTransferManager(ConnectionUtils.getConnection(CrowdChatActivity.this));
							manager.addFileTransferListener(new XmppFileManager(CrowdChatActivity.this){
								@Override
								public void fileTransferRequest(FileTransferRequest request) {
									super.fileTransferRequest(request);
								}
							});
						}
					}
				});
			}else if(chatMode == 3){
				contentView.setVisibility(View.GONE);
				conImageView.setVisibility(View.GONE);
				fileLayout.setVisibility(View.GONE);
				voiceLayout.setVisibility(View.VISIBLE);
				voice_time = (TextView)voiceLayout.findViewById(R.id.voice_time);
				voice_time.setText("5'");
				voice_volumn = (ImageView)voiceLayout.findViewById(R.id.voice_volumn);//播放时需要控制
				//final String url = str.substring(str.lastIndexOf(";")+1);
				final String url = message.getInfoUrl();
				System.out.println("url="+url);
				boolean isSave=false;
				String saveFileStr=null;
				if (message.getFromSubJid() != null && message.getMsgType() != 1){
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
							String byteStr = str.substring(0,str.lastIndexOf(";"));
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
					}else{
						Toast.makeText(CrowdChatActivity.this, "没有SD卡", Toast.LENGTH_SHORT).show();
					}
				}else{
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
				}
			}
			return view1;
		}
	}
	private void createExpressionDialog() {
		builder = new Dialog(CrowdChatActivity.this);
		GridView gridView = GridviewUtils.createGridView(CrowdChatActivity.this,imageIds);
		builder.setContentView(gridView);
		builder.setTitle("默认表情");
		builder.show();
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bitmap = null;
				bitmap = BitmapFactory.decodeResource(getResources(), imageIds[arg2 % imageIds.length]);
				ImageSpan imageSpan = new ImageSpan(CrowdChatActivity.this, bitmap);
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
	
	private void createMoreDialog() {
		builder = new Dialog(CrowdChatActivity.this);
		GridView gridView = GridviewUtils.getMoreGridView(CrowdChatActivity.this);
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
				/*i.setClass(CrowdChatActivity.this, VedioChatActivity.class);
				Bundle bd = new Bundle();
				bd.putParcelable("friends", friends);
				i.putExtra("bd", bd);
				startActivity(i);*/
				Toast.makeText(CrowdChatActivity.this, "开发中", Toast.LENGTH_SHORT).show();
				builder.dismiss();
				break;
			case 3:
				//语音通话
				Intent i1 = new Intent();
				/*i1.setClass(CrowdChatActivity.this, VoiceChatActivity.class);
				Bundle bd1 = new Bundle();
				bd1.putParcelable("friends", friends);
				i1.putExtra("bd", bd1);
				startActivity(i1);*/
				Toast.makeText(CrowdChatActivity.this, "开发中", Toast.LENGTH_SHORT).show();
				builder.dismiss();
				break;
			case 4:
				//文件传输
				Intent i4 = new Intent();
				Toast.makeText(CrowdChatActivity.this, "开发中", Toast.LENGTH_SHORT).show();
				i4.setClass(CrowdChatActivity.this, MyFileManager.class);
				startActivityForResult(i4,FILE_RESULT_CODE);
				builder.dismiss();
				break;
				default:break;
			}
		}
	});
}
	
// 录音时显示Dialog
void showVoiceDialog() {
		dialog = new Dialog(CrowdChatActivity.this, R.style.DialogStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.my_dialog);
		dialog_img = (ImageView) dialog.findViewById(R.id.dialog_img);
		dialog.show();
	}	
// 录音时间太短时Toast显示
	void showWarnToast() {
		Toast toast = new Toast(CrowdChatActivity.this);
		LinearLayout linearLayout = new LinearLayout(CrowdChatActivity.this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(20, 20, 20, 20);

		// 定义一个ImageView
		ImageView imageView = new ImageView(CrowdChatActivity.this);
		imageView.setImageResource(R.drawable.voice_to_short); // 图标

		TextView mTv = new TextView(CrowdChatActivity.this);
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
	//将语音文件编码后传输
	private String getAudoiToStr(String filePath) {
		System.out.println("录制文件的路径" + filePath);
		int audiolenth=0;
		byte[] audiobyte=null;
		String str2=null;
		try {
			File file = new File(filePath);
			FileInputStream fileinput = new FileInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int lenth = 0;
			try {
				while ((lenth = fileinput.read(b)) != -1) {
					output.write(b, 0, lenth);
				}
				audiobyte = output.toByteArray();
				audiolenth = audiobyte.length;// 音频数据的大小
				output.close();
				fileinput.close();
			} catch (IOException e) {
				e.printStackTrace();
				str2="异常！";
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			str2="异常！";
		}

		if (audiolenth > 0) {
			// 对数据进行编码
			str2 = Base64.encodeToString(audiobyte, Base64.DEFAULT);
			System.out.println("str2="+str2);
		}
		return str2;
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
			public void handleMessage(android.os.Message msg) {
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String picPath=null;
		File file = null;   
        Bitmap pic = null;
		if (resultCode != RESULT_CANCELED){
			switch (requestCode) {
            case IMAGE_REQUEST_CODE:
            	Uri uri = data.getData();   
                String scheme = uri.getScheme();   
                if(scheme.equalsIgnoreCase("file")) {   
                    picPath = uri.getPath();   
                    System.out.println("picPath="+picPath);   
                    file = new File(picPath);   
                  //  pic = ComPressUtils.decodeFile(file); 
                  //  String messageStr = ComPressUtils.getBitToStr(pic);
                    String messageStr = ComPressUtils.bitmapToString(picPath);
                   // sendMessage("1"+messageStr, 1);
                    try {
    					muc.sendMessage("1"+messageStr);
    				} catch (XMPPException e) {
    					e.printStackTrace();
    				}
                } else if (scheme.equalsIgnoreCase("content")) {   
                    Cursor cursor = getContentResolver().query(uri, null, null,null, null);   
                    cursor.moveToFirst();   
                    picPath = cursor.getString(1);   
                    file = new File(picPath);   
                   // pic = ComPressUtils.decodeFile(file);   
                    //String messageStr = ComPressUtils.getBitToStr(pic);
                    String messageStr = ComPressUtils.bitmapToString(picPath);
                   // sendMessage("1"+messageStr, 1);
                    try {
    					muc.sendMessage("1"+messageStr);
    				} catch (XMPPException e) {
    					e.printStackTrace();
    				}
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
                    String messageStr = ComPressUtils.bitmapToString(fileName);
                    try {
						muc.sendMessage("1"+messageStr);
					} catch (XMPPException e) {
						e.printStackTrace();
					}
                   /* pic = ComPressUtils.decodeFile(files);   
	                if(pic!=null){
	                	String messageStr = ComPressUtils.getBitToStr(pic);
	                	//sendMessage("1"+messageStr, 1);
	                	 try {
		    					muc.sendMessage("1"+messageStr);
		    				} catch (XMPPException e) {
		    					e.printStackTrace();
		    				}
	                	System.out.println("haha");
	                }else{
	                	Toast.makeText(CrowdChatActivity.this, "pic为空", Toast.LENGTH_SHORT).show();
	                }*/
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
                     //sendFile(friends.getJID(),file2,ConnectionUtils.getConnection(CrowdChatActivity.this));
                 }  
            	break;
                default:break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
				
}
