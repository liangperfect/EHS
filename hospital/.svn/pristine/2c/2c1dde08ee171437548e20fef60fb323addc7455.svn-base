package com.example.ehs.shopping;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.order.HistoryOrderActivity;
import com.example.ehs.order.ReceiveAddressActivity;
import com.example.ehs.order.UnSubmitActivity;

public class ShopOrderActivity extends TabActivity{
/*
 * 购物模块---我的订单界面
 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_order);
		//Toast.makeText(this, "未开发！", Toast.LENGTH_LONG).show();
		
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("收货地址")  
                .setIndicator("收货地址")  
                .setContent(new Intent(this,ReceiveAddressActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("历史订单")  
                .setIndicator("历史订单",getResources().getDrawable(R.drawable.menu_order))  
                .setContent(new Intent(this,HistoryOrderActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("未提交订单")  
                .setIndicator("未提交订单",getResources().getDrawable(R.drawable.menu_order))  
                .setContent(new Intent(this,UnSubmitGoodActivity.class)));
	}

}
