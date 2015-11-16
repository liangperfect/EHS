package com.example.ehs.utils;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.*;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.InvitationRejectionListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInfo;

import android.os.Environment;
import android.util.Log;


/**
 * <p>Title: XmppTool.java</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: DMX Technologies</p>
 * 
 * @author Jeason
 * @version %A%, %B%
 * @since JDK1.6
 * @CheckItem
 */
public class XmppTool {
	private static final String TAG = "XmppTool";

	private static XMPPConnection con = null;
	
	private static void openConnection(String openfireIPAdress, int openfirePort) {
		try {
			ConnectionConfiguration connConfig = new ConnectionConfiguration(openfireIPAdress, openfirePort);
			connConfig.setSASLAuthenticationEnabled(false);
//			connConfig.setTruststorePath("/system/etc/security/cacerts.bks");
//			connConfig.setTruststorePassword("changeit");
//			connConfig.setTruststoreType("bks");
//			connConfig.setDebuggerEnabled(true);
			
			connConfig.setSendPresence(false);
			con = new XMPPConnection(connConfig);
			
			con.connect();
		} catch (XMPPException xe) {
			xe.printStackTrace();
			Log.e(TAG, xe.toString());
		}
	}

	/**
	 * @param openfireIPAdress
	 * @param openfirePort
	 * @return
	 */
	private static XMPPConnection getConnection(String openfireIPAdress, int openfirePort) {
		if (con == null) {
			openConnection(openfireIPAdress, openfirePort);
		}
		return con;
	}
	
	/**
	 */
	public static void closeConnection() {
		if (con != null && con.isConnected()) {
			con.disconnect();
			con = null;
		}
	}
	
	/**
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean loginOpenfire(String openfireIP, int openfirePort,
			String username, String password) {
		try {
			getConnection(openfireIP, openfirePort);
			Thread.sleep(1000 * 3);
			
			if (con.isConnected()) {
				String currentUserJID = con.getUser();
				Log.i(TAG, currentUserJID + "");
				if (currentUserJID != null && currentUserJID.startsWith(username)) {
					return true;
				}else {
					con.disconnect();
				}
				con.login(username, password, "aSmack");
//				con.login(username, password);
				getOfflineMsg();
				Presence presence = new Presence(Presence.Type.available);
				con.sendPacket(presence);
				return true;
			}
		} catch (XMPPException e) {
			XmppTool.closeConnection();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param usernameJID
	 * @return
	 */
	public static boolean decideIsOnlineByJID(String usernameJID){
		Roster roster = con.getRoster();
		Presence presence = roster.getPresence(usernameJID);
		
		if (presence.getType() == Presence.Type.available) {
			return true;
		}
		return false;
	}
	
	/**
	 */
	public static void addSignleChatListener(){
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		con.addPacketListener(new ChatPacketListener(), filter);
	}
	
	/**
	 */
	public static void addGroupChatListener(){
		PacketFilter filter = new MessageTypeFilter(Message.Type.groupchat);
		con.addPacketListener(new ChatPacketListener(), filter);
	}
	
	/**
	 */
	public static void addFileListener() {
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(con);
				if (sdm == null){
					sdm = new ServiceDiscoveryManager(con);
				}
				sdm.addFeature("http://jabber.org/protocol/disco#info");
				sdm.addFeature("jabber:iq:privacy");
				
				final FileTransferManager managerListner = new FileTransferManager(con);
				FileTransferNegotiator.setServiceEnabled(con, true);
				
				managerListner.addFileTransferListener(new InceptFileListener());
			}
		}.start();
	}
	
	/**
	 */
	public static void addMultiInvicationListener(){
		MultiUserChat.addInvitationListener(con, new MulitiChatInvitationListener());
	}
	
	/**
	 */
	public static void addRefuseInviterListener(MultiUserChat muc){
		muc.addInvitationRejectionListener(new RefuseInviterListener());
	}
	
	/**
	 * @param chatRoomJID
	 * @param inviter
	 * @param reason
	 */
	public static void refuse(String chatRoomJID, String inviter, String reason){
		MultiUserChat.decline(con, chatRoomJID, inviter, reason);
	}
	
	/**
	 * @param withWhoForchat
	 * @return
	 */
	public static Chat joinSignleChat(String withWhoForchat){
		return con.getChatManager().createChat(withWhoForchat, null);
	}
	
	/**
	 * @param witchRoomForMultichat
	 * @return
	 */
	public static MultiUserChat joinMultiChat(String witchRoomForMultichat, String selfRemark)throws XMPPException{
		MultiUserChat multiUserChat = new MultiUserChat(con, witchRoomForMultichat);
		if (!multiUserChat.isJoined()) {
			multiUserChat.join(selfRemark);
		}
		
		return multiUserChat;
	}
	
	/**
	 * @return
	 * @throws XMPPException
	 */
	public static MultiUserChat createMultiChatRoom(String chatRoomJID, String chatRoomName) throws XMPPException {
//		MultiUserChat muc = new MultiUserChat(con, "myroom@conference.jabber.org");
		MultiUserChat muc = new MultiUserChat(con, chatRoomJID);
//		muc.create("testbot");
		muc.create(chatRoomName);
//		muc.create(Constants.vCard.getNickName().toString());
		
//		muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));
		Form form = muc.getConfigurationForm();
		Form submitForm = form.createAnswerForm();
		for (Iterator fields = form.getFields(); fields.hasNext();) {
			FormField field = (FormField) fields.next();
			if (!FormField.TYPE_HIDDEN.equals(field.getType())
					&& field.getVariable() != null) {
				submitForm.setDefaultAnswer(field.getVariable());
			}
		}
		// List owners = new ArrayList();
		// owners.add("liaonaibo2\\40slook.cc");
		// owners.add("liaonaibo1\\40slook.cc");
		// submitForm.setAnswer("muc#roomconfig_roomowners", owners);
		submitForm.setAnswer("muc#roomconfig_persistentroom", true);
		submitForm.setAnswer("muc#roomconfig_membersonly", false);
		submitForm.setAnswer("muc#roomconfig_allowinvites", true);
		// submitForm.setAnswer("muc#roomconfig_whois", "anyone");
		submitForm.setAnswer("muc#roomconfig_enablelogging", true);
		submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
		submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
		submitForm.setAnswer("x-muc#roomconfig_registration", false);
		muc.sendConfigurationForm(submitForm);

		return muc;
	}
	
	/**
	 * @param muc
	 * @param userID
	 * @param inviteMsgStr
	 */
	public static void invitationUser(MultiUserChat muc, String userID, String inviteMsgStr){
		muc.invite(userID, inviteMsgStr);
	}
	
	/**
	 * @param muc
	 * @param userJIDS
	 * @return
	 */
	/*public static boolean grantAdmins(MultiUserChat muc, Collection<String> userJIDS){
		try {
			muc.grantAdmin(userJIDS);
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}*/
	
	/**
	 * @param muc
	 * @param userJIDS
	 * @return
	 */
	/*public static boolean grantAdmin(MultiUserChat muc, String userJID){
		try {
			muc.grantAdmin(userJID);
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}*/
	
	/**
	 * @param chat
	 * @param chatContent
	 * @return
	 */
	public static boolean sendSignleChat(Chat chat, String chatContent){
		try {
			chat.sendMessage(chatContent);
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param chat
	 * @param chatContent
	 * @return
	 */
	public static boolean sendMultiChat(MultiUserChat multiUserChat, String chatContent){
		try {
			multiUserChat.sendMessage(chatContent);
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param xmlString
	 * @return
	 */
	public static boolean sendBroadcast(String xmlString){
		if (con.isConnected() && con.getUser() != null) {			
			Message message = new Message("all@broadcast.jeasonliu");
//			Message message = new Message(null);
			message.setBody(xmlString);  
			con.sendPacket(message);
			return true;
		}
		return false;
	}
	
	public static void addBroadcastListener(){
		PacketFilter filter = new MessageTypeFilter(Message.Type.normal);
		con.addPacketListener(new ChatPacketListener(), filter);
	}
	
	/**
	 */
	public static void addConnectListener(){
		con.addConnectionListener(new ConnectListener());
	}	
	
	/**
	 * @param muc
	 * @param memberNickName
	 * @param reason
	 * @return
	 */
	/*public static boolean kicksGroupMember(MultiUserChat muc, String memberNickName, String reason){
		try {
			muc.kickParticipant(memberNickName, reason);
			return true;
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}*/
	
	/**
	 * @param muc
	 * @return
	 */
	/*public static boolean leaveFromChatRoom(MultiUserChat muc){
		muc.leave();
		return true;
	}*/
	
	public static void getOfflineMsg(){
		OfflineMessageManager offlineManager = new OfflineMessageManager(con);
//	            Client.getConnection());  
	    try {  
	        Iterator<Message> it = offlineManager.getMessages();  

	        System.out.println(offlineManager.supportsFlexibleRetrieval());  
	        System.out.println("离线消息数" + offlineManager.getMessageCount());  
	          
	        while (it.hasNext()) {
	        	Message message = it.next();  
//	            System.out.println("鏀跺埌绂荤嚎娑堟伅, Received from 銆� + message.getFrom()+ "銆�message: " + message.getBody());  
//	            String fromUser = message.getFrom().split("/")[0];  
//
//	            if(offlineMsgs.containsKey(fromUser))  
//	            {  
//	                offlineMsgs.get(fromUser).add(message);  
//	            }else{  
//	                ArrayList<Message> temp = new ArrayList<Message>();  
//	                temp.add(message);  
//	                offlineMsgs.put(fromUser, temp);  
//	            }  
	        }  

//	        Set<String> keys = offlineMsgs.keySet();  
//	        Iterator<String> offIt = keys.iterator();  
//	        while(offIt.hasNext())  
//	        {  
//	            String key = offIt.next();  
//	            ArrayList<Message> ms = offlineMsgs.get(key);  
//	            TelFrame tel = new TelFrame(key);  
//	            ChatFrameThread cft = new ChatFrameThread(key, null);  
//	            cft.setTel(tel);  
//	            cft.start();  
//	            for (int i = 0; i < ms.size(); i++) {  
//	                tel.messageReceiveHandler(ms.get(i));  
//	            }  
//	        }  
	          
	          
	        offlineManager.deleteMessages();
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}
	
	/**
	 * @param message
	 * @return
	 */
	private static Date getStampOfDelayTime(Message message) {
		if (message.getExtensions().toArray().length > 1) {
			return ((DelayInfo) message.getExtensions().toArray()[1])
					.getStamp();
		}
		return null;
	}
	
	public static boolean isAuthenticated() {
		if ( null == con)
			return false;
		return con.isAuthenticated();
	}
	
	/**
	 * @param userId
	 * @param conn
	 * @param file
	 * @throws Exception
	 */
	public static void sendFile(String userId, File file)
			throws Exception {
		FileTransferManager manager = new FileTransferManager(con);
		OutgoingFileTransfer transfer = manager
				.createOutgoingFileTransfer(userId);
		if (file.exists()) {
			transfer.sendFile(file, "data backup");
		}

		
		while (!transfer.isDone()) {
//			Thread.sleep(1000*2);
			if (transfer.getStatus() == OutgoingFileTransfer.Status.in_progress) {
			}
		}

		Log.i(TAG, transfer.getStatus()+"**2");// 瀹屾垚锛圕omplete锛�鎷掔粷锛圧efused锛�
	}
	
	private static class InceptFileListener implements FileTransferListener {

		@Override
		public void fileTransferRequest(FileTransferRequest request) {
			// TODO Auto-generated method stub
			Log.i(TAG, "from=" + request.getRequestor());
//			request.reject();
			final IncomingFileTransfer transfer = request.accept();
			
			File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" +  request.getFileName());
			try {
				if (!file.exists()) {
					Log.i(TAG, file.getPath());
					file.createNewFile();
				}
				transfer.recieveFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Timer timer = new Timer();
			TimerTask updateProgessBar = new TimerTask() {
				public void run() {
					if ((transfer.getAmountWritten() >= transfer.getFileSize())
							|| (transfer.getStatus() == FileTransfer.Status.error)
							|| (transfer.getStatus() == FileTransfer.Status.refused)
							|| (transfer.getStatus() == FileTransfer.Status.cancelled)
							|| (transfer.getStatus() == FileTransfer.Status.complete)) {
						cancel();
					} else {
						long p = transfer.getAmountWritten() * 100L / transfer.getFileSize();
					}
				}
			};
			timer.scheduleAtFixedRate(updateProgessBar, 100L, 100L);
		}
	}
	
	private static class ConnectListener implements ConnectionListener{

		@Override
		public void connectionClosed() {
		}

		@Override
		public void connectionClosedOnError(Exception arg0) {
		}

		@Override
		public void reconnectingIn(int arg0) {
		}

		@Override
		public void reconnectionFailed(Exception arg0) {
		}

		@Override
		public void reconnectionSuccessful() {
		}
		
	}
	
	private static class ChatPacketListener implements PacketListener {
		@Override
		public void processPacket(Packet packet) {
			// TODO Auto-generated method stub
			Message message = (Message) packet;
			if (message.getBody() != null) {
				//ChatBean chatBean = new ChatBean();
			}

		}
	}
	
	private static class RefuseInviterListener implements InvitationRejectionListener{

		@Override
		public void invitationDeclined(String inviree, String reason) {
		}
		
	}
	
	private static class MulitiChatInvitationListener implements InvitationListener{
		@Override
		public void invitationReceived(Connection con, String room,
				String invitationer, String invitationStr, String arg4, Message message) {
			
		}
	}
}
