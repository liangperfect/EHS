package com.example.ehs.entertainments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ��Ӱģ��
 * */
public class MovieActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
