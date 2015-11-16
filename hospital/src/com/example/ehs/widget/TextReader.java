package com.example.ehs.widget;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/*
 * @author hcq
 * 文本文件读取
 * */
public class TextReader {
	/*
	 * 閫氳繃杈撳叆娴両nputStream鑾峰彇鍐呭
	 * param:InputStream
	 * return:String
	 * */
	public static String getString(InputStream inputStream){
		InputStreamReader inputStreamReader = null;
		try{
			inputStreamReader = new InputStreamReader(inputStream,"gbk");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line=null;
		try{
			while ((line = reader.readLine())!=null) {
				sb.append(line);
				sb.append("\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	/*
	 * 閫氳繃鏂囦欢璺緞鑾峰彇鏂囦欢鍐呭
	 * param:String (filePath)
	 * return:String 
	 * */
	public static String getStringFromFile(String filePath){
		File file = new File(filePath);
		FileInputStream stream = null;
		try{
			stream = new FileInputStream(file);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return getString(stream);
	}
}
