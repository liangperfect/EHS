package com.example.ehs.hospital;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

import com.example.ehs.R;
/*
 * 医院介绍模块
 * */
@SuppressWarnings("deprecation")
public class HospitalActivity extends TabActivity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
//	private Button rightBtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital);
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
		tabHost.addTab(tabHost.newTabSpec("医院简介")  
                .setIndicator("医院简介")  
                .setContent(new Intent(this,AboutHospitalActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("科室介绍")  
                .setIndicator("科室介绍")  
                .setContent(new Intent(this,AboutSubjectActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("医生介绍")  
                .setIndicator("医生介绍")  
                .setContent(new Intent(this,AboutDoctorActivity.class)));  
		int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("医院简介");
		}
		if(i==1){
			titleView.setText("科室介绍");
		}
		if(i==2){
			titleView.setText("医生介绍");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("医院简介")){
					titleView.setText("医院简介");
				}else if(tabId.equals("科室介绍")){
					titleView.setText("科室介绍");
				}else{
					titleView.setText("医生介绍");
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
