package com.star.android.carporange.javabean;

import cn.bmob.v3.BmobObject;

public class Illness extends BmobObject {
	
	private String illness_name;
	private String illness_department;
	private String illness_people;
	public String getIllness_name() {
		return illness_name;
	}
	public String getIllness_department() {
		return illness_department;
	}
	public String getIllness_people() {
		return illness_people;
	}
	
}
