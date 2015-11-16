package com.example.ehs.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Order implements Parcelable{
	private int order_id;//订单号
	private List<FoodInfo> orderContentInfos;//订单内容
	private String order_time;//下单时间
	private String order_address;//订单配送地址
	private String order_price;//订单价格
	private int order_state;//订单状态,0--未结算，1--已结算
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public List<FoodInfo> getOrderContentInfos() {
		return orderContentInfos;
	}
	public void setOrderContentInfos(List<FoodInfo> orderContentInfos) {
		this.orderContentInfos = orderContentInfos;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getOrder_address() {
		return order_address;
	}
	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}
	public String getOrder_price() {
		return order_price;
	}
	public void setOrder_price(String order_price) {
		this.order_price = order_price;
	}
	public int getOrder_state() {
		return order_state;
	}
	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}
	
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", orderContentInfos="
				+ orderContentInfos + ", order_time=" + order_time
				+ ", order_address=" + order_address + ", order_price="
				+ order_price + ", order_state=" + order_state + "]";
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(order_id);
		dest.writeList(orderContentInfos);
		dest.writeString(order_time);
		dest.writeString(order_address);
		dest.writeString(order_price);
		dest.writeInt(order_state);
		
	}
	public static final Parcelable.Creator<Order> CREATOR = new Creator<Order>() {
		@Override
		public Order createFromParcel(Parcel source) {
			Order order = new Order();
			order.order_id = source.readInt();
            order.orderContentInfos = new ArrayList<FoodInfo>();
			source.readList(order.orderContentInfos, getClass().getClassLoader());
			order.order_time = source.readString();
			order.order_address = source.readString();
			order.order_price = source.readString();
			order.order_state = source.readInt();
			return order;
		}

		@Override
		public Order[] newArray(int size) {
			return new Order[size];
		}
	};
	public Order clone() {
		Order order = new Order();
		order.setOrder_id(Order.this.order_id);
		order.setOrderContentInfos(Order.this.orderContentInfos);
		order.setOrder_time(Order.this.order_time);
		order.setOrder_address(Order.this.order_address);
		order.setOrder_price(Order.this.order_price);
		order.setOrder_state(Order.this.order_state);
		return order;
	}
	
	
}
