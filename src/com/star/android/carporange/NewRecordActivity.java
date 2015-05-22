package com.star.android.carporange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class NewRecordActivity extends Activity implements OnClickListener {
	
	private static final int CAMERA = 1;
	
	private EditText mEtHospital;
	private Spinner mSpinnerDepartment;
	private EditText mEtIllness;
	private EditText mEtCureTime;
	private Button mDatePickerButton;
	private TextView mRemindText;
	private Button mCameraButton; 
	private ImageView mPictureIv;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private Uri mImageUri;
	
	private OnDateSetListener mMyDateSetListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_new);
		buildView();
		initEvents();
	}

	private void buildView() {
		
		mEtHospital = (EditText) findViewById(R.id.et_hospital);
		mSpinnerDepartment = (Spinner) findViewById(R.id.spinner_department);
		mEtIllness = (EditText) findViewById(R.id.et_illness);
		mEtCureTime = (EditText) findViewById(R.id.et_time_cure);
		mDatePickerButton = (Button) findViewById(R.id.button_datePicker);
		mRemindText = (TextView) findViewById(R.id.tv_remind);
		mCameraButton = (Button) findViewById(R.id.button_camera);
		mPictureIv = (ImageView) findViewById(R.id.iv_pictrue);
		
		mMyDateSetListener = new MyDateListener();
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDateText();
	}
	
	private void initEvents() {
		
		mDatePickerButton.setOnClickListener(this);
		mCameraButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.button_datePicker:
			new DatePickerDialog(NewRecordActivity.this, mMyDateSetListener, mYear, mMonth, mDay).show();
			break;
			
		case R.id.button_camera:
			String dir = Environment.getExternalStorageDirectory().getPath()+"/CarpOrange";
			File fileDir = new File(dir);
			if(!fileDir.exists()) {
				fileDir.mkdir();
			}
			Time time = new Time();
			time.setToNow();
			String imageName = time.year+time.month+time.monthDay+time.hour+time.minute+time.second+"";
			File outputImage = new File(dir+"/"+imageName+".jpg");
			if(outputImage.exists()) {
				outputImage.delete();
			}
			try {
				outputImage.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mImageUri = Uri.fromFile(outputImage);
			Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			i.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
			startActivityForResult(i, CAMERA);
			break;
			
		}
	}
	
	class MyDateListener implements OnDateSetListener {

		@Override
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			mYear = year;
			mMonth = month;
			mDay = day;
			updateDateText();
		}
		
	}
	
	private void updateDateText() {
		
		StringBuilder dateTime = new StringBuilder().append(mYear).append("-")
				.append(mMonth < 10 ? "0" + mMonth : mMonth).append("-")
				.append(mDay < 10 ? "0" + mDay : mDay);
		mEtCureTime.setText(dateTime);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CAMERA:
			if(resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory.decodeStream
							(getContentResolver().openInputStream(mImageUri));
					mPictureIv.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}
}
