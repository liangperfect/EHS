package com.example.ehs.xmpphelper;

import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.example.ehs.model.IMMessage;
import com.example.ehs.utils.ConnectionUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
/*
 * 自定义聊天界面抽象类
 * 需要实现
 * */
public abstract class AChatActivity extends BaseActivity {

	private Chat chat = null;
	private List<IMMessage> message_pool = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String to = getIntent().getStringExtra("to");
		if (to == null)
			return;
		String threadId = MChatManager.chatThreads.get(to);
		chat = ConnectionUtils.getConnection().getChatManager()
				.getThreadChat(threadId);
		if (chat == null) {
			chat = ConnectionUtils.getConnection().getChatManager()
					.createChat(to, null);
		}
		message_pool = MChatManager.getMessages(to);
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
				IMMessage message = intent
						.getParcelableExtra(IMMessage.IMMESSAGE_KEY);
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

	protected void sendMessage(String messageContent) {
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
			message_pool.add(newMessage);
			MChatManager.message_pool.add(newMessage);
			// 刷新视图
			refreshMessage(message_pool);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

}
