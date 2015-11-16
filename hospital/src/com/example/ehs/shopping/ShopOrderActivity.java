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
 * ����ģ��---�ҵĶ�������
 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_order);
		//Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
		
		TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("�ջ���ַ")  
                .setIndicator("�ջ���ַ")  
                .setContent(new Intent(this,ReceiveAddressActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("��ʷ����")  
                .setIndicator("��ʷ����",getResources().getDrawable(R.drawable.menu_order))  
                .setContent(new Intent(this,HistoryOrderActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("δ�ύ����")  
                .setIndicator("δ�ύ����",getResources().getDrawable(R.drawable.menu_order))  
                .setContent(new Intent(this,UnSubmitGoodActivity.class)));
	}

}
