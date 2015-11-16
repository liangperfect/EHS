package com.example.ehs.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ehs.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AllCheckResultsActivity extends Activity{
	private ListView listView;
	List<HashMap<String, String>> list=null;
	HashMap<String, String> map=null;
	SimpleAdapter adapter =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_check);
		init();
	}
	private void init() {
		listView = (ListView)findViewById(R.id.check_list);
		
		list = new ArrayList<HashMap<String,String>>();
		map = new HashMap<String, String>();
		map.put("name", "血常规");
		map.put("time", "2013-01-01");
		map.put("more","更多");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("name", "体检");
		map.put("time", "2013-01-01");
		map.put("more","更多");
		list.add(map);
		
		adapter = new SimpleAdapter(AllCheckResultsActivity.this, list, R.layout.chek_item, 
				new String[]{"name","time","more"}, new int[]{R.id.check_item,R.id.check_time,R.id.check_more});
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				map = new HashMap<String, String>();
				map = list.get(arg2);
				// 跳转事件
				Intent i = new Intent();
				i.setClass(AllCheckResultsActivity.this, OneCheckActivity.class);
				Bundle b = new Bundle();
				b.putString("name", map.get("name"));
				b.putString("time", map.get("time"));
				if(arg2==0){
					b.putString("details", "红细胞数：正常\n白细胞数：正常\n血小板数：正常\n血红蛋白：正常\n");
				}else{
					b.putString("details", "身高：165cm\n体重：53kg\n血压：正常\n心率：正常\n心律：正常\n肺：正常\n肝：正常\n脾：正常\n");
				}
				i.putExtra("bd", b);
				startActivity(i);
			}
		});
	}
}
