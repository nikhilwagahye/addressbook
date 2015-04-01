package com.websoftex.sqlite;

import android.provider.BaseColumns;

public class DatabaseContract {
	private DatabaseContract(){}

	/*	public static abstract class SettingsEntry implements BaseColumns {
	    public static final String TABLE_NAME = "settings";
	    public static final String COLUMN_NAME_KEY = "key";
	    public static final String COLUMN_NAME_VALUE = "value";
	}*/

	public static abstract class SchoolDatabaseEntry implements BaseColumns {
		public static final String TABLE_NAME_CONTACTS = "contacts";

		//Column names
		public static final String COLUMN_CONTACT_ID = "contactid";//pk

		public static final String COLUMN_FIRST_NAME = "firstname";
		public static final String COLUMN_LAST_NAME = "lastname";
		public static final String COLUMN_MOBILE_NO = "mobileno";
		public static final String COLUMN_SECONDARY_NO = "seconadryno";
		public static final String COLUMN_EMAIL_ID = "emailid";
		public static final String COLUMN_BIRTH_DATE = "birthdate";
		public static final String COLUMN_ADDRESS = "address";
		public static final String COLUMN_IMAGE_PROFILE = "image";


	}
}
