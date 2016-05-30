package com.pdt.android_gis.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String BASE_INFO = "create table baseinfo ("
			+ "BaseId text, "
			+ "BaseName text, "
			+ "BaseUnit text, "
			+ "Height integer, "
			+ "ContactName text, "
			+ "ContactTel text, "
			+ "Channel integer, "
			+ "BaseType integer, "
			+ "Longitude real, "
			+ "Latitude real, "
			+ "ParentBase text, "
			+ "CoverRadius integer, "
			+ "CoverVisiable integer, "
			+ "primary key(BaseId) )";
	
	public static final String BASE_TYPE = "create table basetype ("
			+ "BaseType integer, "
			+ "BaseDetail text)";
	
	public static final String COVER_DATA = "create table coverdata ("
			+ "BaseId text, "
			+ "Longitude real, "
			+ "Latitude real, "
			+ "Rss real, "
			+ "RssUI integer, "
			+ "RssDI integer, "
			+ "DoMain text, "
			+ "RssDate text)";
	
	private static Context mContext;
	
	/**
	 * 定义构造函数
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	
	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version){
		
		super(context, name, factory, version);
		mContext = context;
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(COVER_DATA);
		db.execSQL(BASE_INFO);
		readdata(db);
		Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_LONG).show();

	}

	private static void readdata(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		ContentValues values1 = new ContentValues();
		//导入测试数据
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","103.814855");
		values.put("Latitude","30.679879");
		values.put("Rss",-54);
		values.put("RssUI",-54);
		values.put("RssDI",-54);
		values.put("DoMain","49620708");
		values.put("RssDate","2015-04-29 15:58:47");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","103.854855");
		values.put("Latitude","30.679879");
		values.put("Rss",-65);
		values.put("RssUI",-65);
		values.put("RssDI",-65);
		values.put("DoMain","49620700");
		values.put("RssDate","2015-04-29 15:58:48");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","103.884855");
		values.put("Latitude","30.679879");
		values.put("Rss",-76);
		values.put("RssUI",-59);
		values.put("RssDI",-65);
		values.put("DoMain","49620707");
		values.put("RssDate","2015-04-29 15:58:46");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","103.924855");
		values.put("Latitude","30.679879");
		values.put("Rss",-85);
		values.put("RssUI",-65);
		values.put("RssDI",-65);
		values.put("DoMain","49620700");
		values.put("RssDate","2015-04-29 15:58:48");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","103.964855");
		values.put("Latitude","30.679879");
		values.put("Rss",-95);
		values.put("RssUI",-65);
		values.put("RssDI",-65);
		values.put("DoMain","49620700");
		values.put("RssDate","2015-04-29 15:58:48");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","103.994855");
		values.put("Latitude","30.679879");
		values.put("Rss",-105);
		values.put("RssUI",-65);
		values.put("RssDI",-65);
		values.put("DoMain","49620700");
		values.put("RssDate","2015-04-29 15:58:48");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","104.064855");
		values.put("Latitude","30.679879");
		values.put("Rss",-115);
		values.put("RssUI",-65);
		values.put("RssDI",-65);
		values.put("DoMain","49620700");
		values.put("RssDate","2015-04-29 15:58:48");
		db.insert("coverdata", null, values);
		
		values.put("BaseId","r65.pdt.cn");
		values.put("Longitude","104.134855");
		values.put("Latitude","30.679879");
		values.put("Rss",-125);
		values.put("RssUI",-65);
		values.put("RssDI",-65);
		values.put("DoMain","49620700");
		values.put("RssDate","2015-04-29 15:58:48");
		db.insert("coverdata", null, values);
		
		
		values1.put("BaseId","r66.pdt.cn");
		values1.put("Longitude","104.024855"); 
		values1.put("Latitude","30.649879");
		values1.put("BaseName","成都中心基站");
		db.insert("baseinfo", null, values1);
		
		values1.put("BaseId","r56.pdt.cn");
		values1.put("Longitude","103.984855"); 
		values1.put("Latitude","30.619879");
		values1.put("BaseName","武侯区基站");
		db.insert("baseinfo", null, values1);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
//		db.execSQL("drop table if exists Book");
//		db.execSQL("drop table if exists Category");
		onCreate(db);

	}

}
