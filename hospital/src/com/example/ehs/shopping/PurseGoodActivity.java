package com.example.ehs.shopping;

import com.example.ehs.R;
import com.example.ehs.R.layout;
import com.example.ehs.R.menu;
import com.example.ehs.db.OrderGoodDb;
import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.GoodInfo;
import com.example.ehs.model.UserInfo;
import com.example.ehs.order.PurseMealActivity;
import com.example.ehs.order.UnSubmitActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PurseGoodActivity extends Activity implements OnClickListener {

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
	String siglePrice = null;
	String goodsName = null;

	int goodsNum = 0;
	UserInfo userInfo = null;
	OrderGoodDb orderGoodDb = null;
	GoodInfo goodInfo = null;
	Float total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_purse_good);
		b = getIntent().getBundleExtra("bd");
		goodsName = b.getString("name");
		siglePrice = b.getString("price");

		userInfo = new UserInfo(this);
		init();
	}

	private void init() {

		titleView = (TextView) findViewById(R.id.title);
		titleView.setText("���빺�ﳵ");

		backView = (Button) findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);

		goodsNameView = (TextView) findViewById(R.id.puchase_good_name);
		goodsNameView.setText(goodsName);

		siglePriceVIew = (TextView) findViewById(R.id.sigle_good_prices);
		siglePriceVIew.setText(siglePrice);

		reduceNumView = (Button) findViewById(R.id.reduce_good_num);
		reduceNumView.setOnClickListener(this);

		goodsNumView = (EditText) findViewById(R.id.input_good_num);
		goodsNumView.setText("1");

		addNumView = (Button) findViewById(R.id.add_good_num);
		addNumView.setOnClickListener(this);

		totalPriceView = (TextView) findViewById(R.id.total_good_prices);

		inputReauireView = (EditText) findViewById(R.id.input_good_requirement);

		addToOrderView = (Button) findViewById(R.id.add_to_my_good_order);
		addToOrderView.setOnClickListener(this);

		goodsNum = Integer.parseInt(goodsNumView.getText().toString());

		total = Float.parseFloat(siglePrice.substring(0,
				siglePrice.lastIndexOf("Ԫ")))
				* goodsNum;
		totalPriceView.setText("" + total);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.purse_good, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.reduce_good_num:
			goodsNum = Integer.parseInt(goodsNumView.getText().toString());
			if (goodsNum > 0) {
				goodsNum = goodsNum - 1;
				goodsNumView.setText("" + goodsNum);
			} else {
				Toast.makeText(PurseGoodActivity.this, "������Ʒ������Ϊ����",
						Toast.LENGTH_SHORT).show();
			}
			total = Float.parseFloat(siglePrice.substring(0,
					siglePrice.lastIndexOf("Ԫ")))
					* goodsNum;
			totalPriceView.setText("" + total);
			break;
		case R.id.add_good_num:
			goodsNum = Integer.parseInt(goodsNumView.getText().toString());
			goodsNum = goodsNum + 1;
			goodsNumView.setText("" + goodsNum);
			total = Float.parseFloat(siglePrice.substring(0,
					siglePrice.lastIndexOf("Ԫ")))
					* goodsNum;
			totalPriceView.setText("" + total);
			break;
		case R.id.add_to_my_good_order:
			new AlertDialog.Builder(PurseGoodActivity.this)
					.setTitle("ȷ�Ͻ���Ʒ��ӵ����ﳵ��")
					.setMessage(
							"������Ϣ��\n" + "��Ʒ���ƣ�" + goodsName + "\n����������"
									+ goodsNumView.getText() + "\n��ע��"
									+ inputReauireView.getText())
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// ��ӵ����ﳵ
									goodInfo = new GoodInfo(
											goodsName,
											"",
											"",
											siglePrice.substring(0,
													siglePrice.lastIndexOf("Ԫ")),
											inputReauireView.getText()
													.toString(), Integer
													.parseInt(goodsNumView
															.getText()
															.toString()));

									orderGoodDb = new OrderGoodDb(
											PurseGoodActivity.this);
									// type�ֱ��ʾ:0---δ�ύ������1---���ύ����,2---δ�����,
									String judge = orderGoodDb.saveOrder(
											goodInfo, "", "123", "", 0);
									Toast.makeText(PurseGoodActivity.this,
											"��ӳɹ����뼰ʱ�������ύ������" + judge,
											Toast.LENGTH_SHORT).show();
									Toast.makeText(PurseGoodActivity.this,
											"�ɹ��˵�", Toast.LENGTH_LONG);
									Intent i = new Intent();
									i.setClass(PurseGoodActivity.this,
											UnSubmitGoodActivity.class);
									startActivity(i);
								}
							})
					.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).create().show();
			break;

		default:
			break;
		}

	}

}
