package com.example.ehs.im;

import java.util.ArrayList;

import com.example.ehs.R;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.User;
import com.example.ehs.widget.XListView;
import com.example.ehs.widget.XListView.IXListViewListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * 最近聊天界面
 * */
public class MessageActivity extends Activity implements OnClickListener,OnItemClickListener,IXListViewListener {
	private TextView titleView;
	private Button leftBtView;
	private Button rightBtView;
	
	XListView listView;
	RecentlyAdapter adapter;
	ArrayList<User> userList;
	
	protected View layoutLoad;
	protected TextView promptInfo;
	protected ProgressBar loading;
	
	private ImageView searchView;
	
	//消息
	IMMessage message=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		
		setupView();
		
		updateView();
	}

	private void setupView() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		rightBtView = (Button)findViewById(R.id.bt_right);
		
		titleView.setText("消息");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		rightBtView.setVisibility(View.VISIBLE);
		rightBtView.setOnClickListener(this);
		
		
		listView = (XListView)this.findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		listView.setXListViewListener(this);
		listView.setPullRefreshEnable(true);
		// Need not to load more.
		listView.setPullLoadEnable(false);
		
		//View headerView = this.getLayoutInflater().inflate(R.layout.search, null);		
		//listView.addHeaderView(headerView);
		searchView = (ImageView)findViewById(R.id.search_img);
		searchView.setOnClickListener(this);
		
		// 专门为下拉
		layoutLoad = this.findViewById(R.id.layout_load);
		promptInfo = (TextView)this.findViewById(R.id.prompt_info);
		loading = (ProgressBar)this.findViewById(R.id.loading);
		promptInfo.setOnClickListener(this);
	}
	
	private void updateView() {
		userList = new ArrayList<User>();


		adapter = new RecentlyAdapter();	
		listView.setAdapter(adapter);	
		requestData();
	}
	
	protected int status = LoadStatus.loadSucceed;
	
	private void requestData() {
		if (!(status == LoadStatus.loadLoadMore) && !(status == LoadStatus.loadRefresh)) {
			status = LoadStatus.loading;
			loadViewDisplay();
		}
		//获取列表，更改相应的状态
		userList.add(new User("hca","123","456","url","开会了"));
		userList.add(new User("hca","123","456","url","开会了"));
		userList.add(new User("hca","123","456","url","开会了"));
		loadEnd();
		status = LoadStatus.loadSucceed;
		loadViewDisplay();
		adapter.notifyDataSetChanged();
		// TXTService.getAttentionList(new JsonHttpResponseHandler() {
			
		//	@Override
		//	public void onFinish() {
		//		System.out.println("onFinish");
			//	super.onFinish();
			//	loadEnd();
			//	status = LoadStatus.loadSucceed;
			//	loadViewDisplay();
				//adapter.notifyDataSetChanged();
			//}
			
			//@Override
			//public void onStart() {
			//	System.out.println("onStart");
			//	super.onStart();
		//	}
			
			//@Override
			//public void onFailure(Throwable arg0,
			//		JSONObject arg1) {
			//	super.onFailure(arg0, arg1);
			//	Log.e("onFailure", arg1.toString());
			//}
			
			//@Override
			//public void onSuccess(int arg0, JSONObject rlst) {
				//super.onSuccess(arg0, rlst);
				//System.out.println("onSuccess");
				//System.out.println("status: " + arg0);
				//System.out.println("onSuccess: " + rlst.toString());
				//try {
				//	if (arg0 == 200) {
				//		parseJSON(rlst);
					//} else {
					//	Toast.makeText(InterestMainActivity.this, 
								///rlst.getString("msg"), Toast.LENGTH_SHORT).show();
					//}
				//} catch (JSONException e) {
					//e.printStackTrace();
				//}
			//}
			
			//public void parseJSON(JSONObject rlst) throws JSONException {
			//	JSONArray jArray = rlst.getJSONArray("data");
			//	// Clear old list.
				//userInfoList.clear();
				//for (int i = 0; i < jArray.length(); i++) {
				//	System.out.println("jArray" + i + ": " + jArray.getJSONObject(i));
					//String activityStr = "";
					// Get the user's recent activity, if he has.
					//if (!(jArray.getJSONObject(i).getString("activity").equals(activityStr))) {
					//	String str[] = jArray.getJSONObject(i).getString("activity").split("=>");
					//	String str2[] = str[3].split("\"");
					//	activityStr = str2[1];
					//	System.out.println(activityStr);
					//}
				//	userInfoList.add(new UserInfo(jArray.getJSONObject(i).getString("avatar"),
						//	jArray.getJSONObject(i).getString("studentID"),
						//	jArray.getJSONObject(i).getString("name"),
						//	activityStr));
				//}
			//}
		//});* /
		 
	}

	@Override
	protected void onResume() {
		System.out.println("onResume");
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh() {
		status = LoadStatus.loadRefresh;
		requestData();
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		User user = userList.get(position-1);
		Intent intent = new Intent();
		intent.setClass(MessageActivity.this, ChatActivity.class);
		message = new IMMessage();
		message.setFromSubJid(user.getAccount());
		message.setFromSubName(user.getUserName());
		message.setToSubJid("");
		message.setToSubName("");
		message.setTime("14:23");
		message.setContent(user.getRecentMessage());
		message.setAcceptType(2);//是新闻还是消息
		message.setType(1);
		message.setInfoUrl(user.getAvater());
		message.setMsgType(1);
		message.setTitle("找你了");
		message.setUnReadCount(1);
		System.out.println("message="+message);
		Bundle bd = new Bundle();
		bd.putString("style","from");
		bd.putParcelable("recentMessage", message);
		intent.putExtra("info", bd);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_right:
			Toast.makeText(MessageActivity.this, "更多操作", Toast.LENGTH_SHORT).show();
			break;
		case R.id.prompt_info:
			doRefresh();
			break;
		case R.id.search_img:
			Toast.makeText(MessageActivity.this, "正在搜索..。", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onLoadMore() {
		status = LoadStatus.loadLoadMore;
		requestData();
	}
	
	protected void loadEnd() {
		listView.stopRefresh();
		listView.stopLoadMore();
		//listview.setRefreshTime("刚刚");
	}
	private void doRefresh() {
		
	}


public class RecentlyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return userList.size();
		}
		@Override
		public Object getItem(int position) {
			return userList.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.user_item, null);
				holder.userAvatar = (ImageView)convertView.findViewById(R.id.userAvatar);
				holder.userID = (TextView)convertView.findViewById(R.id.userID);
				holder.userName = (TextView)convertView.findViewById(R.id.userName);
				holder.userRecentMessage = (TextView)convertView.findViewById(R.id.userRecentMessage);
				holder.time = (TextView)convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {				
				holder = (ViewHolder)convertView.getTag();
			}
			
			User user = userList.get(position);
			/*ImageLoader.getInstance().displayImage(TXTClient.getImageUrl(userInfo.userAvatar), 
					holder.userAvatar, IkankeApplication.getOptionsAvatar());*/
			holder.userID.setText(user.getAccount());
			//holder.userName.setText(user.getUserName());
			//holder.userRecentMessage.setText(userInfo.userRecentActivity);
			//holder.time.setText();
			return convertView;
		}
		
		public class ViewHolder {
			public ImageView userAvatar;
			public TextView userID, userName;
			public TextView userRecentMessage;
			public TextView time;
		}
	}

	private void loadViewDisplay() {
		listView.setVisibility(status==LoadStatus.loadSucceed?View.VISIBLE:View.INVISIBLE);
		promptInfo.setVisibility(status==LoadStatus.loadError?View.VISIBLE:View.INVISIBLE);
		loading.setVisibility(status==LoadStatus.loading?View.VISIBLE:View.INVISIBLE);
		layoutLoad.setVisibility((status==LoadStatus.loadError||status==LoadStatus.loading)?View.VISIBLE:View.INVISIBLE);
	}
	protected class LoadStatus {
		public static final int loading = 0;
		public static final int loadError = 1;
		public static final int loadSucceed = 2;
		
		public static final int loadRefresh = 3;
		public static final int loadLoadMore = 4;
}
}
