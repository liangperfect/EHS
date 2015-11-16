package com.example.ehs.order;

import com.example.ehs.R;
import com.example.ehs.db.OrderDb;
import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.UserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PurseMealActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button backView;
	
	private TextView goodsNameView;
	private TextView siglePriceVIew;
	private Button reduceNumView;
	private EditText goodsNumView;
	private Button addNumView;
	private TextView totalPriceView;
	private EditText inputReauireView;
	private Button addToOrderView;
	
	Bundle b = null;
	String siglePrice=null;
	String goodsName=null;
	
	int goodsNum=0;
	UserInfo userInfo = null;
	OrderDb orderDb = null;
	FoodInfo foodInfo = null;
	Float total;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.puchase_meal);
		
		b = getIntent().getBundleExtra("bd");
		goodsName = b.getString("name");
		siglePrice = b.getString("price");
		System.out.println("goodsName="+goodsName);
		System.out.println("goodsPrice="+siglePrice);
		
		userInfo = new UserInfo(this);
		orderDb = new OrderDb(this);
		
		init();
	}

	private void init() {
		titleView = (TextView) findViewById(R.id.title);
		titleView.setText("加入购物车");
		
		backView = (Button)findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);
		
		goodsNameView = (TextView)findViewById(R.id.puchase_name);
		goodsNameView.setText(goodsName);
		
		siglePriceVIew = (TextView)findViewById(R.id.sigle_prices);
		siglePriceVIew.setText(siglePrice);
		
		
		reduceNumView = (Button)findViewById(R.id.reduce_num);
		reduceNumView.setOnClickListener(this);
		
		goodsNumView = (EditText)findViewById(R.id.input_num);
		goodsNumView.setText("1");
		
		addNumView = (Button)findViewById(R.id.add_num);
		addNumView.setOnClickListener(this);
		
		totalPriceView = (TextView)findViewById(R.id.total_prices);
		
		inputReauireView = (EditText)findViewById(R.id.input_requirement);
		
		addToOrderView = (Button)findViewById(R.id.add_to_my_order);
		addToOrderView.setOnClickListener(this);
		
		goodsNum = Integer.parseInt(goodsNumView.getText().toString());
		System.out.println("goodsNum="+goodsNum);

		total = Float.parseFloat(siglePrice.substring(0,siglePrice.lastIndexOf("元"))) * goodsNum;
		totalPriceView.setText(""+total);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.reduce_num:
			goodsNum = Integer.parseInt(goodsNumView.getText().toString());
			if(goodsNum>0){
				goodsNum = goodsNum-1;
				goodsNumView.setText(""+goodsNum);
			}else{
				Toast.makeText(PurseMealActivity.this, "购买商品数不能为负！", Toast.LENGTH_SHORT).show();
			}
			total = Float.parseFloat(siglePrice.substring(0,siglePrice.lastIndexOf("元"))) * goodsNum;
			totalPriceView.setText(""+total);
			break;
		case R.id.add_num:
			goodsNum = Integer.parseInt(goodsNumView.getText().toString());
			goodsNum = goodsNum+1;
			goodsNumView.setText(""+goodsNum);
			total = Float.parseFloat(siglePrice.substring(0,siglePrice.lastIndexOf("元"))) * goodsNum;
			totalPriceView.setText(""+total);
			break;
		case R.id.add_to_my_order:
			new AlertDialog.Builder(PurseMealActivity.this).setTitle("确认将商品添加到购物车吗？")
			.setMessage("购买信息：\n"+"商品名称："+goodsName+"\n购买数量："+goodsNumView.getText()+"\n备注："
					+inputReauireView.getText()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//添加到购物车
							foodInfo = new FoodInfo(goodsName, "", "", siglePrice.substring(0,siglePrice.lastIndexOf("元")), inputReauireView.getText().toString(), Integer.parseInt(goodsNumView.getText().toString()));
							//type分别表示:0---未提交订单，1---已提交订单,2---未付款订单,
							orderDb.saveOrder(foodInfo, "", userInfo.getAccount(), "", 0);
							Toast.makeText(PurseMealActivity.this, "添加成功！请及时到购物提交订单！", Toast.LENGTH_SHORT).show();
							Intent i = new Intent();
							i.setClass(PurseMealActivity.this, UnSubmitActivity.class);
							startActivity(i);
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

}
