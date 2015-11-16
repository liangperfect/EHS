package com.example.ehs.xmpphelper;

import com.example.ehs.utils.ConnectionUtils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * @author zhanghaitao
 * @date 2011-7-7
 * @version 1.0
 */
public class fileListenerService extends Service {
	private static XmppFileManager _xmppFileMgr;

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("info","Service Bind Success");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		_xmppFileMgr = new XmppFileManager(getBaseContext());
		_xmppFileMgr.initialize(ConnectionUtils.getConnection(this));
		System.out.println("-----fileservice start");
		return START_STICKY;
	}
	

}
