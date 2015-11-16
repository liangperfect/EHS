package com.example.ehs.entertainments;

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
 * 游戏模块
 * */
public class GamesActivity extends Activity{
	private ListView listView;
	//List<HashMap<String, Object>> list =null;
	//HashMap<String, Object> map=null;
	SimpleAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.games);
		init();
	}
	private void init() {
		listView = (ListView)findViewById(R.id.games_list);
		
		adapter = new SimpleAdapter(GamesActivity.this, getList(), R.layout.games_list,
				new String[]{"img", "info"},
				new int[]{R.id.game_img, R.id.game_info});
		listView.setAdapter(adapter);
	}
	private List<HashMap<String, Object>> getList() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map=null;
		map = new HashMap<String, Object>();
		map.put("img",R.drawable.game);
		map.put("info", "推箱子");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img",R.drawable.game);
		map.put("info", "蛇吞象");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("img",R.drawable.game);
		map.put("info", "连连看");
		list.add(map);
		return list;
	}

}
