package com.websoftex.addressbook;


import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;

import com.websoftex.model.Contact;
import com.websoftex.sqlite.DatabaseManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class NewContactActivity extends Activity {


	protected static final int DATE_DIALOG_ID = 3;
	protected static final int ACTION_REQUEST_GALLERY = 0;
	protected static final int ACTION_REQUEST_CAMERA = 1;
	private EditText editTextfirstName;
	private EditText editTextLastName;
	private EditText editTextMobileNo;
	private EditText editTextSecondaryMobNo;
	private EditText editTextEmailId;
	private EditText editTextBirtDate;
	private EditText editTextAddress;
	private Button buttonSave;
	private Button buttonCancel;
	private int day;
	private AlertDialog alertDialog;

	public static boolean flag = false;


	public static Timer eventPoller;
	private int yr;
	private int mon;
	private ImageView imageView;
	private Object selectedImagePath;
	ByteArrayOutputStream baos = new ByteArrayOutputStream();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contact);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		imageView = (ImageView) findViewById(R.id.imageViewForProfilePicture);

		editTextfirstName = (EditText) findViewById(R.id.editTextFirstName);
		editTextLastName = (EditText) findViewById(R.id.editTextLastName);
		editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNumber);
		editTextSecondaryMobNo = (EditText) findViewById(R.id.editTextSecondaryNumber);

		editTextEmailId = (EditText) findViewById(R.id.editTextEmailId);
		editTextBirtDate = (EditText) findViewById(R.id.editTextBirthDate);
		editTextAddress = (EditText) findViewById(R.id.editTextAddress);
		alertDialog = new AlertDialog.Builder(this).create();

		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);

		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				// insert into database....

				if (validateFields()) {




					insertToDB();
					//flag = true;

					Intent intent = new Intent(NewContactActivity.this, AddressListActivityWithExpList.class);
					startActivity(intent);
					finish();
				}

			}
		});

		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NewContactActivity.this, AddressListActivityWithExpList.class);
				startActivity(intent);
				finish();


			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch(item.getItemId()){

		default:

			return false;

		}

	}

	public void onProfilePic(View view) {


		AlertDialog.Builder builder = new AlertDialog.Builder(NewContactActivity.this);
		builder.setTitle("Choose Image Source");
		builder.setItems(new CharSequence[] {"Gallery", "Camera"}, 
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:

					// GET IMAGE FROM THE GALLERY
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");

					Intent chooser = Intent.createChooser(intent, "Choose a Picture");
					startActivityForResult(chooser, ACTION_REQUEST_GALLERY);

					break;

				case 1:

					Intent intentForImageFromCamera = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivityForResult(intentForImageFromCamera, ACTION_REQUEST_CAMERA);
					break;

				default:
					break;
				}
			}
		});


		builder.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			switch (requestCode) {
			case ACTION_REQUEST_GALLERY:

				if (data != null) {


					Uri selectedImageUri = data.getData();
					selectedImagePath = getPath(selectedImageUri);
					System.out.println("Image Path : " + selectedImagePath);

					//Log.d("gallery ---- > ", "" + data.getExtras().get("data"));

					imageView.setImageURI(selectedImageUri);
					imageView.setMaxHeight(200);
					imageView.setMaxWidth(200);

					imageView.setDrawingCacheEnabled(true);
					Bitmap scaledBitmap = imageView.getDrawingCache();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					scaledBitmap .compress(Bitmap.CompressFormat.PNG, 100, baos);
					//bMap is the bitmap object
					byte[] b = baos.toByteArray(); 

					Log.e("Byte Array:", b.toString());


					SharedPreferences sPrefs=PreferenceManager.getDefaultSharedPreferences(NewContactActivity.this);
					SharedPreferences.Editor sEdit=sPrefs.edit();
					sEdit.putString("myByteArray", Arrays.toString(b));
					sEdit.commit();     
				}

				break;

			case ACTION_REQUEST_CAMERA:


				if (data != null) {


					Log.d("camera ---- > ", "" + data.getExtras().get("data"));

					imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));

					imageView.setDrawingCacheEnabled(true);
					Bitmap scaledBitmap = imageView.getDrawingCache();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					scaledBitmap .compress(Bitmap.CompressFormat.PNG, 100, baos);
					//bMap is the bitmap object
					byte[] b = baos.toByteArray(); 

					Log.e("Byte Array:", b.toString());

					SharedPreferences sPrefs=PreferenceManager.getDefaultSharedPreferences(NewContactActivity.this);
					SharedPreferences.Editor sEdit=sPrefs.edit();
					sEdit.putString("myByteArray", Arrays.toString(b));
					sEdit.commit();     

				}

				break;          
			}

		}
	};
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	@SuppressWarnings("deprecation")
	public void showDatePickerDialogFrom(View v)
	{
		Calendar today=Calendar.getInstance();
		yr=today.get(Calendar.YEAR);
		mon=today.get(Calendar.MONTH);
		day=today.get(Calendar.DAY_OF_MONTH);
		showDialog(DATE_DIALOG_ID);
	}

	protected Dialog onCreateDialog(int id)
	{
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,dateSetListener,yr,mon,day);

		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			yr=year;
			mon=monthOfYear+1;
			day=dayOfMonth;
			//EditText it=(EditText)findViewById(R.id.editText1);
			editTextBirtDate.setText(day+"/"+mon+"/"+yr);
		}
	};

	private byte[] array;
	private String[] split;

	private void insertToDB() {

		Contact contact = new Contact();

		if(imageView.getDrawable() != null) {
			SharedPreferences sPrefs=PreferenceManager.getDefaultSharedPreferences(NewContactActivity.this);
			String stringArray = sPrefs.getString("myByteArray", null);

			if (stringArray != null) {
				split = stringArray.substring(1, stringArray.length()-1).split(", ");
				array = new byte[split.length];
				for (int i = 0; i < split.length; i++) {
					array[i] = Byte.parseByte(split[i]);
				}
			}

			Log.e("Array To Save to DB:",array.toString());
			contact.setFirstName(editTextfirstName.getText().toString());
			contact.setLastName(editTextLastName.getText().toString());
			contact.setMobileNo(editTextMobileNo.getText().toString());
			contact.setSecondaryMobNo(editTextSecondaryMobNo.getText().toString());
			contact.setEmailId(editTextEmailId.getText().toString());
			contact.setBirthDate(editTextBirtDate.getText().toString());
			contact.setAddress(editTextAddress.getText().toString());
			contact.setImageForProfile(array);



			DatabaseManager db = new DatabaseManager(NewContactActivity.this);
			db.insertContactDetails(contact);
		} else {

			contact.setFirstName(editTextfirstName.getText().toString());
			contact.setLastName(editTextLastName.getText().toString());
			contact.setMobileNo(editTextMobileNo.getText().toString());
			contact.setSecondaryMobNo(editTextSecondaryMobNo.getText().toString());
			contact.setEmailId(editTextEmailId.getText().toString());
			contact.setBirthDate(editTextBirtDate.getText().toString());
			contact.setAddress(editTextAddress.getText().toString());

			imageView.setImageDrawable(getResources().getDrawable(R.drawable.dummy_avatar));
			imageView.setDrawingCacheEnabled(true);
			Bitmap scaledBitmap = imageView.getDrawingCache();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			scaledBitmap .compress(Bitmap.CompressFormat.PNG, 100, baos);
			//bMap is the bitmap object
			byte[] b = baos.toByteArray(); 

			Log.e("Byte Array:", b.toString());

			contact.setImageForProfile(b);


			DatabaseManager db = new DatabaseManager(NewContactActivity.this);
			db.insertContactDetails(contact);


		}
		//flag = false;
	}

	// validate fields

	public boolean validateFields() {

		//validation for Mobile ID

		if (editTextfirstName.getText().toString().equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("Contact")
			.setMessage("Please Enter Your First Name !!")
			.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int whichButton) {

					/* User clicked OK so do some stuff */
				}
			}).create();
			alertDialog.show();
			return false;
		}

		if (editTextLastName.getText().toString().equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("Contact")
			.setMessage("Please Enter Your Last Name !!")
			.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int whichButton) {

					/* User clicked OK so do some stuff */
				}
			}).create();
			alertDialog.show();
			return false;
		}
		if (editTextMobileNo.getText().toString().equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("Contact")
			.setMessage("Please Enter Your Mobile Number !!")
			.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int whichButton) {

					/* User clicked OK so do some stuff */
				}
			}).create();
			alertDialog.show();
			return false;
		}

		if (editTextBirtDate.getText().toString().equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("Contact")
			.setMessage("Please Enter Your Birth Date !!")
			.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int whichButton) {

					/* User clicked OK so do some stuff */
				}
			}).create();
			alertDialog.show();
			return false;
		}

		if (editTextEmailId.getText().toString().equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("Contact")
			.setMessage("Please Enter Your Email Id !!")
			.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int whichButton) {

					/* User clicked OK so do some stuff */
				}
			}).create();
			alertDialog.show();
			return false;
		}

		if (editTextAddress.getText().toString().equalsIgnoreCase("")) {
			alertDialog = new AlertDialog.Builder(this)
			.setTitle("Contact")
			.setMessage("Please Enter Your Address !!")
			.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,
						int whichButton) {

					/* User clicked OK so do some stuff */
				}
			}).create();
			alertDialog.show();
			return false;
		}

		return true;
	}

}
