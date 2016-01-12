package com.ly.shopsystem.http;

import android.os.Handler;

public abstract class BaseHttpLoad {
	public Thread myThread;
	public String url;
	public static final String FLAG = "加载失败";
	
	
	public Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			handleMyMessage(msg);
			
		};
	};
	public abstract void doIt();
	public abstract void handleMyMessage(android.os.Message msg);
	
	public void start(){
		doIt();
		myThread.start();
	}
	public BaseHttpLoad(String url) {
		this.url = url;
	}
	
	

}
