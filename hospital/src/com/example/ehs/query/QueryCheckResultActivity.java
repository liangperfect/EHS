package com.example.ehs.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class QueryCheckResultActivity extends Activity implements OnClickListener{
	private EditText inputCheckName;
	private Button queryCheck;
	private TextView checkResult;
	private ListView resultListView;
	
	private List<HashMap<String, Object>> list=null;
	private HashMap<String, Object> map=null;
	SimpleAdapter adapter = null;
	
	String queryStr=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_check);
		
		init();
	}
	private void init() {
		inputCheckName = (EditText)findViewById(R.id.input_checkname);
		queryCheck = (Button)findViewById(R.id.query_check);
		queryCheck.setOnClickListener(this);
		
		checkResult = (TextView)findViewById(R.id.result_check);
		resultListView = (ListView)findViewById(R.id.query_check_list);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.query_check:
			queryStr = inputCheckName.getText().toString();
			System.out.println("queryStr="+queryStr);
			if(TextUtils.isEmpty(queryStr)){
				Toast.makeText(QueryCheckResultActivity.this, "����Ĳ�ѯ���Ϊ�գ�", Toast.LENGTH_SHORT).show();
			}else{
				//Toast.makeText(QueryCheckResultActivity.this, "��ѯ��,���Ժ󡣡�", Toast.LENGTH_LONG).show();
				checkResult.setVisibility(View.VISIBLE);
				checkResult.setText("��û�д˼���ļ�¼��");
			}
			break;
		default:
			break;
		}
	}
}
