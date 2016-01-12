package com.example.gouwuche.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlResPonse {
	private onConnectListener mListener;
	private final String FAILED = "网络错误异常";
	public interface onConnectListener{
		void onConnectFailed(String failed);
		void onConnectSuccess();
	}
	
	public void setOnConnectListener(onConnectListener mListener){
		this.mListener = mListener;
	}
	/**
	 * 获取指定URL的响应字符串
	 * 
	 * @param urlString
	 * @return
	 */
	public String getURLResponse(String urlString) {
		HttpURLConnection conn = null; // 连接对象
		InputStream is = null;
		//String resultData = "";
		StringBuffer stringBuffer = new StringBuffer();
		try {
			URL url = new URL(urlString); // URL对象
			conn = (HttpURLConnection) url.openConnection(); // 使用URL打开一个链接
			conn.setDoInput(true); // 允许输入流，即允许下载
			conn.setDoOutput(true); // 允许输出流，即允许上传
			conn.setUseCaches(false); // 不使用缓冲
			conn.setRequestMethod("POST"); 
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			
//			TimedUrlConnection timeoutconn=new TimedUrlConnection(conn,2000);//time out: 100seconds
//			boolean bconnectok=timeoutconn.connect();
//			
//			
//			 if (!bconnectok) {
//				
//				 if (mListener!=null) {
//					mListener.onConnectFailed(FAILED);
//					System.out.println(mListener+"liste");
//				}
//		           System.out.println("网络错误异常！!!!");
//		           return "";
//		       }else{
//		    	   System.out.println(mListener+"lisstener");
//		    	   if (mListener!=null) {
//		    		   System.out.println("elsebbbbb");
//		    		   mListener.onConnectSuccess();
//				}
		    	  
		    	   is = conn.getInputStream(); // 获取输入流，此时才真正建立链接
		    	   InputStreamReader isr = new InputStreamReader(is,"utf-8");
					BufferedReader bufferReader = new BufferedReader(isr);
					String inputLine = "";
					while ((inputLine = bufferReader.readLine()) != null) {
						stringBuffer.append(inputLine);
					}
					System.out.println(stringBuffer.toString()+"KKKK");
//		       }
			 
			

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return stringBuffer.toString();
	}

}
