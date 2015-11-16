package com.example.ehs.shopping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehs.R;
import com.example.ehs.model.GoodInfo;
import com.example.ehs.order.PurseMealActivity;

public class GoodInfoActivity extends Activity implements OnClickListener {
	private TextView titleView;
	private Button btnExit;
	// 操作xml的控件
	private ImageView goodImgView;
	private TextView goodPriceView;
	private Button goodOrderView;
	private TextView goodNameView;
	private TextView goodDetailView;
	// 传递过来的bundle 和产品实体
	private Bundle bd = null;
	private GoodInfo goodInfo = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_info);
		Intent intent1 = getIntent();
		bd = intent1.getBundleExtra("goodBd");
		goodInfo = bd.getParcelable("good");
		init();

	}

	// 初始化界面
	private void init() {
		titleView = (TextView) findViewById(R.id.title);
		titleView.setText(goodInfo.getGoodName());

		btnExit = (Button) findViewById(R.id.bt_left);
		btnExit.setVisibility(View.VISIBLE);
		btnExit.setOnClickListener(this);

		goodImgView = (ImageView) findViewById(R.id.good_big_img);
		int imgNo = Integer.parseInt(goodInfo.getGoodImg());
		goodImgView.setImageResource(imgNo);

		goodPriceView = (TextView) findViewById(R.id.good_price);
		goodPriceView.setText(goodInfo.getGoodPri());

		goodNameView = (TextView) findViewById(R.id.good_name);
		goodNameView.setText(goodInfo.getGoodName());

		goodDetailView = (TextView) findViewById(R.id.good_des);
		goodDetailView.setText(goodInfo.getGoodDes());

		goodOrderView = (Button) findViewById(R.id.good_order);
		goodOrderView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.good_order:
			Intent i = new Intent();
			i.setClass(GoodInfoActivity.this, PurseGoodActivity.class);
			Bundle b = new Bundle();
			b.putString("name", goodInfo.getGoodName());
			b.putString("price", goodInfo.getGoodDes());
			i.putExtra("bd", b);
			startActivity(i);
			break;
		default:
			break;

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.good_info, menu);
		return true;
	}

}
