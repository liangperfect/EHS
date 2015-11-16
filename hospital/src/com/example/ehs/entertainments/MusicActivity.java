package com.example.ehs.entertainments;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * 音乐模块
 * */
public class MusicActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music);
		
		init();
	}

	private void init() {
		TabHost tabHost = getTabHost();
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("本地音乐")  
                .setIndicator("本地音乐")  
                .setContent(new Intent(this,LocalMusicActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("音乐库")  
                .setIndicator("音乐库")  
                .setContent(new Intent(this,RemoteMusicActivity.class)));  
	}

}
