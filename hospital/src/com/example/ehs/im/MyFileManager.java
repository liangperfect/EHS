package com.example.ehs.im;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.ehs.R;
import com.example.ehs.widget.MyAdapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MyFileManager extends ListActivity {  
    private List<String> items = null;  
    private List<String> paths = null;  
    private String rootPath = "/";  
    private String curPath = "/";  
    private TextView titleView;
    private Button backView;
    private TextView mPath;  
    private String fName;
    private final static String TAG = "bb";  
    ProgressDialog dialog=null;
    @Override  
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.fileselect);  
        
        dialog = new ProgressDialog(this);
        
        titleView = (TextView)findViewById(R.id.title);
        titleView.setText("选择文件");
        backView = (Button)findViewById(R.id.bt_left);
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MyFileManager.this.finish();
			}
		});
        mPath = (TextView) findViewById(R.id.mPath);  
        Button buttonConfirm = (Button) findViewById(R.id.buttonConfirm);  
        buttonConfirm.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) { 
            	//dialog.setMessage("请稍后...");
            	//dialog.show();
                Intent data = new Intent(MyFileManager.this, ChatActivity.class);  
                Bundle bundle = new Bundle();  
                bundle.putString("file", curPath);  
                data.putExtras(bundle);  
                setResult(2, data);  
                finish();  
            }  
        });  
        Button buttonCancle = (Button) findViewById(R.id.buttonCancle);  
        buttonCancle.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                finish();  
            }  
        });  
        getFileDir(rootPath);  
    }  
    private void getFileDir(String filePath) {  
        mPath.setText(filePath);  
        items = new ArrayList<String>();  
        paths = new ArrayList<String>();  
        File f = new File(filePath);  
        File[] files = f.listFiles();  
        if (!filePath.equals(rootPath)) {  
            items.add("b1");  
            paths.add(rootPath);  
            items.add("b2");  
            paths.add(f.getParent());  
        }  
        for (int i = 0; i < files.length; i++) {  
            File file = files[i];  
            items.add(file.getName());  
            paths.add(file.getPath());  
        }  
        System.out.println("Context1="+MyFileManager.this);
        setListAdapter(new MyAdapter(MyFileManager.this, items, paths));  
    }  
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) {  
        File file = new File(paths.get(position)); 
        try{
        if (file.isDirectory()) {  
            curPath = paths.get(position);  
            getFileDir(paths.get(position));  
        } else {  
        	 fName = file.getName().toString();  
        	mPath.setText(paths.get(position));
        	curPath=paths.get(position);
        }  
       }catch(Exception e){
    	   new AlertDialog.Builder(MyFileManager.this).setTitle("message")
       	.setMessage("没有权限，只能打开SDcard获取让程序获得root权限！")
       	.setNegativeButton("确定",new DialogInterface.OnClickListener() {
   			public void onClick(DialogInterface arg0, int arg1) {
   				finish();
   			}
   		}).show();
       }
    }  
}  