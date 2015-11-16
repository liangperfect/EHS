package com.example.ehs.xmpphelper;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.example.ehs.im.ChatActivity;
import com.example.ehs.model.Friends;
import com.example.ehs.model.IMMessage;
import com.example.ehs.utils.ConnectionUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
/*
 * 自定义联系人界面抽象类
 * 需要实现
 * */
public abstract class AContacterActivity extends BaseActivity {

	private ContacterReceiver receiver = null;
	//消息
	IMMessage message=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		receiver = new ContacterReceiver();
	}

	@Override
	protected void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ROSTER_ADDED);
		filter.addAction(Constant.ROSTER_DELETED);
		filter.addAction(Constant.ROSTER_PRESENCE_CHANGED);
		filter.addAction(Constant.ROSTER_UPDATED);
		// 好友请求
		filter.addAction(Constant.ROSTER_SUBSCRIPTION);
		registerReceiver(receiver, filter);
		super.onResume();
	}

	private class ContacterReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			Friends friends = intent.getParcelableExtra(Friends.userKey);

			if (Constant.ROSTER_ADDED.equals(action)) {
				addUserReceive(friends);
			}

			else if (Constant.ROSTER_DELETED.equals(action)) {
				deleteUserReceive(friends);
			}

			else if (Constant.ROSTER_PRESENCE_CHANGED.equals(action)) {
				changePresenceReceive(friends);
			}

			else if (Constant.ROSTER_UPDATED.equals(action)) {
				updateUserReceive(friends);
			}

			else if (Constant.ROSTER_SUBSCRIPTION.equals(action)) {
				subscripUserReceive(intent
						.getStringExtra(Constant.ROSTER_SUB_FROM));
			}

		}
	}

	/**
	 * roster添加了一个subcriber
	 * 
	 * @param user
	 */
	protected abstract void addUserReceive(Friends friends);

	/**
	 * roster删除了一个subscriber
	 * 
	 * @param user
	 */
	protected abstract void deleteUserReceive(Friends friends);

	/**
	 * roster中的一个subscriber的状态信息信息发生了改变
	 * 
	 * @param user
	 */
	protected abstract void changePresenceReceive(Friends friends);

	/**
	 * roster中的一个subscriber信息更新了
	 * 
	 * @param user
	 */
	protected abstract void updateUserReceive(Friends friends);

	/**
	 * 收到一个好友添加请求
	 * 
	 * @param subFrom
	 */
	protected abstract void subscripUserReceive(String subFrom);

	/**
	 * 回复一个presence信息给用户
	 * 
	 * @param type
	 * @param to
	 */
	protected void sendSubscribe(Presence.Type type, String to) {
		Presence presence = new Presence(type);
		presence.setTo(to);
		ConnectionUtils.getConnection().sendPacket(presence);
	}

	/**
	 * 修改这个好友的昵称
	 * 
	 * @param user
	 * @param nickname
	 */
	protected void setNickname(Friends friends, String nickname) {
		ContacterManager.setNickname(friends, nickname,
				ConnectionUtils.getConnection());
	}

	/**
	 * 把一个好友添加到一个组中
	 * 
	 * @param user
	 * @param groupName
	 */
	protected void addUserToGroup(Friends friends, String groupName) {
		ContacterManager.addUserToGroup(friends, groupName,
				ConnectionUtils.getConnection());
	}

	/**
	 * 把一个好友从组中删除
	 * 
	 * @param user
	 * @param groupName
	 */
	protected void removeUserFromGroup(Friends friends, String groupName) {
		ContacterManager.removeUserFromGroup(friends, groupName,
				ConnectionUtils.getConnection());
	}

	/**
	 * 添加一个联系人
	 * 
	 * @param userJid
	 *            联系人JID
	 * @param nickname
	 *            联系人昵称
	 * @param groups
	 *            联系人添加到哪些组
	 * @throws XMPPException
	 */
	protected void createSubscriber(String userJid, String nickname,
			String[] groups) throws XMPPException {
		ConnectionUtils.getConnection().getRoster()
				.createEntry(userJid, nickname, groups);
	}

	/**
	 * 删除一个联系人
	 * 
	 * @param userJid
	 *            联系人的JID
	 * @throws XMPPException
	 */
	protected void removeSubscriber(String userJid) throws XMPPException {
		Roster roast = ConnectionUtils.getConnection().getRoster();
		roast.removeEntry(roast.getEntry(userJid));
	}

	/**
	 * 修改一个组的组名
	 * 
	 * @param groupName
	 */
	protected void updateGroupName(String oldGroupName, String newGroupName) {
		ConnectionUtils.getConnection().getRoster().getGroup(oldGroupName)
				.setName(newGroupName);
	}

	/**
	 * 创建一个聊天
	 * 
	 * @param user
	 */
	protected void createChat(Friends friends) {
		Intent intent = new Intent(MContext, ChatActivity.class);
		message = new IMMessage();
		message.setToSubJid(friends.getJID());
		message.setToSubName(friends.getName());
		Bundle bd = new Bundle();
		bd.putString("style","to");
		bd.putParcelable("recentMessage", message);
		intent.putExtra("info", bd);
		startActivity(intent);
	}

}
