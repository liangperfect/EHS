package com.example.ehs.notice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ��ʿվ֪ͨ
 * */
public class NurseNoticeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nurse_notice);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
