package com.example.ehs.im;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.OfflineMessageManager;

import com.example.ehs.R;
import com.example.ehs.db.IMMessageDb;
import com.example.ehs.model.Friends;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.User;
import com.example.ehs.model.UserInfo;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.widget.XListView;
import com.example.ehs.widget.XListView.IXListViewListener;

import android.app.Activity;
import android.content.Context;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * 最近聊天界面
 * */
public class MessageActivity extends Activity implements OnClickListener,OnItemClickListener,IXListViewListener {
	private TextView titleView;
	private Button leftBtView;
	//private Button rightBtView;
	XListView listView;
	RecentlyAdapter adapter;
	//SimpleAdapter adapter;
	ArrayList<Friends> friendsList;
	protected View layoutLoad;
	protected TextView promptInfo;
	protected ProgressBar loading;
	private ImageView searchView;
	private IMMessageDb messageDb=null;
	//消息
	IMMessage message=null;
	UserInfo userInfo=null;
	List<HashMap<String, Object>> list1=null;
	HashMap<String, Object> map = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		messageDb = new IMMessageDb(MessageActivity.this);
		userInfo = new UserInfo(MessageActivity.this);
		setupView();
		list1 = updateView();
		 System.out.println("length="+updateView().size());
		 adapter = new RecentlyAdapter(list1, MessageActivity.this);
		 listView.setAdapter(adapter);
	}

	private void setupView() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		//rightBtView = (Button)findViewById(R.id.bt_right);
		
		titleView.setText("消息");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		//rightBtView.setVisibility(View.VISIBLE);
		//rightBtView.setOnClickListener(this);
		
		
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
	
	private List<HashMap<String, Object>> updateView() {
		List<HashMap<String, Object>>list = new ArrayList<HashMap<String,Object>>();
		/*OfflineMessageManager offlineManager = new OfflineMessageManager(ConnectionUtils.getConnection(MessageActivity.this));
            try {
            	System.out.println("111");
                Iterator<org.jivesoftware.smack.packet.Message> it = offlineManager.getMessages();
                IMMessage imMessage = null;
                while(it.hasNext()){
                    org.jivesoftware.smack.packet.Message message = it.next();
                    imMessage = new IMMessage();
                    imMessage.setContent(message.getBody());
                    imMessage.setFromSubJid(message.getFrom().split("/")[0]);
                    String time = (String) message.getProperty(IMMessage.KEY_TIME);
					imMessage.setTime(time == null ? ConnectionUtils.getStringTime(): time);
					if (Message.Type.error == message.getType()) {
						imMessage.setType(IMMessage.ERROR);
					} else {
						imMessage.setType(IMMessage.SUCCESS);
					}
                    imMessage.setUnReadCount(1);
                    messageDb.saveMessage(imMessage, message.getFrom(),userInfo.getAccount());
                    System.out.println("222");
                }
                //删除离线消息
				offlineManager.deleteMessages();
			} catch (XMPPException e) {
				e.printStackTrace();
				System.out.println("333");
			}
            //将状态设置成在线
            Presence presence = new Presence(Presence.Type.available);
            ConnectionUtils.getConnection(MessageActivity.this).sendPacket(presence);*/		
            System.out.println("444");      
            List<String> testlst = messageDb.getAllFriends(userInfo.getAccount());
            System.out.println("testlstSize="+testlst.size());
            List<IMMessage> listmsg = new ArrayList<IMMessage>();
            IMMessage msg = null;
            for(int i=0;i<testlst.size();i++){
	        	System.out.println(testlst.get(i));
	        	msg = new IMMessage();
	        	listmsg = messageDb.getAllMessage(userInfo.getAccount(), testlst.get(i), null);
	        	msg = listmsg.get(listmsg.size()-1);
	        	System.out.println("msg="+msg.toString());
	        	map = new HashMap<String, Object>();
	        	map.put("img", R.drawable.default_avatar);
	        	map.put("unRead", 1);
	        	map.put("id", testlst.get(i));
	        	String tempStr = msg.getContent();
	        	int chatMode = msg.getChatMode();
	        	if(tempStr.startsWith(";")){
	        		chatMode = Integer.parseInt(tempStr.substring(1,2));
	        		tempStr = tempStr.substring(2);
	        	}
	        	if(chatMode == 0){
	        		map.put("words", tempStr);
	        	}
	        	if(chatMode == 1){
	        		map.put("words", "[图片]");
	        	}
	        	if(chatMode == 2){
	        		map.put("words", "[文件]");
	        	}
	        	if(chatMode == 3){
	        		map.put("words", "[语音]");
	        	}
	        	map.put("time", msg.getTime());
	        	list.add(map);
            }
            System.out.println("555");
           // adapter = new RecentlyAdapter(list,MessageActivity.this);	
           // requestData();
			return list;
	}
	
	protected int status = LoadStatus.loadSucceed;
	
	private void requestData() {
		if (!(status == LoadStatus.loadLoadMore) && !(status == LoadStatus.loadRefresh)) {
			status = LoadStatus.loading;
			loadViewDisplay();
		}
		
		loadEnd();
		status = LoadStatus.loadSucceed;
		loadViewDisplay();
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		System.out.println("onResume");
		list1 = updateView();
		 System.out.println("length="+updateView().size());
		 adapter = new RecentlyAdapter(list1, MessageActivity.this);
		 listView.setAdapter(adapter);
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
		map = list1.get(position-1);
		String jid = ""+map.get("id");
		Friends friends = new Friends();
		friends.setJID(jid);
		Intent intent = new Intent();
		intent.setClass(MessageActivity.this, ChatActivity.class);
		Bundle bd = new Bundle();
		bd.putString("style","from");
		bd.putParcelable("friends", friends);
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
	private List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
	private Context context=null;
	public RecentlyAdapter(List<HashMap<String, Object>> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}
		@Override
		public int getCount() {
			return list.size();
		}
		@Override
		public Object getItem(int position) {
			return list.get(position);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			HashMap<String, Object> map1 = list.get(position);
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.user_item, null);
				holder.userAvatar = (ImageView)convertView.findViewById(R.id.userAvatar);
				holder.unReadCount = (TextView)convertView.findViewById(R.id.messageCount);
				holder.userID = (TextView)convertView.findViewById(R.id.userID);
				holder.userName = (TextView)convertView.findViewById(R.id.userName);
				holder.userRecentMessage = (TextView)convertView.findViewById(R.id.userRecentMessage);
				holder.time = (TextView)convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {				
				holder = (ViewHolder)convertView.getTag();
			}
			if(Integer.parseInt(""+map1.get("unRead"))<=0){
				holder.unReadCount.setVisibility(View.GONE);
			}else{
				holder.unReadCount.setText(map1.get("unRead")+"");
			}
			holder.userID.setText(""+map1.get("id"));
			holder.userName.setText("");
			holder.userRecentMessage.setText(""+map1.get("words"));
			holder.time.setText(""+map1.get("time"));
			return convertView;
		}
		
		public class ViewHolder {
			public ImageView userAvatar;
			public TextView unReadCount;
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
