package com.example.ehs.im;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;

import com.example.ehs.R;
import com.example.ehs.utils.ConnectionUtils;
import com.example.ehs.xmpphelper.ContacterManager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * 好友信息模块
 * */
public class FriendsInfoActivity extends Activity implements OnClickListener{
	private TextView titleView;
	private Button backView;
	
	private ImageView friends_avater;
	private TextView nick_name;
	private TextView friends_sex;
	private TextView friends_jid;
	private TextView friends_phone;
	private TextView friends_level;
	private TextView friends_sign;
	
	private TextView friends_more;
	private TextView friends_more_1;
	private TextView friends_more_2;
	private TextView friends_more_3;
	
	VCard vCard = null;
	
	String friendsName=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_info);
	    friendsName = getIntent().getStringExtra("his_info");
	    System.out.println("his_id="+friendsName);
		init();
	}
	private void init() {
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText("个人资料");
		
		backView = (Button)findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);
		
		friends_avater = (ImageView)findViewById(R.id.friends_avater);
		nick_name = (TextView)findViewById(R.id.friends_name);
		friends_sex = (TextView)findViewById(R.id.friends_sex);
		friends_jid = (TextView)findViewById(R.id.friend_id);
		friends_phone = (TextView)findViewById(R.id.friend_phone);
		friends_level = (TextView)findViewById(R.id.friend_level);
		friends_sign = (TextView)findViewById(R.id.friend_sign);
		friends_more = (TextView)findViewById(R.id.friend_more);
		friends_more_1 = (TextView)findViewById(R.id.friend_more_1);
		friends_more_2 = (TextView)findViewById(R.id.friend_more_2);
		friends_more_3 = (TextView)findViewById(R.id.friend_more_3);
		/*friends_more.setText(">");
		friends_more_1.setText(">");
		friends_more_2.setText(">");
		friends_more_3.setText(">");*/
		try {
			vCard = ContacterManager.getUserVCard(ConnectionUtils.getConnection(FriendsInfoActivity.this), friendsName);
			/*nick_name.setText(vCard.getNickName());
			friends_sex.setText(vCard.getAddressFieldHome("REGION"));
			friends_jid.setText(vCard.getJabberId());
			friends_phone.setText(vCard.getPhoneHome("CELL"));
			friends_level.setText(vCard.getType().toString());
			friends_sign.setText(vCard.getDefaultLanguage());
			byte[] bytes = vCard.getAvatar();
			 if(bytes.length == 0) {
				 return;
			 }else{
				 final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  
					friends_avater.setImageBitmap(bitmap);
			}*/
		
		} catch (XMPPException e) {
			e.printStackTrace();
			System.out.println("获取名片失败！");
		}
		//System.out.println("vcard="+vCard.toString());	
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
