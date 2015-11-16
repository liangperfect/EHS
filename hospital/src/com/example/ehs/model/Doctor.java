package com.example.ehs.model;

import org.xbill.DNS.DClass;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Doctor implements Parcelable{

	private int doctor_id;
	
	private String doctor_name;
	
	private String doctor_sex;		

	private int doctor_age;
	
	private String doctor_introduce;
	
	/** Íâ¼ü×Ö¶Î */
	private int doctor_department_id;
	private String doctor_department_name;
	
	private String doctor_image;
	
	public Doctor() {
		super();
	}

	public Doctor(int doctor_id, String doctor_name, String doctor_sex,
			int doctor_age, String doctor_introduce, int doctor_department_id,
			String doctor_department_name, String doctor_image) {
		super();
		this.doctor_id = doctor_id;
		this.doctor_name = doctor_name;
		this.doctor_sex = doctor_sex;
		this.doctor_age = doctor_age;
		this.doctor_introduce = doctor_introduce;
		this.doctor_department_id = doctor_department_id;
		this.doctor_department_name = doctor_department_name;
		this.doctor_image = doctor_image;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getDoctor_sex() {
		return doctor_sex;
	}

	public void setDoctor_sex(String doctor_sex) {
		this.doctor_sex = doctor_sex;
	}

	public String getDoctor_introduce() {
		return doctor_introduce;
	}

	public void setDoctor_introduce(String doctor_introduce) {
		this.doctor_introduce = doctor_introduce;
	}

	public int getDoctor_department_id() {
		return doctor_department_id;
	}

	public void setDoctor_department_id(int doctor_department_id) {
		this.doctor_department_id = doctor_department_id;
	}

	public String getDoctor_image() {
		return doctor_image;
	}

	public void setDoctor_image(String doctor_image) {
		this.doctor_image = doctor_image;
	}

	public String getDoctor_department_name() {
		return doctor_department_name;
	}

	public void setDoctor_department_name(String doctor_department_name) {
		this.doctor_department_name = doctor_department_name;
	}
	
	public int getDoctor_age() {
		return doctor_age;
	}

	public void setDoctor_age(int doctor_age) {
		this.doctor_age = doctor_age;
	}

	@Override
	public String toString() {
		return "Doctor [doctor_id=" + doctor_id + ", doctor_name="
				+ doctor_name + ", doctor_sex=" + doctor_sex + ", doctor_age="
				+ doctor_age + ", doctor_introduce=" + doctor_introduce
				+ ", doctor_department_id=" + doctor_department_id
				+ ", doctor_department_name=" + doctor_department_name
				+ ", doctor_image=" + doctor_image + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(doctor_id);
		dest.writeString(doctor_name);
		dest.writeString(doctor_sex);
		dest.writeInt(doctor_age);
		dest.writeString(doctor_introduce);
		dest.writeInt(doctor_department_id);
		dest.writeString(doctor_department_name);
		dest.writeString(doctor_image);
		
	}
	public static final Parcelable.Creator<Doctor> CREATOR = new Creator<Doctor>() {
		@Override
		public Doctor createFromParcel(Parcel source) {
			Doctor doctor = new Doctor();
			doctor.doctor_id = source.readInt();
			doctor.doctor_name = source.readString();
			doctor.doctor_sex = source.readString();
			doctor.doctor_age = source.readInt();
			doctor.doctor_introduce = source.readString();
			doctor.doctor_department_id = source.readInt();
			doctor.doctor_department_name = source.readString();
			doctor.doctor_image = source.readString();
			return doctor;
		}

		@Override
		public Doctor[] newArray(int size) {
			return new Doctor[size];
		}
	};
	public Doctor clone() {
		Doctor doctor = new Doctor();
		doctor.setDoctor_id(Doctor.this.doctor_id);
		doctor.setDoctor_name(Doctor.this.doctor_name);
		doctor.setDoctor_sex(Doctor.this.doctor_sex);
		doctor.setDoctor_age(Doctor.this.doctor_age);
		doctor.setDoctor_introduce(Doctor.this.doctor_introduce);
		doctor.setDoctor_department_id(Doctor.this.doctor_department_id);
		doctor.setDoctor_department_name(Doctor.this.doctor_department_name);
		doctor.setDoctor_image(Doctor.this.doctor_image);
		return doctor;
	}
	
	
}
