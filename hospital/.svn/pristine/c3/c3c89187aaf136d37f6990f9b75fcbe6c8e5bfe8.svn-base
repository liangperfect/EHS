package com.example.ehs.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ehs.R;
/*
 * 护士站通知
 * */
public class NurseNoticeActivity extends Activity{
	
	private ListView listview;
	private SimpleAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nurse_notice);
		init();
	}
	private void init() {
		listview = (ListView)findViewById(R.id.nurse_list);
		
		adapter = new SimpleAdapter(NurseNoticeActivity.this, getList(), R.layout.notice_item, 
				new String[]{"title","sb","time","content"}, 
				new int[]{R.id.notice_title,R.id.notice_sb,R.id.notice_time,R.id.notice_content});
		listview.setAdapter(adapter);
	}

	private List<HashMap<String, String>> getList() {
		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map=null;
		map = new HashMap<String, String>();
		map.put("title", "吃药通知");
		map.put("sb", "张护士");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "3号床5分钟后记得吃药！");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("title", "检查通知");
		map.put("sb", "李护士");
		map.put("time", "@ 2014/03/18 11:23:50");
		map.put("content", "请5号床领取检查结果 ！");
		list.add(map);
		return list;
	}
}
