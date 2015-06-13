package com.star.android.carporange;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.star.android.carporange.utils.MyDatabaseHelper;
import com.star.android.carporange.widget.CarpActivity;

public class BPActivity extends CarpActivity implements OnClickListener{
	
	private static final String CREATE_BP = "create table BP ("
			+ "_id integer primary key autoincrement,"
			+ "sbp text,"
			+ "dbp text,"
			+ "hr text,"
			+ "result text,"
			+ "date text,"
			+ "time text)";
	
	private EditText mDateEt;
	private EditText mTimeEt;
	private EditText mSbpEt;
	private EditText mDbpEt;
	private EditText mHrEt;
	
	private Button mSaveButton;
	
	private String mId;
	private String mResult;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private int mHour;
	private int mMinute;
	
	private OnDateSetListener mDateSetListener;
	private OnTimeSetListener mTimeSetListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bp);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mDateEt = (EditText) findViewById(R.id.et_date);
		mTimeEt = (EditText) findViewById(R.id.et_time);
		mSbpEt = (EditText) findViewById(R.id.et_sbp);
		mDbpEt = (EditText) findViewById(R.id.et_dbp);
		mHrEt = (EditText) findViewById(R.id.et_hr);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
		
		mDateSetListener = new MyDateListener();
		mTimeSetListener = new MyTimeListener();
		
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if(bundle != null) {
			mResult = bundle.getString("result");
			
			mId = bundle.getString("id");
			mSbpEt.setText(bundle.getString("sbp"));
			mDbpEt.setText(bundle.getString("dbp"));
			mHrEt.setText(bundle.getString("hr"));
			String[] date = bundle.getString("date").split("年|月|日");
			String[] time = bundle.getString("time").split("点|分");
			
			mYear = Integer.parseInt(date[0]);
			mMonth = Integer.parseInt(date[1]);
			mDay = Integer.parseInt(date[2]);
			updateDate();
			
			mHour = Integer.parseInt(time[0]);
			mMinute = Integer.parseInt(time[1]);
			updateTime();
			
		} else {
			
			Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH) + 1;
			mDay = c.get(Calendar.DAY_OF_MONTH);
			updateDate();
			
			Time t = new Time();
			t.setToNow();
			mHour = t.hour;
			mMinute = t.minute;
			updateTime();
		}
	}

	private void initEvents() {
		
		mDateEt.setOnClickListener(this);
		mTimeEt.setOnClickListener(this);
		mSaveButton.setOnClickListener(this);
		
		
	}

	private void updateDate() {
		mDateEt.setText(new StringBuilder("").append(mYear).append("年")
				.append(mMonth).append("月")
				.append(mDay).append("日"));
	}

	private void updateTime() {
		mTimeEt.setText(new StringBuilder("").append(mHour).append("点")
				.append(mMinute).append("分"));
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.et_date:
			new DatePickerDialog(this, mDateSetListener, mYear, mMonth - 1, mDay).show();
			break;
		case R.id.et_time:
			new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true).show();
			break;
		case R.id.button_save:
			Editable sbp = mSbpEt.getText();
			Editable dbp = mDbpEt.getText();
			Editable hr = mHrEt.getText();
			if(TextUtils.isEmpty(sbp) || TextUtils.isEmpty(dbp) || TextUtils.isEmpty(hr)) {
				Toast.makeText(this, "请完成数据", Toast.LENGTH_SHORT).show();
			} else {
				if(Integer.parseInt(sbp.toString()) > 140 || Integer.parseInt(dbp.toString()) > 90 
						|| Integer.parseInt(hr.toString()) > 100 || (Integer.parseInt(sbp.toString()) < 90
						|| Integer.parseInt(dbp.toString()) < 60 || Integer.parseInt(hr.toString()) < 60)) {
					mResult = "异常";
				} else {
					mResult = "正常";
				}
				MyDatabaseHelper dbHelper = new MyDatabaseHelper(this,
						"CarpOrange", null, 1);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				if (!dbHelper.tableIsExist("BP", db)) {
					db.execSQL(CREATE_BP);
				}
				ContentValues values = new ContentValues();
				values.put("sbp", sbp.toString());
				values.put("dbp", dbp.toString());
				values.put("hr", hr.toString());
				values.put("result", mResult);
				values.put("date", mDateEt.getText().toString());
				values.put("time", mTimeEt.getText().toString());
				if (mId != null) {
					int y = db.update("BP", values, "_id=?",
							new String[] { mId });
					Toast.makeText(this, "更新成功" + y + "条数据", Toast.LENGTH_SHORT)
							.show();
				} else {
					Long x = db.insert("BP", null, values);
				}
				setResult(RESULT_OK);
				new AlertDialog.Builder(this).setIcon(R.drawable.carporange)
				.setTitle("提示").setMessage("异常").setPositiveButton("确认", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.create().show();
			}
			break;
		}
	}
	
	class MyDateListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mYear = year;
			mMonth = month + 1;
			mDay = day;
			updateDate();
		}
		
	}
	
	class MyTimeListener implements OnTimeSetListener {

		@Override
		public void onTimeSet(TimePicker view, int hour, int minute) {
			mHour = hour;
			mMinute = minute;
			updateTime();
		}
		
	}
}
