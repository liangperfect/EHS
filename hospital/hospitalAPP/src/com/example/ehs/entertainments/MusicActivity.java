package com.example.ehs.entertainments;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ÒôÀÖÄ£¿é
 * */
public class MusicActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music);
		Toast.makeText(this, "Î´¿ª·¢£¡", Toast.LENGTH_LONG).show();
	}

}
