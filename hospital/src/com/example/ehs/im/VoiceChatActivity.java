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
	//���з�
	private TextView voiceTitleView;
	private ImageView calledAvaterView;
	private TextView calledNameView;
	private Button closeVoiceView;
	
	//����
	private LinearLayout linearLayout;
	private Button cancelView;
	private Button acceptView;

	//�������кͱ���
	int voiceType=1;//����
	Friends friends=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ������
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
			voiceTitleView.setText("���ڽ�ͨ...");
			closeVoiceView.setText("����ͨ��");
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
			//�ر�����
			this.finish();
			break;
		case R.id.accept:
			Intent i = new Intent();
			i.setClass(VoiceChatActivity.this, VedioVoiceChatActivity.class);
			i.putExtra("type", 2);//2��ʾ��������ͨ
			startActivity(i);
			this.finish();
			break;
		case R.id.voice_closed:
			//�ر�����
			this.finish();
			break;
		default:
			break;
		}
	}

}
