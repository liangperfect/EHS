package com.example.ehs.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
/*
 * 医院通知
 * */
public class HospitalNoticeActivity extends Activity{
	private ListView listview;
	
	private SimpleAdapter adapter=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hospital_notice);
		init();
	}

	private void init() {
		listview = (ListView)findViewById(R.id.notice_list);
		adapter = new SimpleAdapter(HospitalNoticeActivity.this, getList(), R.layout.notice_item, 
				new String[]{"title","sb","time","content"}, 
				new int[]{R.id.notice_title,R.id.notice_sb,R.id.notice_time,R.id.notice_content});
		listview.setAdapter(adapter);
	}

	private List<HashMap<String, String>> getList() {
		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map=null;
		map = new HashMap<String, String>();
		map.put("title", "通知");
		map.put("sb", "张三");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "天气转凉，请大家注意添衣！！");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("title", "紧急通知");
		map.put("sb", "李四");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "下午3点停水，请大家做好准备！");
		list.add(map);
		return list;
	}

}
