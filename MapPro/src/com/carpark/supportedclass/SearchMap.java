package com.carpark.supportedclass;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

public class SearchMap extends Activity {
	public double lat;
	public double lon;
	public SearchMap(){
		
	}
	
	public SearchMap(String placename) {
		// TODO Auto-generated constructor stub
	
		// Break from execution if the user has not entered anything in the field
		
		int numberOptions = 5;
		String [] optionArray = new String[numberOptions];
		Geocoder gcoder = new Geocoder(this);
		
		// Note that the Geocoder uses synchronous network access, so in a serious application
		// it would be best to put it on a background thread to prevent blocking the main UI if network
		// access is slow. Here we are just giving an example of how to use it so, for simplicity, we
		// don't put it on a separate thread.
		
		try{
			List<Address> results = gcoder.getFromLocationName(placename,numberOptions);
			Iterator<Address> locations = results.iterator();
			String raw = "\nRaw String:\n";
			String country;
			int opCount = 0;
			while(locations.hasNext()){
				Address location = locations.next();
				lat = location.getLatitude();
				lon = location.getLongitude();
				country = location.getCountryName();
				if(country == null) {
					country = "";
				} else {
					country =  ", "+country;
				}
				raw += location+"\n";
				optionArray[opCount] = location.getAddressLine(0)+", "+location.getAddressLine(1)
					+country+"\n";
				opCount ++;
			}
			Log.i("Location-List", raw);
			Log.i("Location-List","\nOptions:\n");
			for(int i=0; i<opCount; i++){
				Log.i("Location-List","("+(i+1)+") "+optionArray[i]);
			}
			
		} catch (IOException e){
			Log.e("Geocoder", "I/O Failure; is network available?",e);
		}
		
   
	
	}

	

	
}
