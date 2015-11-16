package com.example.ehs.im;

import com.example.ehs.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
/*
 * 即时通讯模块
 * */
@SuppressWarnings("deprecation")
public class IMActivity extends TabActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im);
		setUpView();
	}

	private void setUpView() {
		TabHost tabHost = getTabHost();
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("消息")  
                .setIndicator("消息",getResources().getDrawable(R.drawable.message))
                .setContent(new Intent(this,MessageActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("联系人")  
                .setIndicator("联系人")
                .setContent(new Intent(this,GroupActivity.class)));  
		tabHost.setCurrentTab(0);
		/*tabHost.addTab(tabHost.newTabSpec("应用")  
                .setIndicator("应用")  
                .setContent(new Intent(this,MotiveActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("设置")  
                .setIndicator("设置")  
                .setContent(new Intent(this,SettingActivity.class)));  */
		
		/*int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("消息");
			leftBtView.setVisibility(View.GONE);
			rightBtView.setVisibility(View.VISIBLE);
			rightBtView.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View arg0) {
					Toast.makeText(MainActivity.this, "更多操作", Toast.LENGTH_SHORT).show();
				}
			});
		}
		if(i==1){
			titleView.setText("联系人");
			leftBtView.setVisibility(View.VISIBLE);
			leftBtView.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					Toast.makeText(MainActivity.this, "管理", Toast.LENGTH_SHORT).show();
				}
			});
			rightBtView.setVisibility(View.VISIBLE);
			rightBtView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(MainActivity.this, "添加好友", Toast.LENGTH_SHORT).show();
				}
			});
		}
		if(i==2){
			leftBtView.setVisibility(View.GONE);
			rightBtView.setVisibility(View.VISIBLE);
			titleView.setText("应用");
		}
		if(i==3){
			leftBtView.setVisibility(View.GONE);
			rightBtView.setVisibility(View.VISIBLE);
			titleView.setText("设置");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				Toast.makeText(MainActivity.this, tabId, Toast.LENGTH_SHORT).show();
				if(tabId.equals("消息")){
					titleView.setText("消息");
					leftBtView.setVisibility(View.GONE);
					rightBtView.setVisibility(View.VISIBLE);
					rightBtView.setOnClickListener(new OnClickListener() {			
						@Override
						public void onClick(View arg0) {
							Toast.makeText(MainActivity.this, "更多操作", Toast.LENGTH_SHORT).show();
						}
					});
				}else if(tabId.equals("联系人")){
					titleView.setText("联系人");
					leftBtView.setVisibility(View.VISIBLE);
					leftBtView.setOnClickListener(new OnClickListener() {				
						@Override
						public void onClick(View v) {
							Toast.makeText(MainActivity.this, "管理", Toast.LENGTH_SHORT).show();
						}
					});
					rightBtView.setVisibility(View.VISIBLE);
					rightBtView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Toast.makeText(MainActivity.this, "添加好友", Toast.LENGTH_SHORT).show();
						}
					});
				}else if(tabId.equals("应用")){
					leftBtView.setVisibility(View.GONE);
					rightBtView.setVisibility(View.VISIBLE);
					titleView.setText("应用");
				}else{
					leftBtView.setVisibility(View.GONE);
					rightBtView.setVisibility(View.VISIBLE);
					titleView.setText("设置");
				}
			}			
		});		*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
