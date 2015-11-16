package com.example.ehs.xmpphelper;


import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.example.ehs.utils.ConnectionUtils;


import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
/*
 * 自定义登陆界面抽象类
 * 需要实现
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
						System.out.println("登陆出错");
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
					Toast.makeText(MContext, "登陆失败", Toast.LENGTH_SHORT)
							.show();
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				getProgressDialog().setTitle("请稍等");
				getProgressDialog().setMessage("正在登陆...");
				getProgressDialog().show();
				super.onPreExecute();
			}

		}.execute();
	}
	
	/**
	 * 初始化各项服务
	 */
	private void initServer() {
		Intent server = new Intent(MContext, IMContactService.class);
		startService(server);
		Intent chatServer = new Intent(MContext, IMChatService.class);
		startService(chatServer);
	}

	protected abstract void doLoginSuccess(String username, String password);
}
