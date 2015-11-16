package com.example.ehs.setting;


import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.example.ehs.MainActivity;
import com.example.ehs.R;
import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.ConnectionUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 重设密码模块
 * 基本框图已经搭建好，与服务器的交互还未做
 * */
public class ResetPswdActivity extends Activity implements OnClickListener{
	//title
	private TextView titleTextView;
	//right button
	private Button btRight;
		
	//screen
	private EditText oldPswdView;
	private EditText newPswdView;
	private EditText verifyPswdView;
	//private Button forgetPswdView;
	
	//bottom imagine
	private ImageView homeView;
	private ImageView setView;
	private ImageView backView;
		
	// Values
	private String oldPswd;
	private String newPswd;
	private String verifyPswd;
	
	private UserInfo userInfo=null;
	private String userPass=null;
	private String userID=null;
	
	private String tag="ResetPswdActivity";
	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_pswd);
		
		dialog = new ProgressDialog(this);
		
		userInfo = new UserInfo(this);
		userPass = userInfo.getPassword();
		userID = userInfo.getAccount();
		setupView();
	}

	private void setupView() {
		titleTextView = (TextView)this.findViewById(R.id.title);
		titleTextView.setText("修改密码");
			
		Button btnBack = (Button)this.findViewById(R.id.bt_left);
		btnBack.setOnClickListener(this);
		btnBack.setBackgroundResource(R.drawable.btn_back);
		btnBack.setVisibility(View.VISIBLE);
			
		btRight = (Button)this.findViewById(R.id.bt_right);
		btRight.setVisibility(View.VISIBLE);
		btRight.setBackgroundResource(R.drawable.btn_ok);
		btRight.setOnClickListener(this);
			
		oldPswdView = (EditText)findViewById(R.id.old_pswd);
		newPswdView = (EditText)findViewById(R.id.new_pswd);
		verifyPswdView = (EditText)findViewById(R.id.de_new_pswd);
			
	//	forgetPswdView = (Button)findViewById(R.id.re_forget_pswd);
	//	forgetPswdView.setOnClickListener(this);
		
//		homeView = (ImageView)findViewById(R.id.reset_pswd_home);
//		homeView.setOnClickListener(this);
//		setView = (ImageView)findViewById(R.id.reset_pswd_setting);
//		setView.setOnClickListener(this);
//		backView = (ImageView)findViewById(R.id.reset_pswd_back);
//		backView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;			
		case R.id.bt_right:{
			attemptSave();
			break;
		}
		//case R.id.re_forget_pswd:
		//	Intent intent = new Intent(this,SearchPswdActivity.class);
		//	startActivity(intent);
		//	break;	
//		case R.id.reset_pswd_home:
//			Intent i = new Intent(ResetPswdActivity.this,HomeMainActivity.class);
//			startActivity(i);
//			break;
//		case R.id.reset_pswd_setting:
//			Intent i1 = new Intent(ResetPswdActivity.this,SettingActivity.class);
//			startActivity(i1);
//			break;
//		case R.id.reset_pswd_back:
//			ResetPswdActivity.this.finish();
//			break;
		default:
			break;
		}
	}
	private void attemptSave() {
	// Reset errors.
		oldPswdView.setError(null);
		newPswdView.setError(null);
		verifyPswdView.setError(null);

		// Store values at the time of the next attempt.
		oldPswd = oldPswdView.getText().toString();
		newPswd = newPswdView.getText().toString();
		verifyPswd = verifyPswdView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		//check if new password is equal to verify password
		if (TextUtils.isEmpty(verifyPswd)) {
			verifyPswdView.setError(getString(R.string.error_field_required));
			focusView = verifyPswdView;
			cancel = true;
		} else if (!verifyPswd.equals(newPswd)) {
			verifyPswdView.setError(getString(R.string.error_invalid_password_again));
			focusView = verifyPswdView;
			cancel = true;
		}
		// Check for a valid new password.
		if (TextUtils.isEmpty(newPswd)) {
			newPswdView.setError(getString(R.string.error_field_required));
			focusView = newPswdView;
			cancel = true;
		} else if (newPswd.length()<4) {
			newPswdView.setError(getString(R.string.error_invalid_password));
			focusView = newPswdView;
			cancel = true;
		}
		// Check for a valid oldPassword.
		if (TextUtils.isEmpty(oldPswd)) {
			oldPswdView.setError(getString(R.string.error_field_required));
			focusView = oldPswdView;
			cancel = true;
		} else if (oldPswd.length() < 3) {
			oldPswdView.setError(getString(R.string.error_invalid_password));
			focusView = oldPswdView;
			cancel = true;
		}
				
		if (cancel) {
			focusView.requestFocus();
			} else {
				System.out.println("userPass="+userPass);
				if(oldPswd.equals(userPass)){
					if(newPswd.equals(verifyPswd)){
						boolean isChanged = changePswd(newPswd);
						if(isChanged){
							Toast.makeText(ResetPswdActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
							this.finish();
						}else{
							Toast.makeText(ResetPswdActivity.this, "密码修改失败！", Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(ResetPswdActivity.this, "两次输入的新密码不一致！", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(ResetPswdActivity.this, "当前密码输入错误！", Toast.LENGTH_SHORT).show();
				}				
			}
	}
	private boolean changePswd(String newPswd) {
		XMPPConnection connection = ConnectionUtils.getConnection(ResetPswdActivity.this);
		if(connection == null)
			return false;
		try{
			connection.getAccountManager().changePassword(newPswd);
			return true;
		}catch(XMPPException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 0, 0, "主页");
		menu.add(1, 1, 0, "设置");
		menu.add(1, 2, 0, "返回");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent i = new Intent(ResetPswdActivity.this,MainActivity.class);
			startActivity(i);
			break;
		case 1:
			Intent i1 = new Intent(ResetPswdActivity.this,SettingActivity.class);
			startActivity(i1);
			break;
		case 2:
			ResetPswdActivity.this.finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}

