package com.example.ehs.entertainments;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ��Ӱģ��
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
		//3����3�����ϵ�д��
		tabHost.addTab(tabHost.newTabSpec("���ص�Ӱ")  
                .setIndicator("���ص�Ӱ")  
                .setContent(new Intent(this,LocalMoviesActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("Ӱ�ӿ�")  
                .setIndicator("Ӱ�ӿ�")  
                .setContent(new Intent(this,RemoteMoviesActivity.class)));  
	}

}
