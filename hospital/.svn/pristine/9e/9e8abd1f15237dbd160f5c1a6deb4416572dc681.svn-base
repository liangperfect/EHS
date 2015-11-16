package com.example.ehs.im;

import java.util.List;
import java.util.Map;

import com.example.ehs.R;
import com.example.ehs.model.IMMessage;
import com.example.ehs.xmpphelper.AChatActivity;
import com.example.ehs.xmpphelper.ContacterManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 聊天界面
 * */
public class ChatActivity extends AChatActivity implements OnClickListener{
	private static final String tag = "ChatActivity";
	
    ListView listView = null;
    List<Map<String, Object>> list;
    IMMessage message ;
    String style=null;
    
    private TextView titleView;//标题
    private Button messageView;//返回消息界面
    private Button imgView;//好友信息
    
    private ImageView bqView;//表情
    private ImageView addView;//发送方式
    private EditText chatInputView;//文字输入
    private Button speakView;//按住说话
    private Button chatSendView;//语音、键盘按钮
    
    ToAdapter toAdapter;
    private MessageListAdapter adapter = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		Bundle bd = this.getIntent().getBundleExtra("info");
		style = bd.getString("style");
		System.out.println("style="+style);
		//if(style.equals("from")){
			message = bd.getParcelable("recentMessage");	
			System.out.println("message2="+message.toString());
			System.out.println("content2="+message.getContent());
		//}

	    setupView();
	}
	private void setupView() {
    	listView = (ListView) findViewById(R.id.chatlist);
    	
    	titleView = (TextView)this.findViewById(R.id.title);
    	
    	//接收的消息分新闻和消息两类
    	if(style.equals("from")){
        	titleView.setText(message.getFromSubJid());
	    	if (message.isNews()) {
	    		Log.e(tag, "it is news :"+message.getAcceptType());
	    		NewsAdapter adapter = new NewsAdapter();
	        	listView.setAdapter(adapter);
			}else {
				Log.e(tag, "it is message :"+message.getAcceptType());
				MessageAdapter adapter = new MessageAdapter();
		    	listView.setAdapter(adapter);
			}
    	}else{//发送的消息同意显示在右边
        	titleView.setText(message.getFromSubJid());
    		toAdapter = new ToAdapter();
    	}
    	
    	adapter = new MessageListAdapter(ChatActivity.this, getMessages(),
				listView);
		listView.setAdapter(adapter);
		
    	messageView = (Button)findViewById(R.id.bt_left);
    	messageView.setVisibility(View.VISIBLE);
    	messageView.setOnClickListener(this);
    	
    	imgView = (Button)findViewById(R.id.bt_right);
    	imgView.setVisibility(View.VISIBLE);
    	imgView.setOnClickListener(this);
    	
    	bqView = (ImageView)findViewById(R.id.chat_bq);
    	bqView.setOnClickListener(this);
    	
    	addView = (ImageView)findViewById(R.id.chat_add);
    	addView.setOnClickListener(this);
    	
    	chatInputView = (EditText)findViewById(R.id.chat_input);
    	chatInputView.setOnClickListener(this);
    	//当有文字时监听
    	
    	speakView = (Button)findViewById(R.id.chat_speak);
    	speakView.setOnClickListener(this);
    	
    	chatSendView = (Button)findViewById(R.id.chat_send);
    	chatSendView.setOnClickListener(this);
    }

	 public class NewsAdapter extends BaseAdapter{
			@Override
			public int getCount() {
				return 2;
			}
			@Override
			public Object getItem(int arg0) {
				return null;
			}
			@Override
			public long getItemId(int position) {
				return 0;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = getLayoutInflater().inflate(R.layout.item_news, null);
					//新闻时间
					holder.newsTime = (TextView)convertView.findViewById(R.id.newsTime);
					holder.senderAvatar = (ImageView)convertView.findViewById(R.id.newsIcon);
					holder.newsContent = (TextView)convertView.findViewById(R.id.newsTitle);
					convertView.setTag(holder);
				}else {				
					holder = (ViewHolder)convertView.getTag();
				}
				
				
				/*ImageLoader.getInstance().displayImage(txtMessage.getSenderUrl(), 
						holder.senderAvatar, IkankeApplication.getOptionsAvatar());*/
				System.out.println("content="+message.getContent());
				holder.newsContent.setText(message.getContent());
				//holder.newsContent.setText("测试数据");
				System.out.println("time="+message.getTime());
				holder.newsTime.setText(message.getTime());
				//holder.newsTime.setText("测试数据3");
				System.out.println("1111");
				
				return convertView;
			}
			
			public class ViewHolder{
				public TextView newsTime;
				public ImageView senderAvatar;
				public TextView newsContent;
			}
	    	
	    }
	 
	 
	 public class MessageAdapter extends BaseAdapter{
			@Override
			public int getCount() {
				return 2;
			}
			@Override
			public Object getItem(int arg0) {
				return null;
			}
			@Override
			public long getItemId(int position) {
				return 0;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = getLayoutInflater().inflate(R.layout.item_message, null);
					holder.messageTime = (TextView)convertView.findViewById(R.id.messageTime);
					holder.messageIcon = (ImageView)convertView.findViewById(R.id.senderAvatar);
					holder.messageTitle = (TextView)convertView.findViewById(R.id.messageContent);
					convertView.setTag(holder);
				}else {				
					holder = (ViewHolder)convertView.getTag();
				}
				
				
				/*ImageLoader.getInstance().displayImage(txtMessage.getSenderUrl(), 
						holder.messageIcon, IkankeApplication.getOptionsAvatar());*/
				
				holder.messageTitle.setText(message.getTitle());
				//设置消息时间
				holder.messageTime.setText(message.getTime());
				
				return convertView;
			}
			
			public class ViewHolder{
				public TextView messageTime;
				public ImageView messageIcon;
				public TextView messageTitle;
			}
	    	
	    }

	 public class ToAdapter extends BaseAdapter{
			@Override
			public int getCount() {
				return 2;
			}
			@Override
			public Object getItem(int arg0) {
				return null;
			}
			@Override
			public long getItemId(int position) {
				return 0;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = getLayoutInflater().inflate(R.layout.item_news, null);
					//新闻时间
					holder.newsTime = (TextView)convertView.findViewById(R.id.newsTime);
					holder.senderAvatar = (ImageView)convertView.findViewById(R.id.newsIcon);
					holder.newsContent = (TextView)convertView.findViewById(R.id.newsTitle);
					convertView.setTag(holder);
				}else {				
					holder = (ViewHolder)convertView.getTag();
				}
				
				
				/*ImageLoader.getInstance().displayImage(txtMessage.getSenderUrl(), 
						holder.senderAvatar, IkankeApplication.getOptionsAvatar());*/
				String toMessage=chatInputView.getText().toString();
				System.out.println("content="+toMessage);
				holder.newsContent.setText(toMessage);
				holder.newsContent.setBackgroundResource(R.drawable.chatfrom_bg_pressed);
				//holder.newsContent.setText("测试数据");
				long time=System.currentTimeMillis();
				System.out.println("time="+time);
				holder.newsTime.setText(""+time);
				//holder.newsTime.setText("测试数据3");
				System.out.println("1111");
				
				return convertView;
			}
			
			public class ViewHolder{
				public TextView newsTime;
				public ImageView senderAvatar;
				public TextView newsContent;
			}
	    	
	    }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.bt_right:
			Intent i = new Intent();
			i.setClass(ChatActivity.this, FriendsInfoActivity.class);
			startActivity(i);
			break;
		case R.id.chat_bq:
			Toast.makeText(this, "弹出表情况", Toast.LENGTH_SHORT).show();
			break;
		case R.id.chat_add:
			Toast.makeText(this, "弹出出送情况", Toast.LENGTH_SHORT).show();
			break;
		case R.id.chat_input:
			Toast.makeText(this, "输入文字", Toast.LENGTH_SHORT).show();
			break;
		case R.id.chat_speak:
			Toast.makeText(this, "按键时间太短", Toast.LENGTH_SHORT).show();
			break;
		case R.id.chat_send:
			//Toast.makeText(this, "更改文字和语音", Toast.LENGTH_SHORT).show();
        	//listView.setAdapter(toAdapter);
			String message = chatInputView.getText().toString();
			if ("".equals(message)) {
				Toast.makeText(ChatActivity.this, "不能为空",
						Toast.LENGTH_SHORT).show();
			} else {
				sendMessage(message);
				chatInputView.setText("");
			}
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void receiveNewMessage(IMMessage message) {
		
	}
	@Override
	protected void refreshMessage(List<IMMessage> messages) {
		adapter.refreshList(messages);
	}
	
	
	private class MessageListAdapter extends BaseAdapter {

		private List<IMMessage> items;
		private Context context;
		private ListView adapterList;

		public MessageListAdapter(Context context, List<IMMessage> items,
				ListView adapterList) {
			this.context = context;
			this.items = items;
			this.adapterList = adapterList;
		}

		public void refreshList(List<IMMessage> items) {
			this.items = items;
			this.notifyDataSetChanged();
			adapterList.setSelection(items.size() - 1);
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			IMMessage message = items.get(position);
			//接收的信息
			if(style.equals("from")){
				if(message.isNews()){
					View  view1 = ChatActivity.this.getLayoutInflater().inflate(R.layout.item_news, null);//新闻
					TextView timeView = (TextView)view1.findViewById(R.id.newsTime);//新闻发送 时间
					ImageView avaterView = (ImageView) view1.findViewById(R.id.newsIcon);//新闻发送者图标
					TextView contentView = (TextView)view1.findViewById(R.id.newsTitle);//新闻标题
				}else{
					View  view1 = ChatActivity.this.getLayoutInflater().inflate(R.layout.item_message, null);//消息
					TextView timeView = (TextView)view1.findViewById(R.id.messageTime);//消息发送 时间
					ImageView avaterView = (ImageView) view1.findViewById(R.id.senderAvatar);//消息发送者头像
					TextView contentView = (TextView)view1.findViewById(R.id.messageContent);//消息内容
				}
			}else{//发送消息
				
			}
			
			TextView view = null;
			if (convertView == null) {
				convertView = new TextView(context);
			}
			view = (TextView) convertView;

			view.setTextSize(16);
			view.setTextColor(Color.WHITE);
			view.setBackgroundColor(Color.BLACK);
			String userName = "我 ";
			if (message.getFromSubJid() != null && message.getMsgType() != 1)
				userName = ContacterManager.contacters.get(
						message.getFromSubJid()).getName();
			view.setText(userName + "  说：  " + message.getTime() + "\n   "
					+ message.getContent());
			return view;
		}

	}
}
