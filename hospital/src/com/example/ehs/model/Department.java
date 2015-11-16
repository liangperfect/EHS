package com.example.ehs.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Department implements Parcelable{
	
	private int department_id;
	
	private String department_name;
	
	private String department_introduce;

	
	public Department() {
		super();
	}

	public Department(int department_id, String department_name,
			String department_introduce) {
		super();
		this.department_id = department_id;
		this.department_name = department_name;
		this.department_introduce = department_introduce;
	}

	public int getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDepartment_introduce() {
		return department_introduce;
	}

	public void setDepartment_introduce(String department_introduce) {
		this.department_introduce = department_introduce;
	}

	
	@Override
	public String toString() {
		return "Department [department_id=" + department_id
				+ ", department_name=" + department_name
				+ ", department_introduce=" + department_introduce + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(department_id);
		dest.writeString(department_name);
		dest.writeString(department_introduce);
	}
	public static final Parcelable.Creator<Department> CREATOR = new Creator<Department>() {
		@Override
		public Department createFromParcel(Parcel source) {
			Department department = new Department();
			department.department_id = source.readInt();
			department.department_name = source.readString();
			department.department_introduce = source.readString();
			return department;
		}

		@Override
		public Department[] newArray(int size) {
			return new Department[size];
		}
	};
	public Department clone() {
		Department department = new Department();
		department.setDepartment_id(Department.this.department_id);
		department.setDepartment_name(Department.this.department_name);
		department.setDepartment_introduce(Department.this.department_introduce);
		return department;
	}
}
