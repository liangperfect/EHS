package com.example.ehs.query;

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
 * ��ѯ����
 * */
@SuppressWarnings("deprecation")
public class QueryActivity extends TabActivity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
//	private Button rightBtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query);
		setUpView();
	}

	private void setUpView() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		//rightBtView = (Button)findViewById(R.id.bt_right);
		
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		
		TabHost tabHost = getTabHost();
		//3����3�����ϵ�д��
		tabHost.addTab(tabHost.newTabSpec("ҽ��")  
                .setIndicator("ҽ��",getResources().getDrawable(R.drawable.ill_notice))  
                .setContent(new Intent(this,DoctorWordsActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("����")  
                .setIndicator("����",getResources().getDrawable(R.drawable.fee))  
                .setContent(new Intent(this,FeeActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("�����")  
                .setIndicator("�����",getResources().getDrawable(R.drawable.ill_notice))  
                .setContent(new Intent(this,CheckResultsActivity.class)));
		
		int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("ҽ��");
		}
		if(i==1){
			titleView.setText("����");
		}
		if(i==2){
			titleView.setText("�����");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("ҽ��")){
					titleView.setText("ҽ��");
				}else if(tabId.equals("����")){
					titleView.setText("����");
				}else{
					titleView.setText("�����");
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

