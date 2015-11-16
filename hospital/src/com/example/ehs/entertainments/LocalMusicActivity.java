package com.example.ehs.entertainments;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.ehs.R;
import com.example.ehs.model.Music;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LocalMusicActivity extends Activity{
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
		setContentView(R.layout.local_music);
		
		init();
	}
	
	private void init() {
		listView = (ListView)findViewById(R.id.local_list);
		Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.ALBUM}, null, null, null);
		ListAdapter adapter2 = new SimpleCursorAdapter(LocalMusicActivity.this,   
				R.layout.music_item, cursor, new String[] {MediaStore.Audio.Media._ID,   
                        MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.ALBUM},   
                new int[] {R.id.music_no,R.id.music_name,R.id.music_detail});   
		listView.setAdapter(adapter2);  
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Toast.makeText(LocalMusicActivity.this, "点击了"+position, Toast.LENGTH_SHORT).show();
				title = (TextView)view.findViewById(R.id.music_name);
				name = title.getText().toString();
				if(oldView == view){
					count++;
					oldView.setBackgroundResource(R.drawable.bg_article_listview_item_pressed);
					if(count!=0 && count%2 == 0){
						image = (ImageView)view.findViewById(R.id.music_image);
						image.setImageResource(R.drawable.play);
						String filePath = "/sdcard/qqmusic/song/";
						myMediaPlayer = new MediaPlayer();
						try {
							myMediaPlayer.setDataSource(filePath+name);
							myMediaPlayer.prepare();
							myMediaPlayer.start();
						} catch (IllegalStateException e) {
							myMediaPlayer.stop();
							myMediaPlayer.release();
							e.printStackTrace();
						} catch (IOException e) {
							myMediaPlayer.stop();
							myMediaPlayer.release();
							e.printStackTrace();
						}
							}
					if(count!=1 && count%2 == 1){
						image = (ImageView)view.findViewById(R.id.music_image);
						image.setImageResource(R.drawable.stop);
						myMediaPlayer.pause();
					}
				}else{
					if(oldView != null){
						image = (ImageView)oldView.findViewById(R.id.music_image);
						image.setImageResource(R.drawable.music);
						oldView.setBackgroundResource(R.drawable.article_listview_item_bg);
						count = 0;
						myMediaPlayer.stop();
						myMediaPlayer.release();
					}
					view.setBackgroundResource(R.drawable.bg_article_listview_item_pressed);
					count++;
					if(count!=0 && count%2 == 0){
						image = (ImageView)view.findViewById(R.id.music_image);
						image.setImageResource(R.drawable.play);
						String filePath = "/sdcard/qqmusic/song/";
						myMediaPlayer = new MediaPlayer();
						try {
							myMediaPlayer.setDataSource(filePath+name);
							myMediaPlayer.prepare();
							myMediaPlayer.start();
						} catch (IllegalStateException e) {
							myMediaPlayer.stop();
							myMediaPlayer.release();
							e.printStackTrace();
						} catch (IOException e) {
							myMediaPlayer.stop();
							myMediaPlayer.release();
							e.printStackTrace();
						}
							}
					if(count!=1 && count%2 == 1){
						image = (ImageView)view.findViewById(R.id.music_image);
						image.setImageResource(R.drawable.stop);
						myMediaPlayer.pause();
					}
					oldView = view;
				}
				bt_option = (ImageView)view.findViewById(R.id.music_choice);
				bt_option.setOnClickListener(new OnClickListener() {				
					@Override
					public void onClick(View v) {
						bt_option.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {					
							@Override
							public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {
								menu.add(0, 1, 0, "设为最爱");
								menu.add(0, 2, 0, "添加到列表");
								menu.add(0, 3, 0, "蓝牙发送");
								menu.add(0, 4, 0, "从列表移除");
								menu.add(0, 5, 0, "从手机删除");
								menu.add(0, 6, 0, "歌曲信息");
							}
						});	
						
					}
				});
			}
		});
	}

	private List<HashMap<String, Object>> getMusicList() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = null;
		List<Music> musics = getMultiData();
		Music music = new Music();
		for(int i=0;i<musics.size();i++){
			map = new HashMap<String, Object>();
			music = musics.get(i);
			map.put("img", R.drawable.music);
			map.put("info", "");
		}
		
		return list;
	}
	// 查找sdcard卡上的所有歌曲信息
	public List<Music> getMultiData() {
		ArrayList<Music> musicList = new ArrayList<Music>(); 
		Music music = null;
		// 加入封装音乐信息的代码// 查询所有歌曲
		ContentResolver musicResolver = this.getContentResolver();
		Cursor musicCursor = musicResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
		int musicColumnIndex;
		if (null != musicCursor && musicCursor.getCount() > 0) {
		for (musicCursor.moveToFirst(); !musicCursor.isAfterLast(); musicCursor.moveToNext()) {
			music = new Music();
			Random random = new Random();
			int musicRating = Math.abs(random.nextInt()) % 10;
			music.setMusicRating(musicRating);
			// 取得音乐播放路径
			musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
			String musicPath = musicCursor.getString(musicColumnIndex);
			music.setMusicPath(musicPath);
			// 取得音乐的名字
			musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
			String musicName = musicCursor.getString(musicColumnIndex);
			music.setMusicName(musicName);
			// 取得音乐的专辑名称
			musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
			String musicAlbum = musicCursor.getString(musicColumnIndex);
			music.setMusicAlbum(musicAlbum);
			// 取得音乐的演唱者
			musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
			String musicArtist = musicCursor.getString(musicColumnIndex);
			music.setMusicArtist(musicArtist);
			// 取得歌曲对应的专辑对应的Key
			musicColumnIndex = musicCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_KEY);
			String musicAlbumKey = musicCursor.getString(musicColumnIndex);
			String[] argArr = { musicAlbumKey };
			ContentResolver albumResolver = this.getContentResolver();
			Cursor albumCursor = albumResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
				MediaStore.Audio.AudioColumns.ALBUM_KEY + " = ?",argArr, null);
			if (null != albumCursor && albumCursor.getCount() > 0) {
				albumCursor.moveToFirst();
				int albumArtIndex = albumCursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ART);
				String musicAlbumArtPath = albumCursor.getString(albumArtIndex);
				if (null != musicAlbumArtPath&& !"".equals(musicAlbumArtPath)) {
					music.setMusicPicPath(musicAlbumArtPath);
				} else {
					music.setMusicPicPath("");
				}
			} else {
				// 没有专辑定义，给默认图片
				music.setMusicPath("");
			}
			musicList.add(music);
		}
	}
	return musicList;
	}


}
