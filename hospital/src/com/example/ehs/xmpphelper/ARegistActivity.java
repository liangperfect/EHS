package com.example.ehs.xmpphelper;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

import com.example.ehs.utils.ConnectionUtils;

import android.os.AsyncTask;
import android.widget.Toast;
/*
 * 自定义注册界面抽象类
 * 需要实现
 * */

public abstract class ARegistActivity extends BaseActivity {

	protected void doRegist(final String username, final String password) {
		new AsyncTask<Void, Void, IQ>() {

			@Override
			protected IQ doInBackground(Void... params) {
				IQ result = null;
				Registration reg = new Registration();
				reg.setType(IQ.Type.SET);
				reg.setTo(ConnectionUtils.getConnection(ARegistActivity.this).getServiceName());
				reg.setUsername(username);
				reg.setPassword(password);
				reg.addAttribute("android", "geolo_createUser_android");
				PacketFilter filter = new AndFilter(new PacketIDFilter(
						reg.getPacketID()), new PacketTypeFilter(IQ.class));

				XMPPConnection regConnection = ConnectionUtils
						.getRegConnection();
				try {
					regConnection.connect();
				} catch (XMPPException e) {
					return null;
				}

				PacketCollector collector = regConnection
						.createPacketCollector(filter);
				regConnection.sendPacket(reg);
				result = (IQ) collector.nextResult(SmackConfiguration
						.getPacketReplyTimeout());
				collector.cancel();// 停止请求results（是否成功的结果）
				regConnection.disconnect();
				return result;
			}

			@Override
			protected void onPostExecute(IQ result) {
				getProgressDialog().dismiss();
				if (result == null) {
					Toast.makeText(getApplicationContext(), "服务器没有返回结果或者连接网络失败...",
							Toast.LENGTH_SHORT).show();
				} else if (result.getType() == IQ.Type.ERROR) {
					if (result.getError().toString()
							.equalsIgnoreCase("conflict(409)")) {
						Toast.makeText(getApplicationContext(), "这个账号已经存在",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "注册失败",
								Toast.LENGTH_SHORT).show();
					}
				} else if (result.getType() == IQ.Type.RESULT) {
					doLoginSuccess(username, password);
				}
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				getProgressDialog().setTitle("请稍等");
				getProgressDialog().setMessage("正在注册...");
				getProgressDialog().show();
				super.onPreExecute();
			}

		}.execute();
	}

	protected abstract void doLoginSuccess(String username, String password);

}
