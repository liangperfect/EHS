package com.example.ehs.im;

import com.example.ehs.R;
import com.example.ehs.R.layout;
import com.example.ehs.model.Friends;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VedioChatActivity extends Activity implements OnClickListener{
	//发起视频通话方
	private ImageView friendsAvaterView;//
	private TextView friendsIdView;
	private TextView connectView;
	private Button bt_closed;
	
	//被叫方
	private TextView vedioTitleView;
	private ImageView calledAvaterView;
	private TextView calledNameView;
	private Button cancelView;
	private Button acceptView;
	private Button byVoiceView;
	
	private LinearLayout linearLayout;
	
	int acceptType=0;//0表示主叫，1表示被叫
	
	Friends friends = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//无标题
		if(acceptType == 0){
			setContentView(R.layout.vedio_chat);
		}else{
			setContentView(R.layout.voice_chat);
		}
		friends = getIntent().getBundleExtra("bd").getParcelable("friends");
		System.out.println("friends="+friends.toString());
		init();
	}



	private void init() {
		if(acceptType==0){
			friendsAvaterView = (ImageView)findViewById(R.id.friends_avater);
			friendsIdView = (TextView)findViewById(R.id.tv1);
			friendsIdView.setText(friends.getJID());
			connectView = (TextView)findViewById(R.id.tv2);
			connectView.setText("正在连接...");
			bt_closed = (Button)findViewById(R.id.vedio_closed_1);
			bt_closed.setOnClickListener(this);
		}else{
			linearLayout = (LinearLayout)findViewById(R.id.vedio_voice_layout);
			linearLayout.setVisibility(View.VISIBLE);
			
			vedioTitleView = (TextView)findViewById(R.id.voice_wait);
			vedioTitleView.setText("视频通话");
			calledAvaterView = (ImageView)findViewById(R.id.voice_img);
			calledNameView = (TextView)findViewById(R.id.voice_name);
			calledNameView.setText(friends.getJID());
			
			cancelView = (Button)findViewById(R.id.reject);
			cancelView.setOnClickListener(this);
			
			acceptView = (Button)findViewById(R.id.accept);
			acceptView.setOnClickListener(this);
			
			byVoiceView = (Button)findViewById(R.id.voice_closed);
			byVoiceView.setText("语音接听");
			byVoiceView.setOnClickListener(this);
		}
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.vedio_closed_1:
			//关闭连接请求
			this.finish();
			break;
		case R.id.reject:
			//关闭连接请求
			this.finish();
			break;
		case R.id.accept:
			Intent i = new Intent();//跳转到视频语音界面
			i.setClass(VedioChatActivity.this, VedioVoiceChatActivity.class);
			i.putExtra("type", 1);//1表示视屏语音其接通
			startActivity(i);
			this.finish();
			break;
		case R.id.voice_closed:
			Intent i1 = new Intent();
			i1.setClass(VedioChatActivity.this, VedioVoiceChatActivity.class);
			i1.putExtra("type", 2);//2表示仅语音接通
			startActivity(i1);
			this.finish();
			break;
		default:
			break;
		}
	}

}
