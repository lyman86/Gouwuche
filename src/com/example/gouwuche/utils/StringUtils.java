package com.example.gouwuche.utils;

public class StringUtils {
	public static String getRegistAllUrl(String origin,String phone,String password,String authcode){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "mobile=";
		String s2 = "password=";
		String s3 = "auth_code=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),phone).toString();
		origin = result.insert(origin.indexOf(s2)+s2.length(), password).toString();
		origin = result.insert(origin.indexOf(s3)+s3.length(), authcode).toString();
		return origin;
	}
	
	public static String getRegistSmsUrl(String origin,String phone){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "mobile=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),phone).toString();
		return origin;
	}
	
	public static String getLoginUrl(String origin,String phone,String password){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "mobile=";
		String s2 = "password=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),phone).toString();
		origin = result.insert(origin.indexOf(s2)+s2.length(), password).toString();
		return origin;
	}
	
	public static String getResetPwdUrl(String origin,String oldPwd,String newPwd,String token){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "oldpassword=";
		String s2 = "password=";
		String s3 = "token=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),oldPwd).toString();
		System.out.println(origin+"origin>>>>>>>>>>");
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), newPwd).toString();
		System.out.println(origin+"origin>>>>>>>>>>");
		origin = result.insert(origin.indexOf(s3)+s3.length(), token).toString();
		return origin;
	}
	
	public static String getHomePageUrl(String origin,String token){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		return origin;
	}
	
	public static String getProDetailUrl(String origin,String id,String token){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "id=";
		String s2 = "token=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),id).toString();
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), token).toString();
		return origin;
	}
	public static String getAddProToCartUrl(String origin,String token,String item){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		String s2 = "item=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), item).toString();
		return origin;
	}
	
	public static String getProCarListUrl(String origin,String token){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		return origin;
	}
	
	public static String getDeleteFromCartUrl(String origin,String token,String item){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		String s2 = "item=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), item).toString();
		return origin;
	}
	
	public static String getUpdateFromCartUrl(String origin,String token,String number,String item){
		StringBuffer result = new StringBuffer(origin);
		String s1 = "token=";
		String s2 = "number=";
		String s3 = "item=";
		origin = result.insert(origin.indexOf(s1)+s1.length(),token).toString();
		origin = result.insert(origin.lastIndexOf(s2)+s2.length(), number).toString();
		origin = result.insert(origin.lastIndexOf(s3)+s3.length(), item).toString();
		return origin;
	}

}
