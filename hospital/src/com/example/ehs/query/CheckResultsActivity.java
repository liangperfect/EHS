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
 * ��ѯģ��---���鱨��ģ��
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
		//3����3�����ϵ�д��
		tabHost.addTab(tabHost.newTabSpec("���м�鱨��")  
                .setIndicator("���м�鱨��")  
                .setContent(new Intent(this,AllCheckResultsActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("��ѯ��鱨��")  
                .setIndicator("��ѯ��鱨��")  
                .setContent(new Intent(this,QueryCheckResultActivity.class)));  
	}
}
