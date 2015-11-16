package com.example.ehs.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.ehs.model.IMMessage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;

public class IMMessageDb {
	private static final String db_name = "message_db";
	private SQLiteDatabase db;
	Context context;

	// 有参构造函数
	public IMMessageDb(Context context) {
		this.context = context;
		db = context.openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);
	}

	/*
	 * 保存消息
	 */
	public String saveMessage(IMMessage message, String friendsJid,
			String userJid) {
		String results = null;
		try {
			db.execSQL("CREATE table IF NOT EXISTS _"
					+ userJid
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ "friendJid TEXT,content TEXT,time TEXT,title TEXT,fromSubjid TEXT,"
					+ "toSubjid TEXT,fromSubName TEXT,toSubName TEXT,"
					+ "infoUrl TEXT,unReadCount INTEGER,msgType INTEGER,type INTEGER,acceptType INTEGER,chatMode INTEGER)");
			if (message != null) {
				db.execSQL(
						"insert into _"
								+ userJid
								+ " (friendJid,content, time,title,fromSubjid,toSubjid,fromSubName,"
								+ "toSubName,infoUrl,unReadCount,msgType,type,acceptType,chatMode) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { friendsJid, message.getContent(),
								message.getTime(), message.getTitle(),
								message.getFromSubJid(), message.getToSubJid(),
								message.getFromSubName(),
								message.getToSubName(), message.getInfoUrl(),
								message.getUnReadCount(), message.getMsgType(),
								message.getType(), message.getAcceptType(),
								message.getChatMode() });
			}
			results = "success";
		} catch (Exception e) {
			e.printStackTrace();
			results = "failed";
		}
		return results;
	}

	// 获取消息记录
	public List<IMMessage> getAllMessage(String userJid, String friendsJid,
			String time) {
		IMMessage message = null;
		List<IMMessage> list = new ArrayList<IMMessage>();
		db.execSQL("CREATE table IF NOT EXISTS _"
				+ userJid
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "friendJid TEXT,content TEXT,time TEXT,title TEXT,fromSubjid TEXT,"
				+ "toSubjid TEXT,fromSubName TEXT,toSubName TEXT,"
				+ "infoUrl TEXT,unReadCount INTEGER,msgType INTEGER,type INTEGER,acceptType INTEGER,chatMode INTEGER)");
		Cursor c = db.rawQuery("SELECT * from _" + userJid
				+ " WHERE friendJid='" + friendsJid + "'", null);
		while (c.moveToNext()) {
			message = new IMMessage(c.getString(c.getColumnIndex("content")),
					c.getString(c.getColumnIndex("time")), c.getString(c
							.getColumnIndex("title")), c.getString(c
							.getColumnIndex("fromSubjid")), c.getString(c
							.getColumnIndex("toSubjid")), c.getString(c
							.getColumnIndex("fromSubName")), c.getString(c
							.getColumnIndex("toSubName")), c.getString(c
							.getColumnIndex("infoUrl")), c.getInt(c
							.getColumnIndex("unReadCount")), c.getInt(c
							.getColumnIndex("msgType")), c.getInt(c
							.getColumnIndex("type")), c.getInt(c
							.getColumnIndex("acceptType")), c.getInt(c
							.getColumnIndex("chatMode")));
			System.out.println("message=" + message.toString());
			list.add(message);
		}
		c.close();
		return list;
	}

	// 获取最近聊天的所有好友jid
	public List<String> getAllFriends(String userJid) {
		List<String> list = new ArrayList<String>();
		String tempjid = null;
		String tojid = null;
		String fromjid = null;
		db.execSQL("CREATE table IF NOT EXISTS _"
				+ userJid
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "friendJid TEXT,content TEXT,time TEXT,title TEXT,fromSubjid TEXT,"
				+ "toSubjid TEXT,fromSubName TEXT,toSubName TEXT,"
				+ "infoUrl TEXT,unReadCount INTEGER,msgType INTEGER,type INTEGER,acceptType INTEGER,chatMode INTEGER)");
		Cursor c = db.rawQuery("SELECT * from _" + userJid, null);
		System.out.println("userjid=" + userJid);
		while (c.moveToNext()) {
			tojid = c.getString(c.getColumnIndex("toSubjid"));
			fromjid = c.getString(c.getColumnIndex("fromSubjid"));
			System.out.println("fromjid=" + fromjid);
			System.out.println("tojid=" + tojid);
			if (tojid != null && !tojid.equals(userJid)) {
				System.out.println("111");
				int i = 0;
				for (i = 0; i < list.size(); i++) {
					System.out.println("222");
					tempjid = list.get(i);
					System.out.println("temp=" + tempjid);
					if (tempjid.equals(tojid)) {
						break;
					}
				}
				System.out.println("333");
				if (i >= list.size()) {
					System.out.println("444");
					list.add(tojid);
				}
			}
			if (fromjid != null && !fromjid.equals(userJid)) {
				int i = 0;
				for (i = 0; i < list.size(); i++) {
					tempjid = list.get(i);
					if (tempjid.equals(fromjid)) {
						break;
					}
				}
				if (i >= list.size()) {
					list.add(fromjid);
				}
			}
		}
		c.close();
		return list;
	}
}
