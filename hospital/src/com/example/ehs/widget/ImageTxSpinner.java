package com.example.ehs.widget;

import com.example.ehs.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ImageTxSpinner extends LinearLayout{
	private ImageView imageView;
	private TextView text;
	private Spinner spinner;
	public ImageTxSpinner(Context context) {
		super(context,null);
	}
	public ImageTxSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.account_info, this, true);
		imageView = (ImageView)findViewById(R.id.my_img);
		text = (TextView)findViewById(R.id.my_nickname);
		spinner = (Spinner)findViewById(R.id.my_state);
	}
	public ImageView getImageView(){
		return imageView;
	}
	public TextView getTextView(){
		return text;
	}
	public Spinner getSpinner(){
		return spinner;
	}
	public void setImageViewImg(int id){
		imageView.setImageResource(id);
	}
	public void setTextViewTx(String textStr){
		text.setText(textStr);
	}
}
