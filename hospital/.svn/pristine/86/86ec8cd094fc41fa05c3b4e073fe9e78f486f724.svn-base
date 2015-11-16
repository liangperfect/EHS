package com.example.ehs.hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.ehs.MainActivity;
import com.example.ehs.R;
import com.example.ehs.entertainments.EntertainMentActivity;
import com.example.ehs.im.IMActivity;
import com.example.ehs.model.Department;
import com.example.ehs.notice.NoticeActivity;
import com.example.ehs.order.OrderMealActivity;
import com.example.ehs.query.QueryActivity;
import com.example.ehs.setting.SettingActivity;
import com.example.ehs.shopping.ShoppingActivity;
import com.example.ehs.utils.HttpUtil;
import com.example.ehs.vedio.VedioMeetActivity;

/*
 * 科室介绍模块
 * */
public class AboutSubjectActivity extends Activity {

	GridView grid = null;
	SimpleAdapter adapter = null;
	
	String queryStr =null;
	ArrayList<HashMap<String, Object>> items = null;
	HashMap<String, Object> map = null;
	//int subjectIds[] = new int[]{0};
	List<Department> departments = null;
	Department department = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_subject);
		
		grid = (GridView) findViewById(R.id.subjectGridView);
		items = new ArrayList<HashMap<String, Object>>();
		departments = new ArrayList<Department>();
		
		new Thread(){
			 @Override
			 public void run(){
				 //你要执行的方法
				 queryStr = query();
				 //执行完毕后给handler发送一个空消息
				 handler.sendEmptyMessage(0);
			 }
		}.start();
		//ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		//HashMap<String, Object> map = null;
		/*
		for (int i = 0; i < 6; i++) {
			map = new HashMap<String, Object>(); 
			map.put("img", R.drawable.redcross);
			map.put("str", "科室名称");
			items.add(map);
		}*/
		
		/*map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.erke);
		map.put("str", "儿科");
		items.add(map);
		
		map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.xin);
		map.put("str", "外科");
		items.add(map);
		
		map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.fuyou);
		map.put("str", "妇科");
		items.add(map);
		
		map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.erbihou);
		map.put("str", "耳鼻喉科");
		items.add(map);

		map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.redcross);
		map.put("str", "眼科");
		items.add(map);
		
		map = new HashMap<String, Object>(); 
		map.put("img", R.drawable.neike);
		map.put("str", "内科");
		items.add(map);
		
		SimpleAdapter adapter = new SimpleAdapter(this, items,
				R.layout.simple_grid_item, new String[] { "img", "str"},
				new int[] { R.id.item, R.id.text });
		grid.setAdapter(adapter);

		
		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					//儿科
					Intent intent1 = new Intent();
					intent1.setClass(AboutSubjectActivity.this, PaediatricsActivity.class);
					AboutSubjectActivity.this.startActivity(intent1);*/
				
					/*
					Intent intent1 = new Intent();
					intent1.setClass(MainActivity.this, IMActivity.class);
					MainActivity.this.startActivity(intent1);
					*/
					//MainActivity.this.finish();
					//break;
				//case 1:
					//外科
					
					
					
					
					
					/*Intent intent2 = new Intent();
					intent2.setClass(MainActivity.this, NoticeActivity.class);
					MainActivity.this.startActivity(intent2);*/
					//MainActivity.this.finish();
					//break;
				//case 2:
					//妇科
					
					
					
					
					
					/*Intent intent3 = new Intent();
					intent3.setClass(MainActivity.this, QueryActivity.class);
					MainActivity.this.startActivity(intent3);*/
					//MainActivity.this.finish();
				//	break;
				//case 3:
					//耳鼻喉科
					 
					
					
					
					/*Intent intent4 = new Intent();
					intent4.setClass(MainActivity.this, HospitalActivity.class);
					MainActivity.this.startActivity(intent4);*/
					//MainActivity.this.finish();
				//	break;
				//case 4:
					//眼科
					
					
					
					
					/*Intent intent5 = new Intent();
					intent5.setClass(MainActivity.this, VedioMeetActivity.class);
					MainActivity.this.startActivity(intent5);*/
					//MainActivity.this.finish();
				//	break;
				//case 5:
					//内科
					
					
					
					/*Intent intent6 = new Intent();
					intent6.setClass(MainActivity.this, OrderMealActivity.class);
					MainActivity.this.startActivity(intent6);*/
					//MainActivity.this.finish();
				//	break;
					//MainActivity.this.finish();
				//	default:break;
			//	}
			//}
		//});
	}
	//定义Handler对象
		private Handler handler =new Handler(){
		 @Override
		 //当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
			 super.handleMessage(msg);
			 //处理UI
		 		doThings(queryStr);
		 		adapter = new SimpleAdapter(AboutSubjectActivity.this, items,R.layout.simple_grid_item, 
		 				new String[] { "img", "str"},
						new int[] { R.id.item, R.id.text });
				grid.setAdapter(adapter);
				grid.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Department tempDepartment = departments.get(arg2);
						System.out.println("department="+tempDepartment.toString());
						Intent intent1 = new Intent();
						intent1.setClass(AboutSubjectActivity.this, PaediatricsActivity.class);
						Bundle b = new Bundle();
						b.putParcelable("department", tempDepartment);
						intent1.putExtra("id", b);
						AboutSubjectActivity.this.startActivity(intent1);
					}
					
				});
		 	}

		};
	private String query() {
		String url = HttpUtil.BASE_URL+"doctor/servlet?Method=departmentList";
		return HttpUtil.queryStringForPost(url);
	}
	private void doThings(String queryStr) {
		String result = null;
		JSONArray array = null;
		JSONObject json;
		int id=0;
		String name=null;
		String intruduce=null;
		try {
			json = new JSONObject(queryStr);
			result = json.getString("msg");
			System.out.println("result="+result);
			if(result.equals("success")){
				array = json.getJSONArray("list");
				JSONObject object = null;
				for(int i=0;i<array.length();i++){
					object = new JSONObject();
					object = (JSONObject) array.get(i);
					id  = object.getInt("department_id");
					name = object.getString("department_name");
					intruduce = object.getString("department_introduce");
					
					System.out.println("id="+id);
					System.out.println("name="+name);
					System.out.println("intruduce="+intruduce);
					
					department = new Department(id, name, intruduce);
					departments.add(department);
					map = new HashMap<String, Object>(); 
					map.put("img", R.drawable.hosptal);
					map.put("str", name);
					items.add(map);
					//subjectIds[i] = id;
				}
			}else{
				Toast.makeText(AboutSubjectActivity.this, "获取科室信息失败！", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			result = "fail";
			e.printStackTrace();
		}
	}
}
