package com.example.ehs.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.IMMessage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OrderDb {
	private static final String db_name = "order_db";
	private SQLiteDatabase db;
	Context context;
	//有参构造函数
	public OrderDb(Context context){
		this.context = context;
		db = context.openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
	}
	/*
	 * 保存消息
	 * 
	 * */
	public String saveOrder(FoodInfo foodInfo, String place, String userJid,String time,int type){
		String results=null;
		try{
			db.execSQL("CREATE table IF NOT EXISTS _" + userJid+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"foodName TEXT,foodPrice TEXT,foodNum TEXT,foodRequire TEXT,place TEXT," +
					"time TEXT,type INTEGER)");
			if(foodInfo != null){
				db.execSQL("insert into _"
					+ userJid
					+ " (foodName,foodPrice,foodNum,foodRequire,place,time,type) values(?,?,?,?,?,?,?)",
					new Object[]{foodInfo.getFoodName(),foodInfo.getFoodPrice(),foodInfo.getFoodNum(),foodInfo.getFoodRequire(),
							place,time,type});
			}
			results = "success";
		}catch(Exception e){
			e.printStackTrace();
			results = "failed";
		}
		return results;
	}
/*
 * 根据订单状态获取获取订单信息
 * 根据订单时间和订单状态获取订单信息
 * 
 * */
	public List<HashMap<String, Object>> getOrderRecord(String userJid,int type,String time) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map=null;
		FoodInfo foodInfo = null;
		db.execSQL("CREATE table IF NOT EXISTS _" + userJid+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"foodName TEXT,foodPrice TEXT,foodNum TEXT,foodRequire TEXT,place TEXT," +
				"time TEXT,type INTEGER)");
		Cursor c = db.rawQuery("SELECT * from _" + userJid +" WHERE type='"+type+"'",null);
		while (c.moveToNext()) {
			foodInfo = new FoodInfo(
					c.getString(c.getColumnIndex("foodName")), //foodName
					"",//foodpic
				   "",//foodDes
				   c.getString(c.getColumnIndex("foodPrice")), //foodPrice
				   c.getString(c.getColumnIndex("foodRequire")), //foodRequire
				   c.getInt(c.getColumnIndex("foodNum")));//foodNum
				map = new HashMap<String, Object>();
				map.put("food", foodInfo);
				list.add(map);
			}
			c.close();
			return list;			
		}
	//更新购物车
		public String updateOrder(FoodInfo foodInfo,String userJid, int type){
			String result=null;
			try{
				db.execSQL("CREATE table IF NOT EXISTS _" + userJid+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"foodName TEXT,foodPrice TEXT,foodNum TEXT,foodRequire TEXT,place TEXT," +
						"time TEXT,type INTEGER)");
				Cursor cursor = db.rawQuery("SELECT * FROM _"+userJid,null);
				if(cursor.getCount() > 0){				
					db.execSQL("UPDATE _"+userJid+" SET "+"type='"+type+"' WHERE foodName='"+foodInfo.getFoodName()+"'");
					Cursor c = db.rawQuery("SELECT * FROM _"+userJid+" WHERE foodNme='"+foodInfo.getFoodName()+"'", null);
					if(c.getCount()==0){
						result = saveOrder(foodInfo, "", userJid, "", type);
					}
					result="success";
				}else{
					result = saveOrder(foodInfo, "", userJid, "", type);
				}
			}catch(Exception e){
				e.printStackTrace();
				result="faled";
			}
			return result;
		}
		//更新购物车
	public String updateReq(FoodInfo foodInfo,String userJid, int type){
		String result=null;
		try{
			db.execSQL("CREATE table IF NOT EXISTS _" + userJid+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"foodName TEXT,foodPrice TEXT,foodNum TEXT,foodRequire TEXT,place TEXT," +
					"time TEXT,type INTEGER)");
			Cursor cursor = db.rawQuery("SELECT * FROM _"+userJid,null);
			if(cursor.getCount() > 0){				
				db.execSQL("UPDATE _"+userJid+" SET "+"foodRequire='"+foodInfo.getFoodRequire()+"' WHERE foodName='"+foodInfo.getFoodName()+"'");
				Cursor c = db.rawQuery("SELECT * FROM _"+userJid+" WHERE foodNme='"+foodInfo.getFoodName()+"'", null);
				if(c.getCount()==0){
					result = saveOrder(foodInfo, "", userJid, "", type);
				}
				result="success";
			}else{
				result = saveOrder(foodInfo, "", userJid, "", type);
			}
		}catch(Exception e){
			e.printStackTrace();
			result="faled";
		}
		return result;
	}
	public String updateNum(FoodInfo foodInfo,String userJid, int type){
		String result=null;
		try{
			db.execSQL("CREATE table IF NOT EXISTS _" + userJid+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"foodName TEXT,foodPrice TEXT,foodNum TEXT,foodRequire TEXT,place TEXT," +
					"time TEXT,type INTEGER)");
			Cursor cursor = db.rawQuery("SELECT * FROM _"+userJid,null);
			if(cursor.getCount() > 0){				
				db.execSQL("UPDATE _"+userJid+" SET "+"foodNum='"+foodInfo.getFoodNum()+"' WHERE foodName='"+foodInfo.getFoodName()+"'");
				Cursor c = db.rawQuery("SELECT * FROM _"+userJid+" WHERE foodNme='"+foodInfo.getFoodName()+"'", null);
				if(c.getCount()==0){
					result = saveOrder(foodInfo, "", userJid, "", type);
				}
				result="success";
			}else{
				result = saveOrder(foodInfo, "", userJid, "", type);
			}
		}catch(Exception e){
			e.printStackTrace();
			result="faled";
		}
		return result;
	}
	
		//删除某条记录
		public String deleteOrder(String foodName,String userJid, int type){
			String result=null;
			try{
				db.execSQL("CREATE table IF NOT EXISTS _" + userJid+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"foodName TEXT,foodPrice TEXT,foodNum TEXT,foodRequire TEXT,place TEXT," +
						"time TEXT,type INTEGER)");
				Cursor cursor = db.rawQuery("SELECT * FROM _"+userJid+" WHERE "+"foodName='"+foodName+"' AND type='"+type+"'",null);
				if(cursor.getCount() > 0){				
					db.execSQL("DELETE _"+userJid+" WHERE "+"foodName='"+foodName+"' AND type='"+type+"'");
				}else{
					System.out.println("没有该条记录");
				}
				result = "success";
			}catch(Exception e){
				e.printStackTrace();
				result="faled";
			}
			return result;
		}
}
