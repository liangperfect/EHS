package com.example.ehs.entertainments;

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
@SuppressWarnings("deprecation")

/*
 * 娱乐模块
 * */
public class EntertainMentActivity extends TabActivity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
//	private Button rightBtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entertain);
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
		tabHost.addTab(tabHost.newTabSpec("电影")  
                .setIndicator("电影",this.getResources().getDrawable(R.drawable.film))  
                .setContent(new Intent(this,MovieActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("游戏")  
                .setIndicator("游戏",getResources().getDrawable(R.drawable.games))  
                .setContent(new Intent(this,GamesActivity.class)));  
		tabHost.addTab(tabHost.newTabSpec("歌曲")  
                .setIndicator("歌曲",getResources().getDrawable(R.drawable.mp3))  
                .setContent(new Intent(this,MusicActivity.class)));  
		int i=tabHost.getCurrentTab();
		if(i==0){
			titleView.setText("电影");
		}
		if(i==1){
			titleView.setText("游戏");
		}
		if(i==2){
			titleView.setText("歌曲");
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {		
			@Override
			public void onTabChanged(String tabId) {
				if(tabId.equals("电影")){
					titleView.setText("电影");
				}else if(tabId.equals("游戏")){
					titleView.setText("游戏");
				}else{
					titleView.setText("歌曲");
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

