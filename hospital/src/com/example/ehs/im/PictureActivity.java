package com.example.ehs.im;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.ehs.R;
import com.example.ehs.utils.ComPressUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PictureActivity extends Activity implements OnClickListener{
	private Button backView;
	private TextView titleView;
	private ImageView pictureView;
	private LinearLayout saveLayout;
	private Button saveView;
	private int isSave=0;//为0表示未保存，为1表示以存储
	Bitmap bitmap=null;
	String style=null;
	String picUrl=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture);
		//bitmap = (Bitmap) this.getIntent().getExtras().get("pic");
		Bundle bd = this.getIntent().getBundleExtra("pic");
		style = bd.getString("from");
		if(style.equals("from")){
			byte[] bis = bd.getByteArray("bit");
			bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
		}else{
			picUrl = bd.getString("url");
			//File f = new File(picUrl);
			//bitmap=BitmapFactory.decodeFile(picUrl);
			bitmap=ComPressUtils.getSmallBitmap(picUrl);
		}

		init();
	}

	private void init() {
		/*backView = (Button)findViewById(R.id.bt_left);
		backView.setVisibility(View.VISIBLE);
		backView.setOnClickListener(this);
		
		titleView = (TextView)findViewById(R.id.title);
		titleView.setText("图片预览");*/
		
		
		pictureView  = (ImageView)findViewById(R.id.picture);
		if(bitmap!=null){
			pictureView.setImageBitmap(bitmap);
		}else{
			Toast.makeText(this, "图片文件为空，没有预览！", Toast.LENGTH_SHORT).show();
		}
		pictureView.setOnClickListener(this);
		
		saveLayout = (LinearLayout)findViewById(R.id.save_layout);
		saveView = (Button)findViewById(R.id.save_pic);
		if(style.equals("from")){
			saveView.setOnClickListener(this);
		}else{
			saveLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	/*	case R.id.bt_left:
			this.finish();
			break;*/
		case R.id.picture:
			this.finish();
			break;
		case R.id.save_pic:
			System.out.println("isSave="+isSave);
			if(isSave==0){
				Toast.makeText(this, "保存中！", Toast.LENGTH_SHORT).show();
				int i = savePic();
				if(i==1){
					Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
					isSave = 1;
				}else{
					Toast.makeText(this, "保存失败！", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(this, "该图片已经保存！", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	private int savePic() {
		int i=0;
		String sdStatus = Environment.getExternalStorageState();  
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用   
			File SAVE_PHOTO_DIR = new File("/sdcard/EHS/receive/pictures");
			if(!SAVE_PHOTO_DIR.exists()){
				SAVE_PHOTO_DIR.mkdirs();
			}
			System.out.println("path="+SAVE_PHOTO_DIR.getPath());
			//File file=null;
			Date date = new Date(System.currentTimeMillis());   
	        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");   
	        String fileName="/sdcard/EHS/receive/pictures/"+dateFormat.format(date) + ".jpg";
			//file = new File(SAVE_PHOTO_DIR,fileName); 
			try {   
			//	file.createNewFile();   
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				bos.flush();
				bos.close();
				File file = new File(fileName);
				if(file.exists()){
					i=1;
				}
			} catch (IOException e) {   
				e.printStackTrace();  
				i = 0;
			}   
        }else{
        	Toast.makeText(this, "没有SD卡！", Toast.LENGTH_SHORT).show();
        }
		return i;
	}

}
