package com.example.ehs.widget;

import com.example.ehs.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
/*
 * 图片容器--应用于主界面
 * */
public class ImageAdapter extends BaseAdapter {
	private Context context;
	public ImageAdapter(Context c){
		context = c;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
		if(convertView == null){
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(160,150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		}else{
			imageView = (ImageView)convertView;
		}
		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}
	private Integer[] mThumbIds={
		R.drawable.im,R.drawable.notice,R.drawable.query,
		R.drawable.hospital,R.drawable.vedio,R.drawable.order,
		R.drawable.entertain,R.drawable.shopping,R.drawable.setting
	};
}

