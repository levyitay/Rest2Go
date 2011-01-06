package com.Rest2Go;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RestDataAdapter extends ArrayAdapter<RestData> {
	
	private ArrayList<RestData> restData;
	int resource; 
	public RestDataAdapter(Context context,
			int textViewResourceId, ArrayList<RestData> items) {
		super(context, textViewResourceId, items);
		
		this.restData =  items;
		this.resource = textViewResourceId;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
           
			LinearLayout restDataView;
            
            RestData rd = restData.get(position);
            if (convertView == null) {
                    restDataView = new LinearLayout(getContext());
                    String inflater = Context.LAYOUT_INFLATER_SERVICE;
                    LayoutInflater vi;
                    vi = (LayoutInflater)getContext().getSystemService(inflater);
                    vi.inflate(resource, restDataView, true);
            }
            else
            {
            	
            	restDataView = (LinearLayout) convertView;
                
            }
          //Get the text boxes from the listitem.xml file
            TextView restName =(TextView)restDataView.findViewById(R.id.txtRestName);
            TextView restAddress =(TextView)restDataView.findViewById(R.id.txtRestAddress);
            TextView restPhone =(TextView)restDataView.findViewById(R.id.txtRestPhone);
            TextView restPhone2 =(TextView)restDataView.findViewById(R.id.txtRestPhone2);
            
     
            //Assign the appropriate data from our alert object above
            restName.setText(rd.name);
            restAddress.setText(rd.Address);
            restPhone.setText(rd.phone);
            restPhone2.setText(rd.phone2);
            
            return restDataView;
    }

}
