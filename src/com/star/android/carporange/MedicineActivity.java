package com.star.android.carporange;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.star.android.carporange.javabean.Medicine;
import com.star.android.carporange.utils.MyDatabaseHelper;

public class MedicineActivity extends Activity {
	
	private ListView mListView;
	private MyDatabaseHelper myDbHelper;
	private List<String> mList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicine);
		buildView();
	}

	private void buildView() {

		mListView = (ListView) findViewById(R.id.listView);
		myDbHelper = new MyDatabaseHelper(this, "CarpOrange", null, 1);
		SQLiteDatabase db = myDbHelper.getWritableDatabase();
		if (myDbHelper.tableIsExist("Medicine", db)) {
			getFromSqlite(db);
		} else {
			initSqlite(db);
		}
	}

	@SuppressWarnings("deprecation")
	private void getFromSqlite(SQLiteDatabase db) {
		final SQLiteDatabase mDb = db;
		String sql = "select _id, medicine_name from medicine order by medicine_name;";
		final Cursor cursor = db.rawQuery(sql, null);
		SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(
				MedicineActivity.this, R.layout.item_list_activity_medicine,
				cursor, new String[] { "medicine_name" },
				new int[] { R.id.textView });
		mListView.setAdapter(mCursorAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {
				cursor.moveToPosition(index);
				int id = cursor.getInt(0);
				String sql = "select medicine_info from medicine where _id="
						+ id;
				Cursor c = mDb.rawQuery(sql, null);
				c.moveToFirst();
				String info = c.getString(0);
				Intent i = new Intent(MedicineActivity.this,
						HealthInfoActivity.class);
				i.putExtra("mainTitle", cursor.getString(1));
				i.putExtra("content", info);
				startActivity(i);
			}
		});
	}

	private void initSqlite(SQLiteDatabase db) {
		final SQLiteDatabase mDb = db;
		final String sql = "create table Medicine ("
				+ "_id integer primary key autoincrement ,"
				+ "medicine_name text ," + "medicine_info text) ;";
		BmobQuery<Medicine> query = new BmobQuery<Medicine>("Medicine");
		query.findObjects(MedicineActivity.this, new FindListener<Medicine>() {

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(MedicineActivity.this, "无法连接到服务器",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(List<Medicine> list) {
				Toast.makeText(MedicineActivity.this, "初始化成功",
						Toast.LENGTH_SHORT).show();
				mDb.execSQL(sql);
				for (Medicine medicine : list) {
					String medicine_name = medicine.getMedicine_name();
					String medicine_info = medicine.getMedicine_info();
					ContentValues values = new ContentValues();
					values.put("medicine_name", medicine_name);
					values.put("medicine_info", medicine_info);
					mDb.insert("Medicine", null, values);
					values.clear();
				}
				getFromSqlite(mDb);
			}
		});
	}
	
}
