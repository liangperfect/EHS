package com.example.ehs.shopping;

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
 * 购物模块
 * */
@SuppressWarnings("deprecation")
public class ShoppingActivity extends TabActivity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
//	private Button rightBtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping);
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
		tabHost.addTab(tabHost.newTabSpec("物品浏览")  
                .setIndicator("物品浏览")  
                .setContent(new Intent(this,GoodsBrowActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("我的订单")  
                .setIndicator("我的订单")  
                .setContent(new Intent(this,ShopOrderActivity.class)));   
		int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("物品浏览");
		}
		if(i==1){
			titleView.setText("我的订单");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("物品浏览")){
					titleView.setText("物品浏览");
				}else{
					titleView.setText("我的订单");
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

