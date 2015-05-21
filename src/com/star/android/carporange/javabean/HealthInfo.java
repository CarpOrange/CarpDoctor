package com.star.android.carporange.javabean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class HealthInfo extends BmobObject {
	
	private String mainTitle;
	private String content;
	private BmobFile image;
	
	public String getMainTitle() {
		return mainTitle;
	}
	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BmobFile getImage() {
		return image;
	}
	public void setImage(BmobFile image) {
		this.image = image;
	}
	
}
