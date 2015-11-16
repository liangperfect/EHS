package com.example.ehs;

import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.xmpphelper.ALoginActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
	private static final String[] DUMMY_CREDENTIALS = new String[] {"foo@example.com:hello", "bar@example.com:world" };
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
//	private UserLoginTask mAuthTask = null;
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
	
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;	
	
	private String account;
	private String password;
	
	private String tag = "LoginActivity";
	
	UserInfo userInfo=null;
	ConnectionUtils connectionUtils=null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userInfo = new UserInfo(this);
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
				return true;
			}
		});
		
		newAccountView = (Button) findViewById(R.id.register_new_user);
		newAccountView.setOnClickListener(this);
		
		searchPasswordView = (Button) findViewById(R.id.search_password);
		searchPasswordView.setOnClickListener(this);
		
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		
		accountView.setText(userInfo.getAccount());
		passwordView.setText(userInfo.getPassword());
		if(userInfo.getSavePswd()){
			savePassword.setChecked(true);
			if(userInfo.getAutoLogin()){
				autoLogin.setChecked(true);	
				//attemptLogin();
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
	/*	if (mAuthTask != null) {
			return;
		}*/
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
			/*new Thread(){
				 @Override
				 public void run(){
				//你要执行的方法
					//connectionUtils.login(account, password, "222.18.162.135");
						System.out.println("account="+account);
						System.out.println("password="+password);
						//PPConnection.DEBUG_ENABLED = true;
				
						AccountManager accountManager;
						final ConnectionConfiguration connectionConfig = new ConnectionConfiguration(
								"222.18.162.135", Integer.parseInt("5222"), "222.18.162.146");
						// 允许自动连接
						connectionConfig.setReconnectionAllowed(true);
						connectionConfig.setSendPresence(true);

						Connection connection = new XMPPConnection(connectionConfig);
						try {
							connection.connect();// 开启连接
							accountManager = connection.getAccountManager();// 获取账户管理类
							// 登录
							connection.login(account, password,"");
							System.out.println(connection.getUser()); 
							connection.getChatManager().createChat("123456@222.18.162.146",null).sendMessage("Hello word!");
						} catch (XMPPException e) {
							throw new IllegalStateException(e);
						}	 
				//执行完毕后给handler发送一个空消息
				handler.sendEmptyMessage(0);
				 }
				 }.start();*/
		}
	}
	//定义Handler对象
	/*@SuppressLint("HandlerLeak")
	private Handler handler =new Handler(){
	 @SuppressLint("HandlerLeak")
	@Override
	 //当有消息发送出来的时候就执行Handler的这个方法
	public void handleMessage(Message msg){
	 super.handleMessage(msg);
	//处理UI
	 Toast.makeText(LoginActivity.this, "恭喜你，登陆成功！", Toast.LENGTH_SHORT).show();
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
    	}else{
    		userInfo.setUserAccount("");
        	userInfo.setUserPswd("");
        	userInfo.setSavePswd(false);
        	userInfo.setAutoLogin(false);
    	}
    	mLoginStatusMessageView.setText("正在登陆，请稍后...");
    	showProgress(true);
    	mAuthTask = new UserLoginTask();
    	mAuthTask.execute((Void) null);
			Intent i = new Intent();
			i.setClass(LoginActivity.this, MainActivity.class);
        	startActivity(i);
	 }
	 };
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);
			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			Log.d(tag, "UserLoginTask doInBackground");
			Intent i = new Intent();
			i.setClass(LoginActivity.this, MainActivity.class);
			startActivity(i);

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(account)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(password);
				}
			}
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				passwordView
						.setError("密码太短");
				passwordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}*/
	@Override
	protected void doLoginSuccess(String username, String password) {
		Toast.makeText(MContext, "登陆成功", Toast.LENGTH_SHORT).show();
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
