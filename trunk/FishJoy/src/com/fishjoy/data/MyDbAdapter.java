package com.fishjoy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * 数据库操作类,部分参考书上
 */
public class MyDbAdapter {
	
	public static final String KEY_DATE = "date";
	public static final String KEY_SCORE = "score";
    public static final String KEY_ID = "_id";
    public static final String GAME_MODE = "mode";

    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSQLiteDababase;
    
    private static final String DATABASE_CREATE =
            "create table scorez (_id integer primary key autoincrement, "
                    + "date text, score integer, mode integer);";

    private static final String DATABASE_NAME = "MyDB3";
    public static final String DATABASE_TABLE = "scorez";
    private static final int DATABASE_VERSION = 1;
    
    private final Context mCtx;
    
    public MyDbAdapter(Context context) {
		this.mCtx = context;
	}
    
    /*
     * DatabaseHelper
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);			
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS scorez");
			onCreate(db);
		}    	
    }	
    
    /*
     * 打开数据库
     */
    public void open() throws SQLException {
    	mDatabaseHelper = new DatabaseHelper(mCtx);
    	mSQLiteDababase = mDatabaseHelper.getWritableDatabase();
    }
    
    /*
     * 关闭数据库
     */
    public void close() {
    	mDatabaseHelper.close();
    }
    
    /*
     * 往数据库中插入数据
     */
    public long insertData(String date, int score, int mode) {
    	ContentValues initialValues = new ContentValues();
    	initialValues.put(KEY_DATE, date);
    	initialValues.put(KEY_SCORE, score);
    	initialValues.put(GAME_MODE, mode);
    	return mSQLiteDababase.insert(DATABASE_TABLE, KEY_ID, initialValues);
    }
    
    /*
     * 获取某一模式的所有数据
     */
    public Cursor fetchData(int mode) throws SQLException {
    	Cursor mCursor = mSQLiteDababase.query(DATABASE_TABLE, new String[] { KEY_ID,
    			KEY_DATE, KEY_SCORE }, GAME_MODE + "=" + mode, null, null, null, KEY_SCORE);
    	
    	if (mCursor != null) {
    		mCursor.moveToFirst();    		
    	}
    	return mCursor;
    }
}
