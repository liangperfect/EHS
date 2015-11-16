package com.example.ehs.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class FoodInfo implements Parcelable {
	String foodName = null;
	String foodPic = null;
	String foodDes = null;
	String foodPrice = null;
	String foodRequire = null;
	int foodNum = 0;

	Context context = null;

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodPic() {
		return foodPic;
	}

	public void setFoodPic(String foodPic) {
		this.foodPic = foodPic;
	}

	public String getFoodDes() {
		return foodDes;
	}

	public void setFoodDes(String foodDes) {
		this.foodDes = foodDes;
	}

	public String getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(String foodPrice) {
		this.foodPrice = foodPrice;
	}

	public String getFoodRequire() {
		return foodRequire;
	}

	public void setFoodRequire(String foodRequire) {
		this.foodRequire = foodRequire;
	}

	public int getFoodNum() {
		return foodNum;
	}

	public void setFoodNum(int foodNum) {
		this.foodNum = foodNum;
	}

	public FoodInfo(String foodName, String foodPic, String foodDes,
			String foodPrice, String foodRequire, int foodNum) {
		super();
		this.foodName = foodName;
		this.foodPic = foodPic;
		this.foodDes = foodDes;
		this.foodPrice = foodPrice;
		this.foodRequire = foodRequire;
		this.foodNum = foodNum;
	}

	public FoodInfo(Context context) {
		super();
		this.context = context;
	}

	public FoodInfo() {
		super();
	}

	@Override
	public String toString() {
		return "FoodInfo [foodName=" + foodName + ", foodPic=" + foodPic
				+ ", foodDes=" + foodDes + ", foodPrice=" + foodPrice
				+ ", foodRequire=" + foodRequire + ", foodNum=" + foodNum + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(foodName);
		dest.writeString(foodPic);
		dest.writeString(foodDes);
		dest.writeString(foodPrice);
		dest.writeString(foodRequire);
		dest.writeInt(foodNum);

	}

	public static final Parcelable.Creator<FoodInfo> CREATOR = new Creator<FoodInfo>() {
		@Override
		public FoodInfo createFromParcel(Parcel source) {
			FoodInfo foodInfo = new FoodInfo();
			foodInfo.foodName = source.readString();
			foodInfo.foodPic = source.readString();
			foodInfo.foodDes = source.readString();
			foodInfo.foodPrice = source.readString();
			foodInfo.foodRequire = source.readString();
			foodInfo.foodNum = source.readInt();
			return foodInfo;
		}

		@Override
		public FoodInfo[] newArray(int size) {
			return new FoodInfo[size];
		}
	};

	public FoodInfo clone() {
		FoodInfo foodInfo = new FoodInfo();
		foodInfo.setFoodName(FoodInfo.this.foodName);
		foodInfo.setFoodPic(FoodInfo.this.foodPic);
		foodInfo.setFoodDes(FoodInfo.this.foodDes);
		foodInfo.setFoodPrice(FoodInfo.this.foodPrice);
		foodInfo.setFoodRequire(FoodInfo.this.foodRequire);
		foodInfo.setFoodNum(FoodInfo.this.foodNum);
		return foodInfo;
	}

}
