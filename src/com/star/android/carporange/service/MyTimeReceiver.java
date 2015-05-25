package com.star.android.carporange.service;

import com.star.android.carporange.MainActivity;
import com.star.android.carporange.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class MyTimeReceiver extends BroadcastReceiver {
	
	private NotificationManager mManager;
	
	@SuppressLint("NewApi") 
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		mManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		String detail = intent.getStringExtra("detail");
		Log.i("bb", detail);
		Intent playIntent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentTitle("∏√≥‘“©¡À≈Û”—").setContentText(detail == null ? "" :detail)
			.setSmallIcon(R.drawable.ic_launcher).setDefaults(Notification.DEFAULT_ALL)
			.setContentIntent(pendingIntent).setAutoCancel(true);
		mManager.notify(1, builder.build());
	}

}
