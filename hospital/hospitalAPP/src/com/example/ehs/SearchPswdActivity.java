package com.example.ehs;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 找回密码模块
 * */
public class SearchPswdActivity extends Activity implements OnClickListener{
	//title
	private TextView titleTextView;
	//left button
	private Button btLeft;
	
	//screen
	private EditText studentIdView;
	private EditText phoneNumberView;
	//private EditText verifyCodeView;
	private EditText newPswdView;
	//private Button getVerifyCodeView;
	private Button submitView;
	
	// Values
	private String studentId;
	private String phoneNumber;
	//private String verifyCode;
	private String newPswd;
	//private String getVerifyCode;
	
	private String myVerifyCode=null;
	private String auth_key=null;
	private String tag="SearchPswdActivity";
	
	private ProgressDialog dialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchpswd);	
		
		dialog = new ProgressDialog(this);
		
		setupView();
	}

	private void setupView() {
		titleTextView = (TextView)this.findViewById(R.id.title);
		titleTextView.setText("找回密码");
		
		btLeft = (Button)this.findViewById(R.id.bt_left);
		btLeft.setVisibility(View.VISIBLE);
		btLeft.setOnClickListener(this);
		
		studentIdView = (EditText)findViewById(R.id.input_student_id);		
		phoneNumberView = (EditText)findViewById(R.id.input_phone_num);
		//verifyCodeView = (EditText)findViewById(R.id.input_verification_num);
		newPswdView = (EditText)findViewById(R.id.input_new_pswd);
		
		/*getVerifyCodeView = (Button)findViewById(R.id.get_verification_num);
		getVerifyCodeView.setOnClickListener(this);*/
		
		submitView = (Button)findViewById(R.id.submit);
		submitView.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:{
			this.finish();
			break;
		}
		/*case R.id.get_verification_num:
			RequestParams params = new RequestParams();
			params.put("studentID", studentIdView.getText().toString());
			params.put("mobile", phoneNumberView.getText().toString());
			TXTClient.get("/users/get_auth_reset_password", params, new JsonHttpResponseHandler(){
				 @Override
		            public void onStart() {
					 dialog.setMessage("正在获取验证码，请稍后...");
					 dialog.show();
		            	System.out.println("onstart");
		            }
		            @Override
		            public void onSuccess(int arg0, JSONObject arg1) {
						super.onSuccess(arg0, arg1);
						System.out.println("onSuccess"+arg1.toString());
						Log.i(tag, "onSuccess");
						int stateCode = decodeResults(arg1,"get");
		            	if(stateCode == 200){
		        				//getVerifyCodeView.setText(""+auth_key);
		            		Toast.makeText(SearchPswdActivity.this, "获取验证码成功！", Toast.LENGTH_SHORT).show();
		        			}else{
		        				Toast.makeText(SearchPswdActivity.this, "获取验证码失败！", Toast.LENGTH_SHORT).show();
		        			}
		            }	        
		            @Override
		            public void onFailure(Throwable e, String response) {
		            	dialog.setMessage("获取验证码失败，请重试!");
		            	dialog.setCancelable(true);
		            	dialog.show();
		            	System.out.println("onfailure="+response);
		            }
		            @Override
		            public void onFinish() {
		            	dialog.dismiss();
		            	System.out.println("onfinish");
		            }
		        });
			break;	*/
		case R.id.submit:{
			attemptSubmit();
			break;
		}	
		default:
			break;
	}
	}
	private void attemptSubmit() {
		// Reset errors.
		studentIdView.setError(null);
		phoneNumberView.setError(null);
		//verifyCodeView.setError(null);

		// Store values at the time of the next attempt.
		studentId = studentIdView.getText().toString();
		phoneNumber = phoneNumberView.getText().toString();
		//verifyCode = verifyCodeView.getText().toString();
		newPswd = newPswdView.getText().toString();
		//getVerifyCode = getVerifyCodeView.getText().toString();

		boolean cancel = false;
		View focusView = null;
		
		//Check for a vaild new password
		if(TextUtils.isEmpty(newPswd)){
			newPswdView.setError(getString(R.string.error_field_required));
			focusView = newPswdView;
			cancel = true;
		}else if(newPswd.length() < 4){
			phoneNumberView.setError(getString(R.string.error_invalid_password));
			focusView = newPswdView;
			cancel = true;
		}
		
		//Check for a valid verify code
		/*if(TextUtils.isEmpty(verifyCode)){
			verifyCodeView.setError(getString(R.string.error_field_required));
			focusView = verifyCodeView;
			cancel = true;
		}/*else if(!verifyCode.equals(getVerifyCode)){
			phoneNumberView.setError(getString(R.string.error_field_required));
			focusView = phoneNumberView;
			cancel = true;
		}*/
		
		// Check for a valid phone number.
		if (TextUtils.isEmpty(phoneNumber)) {
			phoneNumberView.setError(getString(R.string.error_field_required));
			focusView = phoneNumberView;
			cancel = true;
		} else if (phoneNumber.length()<11) {
			phoneNumberView.setError(getString(R.string.error_invalid_telephone));
			focusView = phoneNumberView;
			cancel = true;
		}
		// Check for a valid student id.
		if (TextUtils.isEmpty(studentId)) {
			studentIdView.setError(getString(R.string.error_field_required));
			focusView = studentIdView;
			cancel = true;
		} else if (studentId.length()<10) {
			studentIdView.setError(getString(R.string.error_invalid_account));
			focusView = studentIdView;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {
			//找回密码
		}
				
	}
	
}

