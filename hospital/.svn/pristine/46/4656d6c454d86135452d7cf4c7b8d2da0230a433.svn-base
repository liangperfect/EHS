package com.example.ehs.vedio;

import com.example.ehs.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 视频会诊模块--暂不开发
 * */
public class VedioMeetActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button leftBtView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vedio);
		setview();
		Toast.makeText(VedioMeetActivity.this, "视屏会议--暂未开发", Toast.LENGTH_SHORT).show();
	}
	private void setview() {
		titleView = (TextView)findViewById(R.id.title);
		leftBtView = (Button)findViewById(R.id.bt_left);
		titleView.setText("视频会诊");
		leftBtView.setVisibility(View.VISIBLE);
		leftBtView.setOnClickListener(this);
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
