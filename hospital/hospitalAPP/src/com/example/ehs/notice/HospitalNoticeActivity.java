package com.example.ehs.notice;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
/*
 * 医院通知
 * */
public class HospitalNoticeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_notice);
		Toast.makeText(this, "未开发！", Toast.LENGTH_LONG).show();
	}

}
