package com.example.ehs.entertainments;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
/*
 * ��Ϸģ��
 * */
public class GamesActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.games);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
