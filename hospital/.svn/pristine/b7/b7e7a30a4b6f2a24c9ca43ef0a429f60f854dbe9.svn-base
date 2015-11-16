package com.example.ehs.model;

import android.content.Context;
import android.content.SharedPreferences;
/*
 * 登录模块
 * 用户登陆信息的保存
 * */
public class UserInfo {
	//sp的名字
	private static final String USER_INFO = "user_info";
			
	//登录信息
	private static final String ACCOUNT = "account";
	private static final String PASSWORD = "password";
			
	//用户基本信息
	private static final String USER_NAME = "user_name";
	private static final String USER_SEX = "user_sex";
	private static final String USER_PHONE = "user_phone";
	private static final String IS_SAVE_PSWD_CHECKED = "is_save_pswd_checked";
	private static final String IS_AUTO_LOGIN_CHECKED = "is_auto_login_checked";
			
	private Context context = null;

	public UserInfo() {
		super();
	}

	public UserInfo(Context context) {
		super();
		this.context = context;
	}
	
	//存放字符串型的值
	public void setUserInfo(String key, String value){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.putString(key, value);
		editor.commit();
		}
	//存放整形的值
	public void setUserInfo(String key, int value){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.putInt(key, value);
		editor.commit();
	}
	//存放长整形值
	public void setUserInfo(String key, Long value){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.putLong(key, value);
		editor.commit();
	}
	//存放布尔型值
	public void setUserInfo(String key, Boolean value){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		editor.putBoolean(key, value);
		editor.commit();
	}
	//清空记录
	public void clear(){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
	//注销用户时清空用户名和密码
	public void logOut(){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(ACCOUNT);
		editor.remove(PASSWORD);
		editor.commit();
	}
	//获得用户信息中某项字符串型的值
	public String getStringInfo(String key){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
	//获得用户息中某项整形参数的值
	public int getIntInfo(String key){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		return sp.getInt(key, -1);
	}
	//获得用户信息中某项长整形参数的值
	public Long getLongInfo(String key){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		return sp.getLong(key, -1);
	}
	//获得用户信息中某项布尔型参数的值
	public boolean getBooleanInfo(String key){
		SharedPreferences sp = context.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}

	public void setUserAccount(String account){
		setUserInfo(ACCOUNT, account);
	}
	
	public void setUserPswd(String password){
		setUserInfo(PASSWORD, password);
	}
	public void setUserName(String username){
		setUserInfo(USER_NAME, username);
	}
	public void setUserSex(String sex){
		setUserInfo(USER_SEX, sex);
	}
	public void setUserPhone(String phone){
		setUserInfo(USER_PHONE, phone);
	}
	
	public void setSavePswd(boolean savePswd){
		setUserInfo(IS_SAVE_PSWD_CHECKED, savePswd);
	}
	public void setAutoLogin(boolean autoLogin){
		setUserInfo(IS_AUTO_LOGIN_CHECKED, autoLogin);
	}
	public String getAccount() {
		return getStringInfo(ACCOUNT);
	}

	public String getPassword() {
		return getStringInfo(PASSWORD);
	}

	public String getUserName() {
		return getStringInfo(USER_NAME);
	}

	public String getUserSex() {
		return getStringInfo(USER_SEX);
	}

	public String getUserPhone() {
		return getStringInfo(USER_PHONE);
	}
	public boolean getSavePswd() {
		return getBooleanInfo(IS_SAVE_PSWD_CHECKED);
	}

	public boolean getAutoLogin() {
		return getBooleanInfo(IS_AUTO_LOGIN_CHECKED);
	}
	
	
}
