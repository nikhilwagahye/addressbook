//package com.websoftex.waytohomestay;
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import com.websoftex.model.Customer;
//import com.websoftex.sqlite.DatabaseManager;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.os.StrictMode.ThreadPolicy;
//import android.support.v4.app.NavUtils;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.MenuItem.OnActionExpandListener;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//import android.widget.ExpandableListAdapter;
//import android.widget.ExpandableListView;
//import android.widget.LinearLayout;
//import android.widget.SearchView;
//import android.widget.SearchView.OnQueryTextListener;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class CustomerDetailsListActivity extends Activity {
//
//	List<Customer> groupListOfCustomer;
//	Map<String, List<String>> messagesStoredGroup;
//	ExpandableListView expListView;
//	private AlertDialog alertDialog;
//	private TextView textViewToShowMesg;
//	private EditText editTextForSearch;
//	private LinearLayout linearLayout;
//	private SearchView user_search;
//	private String global_variable;
//
//	@SuppressLint("NewApi")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_customer_list);
//
//		textViewToShowMesg = (TextView) findViewById(R.id.textViewForNoCustomerData);
//
//		editTextForSearch = (EditText) findViewById(R.id.editTextForSearch);
//
//		expListView = (ExpandableListView) findViewById(R.id.customer_list);
//
//		alertDialog = new AlertDialog.Builder(this).create();
//
//		createGroupList();
//
//
//		//		editTextForSearch.addTextChangedListener(new TextWatcher() {
//		//
//		//			private List<Customer> searchedList;
//		//			private String name;
//		//			private String mobileNo;
//		//			private String emailId;
//		//			private String chkInDate;
//		//			private String bookingDate;
//		//			private String chkOutDate;
//		//			@Override
//		//			public void onTextChanged(CharSequence s, int start, int before,
//		//					int count) {
//		//				// TODO Auto-generated method stub
//		//			}
//		//			@Override
//		//			public void beforeTextChanged(CharSequence s, int start, int count,
//		//					int after) {
//		//				// TODO Auto-generated method stub
//		//			}
//		//			@Override
//		//			public void afterTextChanged(Editable s) {
//		//				// TODO Auto-generated method stub
//		//
//		//				try {
//		//
//		//					String textToSearch = editTextForSearch.getText().toString();
//		//
//		//					DatabaseManager db = new DatabaseManager(CustomerDetailsListActivity.this);
//		//
//		//					searchedList = db.getAllDataAsPerSerachEntry(textToSearch);
//		//
//		//					for (Customer customer : searchedList) {
//		//
//		//						name = customer.getCustomerName();
//		//						mobileNo = customer.getMobileNo();
//		//						emailId = customer.getEmailId();
//		//						chkInDate = customer.getCheckInDate();
//		//						chkOutDate = customer.getCheckOutDate();
//		//						bookingDate = customer.getBookingDate();
//		//
//		//					}
//		//
//		//					Log.e("searched Data:", emailId.toString());
//		//
//		//					// update UI for with same list
//		//
//		//					updateUI123(searchedList);
//		//
//		//				} catch (Exception ex) {
//		//
//		//					Toast.makeText(CustomerDetailsListActivity.this, "problem in search!! please retry..", Toast.LENGTH_SHORT).show();
//		//
//		//				}
//		//			} 
//		//
//		//		});
//
//
//		// search
//
//
//
//
//
//
//		//
//	}
//
//	private void updateUI123(List<Customer> searchedList) {
//
//		ExpandableListAdapter expListAdapter = new CustomerListAdapter(
//				this, searchedList);
//		expListView.setAdapter(expListAdapter);
//
//	}
//
//
//	@Override
//	protected void onResume() {
//		//
//
//
//		DatabaseManager dbManager = new DatabaseManager(CustomerDetailsListActivity.this);
//		//dbManager.open();
//		groupListOfCustomer= dbManager.getCustomerDetails();
//
//		if (groupListOfCustomer!=null && groupListOfCustomer.size() > 0) {
//
//			textViewToShowMesg.setVisibility(View.GONE);
//			expListView.setVisibility(View.VISIBLE);
//			editTextForSearch.setVisibility(View.VISIBLE);
//			updateUI();
//
//		} else {
//
//			//			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//			//					CustomerDetailsListActivity.this);
//			//
//			//			// set title
//			//			alertDialogBuilder.setTitle("Customer Details");
//			//
//			//			// set dialog message
//			//			alertDialogBuilder
//			//			.setMessage("No Customer found... Please Login!")
//			//			.setCancelable(false)
//			//			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
//			//
//			//
//			//				public void onClick(DialogInterface dialog,int id) {
//			//
//			//					finish();
//			//				}
//			//			}).setNegativeButton("Login",new DialogInterface.OnClickListener() {
//			//
//			//
//			//				public void onClick(DialogInterface dialog,int id) {
//			//
//			//					
//			//					Intent intent = new Intent(CustomerDetailsListActivity.this, LoginActivity.class);
//			//					startActivity(intent);
//			//
//			//					finish();
//			//				}
//			//			});
//			//
//			//			// create alert dialog
//			//			AlertDialog alertDialog = alertDialogBuilder.create();
//			//
//			//			// show it
//			//			alertDialog.show();
//			//			Log.e("-----","-----");
//
//
//
//			textViewToShowMesg.setVisibility(View.VISIBLE);
//			expListView.setVisibility(View.GONE);
//			editTextForSearch.setVisibility(View.GONE);
//
//		}
//
//		//
//		super.onResume();
//
//
//
//	}
//
//	private void updateUI() {
//
//		DatabaseManager dbManager = new DatabaseManager(CustomerDetailsListActivity.this);
//		//dbManager.open();
//		groupListOfCustomer = dbManager.getCustomerDetails();
//
//		ExpandableListAdapter expListAdapter = new CustomerListAdapter(
//				this, groupListOfCustomer);
//		expListView.setAdapter(expListAdapter);
//
//	}
//
//	private void createGroupList() {
//		groupListOfCustomer = new ArrayList<Customer>();
//
//		DatabaseManager db = new DatabaseManager(CustomerDetailsListActivity.this);
//
//		groupListOfCustomer = db.getCustomerDetails();
//
//	}
//
//	// Convert pixel to dip
//	public int getDipsFromPixel(float pixels) {
//		// Get the screen's density scale
//		final float scale = getResources().getDisplayMetrics().density;
//		// Convert the dps to pixels, based on density scale
//		return (int) (pixels * scale + 0.5f);
//	}
//
//
//	// import button
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater menuInflater = getMenuInflater();
//		menuInflater.inflate(R.menu.menu_settings, menu);
//
//
//
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		
//		
//		switch(item.getItemId()) {
//
//
//		case R.id.item_import:
//
//			Intent intent = new Intent(CustomerDetailsListActivity.this, LoginActivity.class);
//			startActivity(intent);
//			//finish();
//
//
//
//			return true;
//
//		case R.id.item_add:
//
//			Intent intentForAdd = new Intent(CustomerDetailsListActivity.this, AddNewCustomerDetailsActivity.class);
//			startActivity(intentForAdd);
//			//			finish();
//
//
//
//			return true;
//
//		case R.id.search_user:
//
//
//			//Keep a global variable of this so you can set it within the next listener
//			user_search = (SearchView) item.getActionView();
//
//			user_search.setOnQueryTextListener(new OnQueryTextListener() {
//
//
//				@Override
//				public boolean onQueryTextSubmit(String query) {
//					global_variable = query;
//					return true;
//				}
//
//				@Override
//				public boolean onQueryTextChange(String text) {
//
//					List<Customer> searchedList;
//					String name;
//					String mobileNo;
//					String emailId;
//					String chkInDate;
//					String bookingDate;
//					String chkOutDate;
//
//					Log.e("Text to search:", text);
//
//					try {
//
//						DatabaseManager db = new DatabaseManager(CustomerDetailsListActivity.this);
//
//						searchedList = db.getAllDataAsPerSerachEntry(text);
//
//						for (Customer customer : searchedList) {
//
//							name = customer.getCustomerName();
//							mobileNo = customer.getMobileNo();
//							emailId = customer.getEmailId();
//							chkInDate = customer.getCheckInDate();
//							chkOutDate = customer.getCheckOutDate();
//							bookingDate = customer.getBookingDate();
//
//						}
//
//					//	Log.e("searched Data:", emailId.toString());
//
//						// update UI for with same list
//
//						updateUI123(searchedList);
//
//					} catch (Exception ex) {
//
//						Toast.makeText(CustomerDetailsListActivity.this, "problem in search!! please retry..", Toast.LENGTH_SHORT).show();
//
//					}
//
//
//
//					return true;
//				}
//			});
//
//
//			item.setOnActionExpandListener(new OnActionExpandListener() {
//				@Override
//				public boolean onMenuItemActionCollapse(MenuItem item) {
//					// Do something when collapsed
//					return true;       // Return true to collapse action view
//				}
//				@Override
//				public boolean onMenuItemActionExpand(MenuItem item) {
//					user_search.setQuery(global_variable, false);
//					return true;      // Return true to expand action view
//				}
//			});
//
//
//			return true;
//
//
//		default:
//
//
//			return false;
//
//		}
//
//	}
//
//
//}
//
