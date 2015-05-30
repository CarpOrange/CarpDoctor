package com.star.android.carporange;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.star.android.carporange.utils.MyDatabaseHelper;

public class MedicalRecordActivity extends Activity implements OnClickListener {
	 
	private String mUsername;
	
	private Button mNewRecordButton;
	private ListView mListView;
	
	private Cursor mCursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_medical);
		buildView();
		initEvents();
	}

	private void buildView() {
		Intent i = getIntent();
		mUsername = i.getStringExtra("username");
		mNewRecordButton = (Button) findViewById(R.id.button_record_new);
		mListView = (ListView) findViewById(R.id.listView);
		updateListView();
		
	}

	@SuppressWarnings("deprecation")
	private void updateListView() {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "CarpOrange",
				null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (!dbHelper.tableIsExist("Medical_Record", db)) {
			return;
		}

		mCursor = db.rawQuery("select * from Medical_Record where username=?",
				new String[] { mUsername });
		if (mCursor.moveToFirst()) {
			mListView.setAdapter(new SimpleCursorAdapter(this,
					R.layout.item_list_activity_record_medical, mCursor,
					new String[] { "illness", "curetime", "department",
							"hospital" }, new int[] { R.id.tv_illness,
							R.id.tv_time_cure, R.id.tv_department,
							R.id.tv_hospital }));
		}
	}

	private void initEvents() {
		mNewRecordButton.setOnClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {
				mCursor.moveToPosition(index);
				Bundle bundle = new Bundle();
				bundle.putString("_id", mCursor.getString(0));
				bundle.putString("username", mCursor.getString(1));
				bundle.putString("hospital", mCursor.getString(2));
				bundle.putString("department", mCursor.getString(3));
				bundle.putString("illness", mCursor.getString(4));
				bundle.putString("curetime", mCursor.getString(5));
				bundle.putString("imagepath", mCursor.getString(6));
				Intent i = new Intent(MedicalRecordActivity.this, NewRecordActivity.class);
				i.putExtra("bundle", bundle);
				startActivityForResult(i, 1);
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.button_record_new:
			Intent i = new Intent();
			i.putExtra("username", mUsername);
			i.setClass(MedicalRecordActivity.this, NewRecordActivity.class);
			startActivityForResult(i, 1);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
		case 1 :
			if(resultCode == RESULT_OK) {
				updateListView();
			}
			break;
		}
	}

}
