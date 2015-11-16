package com.example.ehs;

import com.example.ehs.xmpphelper.ARegistActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 注册模块
 * */
public class RegisterActivity extends ARegistActivity implements OnClickListener{
	//title
	private TextView titleView;
	//left button
	private Button btLeft;
	
	//screen
	private EditText accountView;
	private EditText passwordView;
	private EditText emailView;
	private Button registerView;
	
	// Values
	private String account;
	private String password;
	private String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		setUpView();
	}
	private void setUpView() {
		titleView = (TextView)this.findViewById(R.id.title);
		titleView.setText("注册新用户");
			
		btLeft = (Button)this.findViewById(R.id.bt_left);
		btLeft.setVisibility(View.VISIBLE);
		btLeft.setBackgroundResource(R.drawable.btn_back);
		btLeft.setOnClickListener(this);
			
		accountView = (EditText)findViewById(R.id.input_account);
		passwordView = (EditText)findViewById(R.id.input_password);
		emailView = (EditText)findViewById(R.id.input_email);
		emailView.setText("407438316@qq.com");
			
		registerView = (Button)findViewById(R.id.register_ok);
		registerView.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.register_ok:
			attemptRegister();
			break;
		default:
			break;
		}
		
	}
	private void attemptRegister() {
		accountView.setError(null);
		passwordView.setError(null);
		emailView.setError(null);
		
		account = accountView.getText().toString();
		password = passwordView.getText().toString();
		email =emailView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		//check for a valid verify code
		if(TextUtils.isEmpty(email)){
			emailView.setError("邮箱不能为空");
			focusView = emailView;
			cancel = true;
		}
		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError("此项不能为空");
			focusView = passwordView;
			cancel = true;
		} else if (password.length()<3) {
			passwordView.setError("密码太短");
			focusView = passwordView;
			cancel = true;
		}
		// Check for a valid account.
		if (TextUtils.isEmpty(account)) {
			accountView.setError("此项不能为空");
			focusView = accountView;
			cancel = true;
		} 
		
		if (cancel) {
			focusView.requestFocus();
			} else {
				doRegist(account, password);
				/*Toast.makeText(RegisterActivity.this, "恭喜你，注册成功！", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
				startActivity(intent);*/
			}
	}
	@Override
	protected void doLoginSuccess(String username, String password) {
		Toast.makeText(getApplicationContext(), "恭喜你注册成功",
				Toast.LENGTH_SHORT).show();
		startActivity(new Intent(MContext, LoginActivity.class));
	}
	
}
