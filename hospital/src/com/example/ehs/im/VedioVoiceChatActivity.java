package com.example.ehs.im;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.example.ehs.R;
import com.example.ehs.model.ConfigEntity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VedioVoiceChatActivity extends Activity implements OnClickListener{
	private LinearLayout layout;//��ʾ�����Ľ���
	private TextView chatTimeView;//����ʱ��
	private ImageView chatMineView;//�ҵ�������ͼ����
	
	private LinearLayout buttonsView;//���еİ�ť����
	private Button closeVedioView;//�ر���Ƶ����
	private Button closeChatView;//��������
	private Button closeVoiceView;//�ر���������
	private 
	
	int type=0;//1--������Ƶ��2--������3--��Ƶ��0--���ر�
	int vedio_type=0;//��Ƶ��1--����0--��
	int voice_type=0;//������1--����0--��
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȫ��
		setContentView(R.layout.vedio_chat_r);
		type = getIntent().getIntExtra("type", 0);
		System.out.println("type="+type);
		if(type == 1){
			vedio_type=1;//��Ƶ��
			voice_type=1;//������
		}else if(type == 2){
			vedio_type=0;//��Ƶ��
			voice_type=1;//������
		}else if(type == 3){
			vedio_type=1;//��Ƶ��
			voice_type=0;//������
		}else{
			vedio_type=0;//��Ƶ��
			voice_type=0;//������
		}
		init();
	}

	private void init() {
		layout = (LinearLayout)findViewById(R.id.chat_layout);//��Ƶ��ʾ����
		//�ж���Ƶ�Ƿ���ʾ
		chatTimeView = (TextView)findViewById(R.id.chat_time);
		chatMineView = (ImageView)findViewById(R.id.chat_mine);
		buttonsView = (LinearLayout)findViewById(R.id.buttons);
		
		closeVedioView = (Button)findViewById(R.id.close_vedio);
		closeVedioView.setOnClickListener(this);
		
		closeChatView = (Button)findViewById(R.id.chat_closed);
		closeChatView.setOnClickListener(this);
		
		closeVoiceView = (Button)findViewById(R.id.close_voice);
		closeVoiceView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close_vedio:
			if(vedio_type == 0){
				//������Ƶ
				vedio_type = 1;
			}else{
				//�ر���Ƶ
				vedio_type = 0;
			}
			break;
		case R.id.chat_closed:
			//�˳�����,�����������
			/*Intent i = new Intent();
			i.setClass(VedioVoiceChatActivity.this, ChatActivity.class);
			startActivity(i);*/
			this.finish();
			break;
		case R.id.close_voice:
			if(voice_type == 0){
				//��������
				voice_type = 1;
			}else{
				//�ر�����
				voice_type = 0;
			}
			break;
		default:
			break;
		}
	}

}
