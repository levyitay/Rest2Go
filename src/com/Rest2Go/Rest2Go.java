package com.Rest2Go;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.Rest2Go.Utils.xmlBuilder;

import javax.xml.*;
import javax.xml.parsers.ParserConfigurationException;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class Rest2Go extends Activity {
  
	protected LocationManager locationManager;

	protected Button btnGetLoc;
	protected ListView listView;
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	private static final String SERVER_IP="127.0.0.1";
	private static int SERVER_PORT = 6666;
	
	private double cLongitude;
	private double cLatitude;
	private Document RestXML;
	
	private Socket m_ClientSocket;
//	private DataInputStream in;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Rest2Go.R.layout.main);
        
        listView = (ListView) findViewById(com.Rest2Go.R.id.ListView01);
        btnGetLoc = (Button) findViewById(com.Rest2Go.R.id.BtnGetLoc);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES,
        									    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, 
        									    new MyLocationListener());
        
        btnGetLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
				
					Document restXMLDocument = xmlBuilder.buildXMLFromString(in.readUTF());
				
					m_ClientSocket.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				
			}
		});
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
    		SocketAddress serverAdd = new InetSocketAddress("192.168.1.2",SERVER_PORT);    	
    	
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