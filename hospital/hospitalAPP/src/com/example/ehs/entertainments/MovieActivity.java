package com.example.ehs.entertainments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * 电影模块
 * */
public class MovieActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);
		Toast.makeText(this, "未开发！", Toast.LENGTH_LONG).show();
	}

}
