package com.example.ehs.notice;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.ehs.R;
/*
 * 通知模块
 * */
@SuppressWarnings("deprecation")
public class NoticeActivity extends TabActivity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
//	private Button rightBtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice);
		setUpView();
	}

	private void setUpView() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		//rightBtView = (Button)findViewById(R.id.bt_right);
		
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		
		TabHost tabHost = getTabHost();
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("院内通知")  
                .setIndicator("院内通知",getResources().getDrawable(R.drawable.ill_notice)) 
                .setContent(new Intent(this,HospitalNoticeActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("护士站通知")  
                .setIndicator("护士站通知")  
                .setContent(new Intent(this,NurseNoticeActivity.class)));  
		
		int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("院内通知");
		}
		if(i==1){
			titleView.setText("护士站通知");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("院内通知")){
					titleView.setText("院内通知");
				}else{
					titleView.setText("护士站通知");
				}
			}
		});		
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
		default:
			break;
		}
	}

}
