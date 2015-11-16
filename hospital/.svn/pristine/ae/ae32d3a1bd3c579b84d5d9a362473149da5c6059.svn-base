package com.example.ehs.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class GoodInfo implements Parcelable {
	private int goodId;
	private String goodName;
	private String goodDes;
	private String goodImg;
	private String goodPri;
	private String goodRequire;
	int goodNum = 0;
	Context context = null;

	public int getGoodId() {
		return goodId;
	}

	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}

	public String getGoodRequire() {
		return goodRequire;
	}

	public void setGoodRequire(String foodRequire) {
		this.goodRequire = foodRequire;
	}

	public int getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodDes() {
		return goodDes;
	}

	public void setGoodDes(String goodDes) {
		this.goodDes = goodDes;
	}

	public String getGoodImg() {
		return goodImg;
	}

	public void setGoodImg(String goodImg) {
		this.goodImg = goodImg;
	}

	public String getGoodPri() {
		return goodPri;
	}

	public void setGoodPri(String goodPri) {
		this.goodPri = goodPri;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(goodName);
		dest.writeString(goodImg);
		dest.writeString(goodDes);
		dest.writeString(goodPri);
		dest.writeString(goodRequire);
		dest.writeInt(goodNum);
		dest.writeInt(goodId);

	}

	public GoodInfo(String goodName, String goodDes, String goodImg,
			String goodPri, String foodRequire, int goodNum) {
		super();
		this.goodName = goodName;
		this.goodDes = goodDes;
		this.goodImg = goodImg;
		this.goodPri = goodPri;
		this.goodRequire = goodRequire;
		this.goodNum = goodNum;
	}

	public GoodInfo(Context context) {
		super();
		this.context = context;

	}

	public GoodInfo() {
		super();
	}


	@Override
	public String toString() {
		return "GoodInfo [goodId=" + goodId + ", goodName=" + goodName
				+ ", goodDes=" + goodDes + ", goodImg=" + goodImg
				+ ", goodPri=" + goodPri + ", goodRequire=" + goodRequire
				+ ", goodNum=" + goodNum + "]";
	}


	public static final Parcelable.Creator<GoodInfo> CREATOR = new Creator<GoodInfo>() {

		@Override
		public GoodInfo[] newArray(int size) {
			return new GoodInfo[size];
		}

		@Override
		public GoodInfo createFromParcel(Parcel source) {
			GoodInfo goodInfo = new GoodInfo();
			goodInfo.goodName = source.readString();
			goodInfo.goodImg = source.readString();
			goodInfo.goodPri = source.readString();
			goodInfo.goodDes = source.readString();
			goodInfo.goodRequire = source.readString();
			goodInfo.goodNum = source.readInt();
			return goodInfo;
		}
	};

	public GoodInfo clone() {
		GoodInfo goodInfo = new GoodInfo();
		goodInfo.setGoodName(GoodInfo.this.goodName);
		goodInfo.setGoodImg(GoodInfo.this.goodImg);
		goodInfo.setGoodDes(GoodInfo.this.goodDes);
		goodInfo.setGoodPri(GoodInfo.this.goodPri);
		goodInfo.setGoodRequire(GoodInfo.this.goodRequire);
		goodInfo.setGoodNum(GoodInfo.this.goodNum);
		return goodInfo;

	}

}
