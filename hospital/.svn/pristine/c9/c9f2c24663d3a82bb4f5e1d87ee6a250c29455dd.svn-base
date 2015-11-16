package com.example.ehs.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ehs.R;
import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.Order;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AOrderDetailsActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button backView;
	
	private TextView orderIdView;
	private TextView orderTimeView;
	private TextView orderAddressView;
	private TextView orderStateView;
	private ListView goodsDetailsView;
	private TextView totalNumView;
	private TextView totalMoneyView;
	
	Bundle b = null;
	Order order = null;
	SimpleAdapter adapter=null;
	int total_num=0;
	float total_price=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		
		b = this.getIntent().getBundleExtra("bd");
		order = b.getParcelable("orderDetails");
		
		init();
	}

	private void init() {
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText("订单详情");
		
		backView = (Button)findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);
		
		orderIdView = (TextView)findViewById(R.id.order_id);
		orderTimeView = (TextView)findViewById(R.id.order_time);
	    orderAddressView = (TextView)findViewById(R.id.order_address);
	    orderStateView = (TextView)findViewById(R.id.order_state);
	    
	    goodsDetailsView = (ListView)findViewById(R.id.goods_list);
	    adapter = new SimpleAdapter(AOrderDetailsActivity.this, getList(), R.layout.history_order_item, 
	    		new String[]{"goodName","goodPrice","goodNum","goodReq"}, 
	    		new int[]{R.id.order_id,R.id.order_content,R.id.order_time,R.id.order_more});
	    
	    totalNumView = (TextView)findViewById(R.id.total_num);
	    totalMoneyView = (TextView)findViewById(R.id.totals);
		
	    orderIdView.setText("订单号："+order.getOrder_id());
	    orderTimeView.setText("下单时间："+order.getOrder_time());
	    orderAddressView.setText("收货地址:"+order.getOrder_address());
	    orderStateView.setText("交易状态："+order.getOrder_state());
	    
	    totalNumView.setText(total_num+"");
	    totalMoneyView.setText(total_price+"");
	    
	    goodsDetailsView.setAdapter(adapter);
	}

	private List<HashMap<String, Object>> getList() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = null;
		map = new HashMap<String, Object>();
		map.put("goodName", "商品名");
		map.put("goodPrice", "商品单价");
		map.put("goodNum", "商品数量");
		map.put("goodReq", "商品描述");
		list.add(map);
		List<FoodInfo> foodInfos = order.getOrderContentInfos();
		FoodInfo foodInfo = null;
		for(int i=0;i<foodInfos.size();i++){
			foodInfo = foodInfos.get(i);
			map = new HashMap<String, Object>();
			map.put("goodName", foodInfo.getFoodName());
			map.put("goodPrice", foodInfo.getFoodPrice());
			map.put("goodNum", foodInfo.getFoodNum());
			map.put("goodReq", foodInfo.getFoodRequire());
			list.add(map);
			total_num = total_num+foodInfo.getFoodNum();
			total_price = total_price+foodInfo.getFoodNum()*Integer.parseInt(foodInfo.getFoodPrice());
		}
		return list;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		default:
			break;
		}
	}

	

}
