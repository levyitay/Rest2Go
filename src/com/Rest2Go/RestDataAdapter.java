package com.Rest2Go;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RestDataAdapter extends ArrayAdapter<RestData> {

	private ArrayList<RestData> restData;
	int resource;

	public RestDataAdapter(Context context, int textViewResourceId, ArrayList<RestData> items) {
		super(context, textViewResourceId, items);

		this.restData = items;
		this.resource = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LinearLayout restDataView;

		RestData rd = restData.get(position);
		if (convertView == null) {
			restDataView = new LinearLayout(getContext());
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi.inflate(resource, restDataView, true);
		} else {
			restDataView = (LinearLayout) convertView;
		}
		// Get the text boxes from the listitem.xml file
		TextView restName = (TextView) restDataView.findViewById(R.id.txtRestName);
		TextView restAddress = (TextView) restDataView.findViewById(R.id.txtRestAddress);
		TextView restPhone = (TextView) restDataView.findViewById(R.id.txtRestPhone);
		TextView restPhone2 = (TextView) restDataView.findViewById(R.id.txtRestPhone2);
		ImageView restIcon = (ImageView) restDataView.findViewById(R.id.icon);

		// Assign the appropriate data from our alert object above
		restName.setText(rd.mName);
		restAddress.setText(rd.mAddress);
		restPhone.setText(rd.mPhone);
		if (!TextUtils.isEmpty(rd.mPhone2)) {
			restPhone2.setText(rd.mPhone2);
		}else{
			restPhone2.setVisibility(View.GONE);
		}
		restIcon.setImageDrawable(rd.mIconDraw);
		restDataView.setTag(resource, rd.mId);

		return restDataView;
	}

}
