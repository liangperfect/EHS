package com.example.ehs.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ehs.model.FoodInfo;
import com.example.ehs.model.GoodInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OrderGoodDb {
	private static final String db_name = "order_good_db";
	private SQLiteDatabase db;
	Context context;

	public OrderGoodDb(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
	}

	/**
	 * 
	 * @param goodInfo
	 * @param place
	 *            收取地址
	 * @param userJid
	 *            用户ID 和 表名
	 * @param time
	 *            订单提交时间
	 * @param type
	 * @return
	 */
	public String saveOrder(GoodInfo goodInfo, String place, String userJid,
			String time, int type) {
		String results = null;
		try {

			db.execSQL("CREATE table IF NOT EXISTS _"
					+ userJid
					+ ""
					+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "goodName TEXT,goodPrice TEXT,goodNum TEXT,goodRequire TEXT,place TEXT,"
					+ "time TEXT,type INTEGER)");
			if (goodInfo != null) {
				db.execSQL(
						"insert into _"
								+ userJid
								+ "(goodName,goodPrice,goodNum,goodRequire,place,time,type) values(?,?,?,?,?,?,?)",
						new Object[] { goodInfo.getGoodName(),
								goodInfo.getGoodPri(), goodInfo.getGoodNum(),
								goodInfo.getGoodRequire(), place, time, type });
			}
			results = "success";
		} catch (Exception e) {
			e.printStackTrace();
			results = "failed";
		}
		return results;
	}

	// 对指定用户的订单查询
	public List<HashMap<String, Object>> getOrderByUid(String uid) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		try {
			String creatDBSql = "CREATE table IF NOT EXISTS _"
					+ uid
					+ ""
					+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "goodName TEXT,goodPrice TEXT,goodNum TEXT,goodRequire TEXT,place TEXT,"
					+ "time TEXT,type INTEGER)";
			db.execSQL(creatDBSql);
			// 查询
			String getOrderSql = "select * from _" + uid;
			Cursor cursor = db.rawQuery(getOrderSql, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					GoodInfo goodInfo = new GoodInfo();

					goodInfo.setGoodId(cursor.getInt(cursor
							.getColumnIndex("_id")));
					goodInfo.setGoodName(cursor.getString(cursor
							.getColumnIndex("goodName")));
					goodInfo.setGoodPri(cursor.getString(cursor
							.getColumnIndex("goodPrice")));
					goodInfo.setGoodNum(cursor.getInt(cursor
							.getColumnIndex("goodNum")));
					map = new HashMap<String, Object>();
					map.put("goodOrder", goodInfo);
					result.add(map);
				}
			} else {

				result = null;
			}

			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	//
	// public List<HashMap<String, Object>> getOrderRecord(String userJid,
	// int type, String time) {
	// List<HashMap<String, Object>> list = new ArrayList<HashMap<String,
	// Object>>();
	// HashMap<String, Object> map = null;
	// GoodInfo goodInfo = null;
	// db.execSQL("CREATE table IF NOT EXISTS _"
	// + userJid
	// + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
	// +
	// "goodName TEXT,goodPrice TEXT,goodNum TEXT,goodRequire TEXT,place TEXT,"
	// + "time TEXT,type INTEGER)");
	// Cursor c = db.rawQuery("SELECT * from _" + userJid + " WHERE type='"
	// + type + "'", null);
	// while (c.moveToNext()) {
	// goodInfo = new GoodInfo(c.getString(c.getColumnIndex("goodName")),
	// c.getString(c.getColumnIndex("goodRequire")), "",
	// c.getString(c.getColumnIndex("goodPrice")), c.getString(c
	// .getColumnIndex("goodRequire")),
	// c.getColumnIndex("goodNum"));
	// map = new HashMap<String, Object>();
	// map.put("good", goodInfo);
	// list.add(map);
	// }
	// return list;
	// }

}
