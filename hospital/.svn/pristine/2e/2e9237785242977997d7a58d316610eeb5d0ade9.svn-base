package com.example.ehs.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ehs.R;
import com.example.ehs.interfaces.CallbackBundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FileDialog {
	static final public String sRoot="/";
	static final public String sParent="..";
	static final public String sFolder=".";
	static final public String sEmpty="";
	static final private String sOnErrorMsg="无权访问!";
	
	public static Dialog createDialog(int id,Context context,String title,CallbackBundle callback,String suffix,Map<String,Integer> images){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(new FileSelectView(context,id,callback,suffix,images));
		Dialog dialog = builder.create();
		dialog.setTitle(title);
		return dialog;
	}
	static class FileSelectView extends ListView implements OnItemClickListener{
		private CallbackBundle callback=null;
		private String path = sRoot;
		private List<Map<String, Object>> list =null;
		private int dialogId=0;
		private String suffix=null;
		private Map<String,Integer> imageMap=null;
		
		public FileSelectView(Context context, int dialogId,CallbackBundle callback,
				String suffix, Map<String, Integer> imageMap) {
			super(context);
			this.callback = callback;
			this.dialogId = dialogId;
			this.suffix = suffix==null?"":suffix.toLowerCase();
			this.imageMap = imageMap;
			this.setOnItemClickListener(this);
			refreshFileList();
		}
		
		private String getSuffix(String fileName){
			int dix=fileName.lastIndexOf('.');
			if(dix<0){
				return "";
			}else{
				return fileName.substring(dix+1);
			}
		}
		
		private int getImageIds(String s){
			if(imageMap == null){
				return 0;
			}else if(imageMap.containsKey(s)){
				return imageMap.get(s);
			}else if(imageMap.containsKey(sEmpty)){
				return imageMap.get(sEmpty);
			}else{
				return 0;
			}
		}
		private int refreshFileList(){
			File[] files=null;
			try{
				files = new File(path).listFiles();
			}catch(Exception e){
				files = null;
			}
			if(files == null){
				//访问出错
				Toast.makeText(getContext(), sOnErrorMsg, Toast.LENGTH_SHORT).show();
				return -1;
			}
			if(list !=null){
				list.clear();
			}else{
				list = new ArrayList<Map<String,Object>>(files.length);
			}
			ArrayList<Map<String, Object>> lFolders = new ArrayList<Map<String,Object>>();
			ArrayList<Map<String, Object>> lFiles = new ArrayList<Map<String,Object>>();
			if(!this.path.equals(sRoot)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", sRoot);
				map.put("path", sRoot);
				map.put("img", getImageIds(sRoot));
				list.add(map);
				
				map = new HashMap<String, Object>();
				map.put("name", sParent);
				map.put("path", path);
				map.put("img", getImageIds(sParent));
				list.add(map);
			}
			for(File file:files){
				if(file.isDirectory()&&file.listFiles()!=null){
					//添加文件夹
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", file.getName());
					map.put("path", file.getPath());
					map.put("img", getImageIds(sFolder));
					lFolders.add(map);
				}else if(file.exists()){
					//添加文件
					String sf = getSuffix(file.getName()).toLowerCase();
					if(suffix == null || suffix.length()==0||(sf.length()>0&&suffix.indexOf("."+sf+";")>=0)){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("name", file.getName());
						map.put("path", file.getPath());
						map.put("img", getImageIds(sf));
						lFolders.add(map);
					}
				}
			}
			list.addAll(lFolders); //添加文件夹
			list.addAll(lFiles);//添加文件
			SimpleAdapter adapter = new SimpleAdapter(getContext(),list,R.layout.folder_item,
					new String[]{"img","path","name"},new int[]{R.id.folder_img,R.id.folder_name,R.id.folder_path});
			this.setAdapter(adapter);
			return files.length;
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long id) {
			//条目选择
			String pt = (String)list.get(position).get("path");
			String fn = (String)list.get(position).get("name");
			if(fn.equals(sRoot)||fn.equals(sParent)){
				//如果是根目录或者上一层
				File fl = new File(pt);
				String ppt = fl.getParent();
				if(ppt!=null){
					//返回上一层
					path = ppt;
				}else{
					//返回根目录
					path = sRoot;
				}
			}else{
				File fl = new File(pt);
				if(fl.isFile()){
					//如果是文件
					((Activity)getContext()).dismissDialog(this.dialogId);
					//设置回调函数的返回值
					Bundle bd = new Bundle();
					bd.putString("path", pt);
					bd.putString("name", fn);
					//调用实现的设置的回调函数
					this.callback.callback(bd);
					return;
				}else if(fl.isDirectory()){
					//如果是文件夹，进入选中的文件夹
					path = pt;
				}
			}
			this.refreshFileList();
		}
		
	}
}
