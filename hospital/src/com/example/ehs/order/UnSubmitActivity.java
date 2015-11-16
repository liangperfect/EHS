package com.example.ehs.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.ehs.R;
import com.example.ehs.db.OrderDb;
import com.example.ehs.im.GroupActivity;
import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.UserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.SumPathEffect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UnSubmitActivity extends Activity{
	private ListView listView;
	private CheckBox allCheckBox;
	private TextView has_choose_num_view;
	private TextView totalPriceView;
	private Button submitBt;
	
	private List<HashMap<String, Object>> list=null;
	HashMap<String, Object> map=null;
	FoodInfo foodInfo = null;
	UserInfo userInfo = null;
	OrderDb db = null;
	
	//SimpleAdapter adapter = null;
	ShoppingAdapter adapter=null;
	private int has_choose_num=0;
	private float total_price=0;
	
	private int contrul=0;//0表示没有点击全选或不选，1--全选，2--全不选
	private String items[] = {"删除","添加要求","更改份数"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.unsubmit_order);
		userInfo = new UserInfo(this);
		db = new OrderDb(UnSubmitActivity.this);
		init();
	}
	private void init() {
		listView = (ListView)findViewById(R.id.unsubmit_list);
		allCheckBox = (CheckBox)findViewById(R.id.check_all);
		has_choose_num_view = (TextView)findViewById(R.id.has_choose_num);
		totalPriceView = (TextView)findViewById(R.id.total_price);
		submitBt = (Button)findViewById(R.id.submit);
		
		allCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(allCheckBox.isChecked()){
					for(int i=1;i<list.size();i++){
						map = new HashMap<String, Object>();
						map = list.get(i);
						has_choose_num = has_choose_num+ Integer.parseInt(""+map.get("num"));
						total_price = total_price+(Float.parseFloat(""+map.get("price")))*(Integer.parseInt(""+map.get("num")));
						has_choose_num_view.setText(has_choose_num+"");
						totalPriceView.setText(total_price+"");
					}
					submitBt.setVisibility(View.VISIBLE);
					adapter = new ShoppingAdapter(UnSubmitActivity.this, getList(), listView,1);
					listView.setAdapter(adapter);
				}else{
					has_choose_num = 0;
					total_price = 0;
					has_choose_num_view.setText(has_choose_num+"");
					totalPriceView.setText(total_price+"");
					adapter = new ShoppingAdapter(UnSubmitActivity.this, getList(), listView,2);
					listView.setAdapter(adapter);
				}
			}
		});
		
		submitBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 提交订单
				FoodInfo foodInfo3 = null;
				for(int i=1;i<list.size();i++){
					map = new HashMap<String, Object>();
					map = list.get(i);
					foodInfo3 = new FoodInfo(""+map.get("name"), "", "", ""+map.get("price"), ""+map.get("req"), Integer.parseInt(map.get("num")+""));
					String result = db.updateOrder(foodInfo, userInfo.getAccount(), 1);
					if(result.equals("success")){
						Toast.makeText(UnSubmitActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(UnSubmitActivity.this, "提交失败！", Toast.LENGTH_SHORT).show();
					}
				}
				adapter = new ShoppingAdapter(UnSubmitActivity.this, getList(), listView,0);
				listView.setAdapter(adapter);
			}
		});
		adapter = new ShoppingAdapter(this, getList(), listView,0);
		listView.setAdapter(adapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int arg2, long arg3) {
				Toast.makeText(UnSubmitActivity.this, "点击了"+arg2, Toast.LENGTH_SHORT).show();
				map = new HashMap<String, Object>();
				map = list.get(arg2);
				final String name = map.get("name")+"";
				final String price = map.get("price")+"";
				final String req = map.get("req")+"";
				final int num = Integer.parseInt(map.get("num")+"");
				final TextView nameView = (TextView)v.findViewById(R.id.food_name);
				final TextView reqView = (TextView)v.findViewById(R.id.food_require);
				final TextView numView = (TextView)v.findViewById(R.id.food_num);
				new AlertDialog.Builder(UnSubmitActivity.this).setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						switch (arg1) {
						case 0:
							String message = db.deleteOrder(nameView.getText().toString(), userInfo.getAccount(), 0);
							if(message.equals("success")){
								Toast.makeText(UnSubmitActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(UnSubmitActivity.this, "删除失败！", Toast.LENGTH_SHORT).show();
							}
							break;
						case 1://添加要求
							final EditText req_input = new EditText(UnSubmitActivity.this);
							req_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT));
							req_input.setHint("请输入您的要求");
							req_input.setBackgroundResource(android.R.drawable.editbox_background);
							new AlertDialog.Builder(UnSubmitActivity.this).setTitle("添加要求").setView(req_input)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									FoodInfo foodInfo4 = new FoodInfo(name, "", "", price, req_input.getText().toString(), num);
									String message = db.updateReq(foodInfo4, userInfo.getAccount(), 0);
									if(message.equals("success")){
										Toast.makeText(UnSubmitActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(UnSubmitActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
									}
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							}).create().show();
							break;
						case 2://修改份数
							final EditText num_input = new EditText(UnSubmitActivity.this);
							num_input.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
									LayoutParams.WRAP_CONTENT));
							num_input.setHint("请输入需要的份数(只能是整数)");
							num_input.setBackgroundResource(android.R.drawable.editbox_background);
							new AlertDialog.Builder(UnSubmitActivity.this).setTitle("添加要求").setView(num_input)
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									FoodInfo foodInfo4 = new FoodInfo(name, "", "", price, req, Integer.parseInt(num_input.getText().toString()));
									String msg = db.updateNum(foodInfo4, userInfo.getAccount(), 0);
									if(msg.equals("success")){
										Toast.makeText(UnSubmitActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(UnSubmitActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
									}
								}
							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							}).create().show();
							break;
						default:
							break;
						}
					}	
				}).setNegativeButton("取消", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create().show();
				return false;
			}
		});
	}
	private List<HashMap<String, Object>> getList() {
		list = new ArrayList<HashMap<String,Object>>();
		map = new HashMap<String, Object>();
		map.put("cb", null);
		map.put("name", "商品名称");
		map.put("price", "商品价格");
		map.put("num", "商品数量");
		map.put("req", "制作要求");
		list.add(map);
		System.out.println("list_length="+list.size());
		List<HashMap<String, Object>> list1 = db.getOrderRecord(userInfo.getAccount(), 0, null);
		HashMap<String, Object> map1 = null;
		FoodInfo foodInfo1 = null;
		for(int i=0;i<list1.size();i++){
			map1 = list1.get(i);
			foodInfo1 = (FoodInfo) map1.get("food");
			map = new HashMap<String, Object>();
			map.put("cb", null);
			map.put("name", foodInfo1.getFoodName());
			map.put("price", foodInfo1.getFoodPrice());
			map.put("num", foodInfo1.getFoodNum());
			map.put("req", foodInfo1.getFoodRequire());
			list.add(map);
		}
		return list;
	}
	private class ShoppingAdapter extends BaseAdapter{
		private List<HashMap<String, Object>> items;
		private Context context;
		private ListView adapterList;
		private int type;
		public ShoppingAdapter(Context context, List<HashMap<String, Object>> items,
				ListView adapterList, int type) {
			this.context = context;
			this.items = items;
			this.adapterList = adapterList;
			this.type = type;
		}
		public void refreshList(List<HashMap<String, Object>> items) {
			this.items = items;
			this.notifyDataSetChanged();
			adapterList.setSelection(items.size() - 1);
		}
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			HashMap<String, Object> map2 = items.get(arg0);
			View v1 = UnSubmitActivity.this.getLayoutInflater().inflate(R.layout.unsubmit_item, null);
			final CheckBox cb = (CheckBox)v1.findViewById(R.id.cb);
			TextView choose = (TextView)v1.findViewById(R.id.food_choose);
			if(arg0 == 0){
				cb.setVisibility(View.GONE);
				choose.setVisibility(View.VISIBLE);
				choose.setText("选择");
			}else{
				cb.setVisibility(View.VISIBLE);
				choose.setVisibility(View.GONE);
			}
			TextView name = (TextView)v1.findViewById(R.id.food_name);
			final TextView price = (TextView)v1.findViewById(R.id.food_price);
			final TextView num = (TextView)v1.findViewById(R.id.food_num);
			TextView req = (TextView)v1.findViewById(R.id.food_require);
			name.setText((CharSequence) map2.get("name"));
			price.setText((CharSequence) map2.get("price"));
			num.setText(""+ map2.get("num"));
			req.setText((CharSequence) map2.get("req"));
			if(type == 1){
				cb.setChecked(true);
			}
			if(type == 2){
				cb.setChecked(false);
			}
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					Toast.makeText(UnSubmitActivity.this, "点击了", Toast.LENGTH_SHORT).show();
					if(cb.isChecked()){
						has_choose_num = has_choose_num+Integer.parseInt(num.getText().toString());
						has_choose_num_view.setText(""+has_choose_num);
						total_price = total_price+Float.parseFloat(price.getText().toString())*Integer.parseInt(num.getText().toString());
						totalPriceView.setText(""+total_price);
						submitBt.setVisibility(View.VISIBLE);
					}
				}
			});
			return v1;
		}
	
	}
}
