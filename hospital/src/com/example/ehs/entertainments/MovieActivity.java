package com.example.ehs.entertainments;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * 电影模块
 * */
public class MovieActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);
		
		init();
	}

	private void init() {
		TabHost tabHost = getTabHost();
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("本地电影")  
                .setIndicator("本地电影")  
                .setContent(new Intent(this,LocalMoviesActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("影视库")  
                .setIndicator("影视库")  
                .setContent(new Intent(this,RemoteMoviesActivity.class)));  
	}

}
