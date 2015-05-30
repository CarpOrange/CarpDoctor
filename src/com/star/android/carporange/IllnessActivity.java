package com.star.android.carporange;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.javabean.Illness;
import com.star.android.carporange.utils.MyDatabaseHelper;

public class IllnessActivity extends Activity implements OnClickListener {
	
	private MyDatabaseHelper dbHelper;
	private TextView peopleTextView;
	private TextView departmentTextView;
	
	private ListView leftListView;
	private ListView rightListView;
	
	private List<String> departmentList = new ArrayList<String>();
	private List<String> peopleList = new ArrayList<String>();
	private List<String> illnessList = new ArrayList<String>();
	
	private boolean mFlag = true;
	
	private ArrayAdapter<String> peopleAdapter;
	private ArrayAdapter<String> departmentAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_illness);
		
		buildView();
		initEvents();
	}

	private void buildView() {
		peopleTextView = (TextView) findViewById(R.id.tv_people);
		departmentTextView = (TextView) findViewById(R.id.tv_department);
		leftListView = (ListView) findViewById(R.id.listView_left);
		rightListView = (ListView) findViewById(R.id.listView_right);

		dbHelper = new MyDatabaseHelper(this, "CarpOrange", null, 1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		if (dbHelper.tableIsExist("Illness", db)) {
			getFromSqlite(db);
		} else {
			initSqlite(db);
		}

	}

	private void getFromSqlite(SQLiteDatabase db) {
		Cursor cursor;
		cursor = db
				.rawQuery(
						"select illness_people from illness group by illness_people having illness_people is not null",
						null);
		if (cursor.moveToFirst()) {
			do {
				String people = cursor.getString(0);
				peopleList.add(people);
			} while (cursor.moveToNext());
		}
		peopleAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, peopleList);
		cursor = db
				.rawQuery(
						"select illness_department from illness group by illness_department",
						null);
		if (cursor.moveToFirst()) {
			do {
				String department = cursor.getString(0);
				departmentList.add(department);
			} while (cursor.moveToNext());
		}
		cursor.close();
		departmentAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, departmentList);

		leftListView.setAdapter(peopleAdapter);
	}

	private void initSqlite(SQLiteDatabase db) {
		BmobQuery<Illness> query = new BmobQuery<Illness>("Illness");
		final SQLiteDatabase mDb = db;
		final String CREATE_ILLNESS = "create table Illness ("
				+ "id integer primary key autoincrement, "
				+ "illness_name text, " + "illness_department text, "
				+ "illness_people text) ";
		query.findObjects(this, new FindListener<Illness>() {

			@Override
			public void onSuccess(List<Illness> list) {
				mDb.execSQL(CREATE_ILLNESS);
				Toast.makeText(IllnessActivity.this, "初始化成功", Toast.LENGTH_LONG)
						.show();
				ContentValues values = new ContentValues();
				for (Illness illness : list) {
					String name = illness.getIllness_name();
					String department = illness.getIllness_department();
					String people = illness.getIllness_people();
					values.put("illness_name", name);
					values.put("illness_department", department);
					values.put("illness_people", people);
					mDb.insert("Illness", null, values);
					values.clear();
				}
				getFromSqlite(mDb);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(IllnessActivity.this, "无法连接到服务器",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void initEvents() {
		
		peopleTextView.setOnClickListener(this);
		departmentTextView.setOnClickListener(this);
		final SQLiteDatabase db = dbHelper.getWritableDatabase();
		leftListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
				long arg3) {
				illnessList.clear();
				Cursor cursor;
				if(mFlag) {
					String people = peopleList.get(index);
					cursor = db.rawQuery("select illness_name from illness where illness_people='"+people+"'", null);
					if(cursor.moveToFirst()) {
						do {
							illnessList.add(cursor.getString(0));
						} while (cursor.moveToNext());
					}
				} else {
					String department = departmentList.get(index);
					cursor = db.rawQuery("select illness_name from illness where illness_department='"+department+"'", null);
					if(cursor.moveToFirst()) {
						do {
							illnessList.add(cursor.getString(0));
						} while (cursor.moveToNext());
					}
				}
				cursor.close();
				rightListView.setAdapter(new ArrayAdapter<String>(IllnessActivity.this, android.R.layout.simple_list_item_1, illnessList));
				rightListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int index, long arg3) {
						String illnessName = illnessList.get(index);
						Intent i = new Intent (IllnessActivity.this, MyWebViewActivity.class);
						i.putExtra("keyword", illnessName);
						startActivity(i);
					}
					
				});
			}
			
		});
		
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.tv_people:
			leftListView.setAdapter(peopleAdapter);
			rightListView.setAdapter(null);
			mFlag = true;
			changeTextColor();
			break;
		case R.id.tv_department:
			leftListView.setAdapter(departmentAdapter);
			mFlag = false;
			changeTextColor();
			rightListView.setAdapter(null);
			break;
		}
	}

	private void changeTextColor() {
		if(mFlag) {
			peopleTextView.setTextColor(Color.parseColor("#45CC53"));
			departmentTextView.setTextColor(Color.parseColor("#000000"));
		} else {
			departmentTextView.setTextColor(Color.parseColor("#45CC53"));
			peopleTextView.setTextColor(Color.parseColor("#000000"));
		}
	}
}
