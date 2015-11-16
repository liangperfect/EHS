package com.example.ehs.query;

import com.example.ehs.R;
import com.example.ehs.model.DoctorWords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DoctorWordsDetailActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button backView;
	private TextView personView;
	private TextView subjectView;
	private TextView timeView;
	private TextView contentView;
	
	DoctorWords doctorWords = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_words_detail);
		
		Intent i = getIntent();
		Bundle d = i.getBundleExtra("bd");
		doctorWords = d.getParcelable("words");
		System.out.println("doctorWord="+doctorWords);
		init();
	}

	private void init() {
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText("医嘱详情");
		
		backView = (Button)findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);
		
		personView = (TextView)findViewById(R.id.words_person);
		subjectView = (TextView)findViewById(R.id.words_subject);
		timeView = (TextView)findViewById(R.id.words_time);
		contentView = (TextView)findViewById(R.id.words_content);
		
		personView.setText("医嘱来源："+doctorWords.getDoctorName());
		subjectView.setText("医生科室："+doctorWords.getDoctorSubject());
		timeView.setText("遗嘱时间："+doctorWords.getDoctorWordsTime());
		contentView.setText("遗嘱内容："+doctorWords.getDoctorWordsContent());
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
