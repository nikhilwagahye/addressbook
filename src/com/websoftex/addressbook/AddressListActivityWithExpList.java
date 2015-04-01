package com.websoftex.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.websoftex.model.Contact;
import com.websoftex.sqlite.DatabaseManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnActionExpandListener;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class AddressListActivityWithExpList extends Activity {

	List<Contact> groupListOfContacts;
	Map<String, List<String>> messagesStoredGroup;
	ExpandableListView expListView;
	private Contact contact;
	private String q1;
	private String q2;
	private int idForDelete;
	private int idForOtherActivity;
	private List<Contact> detailListForId;
	private AlertDialog alertDialog;
	private List<Contact> detailListForMobileNo;
	private String mobNo;


	public static Timer eventPoller;

	private EditText editTextForSearch;
	private List<Contact> searchedList;
	private String fName;
	private String lName;
	private String mobileNo;
	private String SecondaryNo;
	private String emailId;
	private String birthDate;
	private String address;
	private SearchView user_search;
	private String global_variable;
	private List<Contact> detailListForEmailId;
	private String emailIdData;


	@SuppressLint("NewApi")
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_book_list);

		expListView = (ExpandableListView) findViewById(R.id.contacts_list);

		alertDialog = new AlertDialog.Builder(this).create();

		//		// ------------------------------ code added ---------------------
		//
		//		groupListOfContacts = new ArrayList<Contact>();
		//



		//		DatabaseManager db = new DatabaseManager(AddressListActivityWithExpList.this);
		//
		//		groupListOfContacts = db.getAllContactDetails();
		//
		//		if(groupListOfContacts == null && groupListOfContacts.size() == 0) {
		//
		//			alertDialog = new AlertDialog.Builder(AddressListActivityWithExpList.this)
		//			.setTitle("Welcome")
		//			.setMessage("Your address book is empty..")
		//			.setPositiveButton("Ok",
		//					new DialogInterface.OnClickListener() {
		//				@Override
		//				public void onClick(DialogInterface dialog,
		//						int whichButton) {
		//					finish();
		//					/* User clicked OK so do some stuff */
		//				}
		//			}).setNegativeButton("Add", new DialogInterface.OnClickListener() {
		//				@Override
		//				public void onClick(DialogInterface dialog,
		//						int whichButton) {
		//					Intent intent = new Intent(AddressListActivityWithExpList.this, NewContactActivity.class);
		//					startActivity(intent);
		//				}
		//			}).create();
		//
		//			alertDialog.show();	
		//
		//		}
		//
		//		// ------------------------------ code added ---------------------

		createGroupList();

		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View view,
					int groupPosition, long id) {

				//view.setBackgroundColor(Color.parseColor("#ced5de"));
				contact = groupListOfContacts.get(groupPosition);
				q1 = contact.getFirstName();
				q2 = contact.getLastName();

				idForOtherActivity = contact.getId();

				Log.e("fName : , SName: ",q1 + q2 );

				return false;
			}
		});
		// spinner


		//
	}

	private void updateUI123(List<Contact> searchedList) {

		ExpandableListAdapter expListAdapter = new AddressListAdapter(
				this, searchedList);
		expListView.setAdapter(expListAdapter);

	}

	public void search(View view) {

		String textToSearch = editTextForSearch.getText().toString();

		DatabaseManager db = new DatabaseManager(AddressListActivityWithExpList.this);

		searchedList = db.getAllDataAsPerSerachEntry(textToSearch);

		for (Contact contact : searchedList) {

			fName = contact.getFirstName();
			lName = contact.getLastName();
			mobileNo = contact.getMobileNo();
			SecondaryNo = contact.getSecondaryMobNo();
			emailId = contact.getEmailId();
			birthDate = contact.getBirthDate();
			address = contact.getAddress();

		}

		Log.e("searched Data:", emailId.toString());

		// update UI for with same list
		ExpandableListAdapter expListAdapter = new AddressListAdapter(
				this, searchedList);
		expListView.setAdapter(expListAdapter);


	}

	@Override
	protected void onResume() {

		// 
		DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);
		//dbManager.open();
		groupListOfContacts = dbManager.getAllContactDetails();

		if (groupListOfContacts!=null && groupListOfContacts.size()>0) {

			updateUI();

		} else {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					AddressListActivityWithExpList.this);

			// set title
			alertDialogBuilder.setTitle("Contacts");

			// set dialog message
			alertDialogBuilder
			.setMessage("No Contacts configured yet...")
			.setCancelable(false)
			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {


				public void onClick(DialogInterface dialog,int id) {

					finish();
				}
			}).setNegativeButton("Create New Contact",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing

					Intent intent = new Intent(AddressListActivityWithExpList.this, NewContactActivity.class);
					startActivity(intent);

				}
			});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			Log.e("-----","-----");

		}

		//
		super.onResume();
	}

	private void updateUI() {

		DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);
		//dbManager.open();
		groupListOfContacts = dbManager.getAllContactDetails();

		ExpandableListAdapter expListAdapter = new AddressListAdapter(
				this, groupListOfContacts);
		expListView.setAdapter(expListAdapter);

	}

	private void createGroupList() {
		groupListOfContacts = new ArrayList<Contact>();

		DatabaseManager db = new DatabaseManager(AddressListActivityWithExpList.this);

		groupListOfContacts = db.getAllContactDetails();

	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public void deleteContact(View view) {

		//Code to delete message
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				AddressListActivityWithExpList.this);

		// set title
		alertDialogBuilder.setTitle("Delete");

		// set dialog message
		alertDialogBuilder
		.setMessage("Are you sure you want to delete contact?")
		.setCancelable(false)
		.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

			private List<Contact> detailListForId;

			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, delete message


				DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);
				//dbManager.open();

				detailListForId = dbManager.getIdForDelete(q1,q2);

				for (Contact contact : detailListForId) {

					idForDelete = contact.getId();
				}

				dbManager.deleteInboxMessage(idForDelete);

				//dbManager.close();
				updateUI();

			}
		}).setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	// update


	public void updateContact(View view) {   

		DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);

		detailListForId = dbManager.getIdForDelete(q1,q2);

		for (Contact contact : detailListForId) {
			idForDelete = contact.getId();
		}

		Intent myIntent = new Intent(AddressListActivityWithExpList.this, UpdateContactActivity.class);

		UpdateContactActivity.flagForUpdate = false;

		myIntent.putExtra("intVariableName",idForDelete);
		startActivity(myIntent);

	}

	public void call(View view) {

		DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);

		detailListForMobileNo = dbManager.getMobNo(q1,q2);

		for (Contact contact : detailListForMobileNo) {
			mobNo = contact.getMobileNo();
		}

		Intent dial = new Intent();
		String no = mobNo;
		dial.setAction("android.intent.action.DIAL");
		dial.setData(Uri.parse("tel:"+ no));
		startActivity(dial); 

	}

	public void messasge(View view) {

		DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);

		detailListForMobileNo = dbManager.getMobNo(q1,q2);

		for (Contact contact : detailListForMobileNo) {
			mobNo = contact.getMobileNo();
		}

		// saved mobile number will come here for particular contact...

		String number = mobNo;  // The number on which you want to send SMS  
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null))); 


	}


	public void mail(View view) {

		DatabaseManager dbManager = new DatabaseManager(AddressListActivityWithExpList.this);

		detailListForEmailId = dbManager.getEmailId(q1,q2);

		for (Contact contact : detailListForEmailId) {
			emailIdData = contact.getEmailId();
		}

		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.setType("message/rfc822");

		String array[] = {emailIdData};
		emailIntent.putExtra(Intent.EXTRA_EMAIL, array);
		emailIntent.putExtra(Intent.EXTRA_CC, array);
		//					emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
		//					emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");


		startActivity(Intent.createChooser(emailIntent, "Send mail..."));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.add_new_contact, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()) {

		//		case R.id.itemToAddNewAddress:
		//			Intent intentForNewContact = new Intent(AddressListActivityWithExpList.this, NewContactActivity.class);
		//			startActivity(intentForNewContact);
		//			return true;
		//


		case R.id.search_user:

			//Keep a global variable of this so you can set it within the next listener
			user_search = (SearchView) item.getActionView();

			user_search.setOnQueryTextListener(new OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					global_variable = query;
					return true;
				}

				@Override
				public boolean onQueryTextChange(String text) {

					Log.e("Text to search:", text);

					try {

						// to take search data as per text type in search box...
						DatabaseManager db = new DatabaseManager(AddressListActivityWithExpList.this);

						searchedList = db.getAllDataAsPerSerachEntry(text);

						for (Contact contact : searchedList) {

							fName = contact.getFirstName();
							lName = contact.getLastName();
							mobileNo = contact.getMobileNo();
							SecondaryNo = contact.getSecondaryMobNo();
							emailId = contact.getEmailId();
							birthDate = contact.getBirthDate();
							address = contact.getAddress();

						}

						Log.e("searched Data:", emailId.toString());

						// update UI for with same list

						updateUI123(searchedList);
					} catch (Exception ex) {

						Toast.makeText(AddressListActivityWithExpList.this, "problem in search!! please retry..", Toast.LENGTH_SHORT).show();

					}

					return true;
				}
			});

			item.setOnActionExpandListener(new OnActionExpandListener() {

				@Override
				public boolean onMenuItemActionCollapse(MenuItem item) {
					// Do something when collapsed
					return true;       // Return true to collapse action view
				}

				@Override
				public boolean onMenuItemActionExpand(MenuItem item) {
					user_search.setQuery(global_variable, false);
					return true;      // Return true to expand action view
				}
			});


			return true;



		case R.id.item_add:


			Intent intent = new Intent(AddressListActivityWithExpList.this, NewContactActivity.class);
			startActivity(intent);
			finish();


			return true;
		default:

			return false;

		}

	}


}

