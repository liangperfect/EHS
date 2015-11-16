package com.example.ehs.xmpphelper;

import java.io.File;
import java.net.Proxy.Type;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.JarFile;

import org.apache.http.conn.ClientConnectionManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;

import com.example.ehs.R;
import com.example.ehs.db.IMMessageDb;
import com.example.ehs.im.ChatActivity;
import com.example.ehs.model.Friends;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.Byte2KB;
import com.example.ehs.utils.ConnectionUtils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
/*
 * 自定义聊天界面抽象类
 * 需要实现
 * */
public abstract class AChatActivity extends BaseActivity {

	private Chat chat = null;
	private List<IMMessage> message_pool = null;
	Friends friends=null;
	UserInfo userInfo = null;
	IMMessageDb messageDb=null;
	private ProgressBar pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.chat);
		//this.pb = (ProgressBar) findViewById(R.id.formclient_pb);
		userInfo = new UserInfo(this);
	//	String to = getIntent().getStringExtra("to");
		Bundle b = getIntent().getBundleExtra("info");
		String style = b.getString("style");
		//String name = b.getString("name");
		System.out.println("style="+style);
		if(style.equals("crowd")){
			
		}else{
			friends = b.getParcelable("friends");
		}
		//if (name == null)
		if(friends == null)
			return;
		//String threadId = MChatManager.chatThreads.get(name);
		String threadId = MChatManager.chatThreads.get(friends.getJID());
		chat = ConnectionUtils.getConnection(this).getChatManager()
				.getThreadChat(threadId);
		if (chat == null) {
			//chat = ConnectionUtils.getConnection(this).getChatManager()
					//.createChat(name, null);
			chat = ConnectionUtils.getConnection(this).getChatManager()
					.createChat(friends.getJID(), null);
		}
		//message_pool = MChatManager.getMessages(name);
		//message_pool = MChatManager.getMessages(friends.getJID());
		messageDb = new IMMessageDb(AChatActivity.this);
		message_pool = messageDb.getAllMessage(userInfo.getAccount(), friends.getJID(), null);
	}

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.NEW_MESSAGE_ACTION);
		registerReceiver(receiver, filter);
		super.onResume();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Constant.NEW_MESSAGE_ACTION.equals(intent.getAction())) {
				System.out.println("hhahahahhahah");
				NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					  Notification nt = new Notification();
					  nt.defaults = Notification.DEFAULT_SOUND;
					  int soundId = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
					  mgr.notify(soundId, nt);
			    
				IMMessage message = intent
						.getParcelableExtra(IMMessage.IMMESSAGE_KEY);
				System.out.println("Achat---"+message.getChatMode());
				messageDb.saveMessage(message, friends.getJID(), userInfo.getAccount());
				message_pool.add(message);
				receiveNewMessage(message);
				refreshMessage(message_pool);
			}
		}
	};

	protected abstract void receiveNewMessage(IMMessage message);

	protected abstract void refreshMessage(List<IMMessage> messages);

	protected List<IMMessage> getMessages() {
		return message_pool;
	}
//发送消息分文：文字（表情）消息，图片消息，语音消息，视频消息，分别对应0,1,2,3
	protected void sendMessage(String messageContent,String infoUrl,int type) {
		try {
			String time = ConnectionUtils.getStringTime();
			Message message = new Message();
			message.setProperty(IMMessage.KEY_TIME, time);
			message.setBody(messageContent);
			chat.sendMessage(message);
			
			IMMessage newMessage = new IMMessage();
			newMessage.setMsgType(1);
			newMessage.setFromSubJid(chat.getParticipant());
			newMessage.setContent(messageContent);
			newMessage.setTime(time);
			newMessage.setChatMode(type);
			newMessage.setInfoUrl(infoUrl);
			System.out.println("newMessageType="+newMessage.getChatMode());
			System.out.println("infoUrl="+infoUrl);
		
			messageDb.saveMessage(newMessage, friends.getJID(), userInfo.getAccount());
			message_pool.add(newMessage);
			MChatManager.message_pool.add(newMessage);
			// 刷新视图
			refreshMessage(message_pool);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 发送文件
	 * */
	protected void sendFile(String jid,File file1,XMPPConnection connection){
		String filePath = file1.getPath();
		String kbs = Byte2KB.bytes2kb(file1.length());
		System.out.println("kbs="+kbs);
		String messageContent = kbs+";"+filePath.substring(filePath.lastIndexOf("/")+1);
		sendMessage(messageContent, filePath, 2);
		
		FileTransferManager manager = new FileTransferManager(connection);
		OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(jid+"/Spark 2.6.3");
		long timeOut = 1000000;
		long sleepMin = 3000;
		long spTime = 0;
		int rs=0;
		if(file1.exists()){ 
			System.out.println("file_size="+file1.length());
		}
		try {
			transfer.sendFile(file1, "send");
			rs = transfer.getStatus().compareTo(FileTransfer.Status.complete);
			spTime = spTime + sleepMin;
			if(spTime>timeOut){
				return;
			}
			try {
				Thread.sleep(sleepMin);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	//接收文件
	/*protected void acceptFile(XMPPConnection connection){
		FileTransferManager manager = new FileTransferManager(connection);
		manager.addFileTransferListener(new RecFileTrancferListener());
		class RecFileTrancferListener implements FileTransferListener{
			public String getFileType(String fileFullName){
				if(fileFullName.contains(".")){
					return "."+fileFullName.split("//.")[1];
				}else{
					return fileFullName;
				}
			}
			@Override
			public void fileTransferRequest(FileTransferRequest request) {
				System.out.println("接收文件开始...");
				final String fileName = request.getFileName();
				long length = request.getFileSize();
				final String fromUser = request.getRequestor().split("/")[0];
				System.out.println("文件大小为："+length+" "+request.getRequestor());
				System.out.println(""+request.getMimeType());
				try{
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("."));
					int result = chooser.showOpendialog(null);
					if(result == JFileChooser.APPROVE_OPTION){
						final File file = chooser.getSelectedFile();
						System.out.println(file.getAbsolutePath());
						new Thread(){
							public void run(){
								try{
									System.out.println("接收文件:"+fileName);
									IncomingFileTransfer accept = request.accept();
									accept.recieveFile(new File(file.getAbsoluteFile()+getFileType(fileName)));
									Message message = new Message();
									message.setFrom(fromUser);
									message.setProperty("REC_SIGN", "SUCCESS");
									message.setBody("["+fromUser+"]发送文件："+fileName+"/r/n"+"存储位置："+file.getAbsolutePath()+getFileType(fileFullName));
									if(Client.isChatExist(fromUser)){
										Client.getChatRoom(fromUser).messageReceiveHandler(message);
									}else{
										ChatFrameThread cft = new CHatFrameThread(fromUser,message);
										cft.start();
									}
								}catch (Exception e){
									e.printStackTrace();
								}
							}
						}.start();
					}else{
						System.out.println("拒绝接受文件："+fileName);
						request.reject();
						Message message = new Message();
						message.setFrom(fromUser);
						message.setBody("拒绝："+fromUser+"发送文件："+fileName);
						message.setProperty("REC_SIGN", "REJECT");
						if(Client.isChateExist(fromUser)){
							Client.getChatRoom(fromUser).messageReceiverHandler(message));
						}else{
							ChatFrameThread cft = new ChatFrameThread(fromUser,message);
							cft.start();
						}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				System.out.println("接收文件结束...");
				}
			}
			
		}
	}*/
	/*protected void sendFile(String filepath,String jid) {
		//String filePath = file1.getPath();
		File file1 = new File(filepath);
		String kbs = Byte2KB.bytes2kb(file1.length());
		System.out.println("kbs="+kbs);
		String messageContent = kbs+";"+filepath.substring(filepath.lastIndexOf("/")+1);
		sendMessage(messageContent, filepath, 2);
		
		final FileTransferManager fileTransferManager = new FileTransferManager(ConnectionUtils.getConnection(AChatActivity.this));
		final OutgoingFileTransfer fileTransfer = fileTransferManager.createOutgoingFileTransfer(jid+"/Spark 2.6.3");				
		final File file = new File(filepath);
		try {
			fileTransfer.sendFile(file, "Sending");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			System.out.println("The file received from: " + prequest.getRequestor());
			file = new File("mnt/sdcard/" + prequest.getFileName());
			request = prequest;
			handler.sendEmptyMessage(5);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String[] args = (String[]) msg.obj;
				//listMsg.add(new Msg(args[0], args[1], args[2], args[3]));
				//adapter.notifyDataSetChanged();
				break;			
			case 2:
				if(pb.getVisibility()==View.GONE){
					pb.setMax(100);
					pb.setProgress(0);
					pb.setVisibility(View.VISIBLE);
				}
				break;
			case 3:
				pb.setVisibility(View.VISIBLE);
				pb.setProgress(msg.arg1);
				break;
			case 4:
				pb.setVisibility(View.GONE);
				break;
			case 5:
				final IncomingFileTransfer infiletransfer = request.accept();
				AlertDialog.Builder builder = new AlertDialog.Builder(AChatActivity.this);
				builder.setTitle("receive file")
						.setCancelable(false)
						.setPositiveButton("Receive",
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
						.setNegativeButton("Reject",
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
	};*/
}
