package com.example.ehs.shopping;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;

public class GoodsBrowActivity extends Activity{
/*
 * ����ģ��---��Ʒ�������
 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_brow);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
