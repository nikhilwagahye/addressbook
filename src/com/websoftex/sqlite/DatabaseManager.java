package com.websoftex.sqlite;
import java.util.ArrayList;
import java.util.List;

import com.websoftex.model.Contact;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseManager {
	// Database fields
	private Context context = null;
	private SQLiteDatabase database=null;
	private DatabaseHelper dbHelper=null;

	public DatabaseManager(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		if (dbHelper==null) {
			dbHelper = new DatabaseHelper(context);
		}
		if (database==null) {
			database = dbHelper.getWritableDatabase();
		}
	}

	public void close() {

		dbHelper.close();
		database = null;
		dbHelper = null;       
	}


	//Inserting Data From Settings UI to DB : Methods - start
	public synchronized long insertContactDetails(Contact contact) {


		// Insert contacts details from New Contact Page 

		long result = 0;

		try{

			if (database==null) {
				open();
			}
			ContentValues values = new ContentValues();
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME, contact.getFirstName());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME, contact.getLastName());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_MOBILE_NO, contact.getMobileNo());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_SECONDARY_NO, contact.getSecondaryMobNo());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_EMAIL_ID, contact.getEmailId());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_BIRTH_DATE, contact.getBirthDate());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_ADDRESS, contact.getAddress());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_IMAGE_PROFILE, contact.getImageForProfile());

			result = database.insert(DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS, null, values);   

			close();
		} catch(Exception ex){

		}

		return result;
	}


	// Retrieve all the contacts details from Database and show on List Views 

	public synchronized List<Contact> getAllContactDetails() {
		if (database==null) {
			open();
		}    

		
		List<Contact> detailsList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setFirstName(cursor.getString(1));
				contact.setLastName(cursor.getString(2));
				contact.setMobileNo(cursor.getString(3));
				contact.setSecondaryMobNo(cursor.getString(4));
				contact.setEmailId(cursor.getString(5));
				contact.setBirthDate(cursor.getString(6));
				contact.setAddress(cursor.getString(7));
				contact.setImageForProfile(cursor.getBlob(8));

				detailsList.add(contact);
			} while (cursor.moveToNext());
		}

		close();
		return detailsList;
	}


	// to get all data as per fName and lName for perticular position..

	public synchronized List<Contact> getIdForDelete(String fName, String lName) {
		if (database==null) {
			open();
		}    

		List<Contact> detailsList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery  = "SELECT * FROM " + DatabaseContract.
				SchoolDatabaseEntry.TABLE_NAME_CONTACTS +  " WHERE " +
				DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME + " = " + "'" + fName + "'" + " AND " +
				DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME + " = " + "'" + lName + "'";

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setId(Integer.parseInt(cursor.getString(0)));


				detailsList.add(contact);
			} while (cursor.moveToNext());
		}

		close();
		return detailsList;
	}


	public synchronized List<Contact> getMobNo(String fName, String lName) {
		if (database==null) {
			open();
		}    

		List<Contact> detailsList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery  = "SELECT * FROM " + DatabaseContract.
				SchoolDatabaseEntry.TABLE_NAME_CONTACTS +  " WHERE " +
				DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME + " = " + "'" + fName + "'" + " AND " +
				DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME + " = " + "'" + lName + "'";

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setMobileNo(cursor.getString(3));

				detailsList.add(contact);
			} while (cursor.moveToNext());
		}

		close();
		return detailsList;
	}


	public synchronized long updateContactDetails(Contact contact) {

		long result = 0;

		try{

			if (database==null) {
				open();
			}

			ContentValues values = new ContentValues();
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME, contact.getFirstName());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME, contact.getLastName());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_MOBILE_NO, contact.getMobileNo());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_SECONDARY_NO, contact.getSecondaryMobNo());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_EMAIL_ID, contact.getEmailId());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_BIRTH_DATE, contact.getBirthDate());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_ADDRESS, contact.getAddress());
			values.put(DatabaseContract.SchoolDatabaseEntry.COLUMN_IMAGE_PROFILE, contact.getImageForProfile());

			String whereClause = DatabaseContract.SchoolDatabaseEntry.COLUMN_CONTACT_ID + " =  '" +contact.getId()+"'";
			result = database.update(DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS, values, whereClause, null);

			close();
		}catch(Exception ex){

		}
		return result;
	}

	public synchronized void deleteInboxMessage(int id) {
		try {

			if (database==null) {
				open();
			}

			database.delete(DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS,
					DatabaseContract.SchoolDatabaseEntry.COLUMN_CONTACT_ID + " = " + id, null);
			close();
		} catch(Exception ex){

		}
	}

	public synchronized List<Contact> getAllContactDetailsForPerticularId(int id) {
		if (database==null) {
			open();
		}    

		List<Contact> detailsList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS
				+ " WHERE " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." + DatabaseContract.SchoolDatabaseEntry.COLUMN_CONTACT_ID
				+ " = " + "'" + id + "'" ;

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setFirstName(cursor.getString(1));
				contact.setLastName(cursor.getString(2));
				contact.setMobileNo(cursor.getString(3));
				contact.setSecondaryMobNo(cursor.getString(4));
				contact.setEmailId(cursor.getString(5));
				contact.setBirthDate(cursor.getString(6));
				contact.setAddress(cursor.getString(7));
				contact.setImageForProfile(cursor.getBlob(8));

				detailsList.add(contact);
			} while (cursor.moveToNext());
		}

		close();
		return detailsList;
	}

	public synchronized List<Contact> getAllDataAsPerSerachEntry(String textToSearch) {
		if (database==null) {
			open();
		}    

		List<Contact> detailsList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS
				+ " WHERE " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME +  " LIKE " + "'%" + textToSearch + "%'" 
				+ " OR " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME +  " LIKE " + "'%" + textToSearch + "%'"
				+ " OR " + DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_MOBILE_NO +  " LIKE " + "'%" + textToSearch + "%'";


		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setFirstName(cursor.getString(1));
				contact.setLastName(cursor.getString(2));
				contact.setMobileNo(cursor.getString(3));
				contact.setSecondaryMobNo(cursor.getString(4));
				contact.setEmailId(cursor.getString(5));
				contact.setBirthDate(cursor.getString(6));
				contact.setAddress(cursor.getString(7));
				contact.setImageForProfile(cursor.getBlob(8));

				detailsList.add(contact);
			} while (cursor.moveToNext());
		}

		close();
		return detailsList;
	}

	public synchronized List<Contact> getEmailId(String fName, String lName) {
		if (database==null) {
			open();
		}    

		List<Contact> detailsList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery  = "SELECT * FROM " + DatabaseContract.
				SchoolDatabaseEntry.TABLE_NAME_CONTACTS +  " WHERE " +
				DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_FIRST_NAME + " = " + "'" + fName + "'" + " AND " +
				DatabaseContract.SchoolDatabaseEntry.TABLE_NAME_CONTACTS + "." +
				DatabaseContract.SchoolDatabaseEntry.COLUMN_LAST_NAME + " = " + "'" + lName + "'";

		Cursor cursor = database.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setEmailId(cursor.getString(5));

				detailsList.add(contact);
			} while (cursor.moveToNext());
		}

		close();
		return detailsList;
	}

}