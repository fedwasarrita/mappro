/**
 * 
 */
package com.carpark;


import java.util.Locale;

import com.carpark.model.CPoint;
import com.carpark.supportedclass.GoogleDataReader;
import com.carpark.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * @author TUAN-NGUYEN
 *
 */
public class Get_data extends Activity {
	
     Geocoder geocoder;
     CheckBox checkbox_placenow;
     CheckBox checkbox_placechose;
     EditText edit_placenow;
     EditText edit_placechose;
     RadioButton radiob_map;
     RadioButton radiob_list;
     
     String address;
     String keywork;
     double lat;
     double lng;
     
	 public void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.get_data);
	     
	     //Initialize controls
	     final Button button_view = (Button)findViewById(R.id.button_view);
	     checkbox_placenow = (CheckBox)findViewById(R.id.checkbox_placenow);
	     checkbox_placechose = (CheckBox)findViewById(R.id.checkbox_placechose);
	     edit_placenow = (EditText)findViewById(R.id.editText_placenow);
	     edit_placechose = (EditText)findViewById(R.id.editText_placechose);
	     radiob_map = (RadioButton)findViewById(R.id.radiob_map);
	     radiob_list = (RadioButton)findViewById(R.id.radiob_list);
	     
	     
	     geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
	     
	     //Get data from Parking activity
	     Bundle extras = getIntent().getExtras();
	     if(extras !=null) {
	    	 address = extras.getString("address");
	    	 keywork = extras.getString("keyword");
	    	 //current location
	    	 lat = extras.getDouble("lat");
	    	 lng = extras.getDouble("lng");
	    	 Toast.makeText(this, keywork, Toast.LENGTH_LONG);
	    	 setPlaceNow();
	     }
	     
	     //
	     //Event(s)
	     //
	     
	     //Click event on checkbox checkbox_placenow (chon vi tri hien tai)
	     checkbox_placenow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 if (((CheckBox) v).isChecked()) 
				 {
					 checkbox_placechose.setChecked(false);
					 setPlaceNow();
			     }
				 else
			     {
					 checkbox_placechose.setChecked(true);
					 
			     }
			}
		});
	     
	     //Click event on check box checkbox_chose (vi tri cho san)
	     checkbox_placechose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (((CheckBox) v).isChecked()) 
				 {
					 checkbox_placenow.setChecked(false);
			     }
				 else
			     {
					 checkbox_placenow.setChecked(true);
					 setPlaceNow();
			     }
			}
		});
	     
	     //RadionButton map
	     radiob_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((RadioButton) v).isChecked())
				{
					radiob_map.setChecked(false);
				}
			}
		});
	     
	     radiob_map.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (((RadioButton) v).isChecked())
					{
						radiob_list.setChecked(false);
					}
				}
			});
	     
	     //Click event on button_view
	     button_view.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 
            	 Intent intent = new Intent();
            	 
            	 //If user choose current address
            	 if(checkbox_placenow.isChecked())
            	 {
            		 //Display result by list
            		 if(radiob_list.isChecked()){
            			 intent.setClass(Get_data.this, LocationListDetail.class);
                    	 
            			 //attach data
                		 intent.putExtra("lat",lat);
                		 intent.putExtra("lng",lng);
                		 intent.putExtra("keywork", keywork);
                		 intent.putExtra("address", address);
                		 
                		 startActivity(intent);
                		 return;
            		 }
            		 else{
            			 
            			 intent.setClass(Get_data.this, LocationMapDetail.class);
            			 intent.putExtra("lat",lat);
                		 intent.putExtra("lng",lng);
                		 intent.putExtra("keywork", keywork);
                		 intent.putExtra("address", address);
                		 
                		 startActivity(intent);
                		 return; 
            		 }
            	 }
            	 else //users entered their own address
            	 {
            		 if ((edit_placechose.getText().toString().equals("")))
            		 {
            				 new AlertDialog.Builder(Get_data.this).setTitle(R.string.note1 )
            				 .setMessage("Vui lòng nhập vào địa chỉ.")
            				 .setPositiveButton("OK", null).show();
            				 return;
            		 }
            		 //
        			 //Convert address to point
            		 //
 		    	     CPoint point = new CPoint();
 		    	     GoogleDataReader reader = new GoogleDataReader();
 		    	     point = reader.GetLatLngFromAddress(edit_placechose.getText().toString(),
 		    	    		 							 geocoder);
 		    	     
            		 if(radiob_list.isChecked()){
     		    	    //attach data
     		    	    intent.setClass(Get_data.this, LocationListDetail.class);
     		    	    intent.putExtra("lat",point.lat);
     		    	    intent.putExtra("lng",point.lng);
     		    	    intent.putExtra("keywork", keywork);
     		    	    intent.putExtra("address", address);
     		    	   
     		    	    startActivity(intent);
     		    	    return;
            		 }
            		 else
            		 {
            			//Display places on map with entered address
      		    	    intent.setClass(Get_data.this, LocationMapDetail.class);
      		    	    intent.putExtra("lat",point.lat);
      		    	    intent.putExtra("lng",point.lng);
      		    	    intent.putExtra("keywork", keywork);
      		    	    intent.putExtra("address", address);
      		    	  
      		    	    startActivity(intent);
      		    	    return;
            		 }
            	 }
             }
         });
	 }
	 
	 public void setPlaceNow()
	 {
		 if(checkbox_placenow.isChecked())
			 edit_placenow.setText(address);
	 }
	 
	 
}
