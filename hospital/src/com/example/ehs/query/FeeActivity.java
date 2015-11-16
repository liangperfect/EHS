package com.example.ehs.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.ehs.R;
import com.example.ehs.order.MealMenuActivity;
import com.example.ehs.utils.HttpUtil;
/*
 * 查询模块---查询费用界面
 * */
public class FeeActivity extends Activity{
	private Spinner spinner;
	private TextView textView;
	private ListView listView;
	
	private static final String items[]={"所有费用","药品费用","住院费","检查费","治疗费","伙食费","其他"};
	private MyAdapter spinner_adapter=null;
	
	private SimpleAdapter adapter=null;
	private List<HashMap<String, String>> list = null;
	private HashMap<String, String> map=null;
	
	private static final int ALL=0;
	private static final int YAO=1;
	private static final int LIVE=2;
	private static final int CHECK=3;
	private static final int TREAT=4;
	private static final int EAT=5;
	private static final int OTHER=6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fee_query);

		init();
	}

	private void init() {
		spinner = (Spinner)findViewById(R.id.fee_spinner);
		textView = (TextView)findViewById(R.id.fee_text);
		listView = (ListView)findViewById(R.id.fee_list);
		
		spinner_adapter=new MyAdapter(items, FeeActivity.this);
		spinner.setAdapter(spinner_adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0://所有
					list = getList(ALL);
					System.out.println("length="+list.size());
					textView.setText("所有费用清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.fee_all_item, new String[]{
					"type","time","yao","num","price"}, new int[]{R.id.fee_typr,R.id.fee_time,R.id.fee_content,
							R.id.fee_num,R.id.fee_price});
					listView.setAdapter(adapter);
					break;
				case 1://药费
					list = getList(YAO);
					System.out.println("length="+list.size());
					textView.setText("药费清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.history_order_item, 
							new String[]{"time","yao","num","price"}, new int[]{R.id.order_id,R.id.order_content,
							R.id.order_time,R.id.order_more});
					listView.setAdapter(adapter);
					break;
				case 2://住院费
					list = getList(LIVE);
					System.out.println("length="+list.size());
					textView.setText("住院费清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.history_order_item, 
							new String[]{"time","yao","num","price"}, new int[]{R.id.order_id,R.id.order_content,
							R.id.order_time,R.id.order_more});
					listView.setAdapter(adapter);
					break;
				case 3://检查费
					list = getList(CHECK);
					System.out.println("length="+list.size());
					textView.setText("检查费清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.history_order_item, 
							new String[]{"time","yao","num","price"}, new int[]{R.id.order_id,R.id.order_content,
							R.id.order_time,R.id.order_more});
					listView.setAdapter(adapter);
					break;
				case 4://治疗费
					list = getList(TREAT);
					System.out.println("length="+list.size());
					textView.setText("治疗费清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.history_order_item, 
							new String[]{"time","yao","num","price"}, new int[]{R.id.order_id,R.id.order_content,
							R.id.order_time,R.id.order_more});
					listView.setAdapter(adapter);
					break;
				case 5://伙食费
					list = getList(EAT);
					System.out.println("length="+list.size());
					textView.setText("伙食费清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.history_order_item, 
							new String[]{"time","yao","num","price"}, new int[]{R.id.order_id,R.id.order_content,
							R.id.order_time,R.id.order_more});
					listView.setAdapter(adapter);
					break;
				case 6://其他
					list = getList(OTHER);
					System.out.println("length="+list.size());
					textView.setText("其他费清单");
					adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.history_order_item, 
							new String[]{"time","yao","num","price"}, new int[]{R.id.order_id,R.id.order_content,
							R.id.order_time,R.id.order_more});
					listView.setAdapter(adapter);
					break;
				default:break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		list = getList(ALL);
		textView.setText("所有费用清单");
		adapter = new SimpleAdapter(FeeActivity.this, list, R.layout.fee_all_item, new String[]{
		"type","time","yao","num","price"}, new int[]{R.id.fee_typr,R.id.fee_time,R.id.fee_content,
				R.id.fee_num,R.id.fee_price});
		listView.setAdapter(adapter);
	}
	private List<HashMap<String, String>> getList(int type) {
		//String url = HttpUtil.BASE_URL+"doctor/servlet?Method=selDocDepartment&deparmentId="+all;
		//String result = HttpUtil.queryStringForPost(url);
		switch (type) {
		case 0:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("type", "项目");
			map.put("time", "时间");
			map.put("yao", "消费内容");
			map.put("num", "数量");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "药费");
			map.put("time", "2013-01-23");
			map.put("yao", "阿莫西林");
			map.put("num", "1");
			map.put("price", "12");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "药费");
			map.put("time", "2013-03-12");
			map.put("yao", "板蓝根");
			map.put("num", "2");
			map.put("price", "15");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "住院费");
			map.put("time", "2013-04-24");
			map.put("yao", "普通病房");
			map.put("num", "7");
			map.put("price", "700");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "住院费");
			map.put("time", "2013-07-22");
			map.put("yao", "加护病房");
			map.put("num", "3");
			map.put("price", "3000");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "检查费");
			map.put("time", "2013-03-23");
			map.put("yao", "查血");
			map.put("num", "1");
			map.put("price", "50");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "检查费");
			map.put("time", "2013-08-01");
			map.put("yao", "胸透");
			map.put("num", "1");
			map.put("price", "80");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "治疗费");
			map.put("time", "2013-09-24");
			map.put("yao", "激光消炎");
			map.put("num", "2");
			map.put("price", "500");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "治疗费");
			map.put("time", "2013-10-22");
			map.put("yao", "喷雾治疗");
			map.put("num", "3");
			map.put("price", "300");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "伙食费");
			map.put("time", "2013-04-24");
			map.put("yao", "青椒盖饭");
			map.put("num", "2");
			map.put("price", "20");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "伙食费");
			map.put("time", "2013-07-22");
			map.put("yao", "面条");
			map.put("num", "2");
			map.put("price", "16");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "其他费");
			map.put("time", "2013-04-24");
			map.put("yao", "清洗床单");
			map.put("num", "1");
			map.put("price", "50");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("type", "其他费");
			map.put("time", "2013-07-22");
			map.put("yao", "购买毛巾");
			map.put("num", "3");
			map.put("price", "30");
			list.add(map);
			break;
		case 1:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("time", "时间");
			map.put("yao", "药品");
			map.put("num", "数量");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-01-23");
			map.put("yao", "阿莫西林");
			map.put("num", "1");
			map.put("price", "12");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-03-12");
			map.put("yao", "板蓝根");
			map.put("num", "2");
			map.put("price", "15");
			list.add(map);
			break;
		case 2:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("time", "时间");
			map.put("yao", "住院类型");
			map.put("num", "天数");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-04-24");
			map.put("yao", "普通病房");
			map.put("num", "7");
			map.put("price", "700");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-07-22");
			map.put("yao", "加护病房");
			map.put("num", "3");
			map.put("price", "3000");
			list.add(map);
			break;
		case 3:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("time", "时间");
			map.put("yao", "检查项目");
			map.put("num", "次数");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-03-23");
			map.put("yao", "查血");
			map.put("num", "1");
			map.put("price", "50");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-08-01");
			map.put("yao", "胸透");
			map.put("num", "1");
			map.put("price", "80");
			list.add(map);
			break;
		case 4:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("time", "时间");
			map.put("yao", "治疗类型");
			map.put("num", "次数");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-09-24");
			map.put("yao", "激光消炎");
			map.put("num", "2");
			map.put("price", "500");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-10-22");
			map.put("yao", "喷雾治疗");
			map.put("num", "3");
			map.put("price", "300");
			list.add(map);
			break;
		case 5:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("time", "时间");
			map.put("yao", "菜品");
			map.put("num", "数量");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-04-24");
			map.put("yao", "青椒盖饭");
			map.put("num", "2");
			map.put("price", "20");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-07-22");
			map.put("yao", "面条");
			map.put("num", "2");
			map.put("price", "16");
			list.add(map);
			break;
		case 6:
			list = new ArrayList<HashMap<String,String>>();
			map = new HashMap<String, String>();
			map.put("time", "时间");
			map.put("yao", "消费类");
			map.put("num", "次数");
			map.put("price", "总价");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-04-24");
			map.put("yao", "清洗床单");
			map.put("num", "1");
			map.put("price", "50");
			list.add(map);
			
			map = new HashMap<String, String>();
			map.put("time", "2013-07-22");
			map.put("yao", "购买毛巾");
			map.put("num", "3");
			map.put("price", "30");
			list.add(map);
			break;
		default:
			break;
		}
		return list;
	}
	private class MyAdapter extends BaseAdapter{
		private String[] items=null;
		private Context context;
		public MyAdapter(String[] items, Context context) {
			super();
			this.items = items;
			this.context = context;
		}
		@Override
		public int getCount() {
			return items.length;
		}
		@Override
		public Object getItem(int arg0) {
			return items[arg0];
		}
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			 LayoutInflater _LayoutInflater=LayoutInflater.from(context);  
			 v =_LayoutInflater.inflate(R.layout.my_simple_spinner, null);  
			 if(v!=null){
				 TextView label = (TextView) v.findViewById(R.id.simple_spinner_tx); 
				 label.setText(items[arg0]);
			 }
			 return v;
		}
		
	}
}
