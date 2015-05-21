package com.star.android.carporange.javabean;

import cn.bmob.v3.BmobObject;

public class Medicine extends BmobObject {
	
	private String medicine_name;
	private String medicine_info;
	public String getMedicine_name() {
		return medicine_name;
	}
	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}
	public String getMedicine_info() {
		return medicine_info;
	}
	public void setMedicine_info(String medicine_info) {
		this.medicine_info = medicine_info;
	}
	
}
