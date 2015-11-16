package com.example.ehs;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
/*
 * 欢迎界面
 * */
public class WelcomActivity extends Activity implements AnimationListener{
	
	private Animation alphaAnimation = null;	
	FrameLayout txtTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		
		setupView();
	}
	
	public void setupView(){
		txtTextView = (FrameLayout)this.findViewById(R.id.layout_welcom);
		
		alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
		alphaAnimation.setFillEnabled(true); 
		alphaAnimation.setFillAfter(true);  
		txtTextView.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(this);  
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		//DoubleChoiceApplication app = (DoubleChoiceApplication)this.getApplication();
		//Intent intent = new Intent(this, LoginActivity.class);
		//暂时屏蔽登录方式选择，子手机端只能手机登录方式
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);	
		this.finish();
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//在欢迎界面屏蔽BACK键
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

}
