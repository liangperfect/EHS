package com.example.ehs.query;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.order.HistoryOrderActivity;
import com.example.ehs.order.ReceiveAddressActivity;
import com.example.ehs.order.UnSubmitActivity;

import android.app.TabActivity;
import android.content.Intent;
/*
 * 查询模块---检验报告模块
 * */
public class CheckResultsActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_results);
		setUpView();
	}
	private void setUpView() {
		TabHost tabHost = getTabHost();
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("所有检查报告")  
                .setIndicator("所有检查报告")  
                .setContent(new Intent(this,AllCheckResultsActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("查询检查报告")  
                .setIndicator("查询检查报告")  
                .setContent(new Intent(this,QueryCheckResultActivity.class)));  
	}
}
