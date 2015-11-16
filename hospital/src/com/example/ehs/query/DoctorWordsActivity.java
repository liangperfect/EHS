package com.example.ehs.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.hospital.AboutHospitalActivity;
import com.example.ehs.model.DoctorWords;
import com.example.ehs.model.UserInfo;
import com.example.ehs.notice.HospitalNoticeActivity;
import com.example.ehs.utils.HttpUtil;
/*
 * 查询模块----医嘱显示模块（可参看AboutAppActivity模块）
 * */
public class DoctorWordsActivity extends Activity{
	private TextView query_word;
	private ListView listview;
	
	private SimpleAdapter adapter=null;
	private String queryStr = null;
	UserInfo userInfo = null;
	
	List<HashMap<String, String>> list=null;
	HashMap<String, String> map=null;
	List<DoctorWords> doctorWords=null;
	DoctorWords doctorWords2 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_words_query);
		userInfo = new UserInfo(DoctorWordsActivity.this);
		System.out.println("account="+userInfo.getAccount());
		init();
	}
	private void init() {
		query_word = (TextView)findViewById(R.id.quer_words);
		
		listview = (ListView)findViewById(R.id.doctor_words_list);
		list=new ArrayList<HashMap<String,String>>();
		doctorWords = new ArrayList<DoctorWords>();
		
		new Thread(){
			 @Override
			 public void run(){
				 //你要执行的方法
				 queryStr = query(userInfo.getAccount());
				 //执行完毕后给handler发送一个空消息
				 handler.sendEmptyMessage(0);
			 }
		}.start();
	}
	//定义Handler对象
		private Handler handler =new Handler(){
		 @Override
		 //当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
			 super.handleMessage(msg);
			 //处理UI
		 		String results = doThings(queryStr);
		 		System.out.println("results="+results);
		 		if(results.equals("success")){
		 			query_word.setVisibility(View.GONE);
			 		adapter = new SimpleAdapter(DoctorWordsActivity.this, list , R.layout.notice_item, 
							new String[]{"title","sb","time","content"}, 
							new int[]{R.id.notice_title,R.id.notice_sb,R.id.notice_time,R.id.notice_content});
					
					listview.setAdapter(adapter);
					listview.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							DoctorWords tempWords = doctorWords.get(arg2);
							Intent i = new Intent();
							i.setClass(DoctorWordsActivity.this, DoctorWordsDetailActivity.class);
							Bundle b = new Bundle();
							b.putParcelable("words", tempWords);
							i.putExtra("bd", b);
							startActivity(i);
						}
					});
		 		}else if(results.equals("flag")){
		 			query_word.setVisibility(View.VISIBLE);
		 			query_word.setText("还没有医生给您下过医嘱");
		 		}else{
		 			Toast.makeText(DoctorWordsActivity.this, "获取医嘱信息失败！", Toast.LENGTH_SHORT).show();
		 		}
		 	}
		};
		private String query(String userId) {
			String url = HttpUtil.BASE_URL+"doctor/servlet?Method=selWords&userId="+userId;
			return HttpUtil.queryStringForPost(url);
		}
		private String doThings(String queryStr) {
			String result = null;
			JSONObject object = null;
			String words_doctor_name=null;//医生名字
			String departmentname = null;//科室
			String doctor_words = null;//医嘱内容
			String doctorWordsTime = null;//医嘱时间
			try {
				JSONObject json= new JSONObject(queryStr);
				result = json.getString("msg");
				System.out.println("result="+result);
				if(result.equals("success")){
					object = json.getJSONObject("doctorWords");
					if(object.getString("words_doctor_name")!=null){
						words_doctor_name = object.getString("words_doctor_name");
					}
					if(object.getString("departmentname")!=null){
						departmentname = object.getString("departmentname");
					}
					if(object.getString("doctor_words")!=null){
						doctor_words = object.getString("doctor_words");
					}
					if(object.getString("time")!=null){
						doctorWordsTime = object.getString("time");
					}
					
					if(words_doctor_name!=null || departmentname!=null || doctor_words!=null || doctorWordsTime!=null){
						map = new HashMap<String, String>();
						map.put("title", "医嘱");
						map.put("sb", words_doctor_name);
						map.put("time", "@ "+doctorWordsTime);
						map.put("content", doctor_words);
						list.add(map);
						
						doctorWords2 = new DoctorWords(words_doctor_name, departmentname, doctorWordsTime, doctor_words);
						doctorWords.add(doctorWords2);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				result = "fail";
				System.out.println("hahah");
			}
			return result;
		}
		/*private List<HashMap<String, String>> getList() {
		List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map=null;
		map = new HashMap<String, String>();
		map.put("title", "医嘱");
		map.put("sb", "李医生");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "按时吃药，不能碰凉水....");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("title", "医嘱");
		map.put("sb", "张医生");
		map.put("time", "@ 2014/03/28 11:23:54");
		map.put("content", "不要吃辛辣、冰凉的饮食....");
		list.add(map);
		return list;
	}*/

}
