package com.example.ehs.im;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.xmpphelper.ContacterManager;

public class AddNextActivity extends Activity implements OnClickListener {
	private TextView titleView;
	private Button leftBtView;

	private TextView addTxView;
	private EditText addEtView;
	private Button addSearchView;

	private ImageView friendsAvaterView;
	private TextView friendsIdView;
	private TextView friendsNameView;
	private Button addFriendsView;
	LinearLayout linearLayout;

	String add = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_next);
		add = this.getIntent().getStringExtra("add");
		System.out.println("add=" + add);
		init();
	}

	private void init() {
		titleView = (TextView) findViewById(R.id.title);
		leftBtView = (Button) findViewById(R.id.bt_left);

		addTxView = (TextView) findViewById(R.id.add_tx);
		addEtView = (EditText) findViewById(R.id.add_input);
		addSearchView = (Button) findViewById(R.id.add_search);
		addSearchView.setOnClickListener(this);
		if (add.equals("friend")) {
			titleView.setText("添加好友");
			addTxView.setText("请输入好友账号:");
		} else {
			titleView.setText("加入群");
			addTxView.setText("请输入群号");
		}
		leftBtView.setVisibility(View.VISIBLE);

		linearLayout = (LinearLayout) findViewById(R.id.search_layout_1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			break;
		case R.id.add_search:
			search();
			break;
		default:
			break;
		}

	}

	// 查找
	private void search() {
		if (TextUtils.isEmpty(addEtView.getText().toString())) {
			Toast.makeText(AddNextActivity.this, "请输入要查找的ID",
					Toast.LENGTH_SHORT).show();
		} else {
			// 查找完后
			Toast.makeText(AddNextActivity.this,
					addEtView.getText().toString(), Toast.LENGTH_SHORT).show();
			ContacterManager.searchSB(AddNextActivity.this, "", addEtView
					.getText().toString());
			linearLayout.setVisibility(View.VISIBLE);
			friendsAvaterView = (ImageView) findViewById(R.id.add_avater);
			friendsIdView = (TextView) findViewById(R.id.add_id);
			friendsNameView = (TextView) findViewById(R.id.add_name);
			addFriendsView = (Button) findViewById(R.id.add_ok);
			addFriendsView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 添加好友
				}
			});
		}

	}

}
