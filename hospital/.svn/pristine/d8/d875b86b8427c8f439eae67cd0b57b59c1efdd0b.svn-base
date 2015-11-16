package com.example.ehs.im;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.example.ehs.R;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.xmpphelper.ContacterManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CrowdMemberActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
	
	private ListView memberListView;
	
	SimpleAdapter adapter=null;
	List<HashMap<String, Object>> list=null;
	//HashMap<String, Object> map = null;
	
	String jid=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crowd_menber);
		Bundle b = getIntent().getBundleExtra("bd");
		jid = b.getString("jid");
		System.out.println("jid="+jid);
		init();
	}
	public void init(){
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText("成员信息");
		
		leftBtView = (Button)findViewById(R.id.bt_left);
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
		
		memberListView = (ListView)findViewById(R.id.menber_list);
		list = new ArrayList<HashMap<String,Object>>();
		final MultiUserChat muc = new MultiUserChat(ConnectionUtils.getConnection(this), jid);  
		list = ContacterManager.getAllMember(muc);
		/* new Thread(new Runnable() {  
	    //        @Override  
	      //      public void run() {  
	                try {  
	                    Iterator<String> it = muc.getOccupants(); 
	             	   HashMap<String, Object> map = null;
	                    while (it.hasNext()) {  
	                        String name = it.next();  
	                        name = name.substring(name.indexOf("/") + 1);  
	                       System.out.println("成员名字:" + name);  
	                       map = new HashMap<String, Object>();
	                       map.put("img", R.drawable.default_avatar);
	               		   map.put("name", name);
	               		   map.put("state", "管理员");
	               		   list.add(map);
	                    }  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	      //      }  
	    //    }).start();
		 System.out.println("list_length="+list.size());
		/*map = new HashMap<String, Object>();
		map.put("img", R.drawable.default_avatar);
		map.put("name", "hcq");
		map.put("state", "管理员");
		list.add(map);*/
		adapter = new SimpleAdapter(this, list, R.layout.crowd_item, new String[]{"img","name","state"}, 
				new int[]{R.id.crowd_avater,R.id.crowd_name,R.id.crowd_id});
		memberListView.setAdapter(adapter);
		memberListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(CrowdMemberActivity.this, "点击了"+arg2, Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;

		default:
			break;
		}
	}
}
