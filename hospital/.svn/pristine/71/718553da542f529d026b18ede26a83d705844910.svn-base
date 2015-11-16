package com.example.ehs.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View.OnClickListener;

public class DoctorWords implements Parcelable{
	String doctorName;
	String doctorSubject;
	String doctorWordsTime;
	String doctorWordsContent;
	
	
	public DoctorWords(String doctorName, String doctorSubject,
			String doctorWordsTime, String doctorWordsContent) {
		super();
		this.doctorName = doctorName;
		this.doctorSubject = doctorSubject;
		this.doctorWordsTime = doctorWordsTime;
		this.doctorWordsContent = doctorWordsContent;
	}
	
	public DoctorWords() {
		super();
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorSubject() {
		return doctorSubject;
	}

	public void setDoctorSubject(String doctorSubject) {
		this.doctorSubject = doctorSubject;
	}

	public String getDoctorWordsTime() {
		return doctorWordsTime;
	}

	public void setDoctorWordsTime(String doctorWordsTime) {
		this.doctorWordsTime = doctorWordsTime;
	}

	public String getDoctorWordsContent() {
		return doctorWordsContent;
	}

	public void setDoctorWordsContent(String doctorWordsContent) {
		this.doctorWordsContent = doctorWordsContent;
	}

	@Override
	public String toString() {
		return "DoctorWords [doctorName=" + doctorName + ", doctorSubject="
				+ doctorSubject + ", doctorWordsTime=" + doctorWordsTime
				+ ", doctorWordsContent=" + doctorWordsContent + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(doctorName);
		dest.writeString(doctorSubject);
		dest.writeString(doctorWordsTime);
		dest.writeString(doctorWordsContent);
	}
	public static final Parcelable.Creator<DoctorWords> CREATOR = new Creator<DoctorWords>() {
		@Override
		public DoctorWords createFromParcel(Parcel source) {
			DoctorWords doctorWords = new DoctorWords();
			doctorWords.doctorName = source.readString();
			doctorWords.doctorSubject = source.readString();
			doctorWords.doctorWordsTime = source.readString();
			doctorWords.doctorWordsContent = source.readString();
			return doctorWords;
		}

		@Override
		public DoctorWords[] newArray(int size) {
			return new DoctorWords[size];
		}
	};
	public DoctorWords clone() {
		DoctorWords doctorWords = new DoctorWords();
		doctorWords.setDoctorName(DoctorWords.this.doctorName);
		doctorWords.setDoctorSubject(DoctorWords.this.doctorSubject);
		doctorWords.setDoctorWordsTime(DoctorWords.this.doctorWordsTime);
		doctorWords.setDoctorWordsContent(DoctorWords.this.doctorWordsContent);
		return doctorWords;
	}
	
}
