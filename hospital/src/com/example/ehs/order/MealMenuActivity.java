package com.example.ehs.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.model.FoodInfo;
import com.example.ehs.xmpphelper.ContacterManager.MRosterGroup;
/*
 * ���ģ��˵���ʾ����
 * */
public class MealMenuActivity extends Activity{
	private Spinner menuSpinner;
	private ListView menuListView;
	
	private static final String items[]={"����","��ʳ","С��","�ײ�","���","����","����","����"};
	//private ArrayAdapter<String> spinner_adapter=null;
	private MyAdapter spinner_adapter=null;
	private SimpleAdapter list_adapter=null;
	private List<HashMap<String, Object>> menuList=null;
	private HashMap<String, Object> nemuMap=null;
	private FoodInfo foodInfo=null;
	
    private static final int ALL_MENU = 0;
    private static final int MAIN_MENU = 1;
    private static final int MINI_MENU = 2;
    private static final int COUPLE_MENU = 3;
    private static final int BREAKFEST_MENU = 4;
    private static final int DRINK_MENU = 5;
    private static final int SOUP_MENU = 6;
    private static final int OTHER_MENU = 7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meal_menu);
		menuList = getList(ALL_MENU);
		System.out.println("menuList length="+menuList.size());
		init();
	}

	private void init() {
		menuSpinner = (Spinner)findViewById(R.id.menu_spinner);
		menuListView = (ListView)findViewById(R.id.menu_list);
		
		//spinner_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		spinner_adapter=new MyAdapter(items, MealMenuActivity.this);
		menuSpinner.setAdapter(spinner_adapter);
		menuSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int position, long arg3) {
				switch (position) {
				case 0://����
					menuList = getList(ALL_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 1://��ʳ
					menuList = getList(MAIN_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 2://С��
					menuList = getList(MINI_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 3://�ײ�
					menuList = getList(COUPLE_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 4://���
					menuList = getList(BREAKFEST_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 5://����
					menuList = getList(DRINK_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 6://����
					menuList = getList(SOUP_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				case 7://����
					menuList = getList(OTHER_MENU);
					list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
							new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
					menuListView.setAdapter(list_adapter);
					break;
				default:
					break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		list_adapter = new SimpleAdapter(MealMenuActivity.this, menuList, R.layout.menu_item, 
				new String[]{"img","name","description","price"}, new int[]{R.id.menu_img,R.id.menu_name,R.id.menu_description,R.id.menu_price});
		menuListView.setAdapter(list_adapter);
		menuListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				nemuMap = menuList.get(position);
			    Object imgStr = nemuMap.get("img");
			    System.out.println("imgStr="+imgStr);
			    System.out.println("caip="+R.drawable.cp_001);
				foodInfo = new FoodInfo((String)nemuMap.get("name"), imgStr.toString(), (String)nemuMap.get("description"), (String)nemuMap.get("price"), "", 0);
				Intent i = new Intent();
				i.setClass(MealMenuActivity.this, AMealInfoActivity.class);
				Bundle b = new Bundle();
				b.putParcelable("food", foodInfo);
				i.putExtra("foodBd", b);
				startActivity(i);
			}
		});
	}
	private List<HashMap<String, Object>> getList(int menuType) {
		List<HashMap<String, Object>> list=null;
		HashMap<String, Object> map = null;
		switch (menuType) {
		case ALL_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_001);
			map.put("name", "������˿");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_002);
			map.put("name", "ˮ����Ƭ");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_003);
			map.put("name", "��������");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_004);
			map.put("name", "�Ϲϱ�");
			map.put("description", "�������㡢��");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_005);
			map.put("name", "˦��");
			map.put("description", "�������㡢��");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_006);
			map.put("name", "���ױ�");
			map.put("description", "�������㡢��");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_007);
			map.put("name", "�㹽����");
			map.put("description", "�������㡢��");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_008);
			map.put("name", "�ڽ�ţ��");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_009);
			map.put("name", "���Ӽ���");
			map.put("description", "�������㡢��");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_010);
			map.put("name", "��ͷ");
			map.put("description", "������������");
			map.put("price", "1Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_011);
			map.put("name", "����");
			map.put("description", "�������㡢��");
			map.put("price", "1Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_012);
			map.put("name", "Ƥ��������");
			map.put("description", "�������㡢��");
			map.put("price", "2Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_013);
			map.put("name", "��Ȫˮ");
			map.put("description", "���������");
			map.put("price", "1Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_014);
			map.put("name", "����ѩ��");
			map.put("description", "������ֹ�ȡ���̵");
			map.put("price", "3Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_015);
			map.put("name", "����");
			map.put("description", "����������");
			map.put("price", "3Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_016);
			map.put("name", "Ƥ�������");
			map.put("description", "��������̸������");
			map.put("price", "8Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_017);
			map.put("name", "���Ѽ�����");
			map.put("description", "������Ũ������");
			map.put("price", "8Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_018);
			map.put("name", "�����Ź���");
			map.put("description", "������Ӫ����ζ");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_019);
			map.put("name", "������˿");
			map.put("description", "����������");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_020);
			map.put("name", "������");
			map.put("description", "���������");
			map.put("price", "18Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_021);
			map.put("name", "�ݽ���צ");
			map.put("description", "����������");
			map.put("price", "15Ԫ");
			list.add(map);
			break;
		case MAIN_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_001);
			map.put("name", "������˿");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_002);
			map.put("name", "ˮ����Ƭ");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_003);
			map.put("name", "��������");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			break;
		case MINI_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_004);
			map.put("name", "�Ϲϱ�");
			map.put("description", "�������㡢��");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_005);
			map.put("name", "˦��");
			map.put("description", "�������㡢��");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_006);
			map.put("name", "���ױ�");
			map.put("description", "�������㡢��");
			map.put("price", "10Ԫ");
			list.add(map);
			break;
		case COUPLE_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_007);
			map.put("name", "�㹽����");
			map.put("description", "�������㡢��");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_008);
			map.put("name", "�ڽ�ţ��");
			map.put("description", "�������㡢������");
			map.put("price", "15Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_009);
			map.put("name", "���Ӽ����ײ�");
			map.put("description", "�������㡢��");
			map.put("price", "15Ԫ");
			list.add(map);
			break;
		case BREAKFEST_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_010);
			map.put("name", "��ͷ");
			map.put("description", "������������");
			map.put("price", "1Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_011);
			map.put("name", "����");
			map.put("description", "�������㡢��");
			map.put("price", "1Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_012);
			map.put("name", "Ƥ��������");
			map.put("description", "�������㡢��");
			map.put("price", "2Ԫ");
			list.add(map);
			break;
		case DRINK_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_013);
			map.put("name", "��Ȫˮ");
			map.put("description", "���������");
			map.put("price", "1Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_014);
			map.put("name", "����ѩ��");
			map.put("description", "������ֹ�ȡ���̵");
			map.put("price", "3Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_015);
			map.put("name", "����");
			map.put("description", "����������");
			map.put("price", "3Ԫ");
			list.add(map);
			break;
		case SOUP_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_016);
			map.put("name", "Ƥ�������");
			map.put("description", "��������̸������");
			map.put("price", "8Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_017);
			map.put("name", "���Ѽ�����");
			map.put("description", "������Ũ������");
			map.put("price", "8Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_018);
			map.put("name", "�����Ź���");
			map.put("description", "������Ӫ����ζ");
			map.put("price", "15Ԫ");
			list.add(map);
			break;
		case OTHER_MENU:
			list = new ArrayList<HashMap<String,Object>>();
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_019);
			map.put("name", "������˿");
			map.put("description", "����������");
			map.put("price", "10Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_020);
			map.put("name", "������");
			map.put("description", "���������");
			map.put("price", "18Ԫ");
			list.add(map);
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.cp_021);
			map.put("name", "�ݽ���צ");
			map.put("description", "����������");
			map.put("price", "15Ԫ");
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
