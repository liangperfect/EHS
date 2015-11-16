package com.example.ehs.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.ehs.LoginActivity;
import com.example.ehs.R;
import com.example.ehs.model.UserInfo;
/*
 * 设置模块
 * */
public class SettingActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
//	private Button rightBtView;
	private com.example.ehs.widget.My2TextButton aboutAppView;
	private com.example.ehs.widget.My2TextButton resetPswdView;
	private com.example.ehs.widget.My2TextButton logoutView;
	private Button exitView;
	
	UserInfo userInfo=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		userInfo = new UserInfo(this);
		setUpView();
	}

	private void setUpView() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		//rightBtView = (Button)findViewById(R.id.bt_right);
		titleView.setText("设置");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		
		aboutAppView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.about_app);
		aboutAppView.setTextView1Text("应用简介");
		aboutAppView.setTextView2Text(">");
		aboutAppView.setOnClickListener(this);
		
		resetPswdView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.reset_pswd);
		resetPswdView.setTextView1Text("修改密码");
		resetPswdView.setTextView2Text(">");
		resetPswdView.setOnClickListener(this);
		
		logoutView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.logout);
		logoutView.setTextView1Text("注销登陆");
		logoutView.setTextView2Text(">");
		logoutView.setOnClickListener(this);
		
		exitView = (Button)findViewById(R.id.exit_account);
		exitView.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.about_app:
			Intent i1 = new Intent();
			i1.setClass(SettingActivity.this, AboutAppActivity.class);
			startActivity(i1);
			break;
		case R.id.reset_pswd:
			Intent i2 = new Intent();
			i2.setClass(SettingActivity.this, ResetPswdActivity.class);
			startActivity(i2);
			break;
		case R.id.logout:
			new AlertDialog.Builder(SettingActivity.this)
			.setTitle("提示")
			.setMessage("确认要注销用户吗?")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					userInfo.setAutoLogin(false);
					userInfo.setSavePswd(false);
					userInfo.setUserAccount("");
					userInfo.setUserPswd("");
					Intent intent4 = new Intent();
					intent4.setClass(SettingActivity.this, LoginActivity.class);
					startActivity(intent4);
					SettingActivity.this.finish();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
				}
			}).create().show();
			break;
		case R.id.exit_account:
			new AlertDialog.Builder(SettingActivity.this)
			.setTitle("提示")
			.setMessage("确认要退出应用吗?")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					/*userInfo.setAutoLogin(false);
					Intent intent4 = new Intent();
					intent4.setClass(SettingActivity.this, LoginActivity.class);
					startActivity(intent4);
					SettingActivity.this.finish();*/
					System.exit(0);
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
				}
			}).create().show();
			break;
		default:
			break;
		}
	}

}

