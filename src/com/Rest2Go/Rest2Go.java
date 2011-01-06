package com.Rest2Go;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//import com.Rest2Go.Utils.xmlBuilder;

//import com.Rest2Go.Utils.xmlBuilder;


import javax.xml.parsers.ParserConfigurationException;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class Rest2Go extends Activity {
  
	protected LocationManager locationManager;
	
	protected Button btnGetLoc;
	protected ListView listView;
	protected ListView lstTest;
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	private static final String SERVER_IP="127.0.0.1";
	private static int SERVER_PORT = 6666;
	RestDataAdapter arrayAdapter;

	private double cLongitude;
	private double cLatitude;
	private Document RestXML;
	
	ArrayList<RestData> restInfo;
	
	private Socket m_ClientSocket;
//	private DataInputStream in;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Rest2Go.R.layout.main);
       	lstTest= (ListView)findViewById(R.id.lstText);
      	restInfo = new ArrayList<RestData>();
       	arrayAdapter = new RestDataAdapter(this.getApplicationContext(), R.layout.listitems,restInfo);
    	lstTest.setAdapter(arrayAdapter);
      //  listView = (ListView) findViewById(com.Rest2Go.R.id.ListView01);
       // btnGetLoc = (Button) findViewById(com.Rest2Go.R.id.BtnGetLoc);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES,
        									    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, 
        									    new MyLocationListener());
        
        
				showCurrentLocation();
				try {
					ConnectToServer();
				} catch (UnknownHostException e) {
					Log.e("[Rest2Go] ","Error Connecting to Server:"+e.getMessage());
					try {
						m_ClientSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (IOException e) {
					Log.e("[Rest2Go] ","Error Connecting to Server:"+e.getMessage());
					try {
						m_ClientSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					SendLocInfo();
					String temp = (String) in.readObject();
					
					Document restXMLDocument = xmlBuilder.buildXMLFromString(temp);
					
					m_ClientSocket.close();
					displayData(restXMLDocument);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
 
    
    protected void displayData(Document restXMLDocument) {
    	
    	
    	
    	NodeList ndList = restXMLDocument.getElementsByTagName("Restaurant");
    	
  
    	for (int i = 0; i<=ndList.getLength()-1; i++)
    	{
    		Node currRest = ndList.item(i);
    		String phone2="";
    		String name =currRest.getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
    		String address=currRest.getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
    		String phone=currRest.getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
    		if (currRest.getChildNodes().item(3).getChildNodes().item(0)!=null)
    			phone2=currRest.getChildNodes().item(3).getChildNodes().item(0).getNodeValue();
    		
    		restInfo.add(new RestData(name,address,phone,phone2));
    		arrayAdapter.notifyDataSetChanged();
    		
    	}
    	
    	
    	
    	
    	
    	
	}

	private void SendLocInfo() throws IOException
    {
    	
    	if (out!=null){
	    	out.writeObject(this.cLongitude);
	    	out.writeObject(this.cLatitude);
    	}
    	
    	
    	
    	
    }
    
    private void ConnectToServer() throws UnknownHostException, IOException, ClassNotFoundException
    {
    		SocketAddress serverAdd = new InetSocketAddress("192.168.199.4",SERVER_PORT);    	
    		
			m_ClientSocket = new Socket();
			m_ClientSocket.connect(serverAdd);
			in = new ObjectInputStream(m_ClientSocket.getInputStream());
			out = new ObjectOutputStream(m_ClientSocket.getOutputStream());
			Log.i("[Rest2Go] ", (String)in.readObject());
			
		
		
    }
    
    protected void showCurrentLocation()
    {
    	Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	
    	if (location != null)
    	{
    		String message = String.format(
	                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
			             location.getLongitude(), location.getLatitude());
    				            
    		Toast.makeText(Rest2Go.this, message,Toast.LENGTH_LONG).show();
    		this.cLatitude = location.getLatitude();
    		this.cLongitude = location.getLongitude();
    		
    	}

    	}
  
    
    private class MyLocationListener implements LocationListener
    {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    	
    	
    }


}