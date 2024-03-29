package com.carpark;

import java.util.Locale;


import com.carpark.supportedclass.GoogleDataReader;
import com.carpark.supportedclass.Prefs;
import com.carpark.R;
import com.carpark.R.string;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSMessagingActivity extends Activity 
{
	Button btnSendSMS;
	EditText txtPhoneNo;
	EditText txtMessage;
	
	Geocoder geocoder;
	 TextView txtPosition;
	 double lat=0.0;
	 double lng=0.0;
	 
	 LocationManager mlocManager;
	 Location loc;
	 String provider = LocationManager.GPS_PROVIDER;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);        
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        String gps = (String) MainActivity .gettext();
        txtMessage.setText(gps);
        /*
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "Content of the SMS goes here..."); 
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
        */
                
        btnSendSMS.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) 
            {            	
            	
            	String phoneNo = txtPhoneNo.getText().toString();
            	String message = txtMessage.getText().toString();  
            	 
                if (phoneNo.length()>0 && message.length()>0)                
                    sendSMS(phoneNo, message);                
                else
                	Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
            }
           
        });   
        //Location initiation
  	     geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
  	     mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
  	     LocationListener mlocListener = new GPSLocationListener();
  	     mlocManager.requestLocationUpdates(provider, 10000, 5, mlocListener);
    }
    
    
    public class GPSLocationListener implements LocationListener {
    	@Override
    	public void onLocationChanged(Location location)
    	{
    		loc = mlocManager.getLastKnownLocation(provider);
    		GoogleDataReader reader = new  GoogleDataReader();
    		String address = reader.GetAddressFromLatLng(loc.getLatitude(),loc.getLongitude() 
    													,geocoder);
    		lat = location.getLatitude();
    		lng = location.getLongitude();
    		
    		txtMessage.setText(address);
    	}

    	@Override
    	public void onProviderDisabled(String provider)
    	{
    	}
    	@Override
    	public void onProviderEnabled(String provider)
    	{
    	}

    	@Override
    	public void onStatusChanged(String provider, int status, Bundle extras)
    	{

    	}
    }/* End of Class GPSLocationListener */
    //---sends a SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {      
    	/*
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, test.class), 0);                
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, pi, null);        
        */
    	
    	String SENT = "SMS_SENT";
    	String DELIVERED = "SMS_DELIVERED";
    	
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
        
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
    	
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				    case Activity.RESULT_OK:
					    Toast.makeText(getBaseContext(), "SMS sent", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					    Toast.makeText(getBaseContext(), "Generic failure", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_NO_SERVICE:
					    Toast.makeText(getBaseContext(), "No service", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_NULL_PDU:
					    Toast.makeText(getBaseContext(), "Null PDU", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case SmsManager.RESULT_ERROR_RADIO_OFF:
					    Toast.makeText(getBaseContext(), "Radio off", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				}
			}
        }, new IntentFilter(SENT));
        
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode())
				{
				    case Activity.RESULT_OK:
					    Toast.makeText(getBaseContext(), "SMS delivered", 
					    		Toast.LENGTH_SHORT).show();
					    break;
				    case Activity.RESULT_CANCELED:
					    Toast.makeText(getBaseContext(), "SMS not delivered", 
					    		Toast.LENGTH_SHORT).show();
					    break;					    
				}
			}
        }, new IntentFilter(DELIVERED));        
    	
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);               
    }    
}