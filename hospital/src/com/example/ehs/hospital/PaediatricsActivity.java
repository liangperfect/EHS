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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.model.Department;
import com.example.ehs.model.Doctor;
import com.example.ehs.utils.HttpUtil;

public class PaediatricsActivity extends Activity implements
		Button.OnClickListener {
	private TextView txt = null;
	private Button btn = null;
	private Gallery gallery;

	int queryId = 0;
	ArrayList<HashMap<String, Object>> items = null;
	HashMap<String, Object> map = null;
	SimpleAdapter adapter = null;
	String queryStr =null;
	List<Doctor> doctors =null;
	Doctor doctor = null;
	Department department = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paediatrics);
		
		Intent i = getIntent();
		Bundle b = i.getBundleExtra("id");
		department = b.getParcelable("department");
		queryId = department.getDepartment_id();
		System.out.println("queryId="+queryId);
		
		txt = (TextView) findViewById(R.id.title);
		txt.setText(department.getDepartment_name());
		
		btn = (Button) findViewById(R.id.bt_left);
		btn.setVisibility(View.VISIBLE);
		btn.setOnClickListener(this);
		// ���ȵĳ�ʼ��
		gallery = (Gallery) findViewById(R.id.subjectGallery);
		
		items = new ArrayList<HashMap<String, Object>>();
		doctors = new ArrayList<Doctor>();
		
		//HashMap<String, Object> map = null;

		/*map = new HashMap<String, Object>();
		map.put("img", R.drawable.aifushen);
		map.put("str", "��ҽ��");
		items.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.kobe);
		map.put("str", "��ҽ��");
		items.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.weide);
		map.put("str", "Τҽ��");
		items.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.mac);
		map.put("str", "��ҽ��");
		items.add(map);*/

		new Thread(){
			 @Override
			 public void run(){
				 //��Ҫִ�еķ���
				 queryStr = query(queryId);
				 //ִ����Ϻ��handler����һ������Ϣ
				 handler.sendEmptyMessage(0);
			 }
		}.start();
	/*	adapter = new SimpleAdapter(this, items,
				R.layout.copy_of_simple_grid_item,
				new String[] { "img", "str" }, new int[] { R.id.copyitem,
						R.id.copytext });
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position);
				switch (position) {
				case 0:
					// ��һ��ҽ�� ����ɭ
					Intent intent1 = new Intent();
					intent1.putExtra("judgedoctor", 0);
					intent1.setClass(PaediatricsActivity.this, OneDoctor.class);
					PaediatricsActivity.this.startActivity(intent1);					
					break;
				case 1:
					// �ڶ���ҽ�� �Ʊ�ҽ��
					Intent intent2 = new Intent();
					intent2.putExtra("judgedoctor", 1);
					intent2.setClass(PaediatricsActivity.this, OneDoctor.class);
					PaediatricsActivity.this.startActivity(intent2);

					break;
				case 2:
					// ������ҽ�� Τ��ҽ��
					Intent intent3 = new Intent();
					intent3.putExtra("judgedoctor", 2);
					intent3.setClass(PaediatricsActivity.this, OneDoctor.class);
					PaediatricsActivity.this.startActivity(intent3);
					break;
				case 3:
					Intent intent4 = new Intent();
					intent4.putExtra("judgedoctor", 3);
					intent4.setClass(PaediatricsActivity.this, OneDoctor.class);
					PaediatricsActivity.this.startActivity(intent4);
					break;
				default:
					break;
				}
			}
		});*/

	}

	private String query(int id) {
		String url = HttpUtil.BASE_URL+"doctor/servlet?Method=selDocDepartment&deparmentId="+id;
		return HttpUtil.queryStringForPost(url);
	}
	//����Handler����
	private Handler handler =new Handler(){
	 @Override
	 //������Ϣ���ͳ�����ʱ���ִ��Handler���������
	public void handleMessage(Message msg){
		 super.handleMessage(msg);
		 //����UI
	 		doThings(queryStr);
	 		adapter = new SimpleAdapter(PaediatricsActivity.this, items,
					R.layout.copy_of_simple_grid_item,
					new String[] { "img", "str" }, new int[] { R.id.copyitem,
							R.id.copytext });
			gallery.setAdapter(adapter);
			gallery.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Doctor tempDoctor = doctors.get(arg2);
					Intent intent1 = new Intent();
					Bundle b = new Bundle();
					b.putParcelable("doctor", tempDoctor);
					intent1.putExtra("judgedoctor", b);
					intent1.setClass(PaediatricsActivity.this, OneDoctor.class);
					PaediatricsActivity.this.startActivity(intent1);
				}
			});
	 	}
	};
	//����ҽ����Ϣ
	private void doThings(String queryStr) {
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
					
					map.put("img", R.drawable.doctor_2);
					map.put("str", doctor_name);
					items.add(map);
				}
			}else{
				Toast.makeText(PaediatricsActivity.this, "��ȡ����ҽ����Ϣʧ�ܣ�", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			result = "fail";
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paediatrics, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		default:
			break;
		}
	}

}
//
// class ImageAdapter extends BaseAdapter {
// // ����Context ��activity
// private Context context;
// // ������������ ��ͼ����Դ
// private Integer image[] = { R.drawable.a, R.drawable.b,
// R.drawable.hospital, R.drawable.fuyou };
//
// public ImageAdapter(Context c) {
//
// context = c;
// }
//
// @Override
// // ���غ�̨һ���ж�������
// public int getCount() {
// // TODO Auto-generated method stub
// // return image.length;
// return Integer.MAX_VALUE;
// }
//
// @Override
// // ����ָ��λ�õ����ݶ���
// public Object getItem(int position) {
// // TODO Auto-generated method stub
// return position;
// }
//
// @Override
// // ����ָ��λ�ö����ID
// public long getItemId(int position) {
// // TODO Auto-generated method stub
// return position;
// }
//
// @SuppressWarnings("deprecation")
// @Override
// // ����һ�����������ݵ�View���Ա������ؼ����е���
// public View getView(int position, View convertView, ViewGroup parent) {
// // TODO Auto-generated method stub
// ImageView imageview = new ImageView(context);
// imageview.setImageResource(image[position % image.length]);
// // imageview.setImageResource(image[position]);
// imageview.setScaleType(ImageView.ScaleType.FIT_XY);
// imageview.setLayoutParams(new Gallery.LayoutParams(200, 200));
// imageview.setPadding(25, 0, 25, 0);
// return imageview;
// }
// }
