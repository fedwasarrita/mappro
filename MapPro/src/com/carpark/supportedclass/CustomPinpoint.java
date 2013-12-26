package com.carpark.supportedclass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.R.drawable;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.carpark.LocationMapDetail;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;


public class CustomPinpoint  extends ItemizedOverlay<OverlayItem>{

	private ArrayList<OverlayItem> pinpoints= new ArrayList<OverlayItem>();
	private Context c;
	int i;
	
	
	public CustomPinpoint(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	
	public CustomPinpoint(Drawable m,Context context ) {
		this(m);
		c=context;
		
		// TODO Auto-generated constructor stub
	}
	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return pinpoints.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pinpoints.size();
	}
protected boolean onTap(int i){
		
		/*	In this case we will just put a transient Toast message on the screen indicating that we have
		captured the relevant information about the overlay item.  In a more serious application one
		could replace the Toast with display of a customized view with the title, snippet text, and additional
		features like an embedded image, video, or sound, or links to additional information. (The lat and
		lon variables return the coordinates of the icon that was clicked, which could be used for custom
		positioning of a display view.)*/
		
		GeoPoint  gpoint = pinpoints.get(i).getPoint();
		double lat = gpoint.getLatitudeE6()/1e6;
		double lon = gpoint.getLongitudeE6()/1e6;
		
		String display= "";
		
		Geocoder geocoder = new Geocoder(LocationMapDetail.context,Locale.getDefault());
		try{
			List<Address> address = geocoder.getFromLocation(gpoint.getLatitudeE6()/1e6, gpoint.getLongitudeE6()/1e6, 1);
			if (address.size()>0){
				
				for( i=0;i<address.get(0).getMaxAddressLineIndex();i++){
					
					display +=address.get(0).getAddressLine(i)+" ";
					}
				//Toast t= Toast.makeText(getBaseContext(),display, Toast.LENGTH_LONG);
				//t.show();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		
		
		
		//String toast = "Title: ";
		//toast += "\nText: ";
		//toast += 	"\nSymbol coordinates: Lat = "+lat+" Lon = "+lon+" (microdegrees)";
		Toast.makeText( LocationMapDetail.context, display, Toast.LENGTH_LONG).show();
		return(true);
	}
	
	private Context getBaseContext() {
	// TODO Auto-generated method stub
	return null;
}


	public void insertPinpoint(OverlayItem item){
		pinpoints.add(item);
		
		this.populate();
	}

}
