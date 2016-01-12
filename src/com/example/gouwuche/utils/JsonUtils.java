package com.example.gouwuche.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gouwuche.bean.CustomBean;
import com.example.gouwuche.bean.ProCartListBean;





public class JsonUtils {
	static String jsonString = "";
	private static UrlResPonse urlResPonse = new UrlResPonse();
	
	public static List<ProCartListBean> getProListJson(String url) throws JSONException{
		jsonString = urlResPonse.getURLResponse(url);
		System.out.println(jsonString+"<><><>");
		JSONObject jsonObject = new JSONObject(jsonString);
		ProCartListBean bean;
		List<ProCartListBean>list= new ArrayList<ProCartListBean>();
		if (jsonObject.length()==1) {
			bean = new ProCartListBean();
			bean.code = jsonObject.getString("code");
			bean.info = jsonObject.getString("info");
			list.add(bean);
		}else{
			JSONArray jsonArray = jsonObject.getJSONArray("shopcart");
			System.out.println(jsonArray.length()+"jsonArray.length()");
			for (int i = 0; i < jsonArray.length(); i++) {
				bean = new ProCartListBean();
				jsonObject = jsonArray.getJSONObject(i);
				bean.brand = jsonObject.getString("brand");
				bean.item= jsonObject.getString("item");
				bean.model = jsonObject.getString("model");
				bean.number = jsonObject.getString("number");
				bean.minbuy = jsonObject.getString("minbuy");
				bean.price = jsonObject.getString("price");
				bean.thumb = jsonObject.getString("thumb");
				bean.title = jsonObject.getString("title");
				bean.show_price = jsonObject.getString("show_price");
				list.add(bean);
			}
			
		}
		return list;
	}
	
	/**
	 * 获取添加到购物车的json数据
	 * @param url
	 * @return
	 * @throws JSONException
	 */

	public static CustomBean getCustomBean(String url) throws JSONException{
		jsonString = urlResPonse.getURLResponse(url);
		//jsonString = urlResPonse.getURLResponse(url);
		System.out.println(jsonString+"<><><>");
		final JSONObject jsonObject = new JSONObject(jsonString);
		final CustomBean bean = new CustomBean();
		bean.code = jsonObject.getString("code");
		bean.info = jsonObject.getString("info");
		return bean;
	}
	private static String readStreamFromUrl(InputStream in) throws IOException {
		InputStreamReader inputStreamReader;
		StringBuffer result = new StringBuffer();
		String line = "";
		inputStreamReader = new InputStreamReader(in,"utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		while((line=bufferedReader.readLine())!=null){
			result.append(line);
		}
		return result.toString();
	}
	
	

}
