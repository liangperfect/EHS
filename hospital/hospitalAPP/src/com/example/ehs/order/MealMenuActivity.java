package com.example.ehs.order;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * ���ģ��˵���ʾ����
 * */
public class MealMenuActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meal_menu);
		Toast.makeText(this, "δ������", Toast.LENGTH_LONG).show();
	}

}
