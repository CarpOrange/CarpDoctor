package com.star.android.carporange;

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
import com.star.android.carporange.widget.CarpActivity;

public class BPListActivity extends CarpActivity {

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
		mNewRecordButton = (Button) findViewById(R.id.button_record_new);
		mListView = (ListView) findViewById(R.id.listView);
		updateListView();
	}

	@SuppressWarnings("deprecation")
	private void updateListView() {
		MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "CarpOrange",
				null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (!dbHelper.tableIsExist("BP", db)) {
			return;
		}

		mCursor = db.rawQuery("select * from BP", null);
		if (mCursor.moveToFirst()) {
			if (getIntent().getStringExtra("action").equals("bp")) {
				mListView.setAdapter(new SimpleCursorAdapter(this,
						R.layout.item_list_activity_record_medical, mCursor,
						new String[] { "result", "date", "sbp", "dbp" },
						new int[] { R.id.tv_illness, R.id.tv_time_cure,
								R.id.tv_department, R.id.tv_hospital }));
			} else if (getIntent().getStringExtra("action").equals("hr")) {
				mListView.setAdapter(new SimpleCursorAdapter(this,
						R.layout.item_list_activity_record_medical, mCursor,
						new String[] { "result", "date", "hr" }, new int[] {
								R.id.tv_illness, R.id.tv_time_cure, R.id.tv_department}));
			}
		}
	}

	private void initEvents() {

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {

				mCursor.moveToPosition(index);
				Bundle bundle = new Bundle();
				bundle.putString("id", mCursor.getString(0));
				bundle.putString("sbp", mCursor.getString(1));
				bundle.putString("dbp", mCursor.getString(2));
				bundle.putString("hr", mCursor.getString(3));
				bundle.putString("result", mCursor.getString(4));
				bundle.putString("date", mCursor.getString(5));
				bundle.putString("time", mCursor.getString(6));
				Intent i = new Intent(BPListActivity.this, BPActivity.class);
				i.putExtra("bundle", bundle);
				startActivityForResult(i, 1);
				
			}
		});

		mNewRecordButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent();
				i.setClass(BPListActivity.this, BPActivity.class);
				startActivityForResult(i, 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				updateListView();
			}
			break;
		}
	}

}
