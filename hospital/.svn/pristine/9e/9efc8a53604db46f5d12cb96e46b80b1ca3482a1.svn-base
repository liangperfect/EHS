package com.example.ehs.im;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.example.ehs.R;
import com.example.ehs.model.Friends;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.widget.NPullToFreshContainer;
import com.example.ehs.widget.NPullToFreshContainer.OnContainerRefreshListener;
import com.example.ehs.xmpphelper.AContacterActivity;
import com.example.ehs.xmpphelper.ContacterManager;
import com.example.ehs.xmpphelper.ContacterManager.MRosterGroup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * 好友管理界面
 * */
public class GroupActivity extends AContacterActivity implements OnClickListener,OnContainerRefreshListener{
	private TextView titleView;
	private Button leftBtView;
	private Button rightBtView;
	
	//search
	private ImageView searchView;
	//others
	private Button groupView;
	private Button disscussView;
	private Button serviceView;
	
	private ExpandableListView contacterList = null;
	private ContacterExpandAdapter expandAdapter = null;
	private ListView inviteList = null;
	private InviteAdapter inviteAdapter = null;
	private List<Friends> inviteUsers = new ArrayList<Friends>();
	
	private List<MRosterGroup> groups=null;
	
	 /** Called when the activity is first created. */
    LinearLayout lay1,lay2,lay;
    private Scroller mScroller;
    private boolean s1,s2;
    private NPullToFreshContainer iPulltoRefresh;
    ScrollView mScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		init();
	}

	private void init(){
		iPulltoRefresh =(NPullToFreshContainer)findViewById(R.id.pulltofresh);
    	iPulltoRefresh.setOnRefreshListener(this);
    	
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		rightBtView = (Button)findViewById(R.id.bt_right);
		
		titleView.setText("联系人");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		rightBtView.setVisibility(View.VISIBLE);
		rightBtView.setOnClickListener(this);
		
		//search
		searchView = (ImageView)findViewById(R.id.search_img);
		searchView.setOnClickListener(this);
		
		groupView = (Button)findViewById(R.id.group);
		groupView.setOnClickListener(this);
		
		disscussView = (Button)findViewById(R.id.disscus);
		disscussView.setOnClickListener(this);
		
		serviceView = (Button)findViewById(R.id.service);
		serviceView.setOnClickListener(this);
		
		contacterList = (ExpandableListView) findViewById(R.id.main_expand_list);
		contacterList.setGroupIndicator(this.getResources().getDrawable(R.drawable.indocator));
		expandAdapter = new ContacterExpandAdapter(
				ContacterManager.getGroups(ConnectionUtils.getConnection()
						.getRoster(),ConnectionUtils.getConnection()));
		contacterList.setAdapter(expandAdapter);

		inviteList = (ListView) findViewById(R.id.main_invite_list);
		inviteAdapter = new InviteAdapter();
		inviteList.setAdapter(inviteAdapter);
		inviteList.setOnItemClickListener(inviteListClick);
	}
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private OnClickListener contacterOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			System.out.println("friends="+(Friends) v.getTag());
			createChat((Friends) v.getTag());
		}
	};
	private Friends clickUser = null;
	private OnLongClickListener contacterOnLongClick = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			String[] longClickItems = null;
			clickUser = (Friends) v.getTag();
			if (clickUser.getGroupName() != null) {
				longClickItems = new String[] { "设置昵称", "添加好友", "删除好友", "加入组",
						"更改组名", "退出该组" };
			} else {
				longClickItems = new String[] { "设置昵称", "添加好友", "删除好友", "加入组" };
			}
			new AlertDialog.Builder(MContext)
					.setItems(longClickItems,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									// 设置昵称
									case 0:
										setNickname(clickUser);
										break;
									// 添加好友
									case 1:
										addSubscriber();
										break;
									// 删除好友
									case 2:
										try {
											removeSubscriber(clickUser.getJID());
										} catch (XMPPException e) {
										}
										break;
									// 加入组
									case 3:
										addToGroup(clickUser);
										break;
									// 更改组名
									case 4:
										updateGroupNameA(clickUser
												.getGroupName() + "");
										break;
									// 移出组
									case 5:
										removeUserFromGroup(clickUser,
												clickUser.getGroupName());
										break;
									}
								}
							}).setTitle("选项").show();
			return false;
		}
	};
	/**
	 * 设置昵称
	 * 
	 * @param user
	 */
	private void setNickname(final Friends friends) {
		final EditText name_input = new EditText(GroupActivity.this);
		name_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		name_input.setHint("输入昵称");
		new AlertDialog.Builder(GroupActivity.this).setTitle("修改昵称")
				.setView(name_input)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name = name_input.getText().toString();
						if (!"".equals(name))
							setNickname(friends, name);
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 添加好友
	 */
	private void addSubscriber() {
		final EditText name_input = new EditText(GroupActivity.this);
		name_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		name_input.setHint("输入JID");
		final EditText nickname = new EditText(GroupActivity.this);
		nickname.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		nickname.setHint("输入昵称");
		LinearLayout layout = new LinearLayout(GroupActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		layout.addView(name_input);
		layout.addView(nickname);
		new AlertDialog.Builder(GroupActivity.this).setTitle("添加好友")
				.setView(layout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String n = nickname.getText().toString();
						if ("".equals(n)) {
							n = null;
						}
						try {
							createSubscriber(name_input.getText().toString(),
									n, null);
						} catch (XMPPException e) {
						}
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 加入组
	 * 
	 * @param user
	 */
	private void addToGroup(final Friends friends) {
		final EditText name_input = new EditText(GroupActivity.this);
		name_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		name_input.setHint("输入组名");
		new AlertDialog.Builder(GroupActivity.this).setTitle("加入组")
				.setView(name_input)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String groupName = name_input.getText().toString();
						if (!"".equals(groupName))
							addUserToGroup(friends, groupName);
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 修改组名
	 * 
	 * @param user
	 */
	private void updateGroupNameA(final String groupName) {
		final EditText name_input = new EditText(GroupActivity.this);
		name_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		name_input.setHint("输入组名");
		new AlertDialog.Builder(GroupActivity.this).setTitle("修改组名")
				.setView(name_input)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						updateGroupName(groupName, name_input.getText()
								.toString());
					}
				}).setNegativeButton("取消", null).show();
	}

	private OnItemClickListener inviteListClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			final String subFrom = view.getTag().toString();
			new AlertDialog.Builder(MContext)
					.setMessage(subFrom + "请求添加您为好友")
					.setTitle("提示")
					.setPositiveButton("添加",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 接受请求
									sendSubscribe(Presence.Type.subscribed,
											subFrom);
									sendSubscribe(Presence.Type.subscribe,
											subFrom);
									removeInviteUser(subFrom);
								}
							})
					.setNegativeButton("拒绝",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									sendSubscribe(Presence.Type.unsubscribe,
											subFrom);
									removeInviteUser(subFrom);
								}
							}).show();
		}
	};

	private class InviteAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return inviteUsers.size();
		}

		@Override
		public Object getItem(int position) {
			return inviteUsers.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView view = new TextView(GroupActivity.this);
			Friends friends = inviteUsers.get(position);
			view.setPadding(10, 8, 8, 0);
			view.setText(friends.getName() + "\n  邀请聊天");
			view.setTextColor(Color.BLACK);
			view.setTextSize(23);
			view.setTag(friends.getName());
			return view;
		}

	}

	private class ContacterExpandAdapter extends BaseExpandableListAdapter {

		private List<MRosterGroup> groups = null;

		public ContacterExpandAdapter(List<MRosterGroup> groups) {
			this.groups = groups;
		}

		public void setContacter(List<MRosterGroup> groups) {
			this.groups = groups;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return groups.get(groupPosition).getFriends().get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			 convertView= GroupActivity.this.getLayoutInflater().inflate(R.layout.group_item, null);
			ImageView imageView1 = (ImageView)convertView.findViewById(R.id.userAvatar_1);
			TextView textView1 = (TextView)convertView.findViewById(R.id.userName_1);
			TextView textView2 = (TextView)convertView.findViewById(R.id.userID_1);
			TextView textView3 = (TextView)convertView.findViewById(R.id.state_and_words);
			ImageView imageView2 = (ImageView)convertView.findViewById(R.id.login_where);
			//TextView view = new TextView(GroupActivity.this);
			Friends friends = groups.get(groupPosition).getFriends().get(childPosition);
		//	view.setPadding(10, 8, 8, 0);
			if (friends.isAvailable()) {
				//view.setText(friends.getName() + "---" + friends.getStatus());
				//view.setTextColor(Color.WHITE);
				textView1.setText(friends.getName());
				textView2.setText(friends.getJID());
				textView3.setText("["+friends.getStatus()+"]");
			} else {
				//view.setText(friends.getName());
				//view.setTextColor(Color.RED);
				textView1.setText(friends.getName());
				textView2.setText(friends.getJID());
				textView3.setText("["+"离开"+"]");
				imageView2.setVisibility(View.GONE);
			}
			if (groupPosition > 1) {
				friends.setGroupName(groups.get(groupPosition).getName());
			}
			convertView.setOnLongClickListener(contacterOnLongClick);
			//view.setTextSize(23);
			convertView.setTag(friends);
			convertView.setOnClickListener(contacterOnClick);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return groups.get(groupPosition).getCount();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groups.get(groupPosition).getFriends();
		}

		@Override
		public int getGroupCount() {
			return groups.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView view = new TextView(GroupActivity.this);
			view.setText(groups.get(groupPosition).getName() + " ("
					+ groups.get(groupPosition).getCount() + ")");
			view.setPadding(10, 8, 8, 0);
			view.setTextColor(Color.DKGRAY);
			view.setTextSize(20);
			view.setTag(groups.get(groupPosition).getName());
			return view;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}

	/**
	 * 刷新当前的列表
	 */
	private void refreshList() {
		groups = ContacterManager.getGroups(ConnectionUtils.getConnection().getRoster(),ConnectionUtils.getConnection());
		if(groups.size()<=0){
			Toast.makeText(GroupActivity.this, "你还没有好友！", Toast.LENGTH_SHORT).show();
		}else{
			expandAdapter.setContacter(groups);
			expandAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void addUserReceive(Friends friends) {
		refreshList();
	}

	@Override
	protected void deleteUserReceive(Friends friends) {
		Toast.makeText(MContext,
				(friends.getName() == null) ? friends.getJID() : friends.getName()
						+ "被删除了", Toast.LENGTH_SHORT).show();
		refreshList();
	}

	@Override
	protected void changePresenceReceive(Friends friends) {
		// 下线
		if (!friends.isAvailable())
			if (ContacterManager.contacters.get(friends.getJID()).isAvailable())
				Toast.makeText(
						MContext,
						(friends.getName() == null) ? friends.getJID() : friends
								.getName() + "上线了", Toast.LENGTH_SHORT).show();
		// 上线
		if (friends.isAvailable())
			if (!ContacterManager.contacters.get(friends.getJID()).isAvailable())
				Toast.makeText(
						MContext,
						(friends.getName() == null) ? friends.getJID() : friends
								.getName() + "下线了", Toast.LENGTH_SHORT).show();
		refreshList();
	}

	@Override
	protected void updateUserReceive(Friends friends) {
		refreshList();
	}

	@Override
	protected void subscripUserReceive(final String subFrom) {
		Friends friends = new Friends();
		friends.setName(subFrom);
		inviteUsers.add(friends);
		inviteAdapter.notifyDataSetChanged();
	}

	/**
	 * 从聊天邀请人中删除一个条目
	 * 
	 * @param subFrom
	 */
	private void removeInviteUser(String subFrom) {
		for (Friends user : inviteUsers) {
			if (subFrom.equals(user.getName())) {
				inviteUsers.remove(user);
				break;
			}
		}
		inviteAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			//Toast.makeText(GroupActivity.this, "管理", Toast.LENGTH_SHORT).show();
			this.finish();
			break;
		case R.id.bt_right:
			Toast.makeText(GroupActivity.this, "添加好友", Toast.LENGTH_SHORT).show();
			break;
		case R.id.search_img:
			Toast.makeText(GroupActivity.this, "查找好友", Toast.LENGTH_SHORT).show();
			break;
		case R.id.group:
			Toast.makeText(GroupActivity.this, "群", Toast.LENGTH_SHORT).show();
			break;
		case R.id.disscus:
			Toast.makeText(GroupActivity.this, "讨论组", Toast.LENGTH_SHORT).show();
			break;
		case R.id.service:
			Toast.makeText(GroupActivity.this, "生活服务", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public void onContainerRefresh() {
		contacterList = (ExpandableListView) findViewById(R.id.main_expand_list);
		expandAdapter = new ContacterExpandAdapter(
		ContacterManager.getGroups(ConnectionUtils.getConnection()
						.getRoster(),ConnectionUtils.getConnection()));
		contacterList.setAdapter(expandAdapter);
		inviteList = (ListView) findViewById(R.id.main_invite_list);
		inviteAdapter = new InviteAdapter();
		inviteList.setAdapter(inviteAdapter);
		inviteList.setOnItemClickListener(inviteListClick);	
		Date vdate = new Date();
		iPulltoRefresh.onComplete(vdate.toLocaleString());
	}	
	private class MyThread extends Thread{
		@Override
		public void run() {
			contacterList = (ExpandableListView) findViewById(R.id.main_expand_list);
			expandAdapter = new ContacterExpandAdapter(
					ContacterManager.getGroups(ConnectionUtils.getConnection()
							.getRoster(),ConnectionUtils.getConnection()));
			//contacterList.setAdapter(expandAdapter);

			//inviteList = (ListView) findViewById(R.id.main_invite_list);
			//inviteAdapter = new InviteAdapter();
			//inviteList.setAdapter(inviteAdapter);
			//inviteList.setOnItemClickListener(inviteListClick);		
		}
		
	}
	/*public void onCreateEx(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScroller = new Scroller(this);
         lay1 = new LinearLayout(this){
             @Override 
             public void computeScroll() { 
                 if (mScroller.computeScrollOffset()) { 
                     scrollTo(mScroller.getCurrX(), 0); 
                     postInvalidate(); 
                 } 
             } 

         };
         lay2 = new LinearLayout(this){
             @Override 
             public void computeScroll() { 
                 if (mScroller.computeScrollOffset()) { 
                     scrollTo(mScroller.getCurrX(), 0); 
                     postInvalidate(); 
                 } 
             } 

         };
         
        lay1.setBackgroundColor(this.getResources().getColor(android.R.color.darker_gray));
        lay2.setBackgroundColor(this.getResources().getColor(android.R.color.white));
        lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams p0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);     
        this.setContentView(lay, p0);
        
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);     
        p1.weight=1;
        lay.addView(lay1,p1);
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);     
        p2.weight=1;
        lay.addView(lay2,p2);
        Button tx = new Button(this);
        Button tx2 = new Button(this);
        tx.setText("aaaaaaaaa");
        tx2.setText("bbbbbbbb");
        tx.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!s1){
                    mScroller.startScroll(0, 0, 10, 10,10);
                    s1 = true;
                }else{
                    mScroller.startScroll(0, 0, -10, -10,10);
                    s1 = false;
                }
                lay2.postInvalidate();
            }
            
        });
        tx2.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!s2){
                    mScroller.startScroll(0, 0, 20, 20,10);
                    s2=true;
                }else{
                    mScroller.startScroll(20, 20, -20, -20,10);
                    s2=false;
                }
            
            }
            
        });
        lay1.addView(tx);
        lay2.addView(tx2);
    }*/
	
}
