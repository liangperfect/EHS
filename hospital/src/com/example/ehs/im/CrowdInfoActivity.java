package com.example.ehs.im;

import com.example.ehs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CrowdInfoActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
	
	private TextView desTitleView;
	private TextView desContentView;
	private com.example.ehs.widget.My2TextButton crowdMemberView;
	private com.example.ehs.widget.My2TextButton crowdInviteView;
	private com.example.ehs.widget.My2TextButton crowdMyInfoView;
	
	
	String jid=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crowd_info);
		Bundle bd = getIntent().getBundleExtra("bd");
		jid = bd.getString("jid");
		System.out.println("jid="+jid);
		init();
	}
	private void init() {
		//标题和按钮
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		
		titleView.setText("群信息");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		
		desTitleView = (TextView)findViewById(R.id.crowd_description);
		desTitleView.setText("群描述");
		desContentView = (TextView)findViewById(R.id.crowd_des_info);
		
		crowdMemberView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.crowd_members);
		crowdMemberView.setTextView1Text("群成员");
		crowdMemberView.setTextView2Text(">");
		crowdMemberView.setOnClickListener(this);
		
		crowdInviteView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.crowd_invited);
		crowdInviteView.setTextView1Text("邀请新成员");
		crowdInviteView.setTextView2Text(">");
		crowdInviteView.setOnClickListener(this);
		
		crowdMyInfoView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.crowd_my_info);
		crowdMyInfoView.setTextView1Text("我的群名片");
		crowdMyInfoView.setTextView2Text(">");
		crowdMyInfoView.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.crowd_members:
			Intent i = new Intent();
			i.setClass(CrowdInfoActivity.this, CrowdMemberActivity.class);
			Bundle b = new Bundle();
			b.putString("jid",jid);
			i.putExtra("bd", b);
			startActivity(i);
			break;
		case R.id.crowd_invited:
			break;
		case R.id.crowd_my_info:
			break;
		default:
			break;
		}
	}

}
