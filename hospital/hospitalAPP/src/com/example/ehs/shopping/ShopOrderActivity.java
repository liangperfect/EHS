package com.example.ehs.shopping;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;

public class ShopOrderActivity extends Activity{
/*
 * ����ģ��---�ҵĶ�������
 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_order);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
