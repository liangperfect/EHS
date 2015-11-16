package com.example.ehs.shopping;

import com.example.ehs.R;
import com.example.ehs.R.layout;
import com.example.ehs.R.menu;
import com.example.ehs.order.HistoryOrderActivity;
import com.example.ehs.order.ReceiveAddressActivity;
import com.example.ehs.order.UnSubmitActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;

public class GoodOrderActivity  extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_order);
		setUpView();
		
	}

	private void setUpView() {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.good_order, menu);
		return true;
	}

}
