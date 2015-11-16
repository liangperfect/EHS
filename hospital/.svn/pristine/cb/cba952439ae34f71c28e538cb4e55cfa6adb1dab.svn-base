package com.example.ehs;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.ehs.entertainments.EntertainMentActivity;
import com.example.ehs.hospital.HospitalActivity;
import com.example.ehs.im.IMActivity;
import com.example.ehs.notice.NoticeActivity;
import com.example.ehs.order.OrderMealActivity;
import com.example.ehs.query.QueryActivity;
import com.example.ehs.setting.SettingActivity;
import com.example.ehs.shopping.ShoppingActivity;
import com.example.ehs.vedio.VedioMeetActivity;
import com.example.ehs.widget.ImageAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * 主功能模块
 * */
public class MainActivity extends Activity {
	//title
	private TextView titleView;
	private Button exitView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		titleView = (TextView)this.findViewById(R.id.title);
		titleView.setText("医患系统");
		exitView = (Button)this.findViewById(R.id.bt_left);
		exitView.setVisibility(View.VISIBLE);
		exitView.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("提示")
				.setMessage("确认要退出应用吗?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						/*userInfo.setAutoLogin(false);
						Intent intent4 = new Intent();
						intent4.setClass(SettingActivity.this, LoginActivity.class);
						startActivity(intent4);
						SettingActivity.this.finish();*/
						System.exit(0);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {				
					@Override
					public void onClick(DialogInterface dialog, int which) {
					
					}
				}).create().show();
			}
		});
		GridView gridview = (GridView)findViewById(R.id.gridview);
		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = null;
		for(int i=0;i<9;i++){
			map=new HashMap<String, Object>();
			map.put("img", R.drawable.ic_launcher);
			map.put("str", "聊天");
			items.add(map);
		}
		/*map=new HashMap<String, Object>();
		map.put("img", R.drawable.im);
		map.put("str", "聊天");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.notice);
		map.put("str", "通知");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.query);
		map.put("str", "查询");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.hospital);
		map.put("str", "医院信息");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.vedio);
		map.put("str", "视频会诊");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.order);
		map.put("str", "快速订餐");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.entertain);
		map.put("str", "娱乐");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.shopping);
		map.put("str", "购物");
		items.add(map);
		map = new HashMap<String, Object>();
		map.put("img", R.drawable.setting);
		map.put("str", "设置");
		items.add(map);*/
		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.simple_grid_item, new String[]{"img","str"}, new int[]{R.id.item,R.id.text});
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					//即时通讯模块
					Intent intent1 = new Intent();
					intent1.setClass(MainActivity.this, IMActivity.class);
					MainActivity.this.startActivity(intent1);
					//MainActivity.this.finish();
					break;
				case 1:
					//通知模块
					Intent intent2 = new Intent();
					intent2.setClass(MainActivity.this, NoticeActivity.class);
					MainActivity.this.startActivity(intent2);
					//MainActivity.this.finish();
					break;
				case 2:
					//查询模块
					Intent intent3 = new Intent();
					intent3.setClass(MainActivity.this, QueryActivity.class);
					MainActivity.this.startActivity(intent3);
					//MainActivity.this.finish();
					break;
				case 3:
					//医院介绍模块
					Intent intent4 = new Intent();
					intent4.setClass(MainActivity.this, HospitalActivity.class);
					MainActivity.this.startActivity(intent4);
					//MainActivity.this.finish();
					break;
				case 4:
					//视频会议模块
					Intent intent5 = new Intent();
					intent5.setClass(MainActivity.this, VedioMeetActivity.class);
					MainActivity.this.startActivity(intent5);
					//MainActivity.this.finish();
					break;
				case 5:
					//订餐模块
					Intent intent6 = new Intent();
					intent6.setClass(MainActivity.this, OrderMealActivity.class);
					MainActivity.this.startActivity(intent6);
					//MainActivity.this.finish();
					break;
				case 6:
					//娱乐模块
					Intent intent7 = new Intent();
					intent7.setClass(MainActivity.this, EntertainMentActivity.class);
					MainActivity.this.startActivity(intent7);
					//MainActivity.this.finish();
					break;
				case 7:
					//购物模块
					Intent intent8 = new Intent();
					intent8.setClass(MainActivity.this, ShoppingActivity.class);
					MainActivity.this.startActivity(intent8);
					//MainActivity.this.finish();
					break;
				case 8:
					//设置模块
					Intent intent9 = new Intent();
					intent9.setClass(MainActivity.this, SettingActivity.class);
					MainActivity.this.startActivity(intent9);
					//MainActivity.this.finish();
					break;
					default:break;
				}
			}
		});
		/*gridview.setAdapter(new ImageAdapter(this));
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					//即时通讯模块
					Intent intent1 = new Intent();
					intent1.setClass(MainActivity.this, IMActivity.class);
					MainActivity.this.startActivity(intent1);
					//MainActivity.this.finish();
					break;
				case 1:
					//通知模块
					Intent intent2 = new Intent();
					intent2.setClass(MainActivity.this, NoticeActivity.class);
					MainActivity.this.startActivity(intent2);
					//MainActivity.this.finish();
					break;
				case 2:
					//查询模块
					Intent intent3 = new Intent();
					intent3.setClass(MainActivity.this, QueryActivity.class);
					MainActivity.this.startActivity(intent3);
					//MainActivity.this.finish();
					break;
				case 3:
					//医院介绍模块
					Intent intent4 = new Intent();
					intent4.setClass(MainActivity.this, HospitalActivity.class);
					MainActivity.this.startActivity(intent4);
					//MainActivity.this.finish();
					break;
				case 4:
					//视频会议模块
					Intent intent5 = new Intent();
					intent5.setClass(MainActivity.this, VedioMeetActivity.class);
					MainActivity.this.startActivity(intent5);
					//MainActivity.this.finish();
					break;
				case 5:
					//订餐模块
					Intent intent6 = new Intent();
					intent6.setClass(MainActivity.this, OrderMealActivity.class);
					MainActivity.this.startActivity(intent6);
					//MainActivity.this.finish();
					break;
				case 6:
					//娱乐模块
					Intent intent7 = new Intent();
					intent7.setClass(MainActivity.this, EntertainMentActivity.class);
					MainActivity.this.startActivity(intent7);
					//MainActivity.this.finish();
					break;
				case 7:
					//购物模块
					Intent intent8 = new Intent();
					intent8.setClass(MainActivity.this, ShoppingActivity.class);
					MainActivity.this.startActivity(intent8);
					//MainActivity.this.finish();
					break;
				case 8:
					//设置模块
					Intent intent9 = new Intent();
					intent9.setClass(MainActivity.this, SettingActivity.class);
					MainActivity.this.startActivity(intent9);
					//MainActivity.this.finish();
					break;
					default:break;
				}
			}
		});*/
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private long exitTime = 0;
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        	finish();
            System.exit(0);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
