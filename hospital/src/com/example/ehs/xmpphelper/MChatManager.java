package com.example.ehs.xmpphelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.example.ehs.db.IMMessageDb;
import com.example.ehs.model.IMMessage;
/*
 * 我的聊天管理
 * */

public class MChatManager {
	/**
	 * 保存左右的聊天记录，退出后清除
	 */
	public static List<IMMessage> message_pool = new ArrayList<IMMessage>();

	/**
	 * 保存subscriber的threadId
	 */
	public static Map<String, String> chatThreads = new HashMap<String, String>();
	/**
	 * 获得当前这个subscriber的聊天记录
	 * 
	 * @param userJid
	 * @return
	 */
	public static List<IMMessage> getMessages(String subJid) {
		List<IMMessage> messages = new ArrayList<IMMessage>();

		if (subJid == null)
			return messages;

		for (IMMessage message : message_pool) {
			if (subJid.equals(message.getFromSubJid()))
				messages.add(message);
		}
		return messages;
	}
	/** 
	 * 创建房间 
	 *  
	 * @param roomName  房间名称 
	 */  
	public MultiUserChat createRoom(XMPPConnection connection, String user, String roomName,String password) {  
	    if (connection == null)  
	        return null;  
	  
	    MultiUserChat muc = null;  
	    try {  
	        // 创建一个MultiUserChat  
	        muc = new MultiUserChat(connection, roomName + "@conference."  
	                + connection.getServiceName());  
	        // 创建聊天室  
	        muc.create(roomName);  
	        // 获得聊天室的配置表单  
	        Form form = muc.getConfigurationForm();  
	        // 根据原始表单创建一个要提交的新表单。  
	        Form submitForm = form.createAnswerForm();  
	        // 向要提交的表单添加默认答复  
	        for (Iterator<FormField> fields = form.getFields(); fields  
	                .hasNext();) {  
	            FormField field = (FormField) fields.next();  
	            if (!FormField.TYPE_HIDDEN.equals(field.getType())  
	                    && field.getVariable() != null) {  
	                // 设置默认值作为答复  
	                submitForm.setDefaultAnswer(field.getVariable());  
	            }  
	        }  
	        // 设置聊天室的新拥有者  
	        List<String> owners = new ArrayList<String>();  
	        owners.add(connection.getUser());// 用户JID  
	        submitForm.setAnswer("muc#roomconfig_roomowners", owners);  
	        // 设置聊天室是持久聊天室，即将要被保存下来  
	        submitForm.setAnswer("muc#roomconfig_persistentroom", true);  
	        // 房间仅对成员开放  
	        submitForm.setAnswer("muc#roomconfig_membersonly", false);  
	        // 允许占有者邀请其他人  
	        submitForm.setAnswer("muc#roomconfig_allowinvites", true);  
	        if (!password.equals("")) {  
	            // 进入是否需要密码  
	            submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",  
	                    true);  
	            // 设置进入密码  
	            submitForm.setAnswer("muc#roomconfig_roomsecret", password);  
	        }  
	        // 能够发现占有者真实 JID 的角色  
	        // submitForm.setAnswer("muc#roomconfig_whois", "anyone");  
	        // 登录房间对话  
	        submitForm.setAnswer("muc#roomconfig_enablelogging", true);  
	        // 仅允许注册的昵称登录  
	        submitForm.setAnswer("x-muc#roomconfig_reservednick", true);  
	        // 允许使用者修改昵称  
	        submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);  
	        // 允许用户注册房间  
	        submitForm.setAnswer("x-muc#roomconfig_registration", false);  
	        // 发送已完成的表单（有默认值）到服务器来配置聊天室  
	        muc.sendConfigurationForm(submitForm);  
	    } catch (XMPPException e) {  
	        e.printStackTrace();  
	        return null;  
	    }  
	    return muc;  
	}

/** 
	 * 加入会议室 
	 *  
	 * @param user 
	 *            昵称 
	 * @param password 
	 *            会议室密码 
	 * @param roomsName 
	 *            会议室名 
	 */  
	public MultiUserChat joinMultiUserChat(XMPPConnection connection ,String user, String roomsName,  
	        String password) {  
	    if (connection == null)  
	        return null;  
	    try {  
	        // 使用XMPPConnection创建一个MultiUserChat窗口  
	        MultiUserChat muc = new MultiUserChat(connection, roomsName  
	                + "@conference." + connection.getServiceName());  
	        // 聊天室服务将会决定要接受的历史记录数量  
	        DiscussionHistory history = new DiscussionHistory();  
	        history.setMaxChars(0);  
	        // history.setSince(new Date());  
	        // 用户加入聊天室  
	        muc.join(user, password, history,  
	                SmackConfiguration.getPacketReplyTimeout());  
	        System.out.println("MultiUserChat----会议室【"+roomsName+"】加入成功........");  
	        return muc;  
	    } catch (XMPPException e) {  
	        e.printStackTrace();  
	        System.out.println("MultiUserChat----会议室【"+roomsName+"】加入失败........");  
	        return null;  
	    }  
	}  	

}
