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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.ehs.R;
import com.example.ehs.model.Department;
import com.example.ehs.model.Doctor;
import com.example.ehs.utils.HttpUtil;
/*
 * 医生模块
 * */
public class AboutDoctorActivity extends Activity implements OnClickListener{
	private EditText inputDoctorNameView;
	private Button commitView;
	private TextView queryResultView;
	private ListView resultListView;
	
	private List<HashMap<String, Object>> list=null;
	private HashMap<String, Object> map=null;
	SimpleAdapter adapter = null;
	
	String queryStr=null;
	String queryName=null;
	List<Doctor> doctors =null;
	Doctor doctor = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_doctor);
		//Toast.makeText(this, "未开发！", Toast.LENGTH_LONG).show();
		list = new ArrayList<HashMap<String,Object>>();
		doctors = new ArrayList<Doctor>();
		init();
	}

	private void init() {
		inputDoctorNameView = (EditText)findViewById(R.id.input_doctorname);
		commitView = (Button)findViewById(R.id.query_doctor);
		commitView.setOnClickListener(this);
		queryResultView = (TextView)findViewById(R.id.query_doctor_result);
		resultListView = (ListView)findViewById(R.id.query_list);
		//queryName = inputDoctorNameView.getText().toString();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.query_doctor:
			list = new ArrayList<HashMap<String,Object>>();
			queryName = inputDoctorNameView.getText().toString();
			System.out.println("queryName="+queryName);
			 if(TextUtils.isEmpty(queryName)){
				 Toast.makeText(AboutDoctorActivity.this, "查询的医生名字不能为空！", Toast.LENGTH_SHORT).show();
			 }else{
					new Thread(){
						 @Override
						 public void run(){
							 //你要执行的方法
							 queryStr = query(queryName);
							 //执行完毕后给handler发送一个空消息
							 handler.sendEmptyMessage(0);
						 }
				}.start();
			 }
			break;
		default:
			break;
		}
		
	}
	private String query(String doctorName) {
		String url = HttpUtil.BASE_URL+"doctor/servlet?Method=selDocName&doctorName="+doctorName;
		return HttpUtil.queryStringForPost(url);
	}
	private Handler handler =new Handler(){
		 @Override
		 //当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
			 super.handleMessage(msg);
			 //处理UI
		 		String result = doThings(queryStr);
		 		if(result.equals("success")){
					queryResultView.setVisibility(View.VISIBLE);
					queryResultView.setText("查询结果");	
					resultListView.setVisibility(View.VISIBLE);
					adapter = new SimpleAdapter(AboutDoctorActivity.this, list, R.layout.user_item,
		 				new String[]{"img","name","sex","subject","more"},
		 				new int[]{R.id.userAvatar,R.id.userName,R.id.userID,R.id.userRecentMessage,R.id.time});
					resultListView.setAdapter(adapter);
					resultListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Doctor tempDoctor = doctors.get(arg2);
							Intent intent1 = new Intent();
							Bundle b = new Bundle();
							b.putParcelable("doctor", tempDoctor);
							intent1.putExtra("judgedoctor", b);
							intent1.setClass(AboutDoctorActivity.this, OneDoctor.class);
							AboutDoctorActivity.this.startActivity(intent1);
						}
					});
		 		}else if(result.equals("flag")){
					queryResultView.setVisibility(View.VISIBLE);
					queryResultView.setText("抱歉，没有您要找的医生！");
					resultListView.setVisibility(View.GONE);
				}else{
					queryResultView.setVisibility(View.GONE);
					resultListView.setVisibility(View.GONE);
					Toast.makeText(AboutDoctorActivity.this, "获取科室医生信息失败！", Toast.LENGTH_SHORT).show();
				}
		 	}
		};
	private String doThings(String queryStr) {
		String result = null;
		JSONArray array = null;
		JSONObject json;
		int doctor_id=0;
		String doctor_name=null;
		String doctor_sex=null;
		int doctor_age=0;
		String doctor_introduce=null;
		int doctor_department_id=0;
		String doctor_department_name=null;
		String doctor_image=null;
		try{
			json = new JSONObject(queryStr);
			result = json.getString("msg");
			System.out.println("msg="+result);
			if(result.equals("success")){
				array = json.getJSONArray("list");
				JSONObject object = null;
				for(int i=0;i<array.length();i++){
					object = new JSONObject();
					object = (JSONObject) array.get(i);
					map = new HashMap<String, Object>();
					doctor_id = object.getInt("doctor_id");
					doctor_name = object.getString("doctor_name");
					doctor_sex = object.getString("doctor_sex");
					doctor_age = object.getInt("doctor_age");
					doctor_introduce = object.getString("doctor_introduce");
					doctor_department_id = object.getInt("doctor_department_id");
					doctor_department_name = object.getString("doctor_department_name");
					doctor_image = object.getString("doctor_image");
					
					doctor = new Doctor(doctor_id, doctor_name, doctor_sex, doctor_age,
							doctor_introduce, doctor_department_id, doctor_department_name, doctor_image);
					doctors.add(doctor);
					
					map = new HashMap<String, Object>();
					map.put("img", R.drawable.doctor);
					map.put("name", doctor_name);
					map.put("sex", "  "+doctor_sex);
					map.put("subject", doctor_department_name);
					map.put("more", "详情");
					list.add(map);
				}
			}
		} catch (JSONException e) {
			result = "fail";
			e.printStackTrace();
		}
		return result;
	}

}
