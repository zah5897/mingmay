package com.mingmay.cc.app.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager {
	public static final String TAG=DBManager.class.getSimpleName();
	private DBHelper mDHelper;
	private static DBManager mInstance;
	private SQLiteDatabase db;

	private DBManager(Context context) {
		if (mDHelper == null) {
			mDHelper = new DBHelper(context);
			db = mDHelper.getWritableDatabase();
		}
	}

	public synchronized static DBManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBManager(context);
		}
		return mInstance;
	}

	public synchronized static DBManager getInstance() {
		return mInstance;
	}
   
	public void close(){
		if(db!=null&&db.isOpen()){
			db.close();
		}
	}
	public void clearDatas(String tableName){
		db.execSQL("delete from "+tableName);
		Log.d(TAG, "delete from "+tableName);
	}
	  
 

	/*************** create helper ***************/
	class DBHelper extends SQLiteOpenHelper {
		static final String DATABASE_NAME = "mbox.db";
		static final int DATABASE_VERSION = 1;
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			 
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String create_table_sql = "CREATE TABLE talbe_name (_id "
					+ "INTEGER PRIMARY KEY AUTOINCREMENT," 
					+ "name varchar,"
					+ "icon varchar,"
					+ "link varchar,"
					+ "type INTEGER," 
					+ "module_id INTEGER," 
					+ "templete_id INTEGER);";
			
			db.execSQL("DROP TABLE IF EXISTS "+create_table_sql);
			//db.execSQL("DROP TABLE IF EXISTS "+AddressModel.getTable_SQL());
		}

	}
}
