package com.example.ehs.entertainments;

import android.app.Activity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.ehs.R;

public class LocalMoviesActivity extends Activity{
	private MediaPlayer myMediaPlayer = null;
	private ListView listView;
	SimpleAdapter adapter=null;
	private TextView title;
	private String name;
	private View oldView = null;
	public static int count=0;
	private ImageView image,bt_option;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_movies);
		Toast.makeText(this, "¿ª·¢ÖÐ...", Toast.LENGTH_SHORT).show();
	}
	
}
