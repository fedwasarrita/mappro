package com.carpark;


import java.util.ArrayList;
import java.util.Locale;

import com.carpark.supportedclass.GoogleDataReader;
import com.carpark.R;
import com.googlemapapi.GoogleGeocoding;
import com.googlemapapi.GooglePlaces;
import com.googlemapapi.MyLocation;

import android.location.Criteria;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.net.*;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	 Geocoder geocoder;
	 TextView txt_lati;
	 TextView txt_long;
	 TextView txt_status;
	 LocationManager mlocManager;
	 Location loc;
	 String provider = LocationManager.GPS_PROVIDER;
	 static String gpsnow;
	 public static double lat=0.0;
	 public static double lng=0.0;
	 TextView txt;
	
	 	
		private Boolean isGPSEnabled = false;
		private ConnectionChecker ck;
		private GooglePlaces googlePlaces;
		public double latitude;
		public double longtitude;
		private double desLatitude;
		private double desLongtitude;
		
		private EditText destinationInput;
		private Spinner spinner;
		private int selectedRadius = 1;
		private AlertDialogManager alert = new AlertDialogManager();
		private ProgressDialog progressDialog;
		private ArrayAdapter<String> adapter;
		private MyLocation myLocation;
		public Location mLocation;
		private double searchRadius;
		private String DESTINATION;
		private String[] listStation;
		private ListView listView;
		private ArrayList<Integer> stationForFloyd;
		private int tramDich;
		private String HCM_CITY = "Ho Chi Minh City";
		private boolean isInHCM = false;
		private static final String GOOGLE_LOCATION = "GoogleStation";
		private static final String ADDED_LOCATION = "AddedStation";
		private ArrayList<Integer> desStationList = new ArrayList<Integer>();
		private String busNumbers = "";
		private String busRoutes = "";
		boolean isValidStation = false;
	 
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.main);
	     
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	  
	    txt = (TextView)findViewById(R.id.main_text_place);
	    
	     gridview.setAdapter(new ImageAdapter(this));
	     
	     //Click item event
	     gridview.setOnItemClickListener(new OnItemClickListener() {
	    	  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	              //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_LONG).show();
	              
	              Intent intent = new Intent();
	              
	              if(position==0)
	              {
	            	  intent.setClass(MainActivity.this, Get_data.class);
	            
	            	  intent.putExtra("address", txt.getText());
		    		  intent.putExtra("lat", lat);
		    		  intent.putExtra("lng", lng);
	            	  startActivity(intent);

	            	  return;
	              }
	              
	              if(position==1){
	            
	            	  intent.setClass(MainActivity.this, AboutActivity.class);
	            	  startActivity(intent);
	            	  return;
	            	  /*
	            	 Toast.makeText(getApplicationContext(), "Navigation", Toast.LENGTH_SHORT).show();
	            	    
	            	  String url = "http://maps.google.com/maps?saddr=" + latitude + ","
	            	            + longtitude + "&daddr=" + "10.772234,106.657761";

	            	    Intent navigation = new Intent(Intent.ACTION_VIEW);
	            	    navigation.setData(Uri.parse(url));

	            	    startActivity(navigation);*/
	              }
	          }
		 });
	     
	     
	     myLocation = new MyLocation(MainActivity.this);
	     getMyLocation();
	     lat = latitude;
 		 lng = longtitude;
	     gpsnow=txt.getText().toString();
	     GoogleGeocoding geocoding = new GoogleGeocoding(latitude,longtitude);
		 String city = geocoding.getaddress();
		 txt.setText(city);
			
	 }

	 public class ImageAdapter extends BaseAdapter {
	     private Context mContext;
	     public ImageAdapter(Context c) {
	         mContext = c;
	     }
	     public int getCount() {
	         return mThumbIds.length;
	     }
	     public Object getItem(int position) {
	         return null;
	     }
	     public long getItemId(int position) {
	         return 0;
	     }
	     
	     // create a new ImageView for each item referenced by the Adapter
	     public View getView(int position, View convertView, ViewGroup parent) {
	         ImageView imageView;
	         if (convertView == null) {  // if it's not recycled, initialize some attributes
	             imageView = new ImageView(mContext);
	             imageView.setLayoutParams(new GridView.LayoutParams(200, 180));
	             imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	             imageView.setPadding(8, 8, 8, 8);
	         } else {
	             imageView = (ImageView) convertView;
	         }
	         imageView.setImageResource(mThumbIds[position]);
	         return imageView;
	     }
	     // references to our images
	     private Integer[] mThumbIds = {
	             R.drawable.diadiem1, R.drawable.about,
	                          
	     };
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

	 
	 public static double getlat() {
			
			return lat;
		}
	 public static double getlong() {
		
			return lng;
		}

	
	


	public static String gettext() {
		// TODO Auto-generated method stub
		return gpsnow;
	}
	
	
	}	