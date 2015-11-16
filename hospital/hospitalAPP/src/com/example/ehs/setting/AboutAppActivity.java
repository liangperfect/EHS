package com.example.ehs.setting;

import java.io.InputStream;

import com.example.ehs.R;
import com.example.ehs.widget.TextReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/*
 * 应用介绍界面
 * */
public class AboutAppActivity extends Activity implements OnClickListener{
	private Button bt_left;
	private TextView titleTextview;
	private TextView about_app;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_app);
		setUpView();
	}


	private void setUpView() {
		bt_left = (Button)findViewById(R.id.bt_left);
		bt_left.setVisibility(View.VISIBLE);
		bt_left.setOnClickListener(this);
		
		titleTextview = (TextView)findViewById(R.id.title);
		titleTextview.setText("应用简介");
		
		about_app = (TextView)findViewById(R.id.app_intruduce);
		InputStream inputStream = getResources().openRawResource(R.raw.about_app);
		String str = TextReader.getString(inputStream);
		System.out.println("str="+str);
		about_app.setText("111"+str);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		default:
			break;
		}
	}
	
}

