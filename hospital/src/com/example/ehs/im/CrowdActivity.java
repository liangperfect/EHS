package com.example.ehs.im;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jivesoftware.smack.XMPPException;

import com.example.ehs.R;
import com.example.ehs.model.Friends;
import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.utils.XmppTool;
import com.example.ehs.widget.NPullToFreshContainer;
import com.example.ehs.widget.NPullToFreshContainer.OnContainerRefreshListener;
import com.example.ehs.xmpphelper.ContacterManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CrowdActivity extends Activity implements OnClickListener,OnContainerRefreshListener{
	private TextView titleView;
	private Button leftBtView;
	private Button rightBtView;
	
	//下拉刷新
	private NPullToFreshContainer iPulltoRefresh;
	
	//search
	private RelativeLayout search;
	private ImageView searchView;
	private EditText searchInputView;
	
	//列表显示相关
	private ListView crowdList = null;
	private List<HashMap<String, Object>> crowds = new ArrayList<HashMap<String,Object>>();
	private SimpleAdapter adapter2 = null;
	UserInfo userInfo=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crowd);
		userInfo = new UserInfo(this);
		System.out.println("username"+userInfo.getAccount());
		init();
	}

	private void init() {
		//下拉刷新
		iPulltoRefresh =(NPullToFreshContainer)findViewById(R.id.pulltofresh_1);
		iPulltoRefresh.setOnRefreshListener(this);
		//标题和按钮
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		rightBtView = (Button)findViewById(R.id.bt_right);//添加好友和加入群
					
		titleView.setText("群");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		rightBtView.setBackgroundResource(R.drawable.btn_add);
		rightBtView.setVisibility(View.VISIBLE);
		rightBtView.setOnClickListener(this);
		
		//search
		search = (RelativeLayout)findViewById(R.id.search_friends);
		searchInputView = (EditText)findViewById(R.id.search_input);
				
		searchView = (ImageView)findViewById(R.id.search_img);
		searchView.setOnClickListener(this);
		
		crowdList = (ListView)findViewById(R.id.crowd_list);
		crowds = ContacterManager.getCrowds(ConnectionUtils.getConnection(CrowdActivity.this));
		adapter2 = new SimpleAdapter(this, crowds, R.layout.crowd_item, new String[]{"avater","name","jid"}, new int[]{R.id.crowd_avater,R.id.crowd_name,R.id.crowd_id});
		crowdList.setAdapter(adapter2);
		crowdList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				TextView crowd_id = (TextView)v.findViewById(R.id.crowd_id);
				TextView crowd_name = (TextView)v.findViewById(R.id.crowd_name);
				System.out.println("群号："+crowd_id.getText().toString());
				System.out.println("群名字："+crowd_name.getText().toString());
				Intent i = new Intent();
				i.setClass(CrowdActivity.this,CrowdChatActivity.class);
				Bundle bd = new Bundle();
				bd.putString("jid", crowd_id.getText().toString());
				bd.putString("name", crowd_name.getText().toString());
				i.putExtra("crow", bd);
				//bd.putString("style", "crowd");
				//i.putExtra("info", bd);
				startActivity(i);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_right:
			addCrowd();
			break;
		case R.id.search_img:
			Toast.makeText(CrowdActivity.this, "查找群", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
//添加到聊天室
	private void addCrowd() {
		final EditText name_input = new EditText(CrowdActivity.this);
		name_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		name_input.setHint("请输入要加入的群号");	
		name_input.setBackgroundResource(android.R.drawable.editbox_background);
		new AlertDialog.Builder(CrowdActivity.this).setTitle("加入群")
		.setView(name_input)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String n = name_input.getText().toString();
				if ("".equals(n)) {
					n = null;
				}
				try {
					//加入群操作
					//群名，以及自己的标记jid
					ConnectionUtils.joinMultiChat(n, userInfo.getAccount());
				} catch (Exception e) {
				}
			}
		}).setNegativeButton("取消", null).show();
	}

	@Override
	public void onContainerRefresh() {
		
	}	
}
