package com.star.android.carporange.javabean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class HealthInfo extends BmobObject {
	
	private String mainTitle;
	private String content;
	private BmobFile bmobFile;
	
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
	public BmobFile getBmobFile() {
		return bmobFile;
	}
	public void setBmobFile(BmobFile bmobFile) {
		this.bmobFile = bmobFile;
	}
	
}
