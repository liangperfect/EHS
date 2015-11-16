package com.example.ehs.im;

import com.example.ehs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddFriendActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
	
	private com.example.ehs.widget.My2TextButton addFriendView;
	private com.example.ehs.widget.My2TextButton addGroupView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friends);
		init();
	}

	private void init() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		titleView.setText("添加好友/组");
		leftBtView.setVisibility(View.VISIBLE);
		
		addFriendView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.add_friend);
		addFriendView.setTextView1Text("添加好友");
		addFriendView.setTextView2Text(">");
		addFriendView.setOnClickListener(this);
		
		addGroupView = (com.example.ehs.widget.My2TextButton)findViewById(R.id.add_group);
		addGroupView.setTextView1Text("加入群");
		addGroupView.setTextView2Text(">");
		addGroupView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.add_friend:
			Intent i1 = new Intent();
			i1.setClass(AddFriendActivity.this, AddNextActivity.class);
			i1.putExtra("add", "friend");
			startActivity(i1);
			break;
		case R.id.add_group:
			Intent i2 = new Intent();
			i2.setClass(AddFriendActivity.this, AddNextActivity.class);
			i2.putExtra("add", "group");
			startActivity(i2);
			break;
			default:
				break;
		}
		
	}

}
