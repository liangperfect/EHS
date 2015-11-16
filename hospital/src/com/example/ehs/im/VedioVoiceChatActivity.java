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
	private LinearLayout layout;//显示视屏的界面
	private TextView chatTimeView;//聊天时间
	private ImageView chatMineView;//我的聊天视图界面
	
	private LinearLayout buttonsView;//所有的按钮界面
	private Button closeVedioView;//关闭视频界面
	private Button closeChatView;//结束聊天
	private Button closeVoiceView;//关闭语音界面
	private 
	
	int type=0;//1--语音视频；2--语音，3--视频；0--都关闭
	int vedio_type=0;//视频：1--开，0--关
	int voice_type=0;//语音：1--开；0--关
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//全屏
		setContentView(R.layout.vedio_chat_r);
		type = getIntent().getIntExtra("type", 0);
		System.out.println("type="+type);
		if(type == 1){
			vedio_type=1;//视频开
			voice_type=1;//语音开
		}else if(type == 2){
			vedio_type=0;//视频关
			voice_type=1;//语音开
		}else if(type == 3){
			vedio_type=1;//视频开
			voice_type=0;//语音关
		}else{
			vedio_type=0;//视频关
			voice_type=0;//语音关
		}
		init();
	}

	private void init() {
		layout = (LinearLayout)findViewById(R.id.chat_layout);//视频显示界面
		//判断视频是否显示
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
				//开启视频
				vedio_type = 1;
			}else{
				//关闭视频
				vedio_type = 0;
			}
			break;
		case R.id.chat_closed:
			//退出聊天,返回聊天界面
			/*Intent i = new Intent();
			i.setClass(VedioVoiceChatActivity.this, ChatActivity.class);
			startActivity(i);*/
			this.finish();
			break;
		case R.id.close_voice:
			if(voice_type == 0){
				//开启语音
				voice_type = 1;
			}else{
				//关闭语音
				voice_type = 0;
			}
			break;
		default:
			break;
		}
	}

}
