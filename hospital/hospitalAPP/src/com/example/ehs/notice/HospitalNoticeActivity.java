package com.example.ehs.notice;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
/*
 * ҽԺ֪ͨ
 * */
public class HospitalNoticeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_notice);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
