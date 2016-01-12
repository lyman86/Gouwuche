package com.example.gouwuche.bean;

import java.util.List;

public class ProCartListBean {
	public String item;
	public String number;
	public String title;
	public String thumb;
	public String model;
	public String price;
	public String brand;
	public String minbuy;
	// 表示选择商品的种类
	public String totalnumber;
	public String show_price;

	public String code;
	public String info;

	//所有被选选中商品总价的集合
	private List<String> subTotals;
	//判断每个人商品是否被选中
	private List<Boolean> isSelects;
	
	public List<String> getSubTotals() {
		return subTotals;
	}
	public void setSubTotals(List<String> subTotals) {
		this.subTotals = subTotals;
	}
	public List<Boolean> getIsSelects() {
		return isSelects;
	}
	public void setIsSelects(List<Boolean> isSelects) {
		this.isSelects = isSelects;
	}

}
