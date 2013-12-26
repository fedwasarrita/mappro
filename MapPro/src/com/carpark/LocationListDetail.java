package com.carpark;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.carpark.locationlistview.*;
import com.carpark.model.PlaceModel;
import com.carpark.supportedclass.GoogleDataReader;
import com.carpark.R;

public class LocationListDetail extends Activity {
	 	private ListView list;
	    private LazyAdapter adapter;
	    private ArrayList<PlaceModel> lstPlaceModel;
	    
	    private double lat;
	    private double lng;
	    String address;
	    //To determine what kind of place will be searched (atm, school, bookstore...)
	    private String keywork;
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.locationdetail);
	        list=(ListView)findViewById(R.id.list);
	        
	         //Get data from InputAddress activity
		     Bundle extras = getIntent().getExtras();
		     if(extras !=null) {
		    	 GoogleDataReader dataReader = new GoogleDataReader();
		    	 keywork = extras.getString("keywork");
		    	 
		         //current location
			     lat = extras.getDouble("lat");
			     lng = extras.getDouble("lng");
			     address = extras.getString("address"); 
			     //get info from google map by given key work
			     lstPlaceModel = dataReader.PlacesInfoReader(keywork, lat, lng, 10); 
		     }
	        
	        adapter=new LazyAdapter(this, lstPlaceModel,keywork);
	        list.setAdapter(adapter);
	        
	        list.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	              // When clicked, show a toast with the TextView text
	              //Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
	              Intent intent = new Intent();
	      		  intent.setClass(LocationListDetail.this, DrivingDirectionMapDetail.class);
	      		  intent.putExtra("src",address);
	      		  intent.putExtra("dest",lstPlaceModel.get(position).getAddress());
	      		  startActivity(intent);
	            }
	          });
	        
	        Button b=(Button)findViewById(R.id.btndemo);
	        b.setOnClickListener(listener);
	    }
	    
	    @Override
	    public void onDestroy()
	    {
	        adapter.imageLoader.stopThread();
	        list.setAdapter(null);
	        super.onDestroy();
	    }
	    
	    public OnClickListener listener=new OnClickListener(){
	        @Override
	        public void onClick(View arg0) {
	            adapter.imageLoader.clearCache();
	            adapter.notifyDataSetChanged();
	        }
	    };	    
	    
}
