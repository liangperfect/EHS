package com.example.ehs.im;

import com.example.ehs.R;
import com.example.ehs.model.Friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VoiceChatActivity extends Activity implements OnClickListener{
	//主叫方
	private TextView voiceTitleView;
	private ImageView calledAvaterView;
	private TextView calledNameView;
	private Button closeVoiceView;
	
	//被叫
	private LinearLayout linearLayout;
	private Button cancelView;
	private Button acceptView;

	//区分主叫和被叫
	int voiceType=1;//主叫
	Friends friends=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题
		setContentView(R.layout.voice_chat);
		friends = this.getIntent().getBundleExtra("bd").getParcelable("friends");
		System.out.println("voice_friends="+friends.toString());
		init();
	}

	private void init() {
		voiceTitleView = (TextView)findViewById(R.id.voice_wait);

		calledAvaterView = (ImageView)findViewById(R.id.voice_img);
		calledNameView = (TextView)findViewById(R.id.voice_name);
		calledNameView.setText(friends.getJID());
		
		closeVoiceView = (Button)findViewById(R.id.voice_closed);
		linearLayout = (LinearLayout)findViewById(R.id.vedio_voice_layout);
		
		if(voiceType == 0){
			voiceTitleView.setText("正在接通...");
			closeVoiceView.setText("结束通话");
			closeVoiceView.setOnClickListener(this);
			linearLayout.setVisibility(View.GONE);
		}else{
			linearLayout.setVisibility(View.VISIBLE);
			cancelView = (Button)findViewById(R.id.reject);
			cancelView.setOnClickListener(this);
			acceptView = (Button)findViewById(R.id.accept);
			acceptView.setOnClickListener(this);
			closeVoiceView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reject:
			//关闭请求
			this.finish();
			break;
		case R.id.accept:
			Intent i = new Intent();
			i.setClass(VoiceChatActivity.this, VedioVoiceChatActivity.class);
			i.putExtra("type", 2);//2表示仅语音接通
			startActivity(i);
			this.finish();
			break;
		case R.id.voice_closed:
			//关闭请求
			this.finish();
			break;
		default:
			break;
		}
	}

}
