package com.example.ehs.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.model.GoodInfo;
import com.example.ehs.order.MealMenuActivity;

public class GoodsBrowActivity extends Activity {
	/*
	 * 购物模块---物品浏览界面
	 */
	private Spinner goodsSpinner;
	private ListView goodsList;
	private static final String items[] = { "所有", "生活用品", "医药用品", "小零食" };
	private ArrayAdapter<String> spinnerAdapter = null;
	private SimpleAdapter listAdapter = null;
	private List<HashMap<String, Object>> meanuList = null;
	private HashMap<String, Object> menuMap = null;
	private GoodInfo goodInfo;

	private static final int ALL_MENU = 0;
	private static final int DAIL_MENU = 1;
	private static final int MEDICAL_MENU = 2;
	private static final int SNACK_MEANU = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_brow);
		// Toast.makeText(this, "未开发！", Toast.LENGTH_LONG).show();

		init();

	}

	// 界面数据的初始化
	private void init() {
		goodsSpinner = (Spinner) findViewById(R.id.goods_spinner);
		goodsList = (ListView) findViewById(R.id.goods_listView);
		// 对goodspinner进行初始化
		spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		goodsSpinner.setAdapter(spinnerAdapter);
		goodsSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						switch (position) {
						case 0:// 所有
							meanuList = getList(ALL_MENU);
							listAdapter = new SimpleAdapter(
									GoodsBrowActivity.this, meanuList,
									R.layout.goodmenu_item, new String[] {
											"good_img", "good_name",
											"good_description", "good_price" },
									new int[] { R.id.good_img, R.id.good_name,
											R.id.good_description,
											R.id.good_price });
							goodsList.setAdapter(listAdapter);
							break;
						case 1:// 生活用品
							meanuList = getList(DAIL_MENU);
							listAdapter = new SimpleAdapter(
									GoodsBrowActivity.this, meanuList,
									R.layout.goodmenu_item, new String[] {
											"good_img", "good_name",
											"good_description", "good_price" },
									new int[] { R.id.good_img, R.id.good_name,
											R.id.good_description,
											R.id.good_price });
							goodsList.setAdapter(listAdapter);

							break;
						case 2:// 医药用品

							meanuList = getList(MEDICAL_MENU);
							listAdapter = new SimpleAdapter(
									GoodsBrowActivity.this, meanuList,
									R.layout.goodmenu_item, new String[] {
											"good_img", "good_name",
											"good_description", "good_price" },
									new int[] { R.id.good_img, R.id.good_name,
											R.id.good_description,
											R.id.good_price });
							goodsList.setAdapter(listAdapter);
							break;
						case 3:// 小零食

							meanuList = getList(SNACK_MEANU);
							listAdapter = new SimpleAdapter(
									GoodsBrowActivity.this, meanuList,
									R.layout.goodmenu_item, new String[] {
											"good_img", "good_name",
											"good_description", "good_price" },
									new int[] { R.id.good_img, R.id.good_name,
											R.id.good_description,
											R.id.good_price });
							goodsList.setAdapter(listAdapter);
							break;

						default:
							break;

						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		goodsList.setOnItemClickListener(new ListView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				menuMap = meanuList.get(position);
				Object imgStr = menuMap.get("good_img");
				System.out.println("imgStr=" + imgStr);
				goodInfo = new GoodInfo((String) menuMap.get("good_name"),
						(String) menuMap.get("good_description"), imgStr
								.toString(),
						(String) menuMap.get("good_price"), "", 0);
				System.out.println(goodInfo.toString());
				Intent i = new Intent();
				i.setClass(GoodsBrowActivity.this, GoodInfoActivity.class);
				Bundle b = new Bundle();
				b.putParcelable("good", goodInfo);
				i.putExtra("goodBd", b);
				startActivity(i);
			}
		});

	}

	// 根据不同分类获取list
	private List<HashMap<String, Object>> getList(int menuType) {
		List<HashMap<String, Object>> goodslist = null;
		HashMap<String, Object> item = null;
		switch (menuType) {
		case ALL_MENU:

			// 生活用品
			goodslist = new ArrayList<HashMap<String, Object>>();
			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.cup1);
			item.put("good_name", "保温杯");
			item.put("good_description", "描述：保温长达5小时");
			item.put("good_price", "50元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.zhi1);
			item.put("good_name", "卫生纸");
			item.put("good_description", "描述：芬香，三层，坚韧");
			item.put("good_price", "15.5元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.cup2);
			item.put("good_name", "保温杯");
			item.put("good_description", "描述：保温长达5小时");
			item.put("good_price", "65元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.zhi1);
			item.put("good_name", "保温杯");
			item.put("good_description", "描述：芬香,三层,坚韧,柔软");
			item.put("good_price", "13元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.yashua1);
			item.put("good_name", "牙刷");
			item.put("good_description", "描述：柔软");
			item.put("good_price", "15元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.shuzi3);
			item.put("good_name", "梳子");
			item.put("good_description", "描述：檀木");
			item.put("good_price", "39元");
			goodslist.add(item);

			// 医药用品
			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.mask1);
			item.put("good_name", "口罩");
			item.put("good_description", "描述：超强净化");
			item.put("good_price", "20元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.first_ad);
			item.put("good_name", "急救包");
			item.put("good_description", "描述：便携药包");
			item.put("good_price", "120元");
			goodslist.add(item);

			// 零食
			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.renshen1);
			item.put("good_name", "野生人参");
			item.put("good_description", "描述：高药性");
			item.put("good_price", "120元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.yanwo);
			item.put("good_name", "燕窝");
			item.put("good_description", "描述：高营养");
			item.put("good_price", "200元");
			goodslist.add(item);
			break;
		case DAIL_MENU:
			goodslist = new ArrayList<HashMap<String, Object>>();
			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.cup1);
			item.put("good_name", "保温杯");
			item.put("good_description", "描述：保温长达5小时");
			item.put("good_price", "50元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.zhi1);
			item.put("good_name", "卫生纸");
			item.put("good_description", "描述：芬香，三层，坚韧");
			item.put("good_price", "15.5元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.cup2);
			item.put("good_name", "保温杯");
			item.put("good_description", "描述：保温长达5小时");
			item.put("good_price", "65元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.zhi1);
			item.put("good_name", "保温杯");
			item.put("good_description", "描述：芬香，三层，坚韧,柔软");
			item.put("good_price", "13元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.yashua1);
			item.put("good_name", "牙刷");
			item.put("good_description", "描述：柔软");
			item.put("good_price", "15s元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.shuzi3);
			item.put("good_name", "梳子");
			item.put("good_description", "描述：檀木");
			item.put("good_price", "39元");
			goodslist.add(item);

			break;
		case MEDICAL_MENU:
			goodslist = new ArrayList<HashMap<String, Object>>();
			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.mask1);
			item.put("good_name", "口罩");
			item.put("good_description", "描述：超强净化");
			item.put("good_price", "20元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.first_ad);
			item.put("good_name", "急救包");
			item.put("good_description", "描述：便携药包");
			item.put("good_price", "120元");
			goodslist.add(item);
			break;
		case SNACK_MEANU:
			goodslist = new ArrayList<HashMap<String, Object>>();
			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.renshen1);
			item.put("good_name", "野生人参");
			item.put("good_description", "描述：高药性");
			item.put("good_price", "120元");
			goodslist.add(item);

			item = new HashMap<String, Object>();
			item.put("good_img", R.drawable.yanwo);
			item.put("good_name", "燕窝");
			item.put("good_description", "描述：高营养");
			item.put("good_price", "200元");
			goodslist.add(item);
			break;
		default:
			break;
		}
		return goodslist;
	}

}
