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
 * �Զ�����ϵ�˽��������
 * ��Ҫʵ��
 * */
public abstract class AContacterActivity extends BaseActivity {

	private ContacterReceiver receiver = null;
	//��Ϣ
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
		// ��������
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
	 * roster�����һ��subcriber
	 * 
	 * @param user
	 */
	protected abstract void addUserReceive(Friends friends);

	/**
	 * rosterɾ����һ��subscriber
	 * 
	 * @param user
	 */
	protected abstract void deleteUserReceive(Friends friends);

	/**
	 * roster�е�һ��subscriber��״̬��Ϣ��Ϣ�����˸ı�
	 * 
	 * @param user
	 */
	protected abstract void changePresenceReceive(Friends friends);

	/**
	 * roster�е�һ��subscriber��Ϣ������
	 * 
	 * @param user
	 */
	protected abstract void updateUserReceive(Friends friends);

	/**
	 * �յ�һ�������������
	 * 
	 * @param subFrom
	 */
	protected abstract void subscripUserReceive(String subFrom);

	/**
	 * �ظ�һ��presence��Ϣ���û�
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
	 * �޸�������ѵ��ǳ�
	 * 
	 * @param user
	 * @param nickname
	 */
	protected void setNickname(Friends friends, String nickname) {
		ContacterManager.setNickname(friends, nickname,
				ConnectionUtils.getConnection());
	}

	/**
	 * ��һ��������ӵ�һ������
	 * 
	 * @param user
	 * @param groupName
	 */
	protected void addUserToGroup(Friends friends, String groupName) {
		ContacterManager.addUserToGroup(friends, groupName,
				ConnectionUtils.getConnection());
	}

	/**
	 * ��һ�����Ѵ�����ɾ��
	 * 
	 * @param user
	 * @param groupName
	 */
	protected void removeUserFromGroup(Friends friends, String groupName) {
		ContacterManager.removeUserFromGroup(friends, groupName,
				ConnectionUtils.getConnection());
	}

	/**
	 * ���һ����ϵ��
	 * 
	 * @param userJid
	 *            ��ϵ��JID
	 * @param nickname
	 *            ��ϵ���ǳ�
	 * @param groups
	 *            ��ϵ����ӵ���Щ��
	 * @throws XMPPException
	 */
	protected void createSubscriber(String userJid, String nickname,
			String[] groups) throws XMPPException {
		ConnectionUtils.getConnection().getRoster()
				.createEntry(userJid, nickname, groups);
	}

	/**
	 * ɾ��һ����ϵ��
	 * 
	 * @param userJid
	 *            ��ϵ�˵�JID
	 * @throws XMPPException
	 */
	protected void removeSubscriber(String userJid) throws XMPPException {
		Roster roast = ConnectionUtils.getConnection().getRoster();
		roast.removeEntry(roast.getEntry(userJid));
	}

	/**
	 * �޸�һ���������
	 * 
	 * @param groupName
	 */
	protected void updateGroupName(String oldGroupName, String newGroupName) {
		ConnectionUtils.getConnection().getRoster().getGroup(oldGroupName)
				.setName(newGroupName);
	}

	/**
	 * ����һ������
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
