package com.example.ehs.entertainments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ehs.R;

public class RemoteMoviesActivity extends Activity{
	private TextView tv; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_movies);
		init();
	}

	private void init() {
		tv = (TextView)findViewById(R.id.r_movies);
		tv.setText("访问远程服务器获得音乐信息！");
		
	}

}
