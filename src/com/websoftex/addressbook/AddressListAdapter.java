package com.websoftex.addressbook;

import java.io.ByteArrayInputStream;
import java.util.List;
import com.websoftex.model.Contact;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddressListAdapter extends BaseExpandableListAdapter {

	private Activity context;
	private List<Contact> contactList;

	private TextView textViewMobileNo;
	private TextView textViewSecondaryNo;
	private TextView textViewEmailId;
	private TextView textViewBirthDate;
	private TextView textViewAddress;
	private TextView textViewFirstName;
	private ImageView imageViewForContact;


	//  public TextView itemForMesg;

	public AddressListAdapter(Activity context, List<Contact> contactList) {
		this.context = context;
		this.contactList = contactList;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return contactList.get(groupPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressLint("InflateParams")
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final Contact contact = (Contact) getChild(groupPosition, childPosition);
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.address_child_item, null);
		}

		try {

			textViewMobileNo = (TextView) convertView.findViewById(R.id.textViewMobileNoToShow);
			textViewSecondaryNo = (TextView) convertView.findViewById(R.id.textViewSecondaryNoToShow);
			textViewEmailId = (TextView) convertView.findViewById(R.id.textViewEmailIdToShow);
			textViewBirthDate = (TextView) convertView.findViewById(R.id.textViewBirthDateToShow);
			textViewAddress = (TextView) convertView.findViewById(R.id.textViewAddressToShow);

			textViewMobileNo.setText(contact.getMobileNo());
			textViewSecondaryNo.setText(contact.getSecondaryMobNo());
			textViewEmailId.setText(contact.getEmailId());
			textViewBirthDate.setText(contact.getBirthDate());
			textViewAddress.setText(contact.getAddress());

		} catch(Exception ex) {
			
			String ExceptionMessage = ex.toString();
			Toast.makeText(context, "Exception in child:"+ExceptionMessage, Toast.LENGTH_LONG).show();
		}
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	public Object getGroup(int groupPosition) {
		return contactList.get(groupPosition);
	}

	public int getGroupCount() {
		return contactList.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		Contact contact = (Contact) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.address_group_item,
					null);
		}

		imageViewForContact = (ImageView) convertView.findViewById(R.id.imageViewForTest);
		textViewFirstName = (TextView) convertView.findViewById(R.id.textViewFirstNameToShow);
		//		textViewLastName = (TextView) convertView.findViewById(R.id.textViewLastNameToShow);
		//		textViewMobileNoOnGroup = (sTextView) convertView.findViewById(R.id.textViewMobileNoToShowInGroup);


		String str = contact.getFirstName() + " " + contact.getLastName() + " " + contact.getMobileNo();
		textViewFirstName.setText(str);

		if (contact.getImageForProfile() != null) {
			ByteArrayInputStream imageStream = new ByteArrayInputStream(contact.getImageForProfile());
			Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			imageViewForContact.setImageBitmap(theImage);
		}


		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		//return true;
		return false;

	}

}