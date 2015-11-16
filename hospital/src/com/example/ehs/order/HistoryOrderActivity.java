package com.example.ehs.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ehs.R;
import com.example.ehs.db.IMMessageDb;
import com.example.ehs.db.OrderDb;
import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.IMMessage;
import com.example.ehs.model.Order;
import com.example.ehs.model.UserInfo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HistoryOrderActivity extends ListActivity implements OnClickListener{
	private Button bt_update;
	
	OrderDb orderDb = null;
	private List<HashMap<String, String>> list=null;
	private HashMap<String,String> map = null;
	
	SimpleAdapter adapter = null;
	UserInfo userInfo = null;
	FoodInfo foodInfo=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_order);
		userInfo = new UserInfo(this);
		init();
		upDateListView();
	}
	private void init() {
		bt_update = (Button)findViewById(R.id.bt_update);
		bt_update.setOnClickListener(this);
		bt_update.setVisibility(View.GONE);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_update:
			upDateListView();
			break;
		default:
			break;
		}
	}
	private void upDateListView() {
		list = new ArrayList<HashMap<String,String>>();
		adapter = buildAdapter();
		setListAdapter(adapter);
		//获取列表信息
	}
	private SimpleAdapter buildAdapter() {
		putValues("订单号","订单内容","订单时间","更多信息");
		putValues("657399", "鱼香肉丝:1,10,少放点盐;米饭:1,2,多打点)", "2014/03/09", "详情");
		putValues("657400", "皮蛋粥:1,2,多打点;油条:1,1,不要太焦的", "2014/03/08", "详情");
		adapter = new SimpleAdapter(HistoryOrderActivity.this, list, R.layout.history_order_item, 
				new String[]{"order_id","order_content","order_time","order_more"}, new int[]{R.id.order_id,R.id.order_content,R.id.order_time,R.id.order_more});
		return adapter; 
	}
	//列表放值方法
	 private void putValues(String order_id, String order_content, String order_time,String order_more) {
			map = new HashMap<String, String>();
			map.put("order_id", order_id);
			map.put("order_content", order_content);
			map.put("order_time", order_time);
			map.put("order_more", order_more);
			list.add(map);
		}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if(position==0){
			return;
		}
		map = new HashMap<String, String>();
		map = list.get(position);
		String str[] = (map.get("order_content")+"").split(";");
		System.out.println("length="+str.length);
		for(int x=0;x<str.length;x++){
			System.out.println("zhi="+str[x]);
		}
		System.out.println("");
		int i = str.length;
		List<FoodInfo> infos = new ArrayList<FoodInfo>();
		Float total = (float) 0;
		for(int j=0;j<i;j++){
			String foodName = str[j].substring(0,str[j].indexOf(":"));
			System.out.println("foodName="+foodName);
			String temp[] = str[j].substring(str[j].indexOf(":")+1).split(",");
			foodInfo = new FoodInfo(foodName, "", "", temp[1], temp[2], Integer.parseInt(temp[0]));
			infos.add(foodInfo);
			total = (float) (Integer.parseInt(temp[1])*Integer.parseInt(temp[0]));
		}
		Order order = new Order();
		order.setOrder_id(Integer.parseInt(""+map.get("order_id")));
		order.setOrder_address(userInfo.getAddress());
		order.setOrder_price(total+"");
		order.setOrder_state(1);
		order.setOrder_time(""+map.get("order_time"));
		order.setOrderContentInfos(infos);
		Intent i1 = new Intent();
		i1.setClass(HistoryOrderActivity.this, AOrderDetailsActivity.class);
		Bundle b = new Bundle();
		b.putParcelable("orderDetails", order);
		i1.putExtra("bd", b);
		startActivity(i1);
		super.onListItemClick(l, v, position, id);
	}
	 
}
