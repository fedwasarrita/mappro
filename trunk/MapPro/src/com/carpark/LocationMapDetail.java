package com.carpark;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.carpark.supportedclass.CustomPinpoint;
import com.carpark.supportedclass.GoogleDataReader;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.carpark.R;

public class LocationMapDetail extends MapActivity {

	public static Context context; 
	
	private MapController mapControl;
	private MapView mapView;
	
	 private GeoPoint gp;
	 private int lat1;
	  private int lon;
	 
	private double lat;
	  private double lng;
	  //To determine what kind of place will be searched (atm, school, bookstore...)
	  private String keywork;
	
	  private ArrayList<GeoPoint> lstGeoPoint;
	  private List<Overlay> overlayList;
	  private Drawable d;
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapme);
	        d=getResources().getDrawable(R.drawable.startpoint);
	        //d1=getResources().getDrawable(R.drawable.);
	        
	        
	        //Get data from InputAddress activity
		    Bundle extras = getIntent().getExtras();
		    if(extras !=null) {
		    	 GoogleDataReader dataReader = new GoogleDataReader();
		    	 keywork = extras.getString("keywork");
		    	 
		    	 if( keywork.equalsIgnoreCase("atm")) {d=getResources().getDrawable(R.drawable.atmicon4);}
		    	 else if(keywork.equalsIgnoreCase("school")) {d=getResources().getDrawable(R.drawable.school);}
		    	 else if(keywork.equalsIgnoreCase("hospital")){d=getResources().getDrawable(R.drawable.hospital_nurse);}
		    	 else if(keywork.equalsIgnoreCase("bus")) {d=getResources().getDrawable(R.drawable.bus);}
		    	 else if(keywork.equalsIgnoreCase("bookstore")){d=getResources().getDrawable(R.drawable.bookstore);}
		    	 else if(keywork.equalsIgnoreCase("hotel")){d=getResources().getDrawable(R.drawable.restaurant);}
			    
			     
		         //current location
			     lat = extras.getDouble("lat");
			     lng = extras.getDouble("lng");
			    	 
			     //get coordinate from google map by given key work
			     lstGeoPoint = dataReader.PlacesCoordinateReader(keywork, lat, lng, 10);
		     }
		    
		    lat1=lstGeoPoint.get(0).getLatitudeE6();
	     	lon=lstGeoPoint.get(0).getLongitudeE6();
		    GeoPoint gp1 =new GeoPoint(lat1,lon);
	        //Add map controller with zoom controls
	        mapView = (MapView) findViewById(R.id.mv2);
	        mapView.setSatellite(false);
	        mapView.setTraffic(false);
	        mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
	        int maxZoom = mapView.getMaxZoomLevel();
	        int initZoom = (int)(0.95*(double)maxZoom);
	        mapControl = mapView.getController();
	        mapControl.setZoom(initZoom);
	        mapControl.animateTo(gp1);
	        
	        context = getApplicationContext();
	        
	        for(int i=0;i<lstGeoPoint.size()-1;i++){
		        lat1=lstGeoPoint.get(i).getLatitudeE6();
		     	lon=lstGeoPoint.get(i).getLongitudeE6();
			    gp =new GeoPoint(lat1,lon);
			    //mapControl.animateTo(gp);
			    overlayList = mapView.getOverlays();
		        OverlayItem overlayItem= new OverlayItem(gp,"What 's up"," 2nd string");
				CustomPinpoint custom = new CustomPinpoint(d, LocationMapDetail.this);
				custom.insertPinpoint(overlayItem);
				overlayList.add(custom);
		        }
	 }
	 
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void showToast(String text){
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

}
