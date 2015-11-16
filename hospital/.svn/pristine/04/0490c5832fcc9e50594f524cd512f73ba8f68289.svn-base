package com.example.ehs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	public static final String BASE_URL = "http://222.18.162.130:9090/plugins/";
	public static HttpGet getHttpGet(String url){
		HttpGet request = new HttpGet(url);
		return request;
	}
	public static HttpPost getHttpPost(String url){
		HttpPost post = new HttpPost(url);
		return post;	
	}
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
		
	}
	public static HttpResponse getHttpResponse(HttpPost post) throws ClientProtocolException, IOException{
		HttpResponse response = new DefaultHttpClient().execute(post);
		return response;	
	}
	public static String queryStringForPost(String url){
		HttpPost post = HttpUtil.getHttpPost(url);
		String result = null;
		try{
			HttpResponse response = HttpUtil.getHttpResponse(post);
			post.addHeader("Content-Type","text/html");
			post.addHeader("charset", HTTP.UTF_8);
			//response.getParams().getParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET);  
			if(response.getStatusLine().getStatusCode()==200){
				//result = EntityUtils.toString(response.getEntity(),"UTF-8");
				result = EntityUtils.toString(response.getEntity(),"UTF-8");
				/*BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "gb2312"));  
				String line;  
				while((line = br.readLine()) != null){  
				    result = line;
				}*/
				return result;
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
			result = "ÍøÂçÒì³££¡";
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result = "ÍøÂçÒì³££¡";
			return result;
		}
		return null;
	}
	public static String queryStringForGet(String url){
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try{
			HttpResponse response = HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
			result = "ÍøÂçÒì³££¡";
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result = "ÍøÂçÒì³££¡";
			return result;
		}
		return result;
	}
	public static String queryStringForPost(HttpPost post){
		String result = null;
		try{
			HttpResponse response = HttpUtil.getHttpResponse(post);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
			result = "ÍøÂçÒì³££¡";
			return result;
		}catch(IOException e){
			e.printStackTrace();
			result = "ÍøÂçÒì³££¡";
			return result;
		}
		return null;
	}
}
