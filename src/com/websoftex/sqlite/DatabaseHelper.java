package com.websoftex.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


	private static final String TEXT_TYPE = " TEXT";
	//	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ", ";
	private static final String BLOB_TYPE = " BLOB";


	private static final  String SQL_CREATE_CONTACT_TABLE =
			"CREATE TABLE " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + " (" +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_MOBILE_NO + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_SECONDARY_NO + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_EMAIL_ID + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_BIRTH_DATE + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
					DatabaseContract.SchoolDatabaseEntry.COLUMN_IMAGE_PROFILE + BLOB_TYPE +

					" )";

	private static final String SQL_DELETE_CONTACT_TABLE  =
			"DROP TABLE IF EXISTS " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS ;	




	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "contacts.db";
	public DatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_CONTACT_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_CONTACT_TABLE);


		onCreate(db);		
	}


}
