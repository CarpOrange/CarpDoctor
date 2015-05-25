package com.star.android.carporange.service;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.javabean.HealthInfo;

public class CoreService extends Service {
	
	private LoadBinder mBinder = new LoadBinder();
	private List<HealthInfo> mHealthInfo;
	private String mDetail;
	private AlarmManager mAm;
	private Calendar mCalendar;
	
	public class LoadBinder extends Binder {
		
		public void startLoadHealthInfo(String detail, Calendar calendar) {
			mDetail = detail;
			mCalendar = calendar;
			new MyThread().start();
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
			Intent intent = new Intent(CoreService.this, MyTimeReceiver.class);
			intent.putExtra("detail", mDetail);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(CoreService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			mAm = (AlarmManager) getSystemService(ALARM_SERVICE);
			mAm.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
		}
	}
	
}
