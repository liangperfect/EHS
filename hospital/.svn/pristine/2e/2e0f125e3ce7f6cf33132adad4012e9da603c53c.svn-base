package com.example.ehs.hospital;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehs.R;
import com.example.ehs.model.Doctor;

public class OneDoctor extends Activity implements OnClickListener{
	
	Button btnexit;
	Doctor doctor = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_one_doctor);
		Intent intentjudge = getIntent();
		Bundle b = intentjudge.getBundleExtra("judgedoctor");
		//int judge = intentjudge.getExtras().getInt("judgedoctor");
		doctor = b.getParcelable("doctor");
		
		ImageView doctorImage = (ImageView) findViewById(R.id.doctorimage);
		TextView textdoctor = (TextView) findViewById(R.id.title);
		TextView doctorInfo = (TextView)findViewById(R.id.TestViewaboutdoctor);
		
		btnexit = (Button) findViewById(R.id.bt_left);
		btnexit.setVisibility(View.VISIBLE);
		btnexit.setOnClickListener(this);
		
		
		textdoctor.setText(doctor.getDoctor_name()+"简介");
		doctorInfo.setText("姓名："+doctor.getDoctor_name()+"\n"+"性别："+doctor.getDoctor_sex()
				+"\n年龄："+doctor.getDoctor_age()+"\n科室："+doctor.getDoctor_department_name()
				+"\n简介："+doctor.getDoctor_introduce());
		
		/*if (0 == judge) {
			doctorImage.setImageResource(R.drawable.aifushen);
			textdoctor.setText("艾弗森医生简介");
		}
		if (1 == judge) {
			doctorImage.setImageResource(R.drawable.kobe);
			textdoctor.setText("科比医生简介");
		}
		if (2 == judge) {

			doctorImage.setImageResource(R.drawable.weide);
			textdoctor.setText("韦德医生简介");
		}
		if (3 == judge) {

			doctorImage.setImageResource(R.drawable.mac);
			textdoctor.setText("麦迪医生简介");
		}
		btnexit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OneDoctor.this.finish();
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.one_doctor, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			this.finish();
			Toast.makeText(OneDoctor.this, "点击了某个医生详情！", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		
	}

}
