package com.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.carpark.*;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.ListActivity;
import android.content.Intent;


import com.carpark.supportedclass.GoogleDataReader;
import com.carpark.R;
import com.googlemapapi.GoogleGeocoding;
import com.googlemapapi.GooglePlaces;
import com.googlemapapi.MyLocation;

 

public class SocketsActivity extends Activity {
	
	private static final String TAG_ID = "ParkID";
	private static final String TAG_ADDRESS = "ADDRESS";
	private static final String TAG_STATUS = "Statuscar";
	private static final String TAG_JSON = "";
	private MyLocation myLocation;
	public Location mLocation;
	private Boolean isGPSEnabled = false;
	private ConnectionChecker ck;
	private GooglePlaces googlePlaces;
	public double latitude;
	public double longtitude;
	private double desLatitude;
	private double desLongtitude;
	LocationManager mlocManager;
	 Location loc;
	 String provider = LocationManager.GPS_PROVIDER;
	 static String gpsnow;
	 public static double lat=0.0;
	 public static double lng=0.0;

    static final String NICKNAME = "Wei-Meng";
	//---socket---
	InetAddress serverAddress;
	Socket socket;
	
	//---all the Views---    
	static TextView txtMessagesReceived;

	static EditText txtMessage;

	//---thread for communicating on the socket---
	CommsThread commsThread;

	//---used for updating the UI on the main activity---
	 static Handler UIupdater = new Handler() {
		@Override
		public void handleMessage(Message msg) { 
			
			int numOfBytesReceived = msg.arg1;
			byte[] buffer = (byte[]) msg.obj;
			
		
			//---convert the entire byte array to string---
			String strReceived = new String(buffer);

			//---extract only the actual string received---
			strReceived = strReceived.substring(
					0, numOfBytesReceived);
			txtMessagesReceived.setText(strReceived);
			
			
			//---display the text received on the TextView---              
			
			
			//---display the text received on the TextView---              
					}
	};


    private class CreateCommThreadTask extends AsyncTask
    <Void, Integer, Void> {
		@Override
        protected Void doInBackground(Void... params) {            
            try {
                //---create a socket---
                serverAddress = 
                    InetAddress.getByName("192.168.1.100");
                socket = new Socket(serverAddress, 2020);
                commsThread = new CommsThread(socket);
                commsThread.start();                
    			//---sign in for the user; sends the nick name---
    			sendToServer("sear");
            } catch (UnknownHostException e) {
                Log.d("Sockets", e.getLocalizedMessage());
            } catch (IOException e) {
            	Log.d("Sockets", e.getLocalizedMessage());
            }
            return null;
        }
    }
        
    private class WriteToServerTask extends AsyncTask
    <byte[], Void, Void> {
        protected Void doInBackground(byte[]...data) {
            commsThread.write(data[0]);
            return null;
        }
    }
    
    private class CloseSocketTask extends AsyncTask
    <Void, Void, Void> {        
        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket.close();
            } catch (IOException e) {
            	Log.d("Sockets", e.getLocalizedMessage());                
            }
            return null;
        }
    }
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.socket);

		//---get the views---
		txtMessage = (EditText) findViewById(R.id.txtMessage);
		txtMessagesReceived = (TextView)
				findViewById(R.id.txtMessagesReceived);
		 myLocation = new MyLocation(SocketsActivity.this);
	     getMyLocation();
	  
		
	}
	
	 
	 public void getMyLocation() {
			if (myLocation.canGetLocation()) {
				isGPSEnabled = true;
				latitude = myLocation.getLatitude();
				longtitude = myLocation.getLongtitude();
			} else {
				isGPSEnabled = false;
				myLocation.showSettingAlert();

			}
		}


	public void onClickSend(View view) {
		//---send the message to the server---
		txtMessagesReceived.setText("");
		sendToServer("sear");
		
	}
	public void onClicGPS(View view) {
		//---send the message to the server---
		txtMessagesReceived.setText("");
		
		
		 sendToServer("GPRS"+latitude +"," + longtitude);
	}
	
	
	
	public void onClickbook(View view) {
		//---send the message to the server---
		sendToServer("book"+txtMessage.getText().toString());
	}

	private void sendToServer(String message) {
		byte[] theByteArray = 
				message.getBytes();
        new WriteToServerTask().execute(theByteArray);		
	}

    @Override
    public void onResume() {
        super.onResume();
        new CreateCommThreadTask().execute();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        new CloseSocketTask().execute();
    }

}