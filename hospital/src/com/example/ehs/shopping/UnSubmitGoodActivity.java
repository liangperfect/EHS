package com.example.ehs.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.ehs.R;
import com.example.ehs.R.layout;
import com.example.ehs.R.menu;
import com.example.ehs.db.OrderGoodDb;
import com.example.ehs.model.GoodInfo;
import com.example.ehs.model.UserInfo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UnSubmitGoodActivity extends Activity {

	private List<Object> judgeSet = new ArrayList<Object>(); // ��ֹ������������checkbox��ת̨�ָ�
	private List<HashMap<String, Object>> goodSelected = new ArrayList<HashMap<String, Object>>(); // װ���ڱ�ѡ�е���Ʒ
	private ListView listView;
	// ȫѡ��checkbox
	private CheckBox allCheckBox;
	private TextView has_choose_num_view;
	private TextView totalPriceView;
	private Button submitBt;

	private int goodTotalNum = 0;
	private float priceSum = 0;

	private List<HashMap<String, Object>> list = null;
	HashMap<String, Object> map = null;
	GoodInfo goodInfo = null;
	UserInfo userInfo = null;
	OrderGoodDb db = null;

	GoodAdapter adapter = null;
	GoodAdapter adapterCheckAll = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_un_submit_good);

		userInfo = new UserInfo(UnSubmitGoodActivity.this);
		db = new OrderGoodDb(UnSubmitGoodActivity.this);
		init();

	}

	private void init() {
		listView = (ListView) findViewById(R.id.unsubmit_good_list);
		allCheckBox = (CheckBox) findViewById(R.id.check_good_all);
		has_choose_num_view = (TextView) findViewById(R.id.has_choose_good_num);
		totalPriceView = (TextView) findViewById(R.id.total_good_price);
		submitBt = (Button) findViewById(R.id.submit_good);
		/*
		 * checkboxALLˢ��������
		 */
		allCheckBox.setVisibility(View.GONE);
		allCheckBox.setVisibility(View.GONE);
		// ��ListView���г�ʼ��
		adapter = new GoodAdapter(this, getOrderList(), listView, 0);
		listView.setAdapter(adapter);

		// allCheckBox
		// .setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if (allCheckBox.isChecked()) {
		//
		// adapterCheckAll = new GoodAdapter(
		// UnSubmitGoodActivity.this, getOrderList(),
		// listView, 1);
		//
		// listView.setAdapter(adapterCheckAll);
		// List<HashMap<String, Object>> items = new ArrayList<HashMap<String,
		// Object>>();
		// items = db.getOrderByUid("123");
		// HashMap<String, Object> map1 = new HashMap<String, Object>();
		// GoodInfo goodInfo1 = new GoodInfo();
		// goodTotalNum = 0;
		// priceSum = 0;
		// for (int i = 0; i < items.size(); i++) {
		// map1 = items.get(i);
		// goodInfo1 = (GoodInfo) map1.get("goodOrder");
		// goodTotalNum = goodTotalNum
		// + Integer.parseInt(""
		// + goodInfo1.getGoodNum());
		//
		// priceSum = priceSum
		// + Float.parseFloat(goodInfo1
		// .getGoodPri()) * goodTotalNum;
		//
		// }
		// has_choose_num_view.setText("" + goodTotalNum);
		// totalPriceView.setText("" + priceSum);
		// submitBt.setVisibility(View.VISIBLE);
		// } else {
		// adapterCheckAll = new GoodAdapter(
		// UnSubmitGoodActivity.this, getOrderList(),
		// listView, 2);
		// has_choose_num_view.setText("" + 0);
		// totalPriceView.setText("" + 0);
		// goodTotalNum = 0;
		// priceSum = 0;
		// listView.setAdapter(adapterCheckAll);
		// submitBt.setVisibility(View.GONE);
		// }
		//
		// }
		// });
		//

		submitBt.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(UnSubmitGoodActivity.this)
						.setTitle("����ȷ��")
						.setMessage("��ȷ���Ƿ��ύ����,�ύ�����޸�")
						.setPositiveButton("ȷ���ύ",
								new AlertDialog.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// putServerDataװ�����ύ��������������
										List<HashMap<String, Object>> putServerData = new ArrayList<HashMap<String, Object>>();
										HashMap<String, Object> mapData = null;
										GoodInfo asiGoodInfo = null;
										for (int i = 0; i < goodSelected.size(); i++) {
											mapData = new HashMap<String, Object>();
											asiGoodInfo = new GoodInfo();
											mapData = goodSelected.get(i);
											asiGoodInfo
													.setGoodName(mapData.get(
															"goodName")
															.toString());
											asiGoodInfo.setGoodPri(mapData.get(
													"goodPrice").toString());
											asiGoodInfo.setGoodNum(Integer
													.parseInt(mapData.get(
															"goodNum")
															.toString()));
											System.out.println("��Ʒ������Ϊ:"
													+ mapData.get("goodName")
															.toString());
											System.out.println("��Ʒ�ļ۸�Ϊ:"
													+ mapData.get("goodPrice")
															.toString());
											System.out.println("��Ʒ��������"
													+ Integer.parseInt(mapData
															.get("goodNum")
															.toString()));

										}

										goodSelected.clear();
									}
								})
						.setNegativeButton("���ύ",
								new AlertDialog.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										System.out.println("���ύ����");
									}
								}).create().show();

			}
		});

		listView.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(UnSubmitGoodActivity.this, "�����˵�" + arg2,
						Toast.LENGTH_LONG);

			}

		});

	}

	// ��list��ÿ��item�����¼���

	// ��ȡ���ݿ�����ݵķ���
	private List<HashMap<String, Object>> getOrderList() {
		// ���õ�һ����ʾ������
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		map = new HashMap<String, Object>();
		map.put("cb", null);
		map.put("goodName", "��Ʒ����");
		map.put("goodPrice", "��Ʒ�۸�");
		map.put("goodNum", "��Ʒ����");
		result.add(map);
		List<HashMap<String, Object>> orderByDB = new ArrayList<HashMap<String, Object>>();
		orderByDB = db.getOrderByUid("123");
		for (int i = 0; i < orderByDB.size(); i++) {
			map = new HashMap<String, Object>();
			goodInfo = (GoodInfo) orderByDB.get(i).get("goodOrder");
			map.put("goodName", goodInfo.getGoodName());
			map.put("goodPrice", goodInfo.getGoodPri());
			map.put("goodNum", goodInfo.getGoodNum());
			result.add(map);
		}
		return result;
	}

	// �Զ���Adapter Ҳ����ÿ��Ҫ��ʾ�Ķ���

	public class GoodAdapter extends BaseAdapter {
		private Context context;
		private List<HashMap<String, Object>> items;
		private ListView goodList;
		private int Type;

		public GoodAdapter(Context context,
				List<HashMap<String, Object>> items, ListView goodList, int Type) {
			this.context = context;
			this.items = items;
			this.goodList = goodList;
			this.Type = Type;
		}

		@Override
		public int getCount() {

			return items.size();
		}

		@Override
		public Object getItem(int position) {

			return items.get(position);
		}

		/**
		 * ���ڻ�ȡѡ�е���Ʒ
		 * 
		 * @param position
		 * @return
		 */
		public HashMap<String, Object> getItemMap(int position) {
			return items.get(position);

		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			HashMap<String, Object> map = items.get(position);
			View layoutView = UnSubmitGoodActivity.this.getLayoutInflater()
					.inflate(R.layout.unsubmit_good_item, null);
			final CheckBox cb = (CheckBox) layoutView.findViewById(R.id.goodCb);
			TextView selectText = (TextView) layoutView
					.findViewById(R.id.good_choose);
			TextView nameText = (TextView) layoutView
					.findViewById(R.id.good_name);
			final TextView priceText = (TextView) layoutView
					.findViewById(R.id.good_price);
			final TextView numText = (TextView) layoutView
					.findViewById(R.id.good_num);
			if (0 == position) {
				cb.setVisibility(View.GONE);
				selectText.setVisibility(View.VISIBLE);
				selectText.setText("ѡ��");
			} else {
				cb.setVisibility(View.VISIBLE);
				selectText.setVisibility(View.GONE);
			}
			nameText.setText((CharSequence) map.get("goodName"));
			priceText.setText((CharSequence) map.get("goodPrice"));
			numText.setText("" + map.get("goodNum"));

			// if (1 == Type) {
			// cb.setChecked(true);
			// judgeSet.add(getItem(position).toString() + position);
			// }
			//
			// if (2 == Type) {
			// cb.setChecked(false);
			// judgeSet.remove(getItem(position).toString() + position);
			//
			// }

			cb.setOnClickListener(new CheckBox.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (cb.isChecked()) {
						Toast.makeText(UnSubmitGoodActivity.this,
								"ѡ��" + getItemMap(position).get("goodName"),
								Toast.LENGTH_LONG).show();
						judgeSet.add(getItem(position).toString() + position);
						goodSelected.add(getItemMap(position));
						goodTotalNum = goodTotalNum
								+ Integer
										.parseInt(numText.getText().toString());
						priceSum = priceSum
								+ (Float.parseFloat(priceText.getText()
										.toString()))
								* Integer
										.parseInt(numText.getText().toString());
						has_choose_num_view.setText("" + goodTotalNum);
						totalPriceView.setText("" + priceSum);
						submitBt.setVisibility(View.VISIBLE);
					}
					if (!cb.isChecked()) {

						judgeSet.remove(getItem(position).toString() + position);
						goodSelected.remove(getItemMap(position));
						goodTotalNum = goodTotalNum
								- Integer
										.parseInt(numText.getText().toString());
						priceSum = priceSum
								- (Float.parseFloat(priceText.getText()
										.toString()))
								* Integer
										.parseInt(numText.getText().toString());

						has_choose_num_view.setText("" + goodTotalNum);

						totalPriceView.setText("" + priceSum);
						if (judgeSet.isEmpty()) {

							submitBt.setVisibility(View.GONE);
						}

					}
				}
			});
			Object b = (Object) getItem(position);

			if (b != null) {
				if (judgeSet.contains(b.toString() + position)) {
					cb.setChecked(true);
				} else {
					cb.setChecked(false);
				}

			}
			/*
			 * cb.setOnCheckedChangeListener(new
			 * CheckBox.OnCheckedChangeListener() {
			 * 
			 * @Override public void onCheckedChanged(CompoundButton buttonView,
			 * boolean isChecked) { if (cb.isChecked()) {
			 * Toast.makeText(UnSubmitGoodActivity.this, "�����",
			 * Toast.LENGTH_LONG).show();
			 * 
			 * goodTotalNum = goodTotalNum + Integer
			 * .parseInt(numText.getText().toString()); priceSum = priceSum +
			 * (Float.parseFloat(priceText.getText() .toString())) Integer
			 * .parseInt(numText.getText().toString());
			 * has_choose_num_view.setText("" + goodTotalNum);
			 * totalPriceView.setText("" + priceSum);
			 * submitBt.setVisibility(View.VISIBLE); } if (!cb.isChecked()) {
			 * goodTotalNum = goodTotalNum - Integer
			 * .parseInt(numText.getText().toString()); priceSum = priceSum -
			 * (Float.parseFloat(priceText.getText() .toString())) Integer
			 * .parseInt(numText.getText().toString());
			 * has_choose_num_view.setText("" + goodTotalNum);
			 * totalPriceView.setText("" + priceSum);
			 * 
			 * }
			 * 
			 * } });
			 */

			return layoutView;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.un_submit_good, menu);
		return true;
	}

}
