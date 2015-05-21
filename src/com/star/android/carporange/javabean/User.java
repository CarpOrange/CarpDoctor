package com.star.android.carporange.javabean;

import java.sql.Date;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {
	private String username;
	private String password;
	private String xingming;
	private String sex;
	private String birthDate;
	private Integer height;
	private Integer weight;
	
	public User(){
		super();
	}
	public User(String username, String password, String xingming, String sex,
			String birthDate, Integer height, Integer weight) {
		super();
		this.username = username;
		this.password = password;
		this.xingming = xingming;
		this.sex = sex;
		this.birthDate = birthDate;
		this.height = height;
		this.weight = weight;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
