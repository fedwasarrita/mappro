/**
 * 
 */
package com.carpark;

import java.util.Locale;

import com.carpark.supportedclass.GoogleDataReader;
import com.carpark.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * @author TUAN-NGUYEN
 *
 */
public class MapDirection extends Activity {
	 /** Called when the activity is first created. */
	private EditText placeA;
	private EditText placeB;
	private CheckBox chkPlaceNow;
	private RadioButton radiob_map1;
	private RadioButton radiob_list1;
	
	private String Address="";
	
	Geocoder geocoder;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maindirection);
        
        //Initialize controls
	    final Button btnViewDirection = (Button)findViewById(R.id.btnViewDirection);
	    final Button btnViewDirBus = (Button)findViewById(R.id.btnViewDirBus);
	    final Button btnViewDirWalk = (Button)findViewById(R.id.btnViewDirWalk);
	    placeA = (EditText)findViewById(R.id.editTextPlaceA);
	    placeB = (EditText)findViewById(R.id.editTextPlaceB);
	    chkPlaceNow = (CheckBox)findViewById(R.id.chkPlaceNow);
	    radiob_list1 = (RadioButton)findViewById(R.id.radiob_list1);
	    radiob_map1 = (RadioButton)findViewById(R.id.radiob_map1);
	    
	    //Location initiation
	    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	    LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    LocationListener mlocListener = new GPSLocationListener();
	    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
	    
	    
	    //
	    //Event(s)
	    //
	    
	    chkPlaceNow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 if (((CheckBox) v).isChecked()) 
				 {
					 
					 String gps = (String) MainActivity .gettext();
					 if(Address=="")
					 {placeA.setText(gps);}
					 else
					 {placeA.setText(Address);}
						 
					 
			     }
				 else
			     {
					 placeA.setText(" ");
			     }
			}
		});
	    
	    radiob_list1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (((RadioButton) v).isChecked())
				{
					radiob_map1.setChecked(false);
				}
			}
		});
	    
	    radiob_map1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((RadioButton) v).isChecked())
				{
					radiob_list1.setChecked(false);
				}
			}
		});
	    
	    btnViewDirection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if ((placeA.getText().toString().equals("")) || 
					(placeB.getText().toString().equals("")))
       		 	{
       				 new AlertDialog.Builder(MapDirection.this).setTitle("Thông báo" )
       				 .setMessage("Vui lòng nhập vào địa chỉ.")
       				 .setPositiveButton("OK", null).show();
       				 return;
       		 	}
				
				String src = placeA.getText().toString();
				String dest = placeB.getText().toString();
				
				Intent intent = new Intent();
				
				
				if(radiob_list1.isChecked())
				{
					intent.setClass(MapDirection.this, DrivingDirectionListDetail.class);
				}
				else
				{
					intent.setClass(MapDirection.this, DrivingDirectionMapDetail.class);
				}
				
				intent.putExtra("src",src);
				intent.putExtra("dest",dest);
				intent.putExtra("transit", "");
				startActivity(intent);
			}
		});
	    
	    btnViewDirBus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if ((placeA.getText().toString().equals("")) || 
						(placeB.getText().toString().equals("")))
	       		 	{
	       				 new AlertDialog.Builder(MapDirection.this).setTitle("Thông báo" )
	       				 .setMessage("Vui lòng nhập vào địa chỉ.")
	       				 .setPositiveButton("OK", null).show();
	       				 return;
	       		 	}
				
				String src = placeA.getText().toString();
				String dest = placeB.getText().toString();
				
				Intent intent = new Intent();
				
				
				if(radiob_list1.isChecked())
				{
					intent.setClass(MapDirection.this, DrivingDirectionListDetail.class);
				}
				else
				{
					intent.setClass(MapDirection.this, DrivingDirectionMapDetail.class);
				}
				
				intent.putExtra("src",src);
				intent.putExtra("dest",dest);
				intent.putExtra("transit", "r");
				startActivity(intent);
			}
		});
	    
	    btnViewDirWalk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if ((placeA.getText().toString().equals("")) || 
						(placeB.getText().toString().equals("")))
	       		 	{
	       				 new AlertDialog.Builder(MapDirection.this).setTitle("Thông báo" )
	       				 .setMessage("Vui lòng nhập vào địa chỉ.")
	       				 .setPositiveButton("OK", null).show();
	       				 return;
	       		 	}
				
				String src = placeA.getText().toString();
				String dest = placeB.getText().toString();
				
				Intent intent = new Intent();
				
				
				if(radiob_list1.isChecked())
				{
					intent.setClass(MapDirection.this, DrivingDirectionListDetail.class);
				}
				else
				{
					intent.setClass(MapDirection.this, DrivingDirectionMapDetail.class);
				}
				
				intent.putExtra("src",src);
				intent.putExtra("dest",dest);
				intent.putExtra("transit", "w");
				startActivity(intent);
			}
		});
    }
    
	 public class GPSLocationListener implements LocationListener {
	    	@Override
	    	public void onLocationChanged(Location location)
	    	{
	    		GoogleDataReader reader = new  GoogleDataReader();
	    		String address = reader.GetAddressFromLatLng(location.getLatitude(),location.getLongitude() 
	    													,geocoder);
	    		Address = address;
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
	    }
}
