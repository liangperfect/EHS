package com.example.ehs.order;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;

import com.example.ehs.R;
/*
 * 点餐模块---我的订单界面
 * */
public class MyMealOrderActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meal_order);
		setUpView();
	}
	private void setUpView() {
		TabHost tabHost = getTabHost();
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("收货地址")  
                .setIndicator("收货地址")  
                .setContent(new Intent(this,ReceiveAddressActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("历史订单")  
                .setIndicator("历史订单",getResources().getDrawable(R.drawable.menu_order))  
                .setContent(new Intent(this,HistoryOrderActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("未提交订单")  
                .setIndicator("未提交订单",getResources().getDrawable(R.drawable.menu_order))  
                .setContent(new Intent(this,UnSubmitActivity.class)));  
	}
}
