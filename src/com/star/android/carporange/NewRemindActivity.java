package com.star.android.carporange;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.star.android.carporange.service.MyTimeReceiver;
import com.star.android.carporange.widget.CarpActivity;

public class NewRemindActivity extends CarpActivity implements OnClickListener{
	
	private EditText mMedicineET;
	private EditText mRemindTimeET;
	private Button mTimePickerButton;
	private Button mSaveButton;
	
	private int mHour;
	private int mMinute;
	private AlarmManager mAm;
	
//	private Calendar mCalendar;
//	private String mDetail;
//	private CoreService.LoadBinder mLoadBinder;
//	private ServiceConnection conn = new ServiceConnection() {
//		
//		@Override
//		public void onServiceDisconnected(ComponentName arg0) {
//		}
//		
//		@Override
//		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
//			mLoadBinder = (CoreService.LoadBinder) arg1;
//			mLoadBinder.startLoadHealthInfo(mDetail, mCalendar);
//		}
//	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind_new);
		buildView();
		initEvents();
	}
	
	@Override
	protected void onDestroy() {
//		unbindService(conn);
		super.onDestroy();
	}
	private void buildView() {
		
		mMedicineET = (EditText) findViewById(R.id.et_medicine);
		mRemindTimeET = (EditText) findViewById(R.id.et_time_remind);
		mTimePickerButton = (Button) findViewById(R.id.button_timePicker);
		mSaveButton = (Button) findViewById(R.id.button_save);
		
		Time time = new Time();
		time.setToNow();
		mHour = time.hour;
		mMinute = time.minute;
		updateTime();
		
	}

	private void initEvents() {
		
		mTimePickerButton.setOnClickListener(this);
		mSaveButton.setOnClickListener(this);
	}
	
	private void updateTime() {
		mRemindTimeET.setText(mHour+" : "+mMinute);
	}
	@Override
	public void onClick(View view) {
		
		switch(view.getId()) {
		
		case R.id.button_timePicker:
			new TimePickerDialog(this, new MyTimeSetListener(), mHour, mMinute, true).show();
			break;
		case R.id.button_save:
//			mDetail = mMedicineET.getText().toString();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, mHour);
			calendar.set(Calendar.MINUTE, mMinute);
			calendar.set(Calendar.SECOND, 0);  
	        calendar.set(Calendar.MILLISECOND, 0);  
//	        mCalendar = calendar;
	        
//	        Intent i = new Intent(this, CoreService.class);
//	        bindService(i, conn, BIND_AUTO_CREATE);
	        Intent intent = new Intent(this, MyTimeReceiver.class);
	        String detail = mMedicineET.getText().toString();
			intent.putExtra("detail", detail);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			mAm = (AlarmManager) getSystemService(ALARM_SERVICE);
			mAm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
			Toast.makeText(this, "…Ë÷√≥…π¶", Toast.LENGTH_SHORT).show(); 
			setResult(RESULT_OK);
			finish();
			break;
		default:
			break;
		}
		
	}
	
	class MyTimeSetListener implements OnTimeSetListener {

		@Override
		public void onTimeSet(TimePicker view, int hour, int minute) {
			mHour = hour;
			mMinute = minute;
			updateTime();
		}
		
	}
}
