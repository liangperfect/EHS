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
 * ҽԺ֪ͨ
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
		map.put("title", "֪ͨ");
		map.put("sb", "����");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "����ת��������ע�����£���");
		list.add(map);
		map = new HashMap<String, String>();
		map.put("title", "����֪ͨ");
		map.put("sb", "����");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "����3��ͣˮ����������׼����");
		list.add(map);
		return list;
	}

}
