package com.example.ehs.order;

import com.example.ehs.R;
import com.example.ehs.model.FoodInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AMealInfoActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button backView;
	
	
	private ImageView mealImageview;
	private TextView mealPriceView;
	private Button mealOrderView;
	private TextView mealNameView;
	private TextView mealDetailView;
	
	private Bundle bd=null;
	private FoodInfo foodInfo=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_meal_info);
		
		bd = getIntent().getBundleExtra("foodBd");
		foodInfo = bd.getParcelable("food");
		System.out.println("foodInfo="+foodInfo.toString());
		
		init();
	}

	private void init() {
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText(foodInfo.getFoodName());
		
		backView = (Button)findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);
		
		mealImageview = (ImageView)findViewById(R.id.meal_big_img);
		String img = foodInfo.getFoodPic();
		int temp = Integer.parseInt(img);
		mealImageview.setImageResource(temp);
		
		mealPriceView = (TextView)findViewById(R.id.meal_price);
		mealPriceView.setText(foodInfo.getFoodPrice());
		
		mealOrderView = (Button)findViewById(R.id.order);
		mealOrderView.setOnClickListener(this);
		
		mealNameView = (TextView)findViewById(R.id.meal_name);
		mealNameView.setText(foodInfo.getFoodName());
		
		mealDetailView = (TextView)findViewById(R.id.meal_des);
		mealDetailView.setText(foodInfo.getFoodDes());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.order:
			Intent i = new Intent();
			i.setClass(AMealInfoActivity.this, PurseMealActivity.class);
			Bundle b = new Bundle();
			b.putString("name", foodInfo.getFoodName());
			b.putString("price", foodInfo.getFoodPrice());
			i.putExtra("bd", b);
			startActivity(i);
			break;
		default:
			break;
		}
	}

}
