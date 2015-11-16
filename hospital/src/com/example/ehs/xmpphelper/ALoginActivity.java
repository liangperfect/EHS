package com.example.ehs.xmpphelper;


import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.example.ehs.utils.ConnectionUtils;


import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
/*
 * �Զ����½���������
 * ��Ҫʵ��
 * */
public abstract class ALoginActivity extends BaseActivity {
	protected void doLogin(final String username, final String password) {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				if (ConnectionUtils.getConnection(ALoginActivity.this).isAuthenticated()) {
					return true;
				} else {
					try {
						ConnectionUtils.getConnection(ALoginActivity.this).login(username,
								password);
						return ConnectionUtils.getConnection(ALoginActivity.this)
								.isAuthenticated();
					} catch(XMPPException e) {
						System.out.println("��½����");
						return false;
					}
				}
			}

			@Override
			protected void onPostExecute(Boolean result) {
				getProgressDialog().dismiss();
				if (result) {
					initServer();
					doLoginSuccess(username, password);
				} else {
					Toast.makeText(MContext, "��½ʧ��", Toast.LENGTH_SHORT)
							.show();
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				getProgressDialog().setTitle("���Ե�");
				getProgressDialog().setMessage("���ڵ�½...");
				getProgressDialog().show();
				super.onPreExecute();
			}

		}.execute();
	}
	
	/**
	 * ��ʼ���������
	 */
	private void initServer() {
		Intent server = new Intent(MContext, IMContactService.class);
		startService(server);
		Intent chatServer = new Intent(MContext, IMChatService.class);
		startService(chatServer);
	}

	protected abstract void doLoginSuccess(String username, String password);
}
