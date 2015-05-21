package com.star.android.carporange.utils;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists Illness");
		onCreate(db);
	}

	public boolean tableIsExist(String tableName, SQLiteDatabase db) {
		boolean result = false;
		String sql = "select count(*) as c from sqlite_master where type='table' and name='"+tableName.trim()+"'"; 
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if(count>0) {
				result = true;
			}
		}
		cursor.close();
		return result;
	}

}
