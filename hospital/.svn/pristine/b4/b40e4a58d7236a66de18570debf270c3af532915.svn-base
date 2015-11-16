package com.example.ehs.model;

import org.jivesoftware.smack.packet.RosterPacket;

import android.os.Parcel;
import android.os.Parcelable;
/*
 * 聊天模块中的模型
 * 好友模型
 * */
public class Friends implements Parcelable {
	
	/**
	 * 将user保存在intent中时的key
	 */
	public static final String userKey = "lovesong_user";

	private String name;
	private String JID;
	private static RosterPacket.ItemType type;
	private String status;
	private String from;
	private String groupName;
	/**
	 * 用户状态对应的图片
	 */
	private int imgId;
	/**
	 * group的size
	 */
	private int size;
	private boolean available;

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJID() {
		return JID;
	}

	public void setJID(String jID) {
		JID = jID;
	}

	public RosterPacket.ItemType getType() {
		return type;
	}

	@SuppressWarnings("static-access")
	public void setType(RosterPacket.ItemType type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(JID);
		dest.writeString(name);
		dest.writeString(from);
		dest.writeString(status);
		dest.writeInt(available ? 1 : 0);
	}

	public static final Parcelable.Creator<Friends> CREATOR = new Parcelable.Creator<Friends>() {

		@Override
		public Friends createFromParcel(Parcel source) {
			Friends f = new Friends();
			f.JID = source.readString();
			f.name = source.readString();
			f.from = source.readString();
			f.status = source.readString();
			f.available = source.readInt() == 1 ? true : false;
			return f;
		}

		@Override
		public Friends[] newArray(int size) {
			return new Friends[size];
		}

	};

	public Friends clone() {
		Friends friends = new Friends();
		friends.setAvailable(Friends.this.available);
		friends.setFrom(Friends.this.from);
		friends.setGroupName(Friends.this.groupName);
		friends.setImgId(Friends.this.imgId);
		friends.setJID(Friends.this.JID);
		friends.setName(Friends.this.name);
		friends.setSize(Friends.this.size);
		friends.setStatus(Friends.this.status);
		return friends;
	}

}
