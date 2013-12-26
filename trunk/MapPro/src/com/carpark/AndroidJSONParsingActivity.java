package com.carpark;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AndroidJSONParsingActivity extends ListActivity {

	// url to make request
	
 static String url = "http://api.androidhive.info/contacts/";
	
	// JSON Node names
   private static final String TAG_ID = "ParkID";
	private static final String TAG_ADDRESS = "ADDRESS";
	private static final String TAG_STATUS = "Statuscar";
	private static final String TAG_JSON = "JSON";

	// contacts JSONArray
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_park);
		
		Intent in = getIntent();
        
        // Get JSON values from previous intent
        String json = in.getStringExtra(TAG_JSON);
        
		// Hashmap for ListView
		ArrayList<HashMap<String, String>> List1 = new ArrayList<HashMap<String, String>>();

		
		try {
			JSONArray jsonarray = new JSONArray(json);
			for(int i = 0; i < jsonarray.length(); i++){
	            JSONObject c = jsonarray.getJSONObject(i);
	             
	            // Storing each json item in variable
	            String id = c.getString(TAG_ID);
	            String address = c.getString(TAG_ADDRESS);
	            String status = c.getString(TAG_STATUS);
	             
	            HashMap<String, String> map = new HashMap<String, String>();
	            // adding each child node to HashMap key => value
	            map.put(TAG_ID, id);
	            map.put(TAG_ADDRESS, address);
	            map.put(TAG_STATUS, status);
	            List1.add(map);
	            
			// adding HashList to ArrayList
			List1.add(map);
		}
	} catch (JSONException e) {
		e.printStackTrace();
	}
	
		
		
		ListAdapter adapter = new SimpleAdapter(this, List1,R.layout.list_item,
	                new String[] { TAG_ID, TAG_ADDRESS, TAG_STATUS}, new int[] {
	                        R.id.item_id, R.id.item_address, R.id.item_status });
		setListAdapter(adapter);
		ListView lv = getListView();
	        // Launching new screen on Selecting Single ListItem
		 lv.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                // getting values from selected ListItem
	                String name = ((TextView) view.findViewById(R.id.item_id)).getText().toString();
	                String cost = ((TextView) view.findViewById(R.id.item_address)).getText().toString();
	                String description = ((TextView) view.findViewById(R.id.item_status)).getText().toString();
	                 
	                // Starting new intent
	                Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
	                in.putExtra(TAG_ID, name);
	                in.putExtra(TAG_ADDRESS, cost);
	                in.putExtra(TAG_STATUS, description);
	                startActivity(in);
	            }
	        });
		
	

		
		
		/**
		 * Updating parsed JSON data into ListView
		 * */
	
		

	}

}