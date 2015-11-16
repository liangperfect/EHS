package com.example.ehs;

import org.jivesoftware.smack.packet.Presence;

import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.xmpphelper.ALoginActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.AvoidXfermode.Mode;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
/*
 * 登陆模块
 * */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LoginActivity extends ALoginActivity implements OnClickListener{
	//title
	private TextView titleView;
	// UI references.
	private EditText accountView;
	private EditText passwordView;	
	private CheckBox savePassword;
	private CheckBox autoLogin;
	private Button loginView;
	private Button newAccountView;
	private Button searchPasswordView;
	
	private String account;
	private String password;
	
	UserInfo userInfo=null;
	ConnectionUtils connectionUtils=null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userInfo = new UserInfo(this);
		Toast.makeText(LoginActivity.this, "长按登陆按钮可以更改服务器IP地址", Toast.LENGTH_LONG).show();
		setupView();
	}

	private void setupView() {
		titleView = (TextView)this.findViewById(R.id.title);
		titleView.setText("登陆界面");
		
		accountView = (EditText) findViewById(R.id.account);
		passwordView = (EditText) findViewById(R.id.password);	
		
		savePassword = (CheckBox)findViewById(R.id.save_password);
		autoLogin = (CheckBox)findViewById(R.id.auto_login);
		
		loginView = (Button)findViewById(R.id.login);
		loginView.setOnClickListener(this);
		
		//长按登陆键可以输入服务器IP地址和端口号
		loginView.setOnLongClickListener(new OnLongClickListener() {		
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(LoginActivity.this, "更改服务器IP", Toast.LENGTH_SHORT).show();
				final EditText host_input = new EditText(LoginActivity.this);
				host_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				host_input.setText(ConnectionUtils.getHost());
				host_input.setBackgroundResource(android.R.drawable.editbox_background);
				new AlertDialog.Builder(LoginActivity.this).setTitle("请输入服务器IP地址:")
						.setView(host_input)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String host = host_input.getText().toString();
								System.out.println("host="+host);
								//ConnectionUtils.setHost(host);
								ConnectionUtils connectionUtils = new ConnectionUtils(host);
								System.out.println("hostzzz="+connectionUtils.getHost());
							}
						}).setNegativeButton("取消", null).show();
				return true;
			}
		});
		
		newAccountView = (Button) findViewById(R.id.register_new_user);
		newAccountView.setOnClickListener(this);
		
		searchPasswordView = (Button) findViewById(R.id.search_password);
		searchPasswordView.setOnClickListener(this);
		
		accountView.setText(userInfo.getAccount());
		passwordView.setText(userInfo.getPassword());
		if(userInfo.getSavePswd()){
			savePassword.setChecked(true);
			if(userInfo.getAutoLogin()){
				autoLogin.setChecked(true);	
				doLogin(account, password);
			}
		}
		passwordView
		.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});
		
		//保存用户信息，下次自动获取用户信息
		savePassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {	
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(savePassword.isChecked()){
					Toast.makeText(LoginActivity.this, "选中记住密码！", Toast.LENGTH_SHORT).show();
				}else{
				}
			}
		});
		//自动登录，点击此选项后每次将自动登录到Logo界面
		autoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {		
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(autoLogin.isChecked()){
					Toast.makeText(LoginActivity.this, "选中自动登录！", Toast.LENGTH_SHORT).show();					
				}else{
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			attemptLogin();
			break;
		case R.id.register_new_user:
			Intent i1 = new Intent();
			i1.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(i1);
			break;
		case R.id.search_password:
			Intent i2 = new Intent();
			i2.setClass(LoginActivity.this, SearchPswdActivity.class);
			startActivity(i2);
			break;
		default:
			break;
		}		
	}
	private void attemptLogin() {
		passwordView.setError(null);
		password = passwordView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError("此项不能为空");
			focusView = passwordView;
			cancel = true;
		} else if (password.length() < 3) {
			passwordView.setError("密码太短");
			focusView = passwordView;
			cancel = true;
		}	
		
		// Reset errors.
		accountView.setError(null);
		// Store values at the time of the login attempt.
		account = accountView.getText().toString();
		// Check for a valid studentId.
		if (TextUtils.isEmpty(account)) {
			accountView.setError("此项不能为空");
			focusView = accountView;
			cancel = true;
		} 
		if (cancel) {
			focusView.requestFocus();
		}else{
			doLogin(account, password);
		}
	}
	
	@Override
	protected void doLoginSuccess(String username, String password) {
		Toast.makeText(MContext, "登陆成功", Toast.LENGTH_SHORT).show();
		Presence presence = new Presence(Presence.Type.available);
		presence.setMode(org.jivesoftware.smack.packet.Presence.Mode.available);
		ConnectionUtils.getConnection(LoginActivity.this).sendPacket(presence);
		if(savePassword.isChecked()){
        	userInfo.setUserAccount(account);
        	userInfo.setUserPswd(password);
        	userInfo.setSavePswd(true);
        	if(autoLogin.isChecked()){    
    			userInfo.setAutoLogin(true);
    			System.out.println("userInfo="+userInfo.getAccount()+","+userInfo.getPassword()+","+userInfo.getSavePswd()+","+userInfo.getAutoLogin());
        	}else{
        		userInfo.setAutoLogin(false);
        	}
		}
		startActivity(new Intent(LoginActivity.this, MainActivity.class));
		this.finish();
	}
}
