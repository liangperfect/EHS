package com.example.ehs.hospital;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ���ҽ���ģ��
 * */
public class AboutSubjectActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_subject);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
