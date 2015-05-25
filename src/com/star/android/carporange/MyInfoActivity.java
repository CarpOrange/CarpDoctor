package com.star.android.carporange;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.star.android.carporange.javabean.User;

public class MyInfoActivity extends Activity implements OnClickListener{
	
	private String mObjectId;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private EditText mEtXingming;
	private RadioButton mRbMale;
	private RadioButton mRbFemale;
	private Button mSaveButton;
	private Button mDatePickerButton;
	
	private EditText mEtBirthDate;
	private EditText mEtHeight;
	private EditText mEtWeight;
	
	private OnDateSetListener mDatePickerListener = new MyDateListener();
	private static final int UPDATE_INTO = 1;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		buildView();
		initEvents();
		mHandler = new MyHandler();
	}

	private void buildView() {
		Intent i = getIntent();
		String userName = i.getStringExtra("username");
		
		mEtXingming = (EditText) findViewById(R.id.et_xingming);
		mEtHeight = (EditText) findViewById(R.id.et_height);
		mEtWeight = (EditText) findViewById(R.id.et_weight);
		mEtBirthDate = (EditText) findViewById(R.id.et_date_birth);
		mSaveButton = (Button) findViewById(R.id.button_save);
		mRbMale = (RadioButton) findViewById(R.id.radiobutton_male);
		mRbFemale = (RadioButton) findViewById(R.id.radiobutton_female);
		
		mDatePickerButton = (Button) findViewById(R.id.button_datePicker);
		
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDateDisplay();
		
		if(!TextUtils.isEmpty(userName)) {
			BmobQuery<User> query = new BmobQuery<User>("User");
			query.addWhereEqualTo("username", userName);
			query.findObjects(MyInfoActivity.this, new FindListener<User>() {
				
				@Override
				public void onSuccess(List<User> list) {
					User user = list.get(0);
					mObjectId = user.getObjectId();
					Message m = new Message();
					m.what = UPDATE_INTO;
					m.obj = user;
					mHandler.sendMessage(m);
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					
				}
			});
		}
	}
	
	private void updateDateDisplay() {
		mEtBirthDate.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1 < 10 ? "0" + (mMonth + 1) : mMonth+1 )).append("-")
				.append(mDay < 10 ? "0" + mDay : mDay));
		
	}

	private void initEvents() {
		mSaveButton.setOnClickListener(this);
		mDatePickerButton.setOnClickListener(this);
	}
	
	class MyHandler extends Handler {
		public void handleMessage(Message message) {
			switch(message.what) {
			case UPDATE_INTO:
				User user = (User) message.obj;
				String xingming = user.getXingming();
				String sex = user.getSex();
				String birthDate = user.getBirthDate();
				Integer height = user.getHeight();
				Integer weight = user.getWeight();
				if(xingming != null) {
					mEtXingming.setText(xingming);
				}
				if(sex != null) {
					if(sex.equals("ÄÐ")) {
						mRbMale.setChecked(true);
					} else if(sex.equals("Å®")) {
						mRbFemale.setChecked(true);
					}
				}
				if(birthDate != null) {
					mEtBirthDate.setText(birthDate);
				}
				if(height != null) {
					mEtHeight.setText(height.toString());
				}
				if(weight != null) {
					mEtWeight.setText(weight.toString());
				}
			}
		};
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_save:
			User u = new User();
			u.setXingming(mEtXingming.getText() != null ? mEtXingming.getText().toString() : null);
			u.setWeight(mEtWeight.getText() != null ? Integer.parseInt(mEtWeight.getText().toString()) : null);
			u.setHeight(mEtHeight.getText() != null ? Integer.parseInt(mEtHeight.getText().toString()) : null);
			u.setBirthDate(mEtBirthDate.getText() != null ? mEtBirthDate.getText().toString() : null);
			if(mRbMale.isChecked()) {
				u.setSex("ÄÐ");
			} else if(mRbFemale.isChecked()) {
				u.setSex("Å®");
			}
			u.update(this, mObjectId, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(MyInfoActivity.this, "±£´æÊ§°Ü", Toast.LENGTH_SHORT).show();
				}
			});
			break;
		case R.id.button_datePicker:
			new DatePickerDialog(MyInfoActivity.this, mDatePickerListener, mYear, mMonth, mDay).show();
			break;
		}
	};
	
	class MyDateListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int day) {
			mYear = year;
			mMonth = month;
			mDay = day;
			
			updateDateDisplay();
		}
		
	}
	
}
