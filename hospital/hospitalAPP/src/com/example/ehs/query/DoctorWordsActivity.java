package com.example.ehs.query;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ��ѯģ��----ҽ����ʾģ�飨�ɲο�AboutAppActivityģ�飩
 * */
public class DoctorWordsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_words_query);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
