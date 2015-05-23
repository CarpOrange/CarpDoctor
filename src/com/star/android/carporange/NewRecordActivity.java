package com.star.android.carporange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.star.android.carporange.utils.MyDatabaseHelper;

public class NewRecordActivity extends Activity implements OnClickListener {
	
	private static final int CAMERA = 1;
	private static final String CREATE_MEDICAL_RECORD = "create table Medical_Record ("
			+ "_id integer primary key autoincrement,"
			+ "username text,"
			+ "hospital text,"
			+ "department text,"
			+ "illness text,"
			+ "curetime text,"
			+ "imagepath text)";
	
	private String mUsername;
	private String mId;
	
	private String mDepartmentArray[] = new String[]{
			"普外科", "泌尿外科", " 风湿免疫科", "眼科", "耳鼻咽喉科", "呼吸内科",
			"神经内科", "心血管内科", "消化内科", "内分泌与代谢科", "性病科", "皮肤科", "肝胆外科"
	};
	
	private EditText mEtHospital;
	private Spinner mSpinnerDepartment;
	private EditText mEtIllness;
	private EditText mEtCureTime;
	private Button mDatePickerButton;
	private TextView mRemindText;
	private Button mCameraButton; 
	private ImageView mPictureIv;
	private Button mSaveButton;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	private Uri mImageUri;
	private String mImagePath;
	
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
		mSaveButton = (Button) findViewById(R.id.button_save);
		
		mSpinnerDepartment.setAdapter(new ArrayAdapter<String>
			(this, android.R.layout.simple_spinner_item, mDepartmentArray));
		
		mMyDateSetListener = new MyDateListener();
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if(bundle != null){
			mUsername = bundle.getString("username");
			mId = bundle.getString("_id");
			
			String department = bundle.getString("department");
			for(int i = 0; i<mDepartmentArray.length; i++) {
				if(mDepartmentArray[i].equals(department)) 
					mSpinnerDepartment.setSelection(i);
			}
			
			mEtHospital.setText(bundle.getString("hospital"));
			mEtIllness.setText(bundle.getString("illness"));
			
			String curetime = bundle.getString("curetime");
			
			String[] time = curetime.split("-");
			mYear = Integer.parseInt(time[0]);
			mMonth = Integer.parseInt(time[1]);
			mDay = Integer.parseInt(time[2]);
			updateDateText();
			
			mEtCureTime.setText(curetime);
			mImagePath = bundle.getString("imagepath");
			File outputImage = new File(mImagePath);
			mImageUri = Uri.fromFile(outputImage);
			Bitmap bitmap;
			try {
				bitmap = BitmapFactory.decodeStream
						(getContentResolver().openInputStream(mImageUri));
				mPictureIv.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return;
		}
		mUsername = getIntent().getStringExtra("username");
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDateText();
		
	}
	
	private void initEvents() {
		
		mDatePickerButton.setOnClickListener(this);
		mCameraButton.setOnClickListener(this);
		mSaveButton.setOnClickListener(this);
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
			mImagePath = dir+"/"+imageName+".jpg";
			File outputImage = new File(mImagePath);
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
		case R.id.button_save:
			String hospital = mEtHospital.getText().toString();
			String department = mSpinnerDepartment.getSelectedItem().toString();
			String illness = mEtIllness.getText().toString();
			String cureTime = mEtCureTime.getText().toString();
			String imagePath = mImagePath;
			if(TextUtils.isEmpty(hospital) || TextUtils.isEmpty(department) 
					|| TextUtils.isEmpty(illness) || TextUtils.isEmpty(cureTime)
					|| TextUtils.isEmpty(imagePath)) {
				Toast.makeText(NewRecordActivity.this, "资料未完成", Toast.LENGTH_SHORT).show();
				return;
			}
			
			MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "CarpOrange", null, 1);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			if(!dbHelper.tableIsExist("Medical_Record", db)) {
				db.execSQL(CREATE_MEDICAL_RECORD);
			}
			ContentValues values = new ContentValues();
			values.put("username", mUsername);
			values.put("hospital", hospital);
			values.put("department", department);
			values.put("illness", illness);
			values.put("curetime", cureTime);
			values.put("imagepath", imagePath);
			if(mId != null) {
				int y = db.update("Medical_Record", values, "_id=?", new String[]{mId});
				Toast.makeText(this, "更新成功"+y+"条数据", Toast.LENGTH_SHORT).show();
			} else {
				Long x =db.insert("Medical_Record", null, values);
				Toast.makeText(this, "保存成功"+x+"条数据", Toast.LENGTH_SHORT).show();
			}
			
			setResult(RESULT_OK);
			finish();
			break;
		default: 
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
				.append(mMonth).append("-")
				.append(mDay);
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
