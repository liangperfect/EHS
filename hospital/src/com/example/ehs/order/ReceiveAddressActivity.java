package com.example.ehs.order;

import com.example.ehs.R;
import com.example.ehs.model.UserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager.Query;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveAddressActivity extends Activity implements OnClickListener{
	private TextView receiveAddress;
	private Button changeAddress;
	private RelativeLayout changeLayout;
	private Button chooseBt;
	private Button inputBt;
	private LinearLayout chooseLayout;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	private EditText inputAddress;
	private RelativeLayout addressBt;
	private Button saveBt;
	private Button cancelBt;
	
	private static final String[] items1=new String[]{"��ѡ����","������","�����","סԺ¥"};
	private static final String[] items2=new String[]{"��ѡ�񷿼��","101","102","103"};
	private static final String[] items3=new String[]{"��ѡ�񴲺�","001","002","003"};
	
	private ArrayAdapter<String> spinner_adapter1=null;
	private ArrayAdapter<String> spinner_adapter2=null;
	private ArrayAdapter<String> spinner_adapter3=null;
	
	
	private int type=1;//1--��ʾѡ��2---��ʾ����
	private String address=null;
	private String temp[] = new String[3];
	
	private UserInfo userInfo = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive_address);
		userInfo = new UserInfo(this);
		init();
	}

	private void init() {
		receiveAddress = (TextView)findViewById(R.id.receive_address);
		
		changeAddress = (Button)findViewById(R.id.change_address);
		if((userInfo.getAddress().equals(""))||(userInfo.getAddress()==null)){
			receiveAddress.setText("����û�б������ַ���������");
			changeAddress.setText("���");
		}else{
			receiveAddress.setText(userInfo.getAddress());
		}
		changeAddress.setOnClickListener(this);
		
		changeLayout = (RelativeLayout)findViewById(R.id.change_address_layout);
		chooseBt = (Button)findViewById(R.id.choose_address);
		chooseBt.setOnClickListener(this);
		
		inputBt = (Button)findViewById(R.id.input_address);
		inputBt.setOnClickListener(this);
		
		chooseLayout = (LinearLayout)findViewById(R.id.choose_address_layout);
		spinner1 = (Spinner)findViewById(R.id.spinner_1);
		spinner2 = (Spinner)findViewById(R.id.spinner_2);
		spinner3 = (Spinner)findViewById(R.id.spinner_3);
		
		inputAddress = (EditText)findViewById(R.id.input_new_address);
		
		addressBt = (RelativeLayout)findViewById(R.id.address_bt);
		saveBt = (Button)findViewById(R.id.save_address);
		saveBt.setOnClickListener(this);
		cancelBt = (Button)findViewById(R.id.quit_address);
		cancelBt.setOnClickListener(this);
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				temp[0]=items1[arg2];
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				temp[1]=items2[arg2];
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				temp[2]=items3[arg2];
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_address:
			changeLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.choose_address:
			type = 1;
			chooseLayout.setVisibility(View.VISIBLE);
			inputAddress.setVisibility(View.GONE);
			addressBt.setVisibility(View.VISIBLE);
			spinner_adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items1);
			spinner_adapter2=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items2);
			spinner_adapter3=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items3);
			spinner1.setAdapter(spinner_adapter1);
			spinner2.setAdapter(spinner_adapter2);
			spinner3.setAdapter(spinner_adapter3);
			break;
		case R.id.input_address:
			type = 2;
			chooseLayout.setVisibility(View.GONE);
			inputAddress.setVisibility(View.VISIBLE);
			addressBt.setVisibility(View.VISIBLE);
			break;
		case R.id.save_address:
			if(type==1){
				address=temp[0]+temp[1]+"����"+temp[2]+"��";
			}else{
				address = inputAddress.getText().toString();
			}
			receiveAddress.setText(address);
			//�����ҵĵ�ַ
			userInfo.setAddress(address);
			changeAddress.setText("����");
			Toast.makeText(ReceiveAddressActivity.this, "����ɹ���", Toast.LENGTH_SHORT).show();
			/*new AlertDialog.Builder(ReceiveAddressActivity.this).setTitle("��ʾ")
			.setMessage("�����µ�ַ��\n"+address).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					receiveAddress.setText(address);
					//�����ҵĵ�ַ
					userInfo.setAddress(address);
					Toast.makeText(ReceiveAddressActivity.this, "����ɹ���", Toast.LENGTH_SHORT).show();
				}
			}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).create().show();*/
			break;
		case R.id.quit_address:
			changeLayout.setVisibility(View.GONE);
			chooseLayout.setVisibility(View.GONE);
			inputAddress.setVisibility(View.GONE);
			addressBt.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		
	}

}
