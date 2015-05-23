package com.star.android.carporange;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

public class NewRemindActivity extends Activity implements OnClickListener{
	
	private EditText mMedicineET;
	private EditText mRemindTimeET;
	private Button mTimePickerButton;
	private Button mSaveButton;
	
	private int mHour;
	private int mMinute;
	
	private AlarmManager mAm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remind_new);
		buildView();
		initEvents();
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
			String detail = mMedicineET.getText().toString();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, mHour);
			calendar.set(Calendar.MINUTE, mMinute);
			Intent intent = new Intent("com.star.android.carporange.alarm");
			intent.putExtra("detail", detail);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			mAm = (AlarmManager) getSystemService(ALARM_SERVICE);
			mAm.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
			IntentFilter filter = new IntentFilter();
			filter.addAction("com.star.android.carporamge.alarm");
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
