package com.websoftex.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerAdapter extends BaseAdapter {
	private Context mContext;

	public DrawerAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;             
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {

		View myView = convertView;
		ImageView imageView;
		if (convertView == null) {
			// if it's not recycled, initialize some attributes
			//Inflate the layout

			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			myView = inflater.inflate(R.layout.drawer_list_adapter, null);

			// Add The Image!!!           
			ImageView iv = (ImageView)myView.findViewById(R.id.imageForIconOfDrawerList);
			iv.setImageResource(mThumbIds[position]);

			// Add The Text!!!
			TextView tv = (TextView)myView.findViewById(R.id.textViewForGrawerList);
			tv.setText(names[position]);

		}

		return myView;
	}

	// references to our images
	private Integer[] mThumbIds = {
			R.drawable.view, R.drawable.add,
			//	R.drawable.ic_launcher
	};

	private String[] names={"VIEW CONTACTS","ADD NEW"};
	//,"UPDATE CONTACT"};


}
