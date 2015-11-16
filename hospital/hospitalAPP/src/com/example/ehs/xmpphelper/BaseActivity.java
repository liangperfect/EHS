package com.example.ehs.xmpphelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
/*
 * 自定义含有ProgressDialog的界面
 * 
 * */
public class BaseActivity extends Activity {

	protected Context MContext = null;
	private ProgressDialog pg = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MContext = this;
		pg = new ProgressDialog(MContext);
	}
	
	protected ProgressDialog getProgressDialog() {
		return pg;
	}

}
