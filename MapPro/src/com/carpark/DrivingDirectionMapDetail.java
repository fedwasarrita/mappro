package com.carpark;

import java.util.Locale;

import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.carpark.model.CPoint;
import com.carpark.supportedclass.GoogleDataReader;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.carpark.R;

public class DrivingDirectionMapDetail extends MapActivity{
	
	Geocoder geocoder;
	private MapController mapControl;
	private MapView mapView;
	
	private String src;
    private String dest;
    CPoint desPoint;
    CPoint srcPoint;
    
    GeoPoint desGeo;
    GeoPoint srcGeo;
    
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapme);
	        
	        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	        GoogleDataReader dataReader = new GoogleDataReader();
	        CPoint desPoint = new CPoint();
	    	CPoint srcPoint = new CPoint();
	    	
	    	
	    	
	        //Get data from InputAddress activity
		    Bundle extras = getIntent().getExtras();
		     
		    if(extras !=null) {
		    	 
		         //current location
			     src = extras.getString("src");
			     dest = extras.getString("dest");
			     String transit = extras.getString("transit");
			     
			     srcPoint = dataReader.GetLatLngFromAddress(src, geocoder);
			     desPoint = dataReader.GetLatLngFromAddress(dest, geocoder);
			     
			     if(srcPoint==null){
			    	 Toast.makeText(this, "Chưa tồn tại dữ liệu cho thành phố này",
								Toast.LENGTH_LONG).show();
			    	 return;
			     }
			     
			     //Convert Point to GeoPoint
			     srcGeo = new GeoPoint((int)(srcPoint.lat*1E6),(int)(srcPoint.lng*1E6));
			     desGeo = new GeoPoint((int)(desPoint.lat*1E6),(int)(desPoint.lng*1E6));
			     
			     //Begin to draw route
			     mapView = (MapView) findViewById(R.id.mv2);
			     mapView.setSatellite(false);
			     mapView.setTraffic(false);
			     mapView.setBuiltInZoomControls(true);   // Set android:clickable=true in main.xml
			     
			     
			     mapView.getController().animateTo(srcGeo);
			     
			     mapControl = mapView.getController();
			     mapView.getController().setZoom(15); 

		    }

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
