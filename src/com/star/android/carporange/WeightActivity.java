package com.star.android.carporange;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
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

public class WeightActivity extends Activity implements OnClickListener{
	
	private static final String CREATE_WEIGHT = "create table Weight ("
			+ "_id integer primary key autoincrement,"
			+ "weight text,"
			+ "date text,"
			+ "time text)";
	
	private EditText mDateEt;
	private EditText mTimeEt;
	private EditText mWeightEt;
	
	private Button mSaveButton;
	
	private String mId;
	
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
		setContentView(R.layout.activity_weight);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mDateEt = (EditText) findViewById(R.id.et_date);
		mTimeEt = (EditText) findViewById(R.id.et_time);
		mWeightEt = (EditText) findViewById(R.id.et_weight);
		
		mSaveButton = (Button) findViewById(R.id.button_save);
		
		mDateSetListener = new MyDateListener();
		mTimeSetListener = new MyTimeListener();
		
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if(bundle != null) {
			
			mId = bundle.getString("id");
			mWeightEt.setText(bundle.getString("weight"));
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
			mMonth = c.get(Calendar.MONTH);
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
	
	class MyDateListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			mYear = year;
			mMonth = month;
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

	@Override
	public void onClick(View view) {
		
		switch(view.getId()) {
		case R.id.et_date:
			new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay).show();
			break;
		case R.id.et_time:
			new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true).show();
			break;
		case R.id.button_save:
			Editable weight = mWeightEt.getText();
			if(TextUtils.isEmpty(weight)) {
				Toast.makeText(this, "请完成数据", Toast.LENGTH_SHORT).show();
			} else {
				MyDatabaseHelper dbHelper = new MyDatabaseHelper(this,
						"CarpOrange", null, 1);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				if (!dbHelper.tableIsExist("Weight", db)) {
					db.execSQL(CREATE_WEIGHT);
				}
				ContentValues values = new ContentValues();
				values.put("weight", weight.toString());
				values.put("date", mDateEt.getText().toString());
				values.put("time", mTimeEt.getText().toString());
				if (mId != null) {
					int y = db.update("Weight", values, "_id=?",
							new String[] { mId });
					Toast.makeText(this, "更新成功" + y + "条数据", Toast.LENGTH_SHORT)
							.show();
				} else {
					Long x = db.insert("Weight", null, values);
				}
				setResult(RESULT_OK);
				finish();
			}
			break;
		}
		
	}
	
}
