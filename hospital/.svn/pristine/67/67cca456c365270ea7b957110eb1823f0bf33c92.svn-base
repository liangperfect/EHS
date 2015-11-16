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
 * 查询界面
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
		//3个及3个以上的写法
		tabHost.addTab(tabHost.newTabSpec("医嘱")  
                .setIndicator("医嘱",getResources().getDrawable(R.drawable.ill_notice))  
                .setContent(new Intent(this,DoctorWordsActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("费用")  
                .setIndicator("费用",getResources().getDrawable(R.drawable.fee))  
                .setContent(new Intent(this,FeeActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("检查结果")  
                .setIndicator("检查结果",getResources().getDrawable(R.drawable.ill_notice))  
                .setContent(new Intent(this,CheckResultsActivity.class)));
		
		int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("医嘱");
		}
		if(i==1){
			titleView.setText("费用");
		}
		if(i==2){
			titleView.setText("检查结果");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("医嘱")){
					titleView.setText("医嘱");
				}else if(tabId.equals("费用")){
					titleView.setText("费用");
				}else{
					titleView.setText("检查结果");
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

