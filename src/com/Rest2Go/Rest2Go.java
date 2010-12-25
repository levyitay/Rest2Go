package com.Rest2Go;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

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
import android.widget.Toast;


public class Rest2Go extends Activity {
  
	protected LocationManager locationManager;

	protected Button btnGetLoc;
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
	
	private static final String SERVER_IP="127.0.0.1";
	private static int SERVER_PORT = 5555;
	
	private double cLongitude;
	private double cLatitude;
	
	private Socket m_ClientSocket;
	private DataInputStream in;
	private DataOutputStream out;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.Rest2Go.R.layout.main);
        

        btnGetLoc = (Button) findViewById(com.Rest2Go.R.id.BtnGetLoc);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES,
        									    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, 
        									    new MyLocationListener());
        
        btnGetLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showCurrentLocation();
				ConnectToServer();
				SendLocInfo();
				
			}
		});
    }
    
    private void SendLocInfo()
    {
    	try{
    	out.writeDouble(this.cLongitude);
    	out.writeDouble(this.cLatitude);
    	}catch(IOException e){
    		Log.e("[Rest2Go] ","Error Sending to Server:"+e.getMessage());
			
    	}
    	
    	
    }
    
    private void ConnectToServer() 
    {
    	SocketAddress serverAdd = new InetSocketAddress(SERVER_IP,SERVER_PORT);
    	
    	try {
			m_ClientSocket = new Socket();
			m_ClientSocket.connect(serverAdd);
			in = new DataInputStream(m_ClientSocket.getInputStream());
			out = new DataOutputStream(m_ClientSocket.getOutputStream());
			Log.i("[Rest2Go] ", in.readUTF());
			
		} catch (UnknownHostException e) {
			Log.e("[Rest2Go] ","Error Connecting to Server:"+e.getMessage());
			
		} catch (IOException e) {
			Log.e("[Rest2Go] ","Error Connecting to Server:"+e.getMessage());
		}
		
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