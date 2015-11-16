package com.example.ehs.query;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OneCheckActivity extends Activity implements OnClickListener{
	private TextView title;
	private Button backView;
	private TextView time;
	private TextView content;
	Bundle b =null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_check);
		
		b = getIntent().getBundleExtra("bd");
		init();
	}

	private void init() {
		title = (TextView)findViewById(R.id.title);
		title.setText(b.getString("name"));
		
		backView = (Button)findViewById(R.id.bt_left);
		backView.setOnClickListener(this);
		backView.setVisibility(View.VISIBLE);
		
		time = (TextView)findViewById(R.id.one_check_time);
		time.setText("ºÏ≤È ±º‰£∫"+b.getString("time"));
		content = (TextView)findViewById(R.id.one_check_content);
		content.setText(b.getString("details"));
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
