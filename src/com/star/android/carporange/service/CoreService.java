package com.star.android.carporange.service;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.javabean.HealthInfo;

public class CoreService extends Service {
	
	private LoadBinder mBinder = new LoadBinder();
	private List<HealthInfo> mHealthInfo;
	public class LoadBinder extends Binder {
		
		public List<HealthInfo> startLoadHealthInfo() {
			new MyThread().start();
			return mHealthInfo;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("CoreService", "onBind execute");
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("CoreService", "onCreate execute");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("CoreService", "onStartCommand execute");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("CoreService", "onDestroy execute");
	}
	
	class MyThread extends Thread{
		
		@Override
		public void run() {
			
		}
	}
	
}
