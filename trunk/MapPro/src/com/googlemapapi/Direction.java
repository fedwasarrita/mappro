package com.googlemapapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;



public class Direction {
	public final static String MODE_DRIVING = "driving";
	public final static String MODE_WALKING = "walking";
	public final static String API_KEY = "AIzaSyBUVH4gXbur_kqF0sgsT2wG5mxoJKzeC_c";
	public Direction() {
	}
	
	
	// get distance on google map

	public String getDistance(double gp1lat, double gp1long , double gp2lat , double gp2long) {

		String url = "http://maps.googleapis.com/maps/api/directions/json?origin=";
		url += gp1lat;
		url += ",";
		url += gp1long;
		url += "&destination=";
		url += gp2lat;
		url += ",";
		url += gp2long;
		url += "&sensor=false";

		Log.d("MYAPP", url);
		String distance_value = "";

		String jsonString = getConnect(url);
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray routes = json.getJSONArray("routes");

			JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
			JSONObject distance = legs.getJSONObject(0).getJSONObject(
					"distance");
			distance_value = distance.getString("text");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return distance_value;

	}
	
	// get direction on google map

	public ArrayList<LatLong> getDirection(LatLong gp1, LatLong gp2) {
		ArrayList<LatLong> points = new ArrayList<LatLong>();

		String url = "http://maps.googleapis.com/maps/api/directions/json?origin=";
		url += gp1.getLat();
		url += ",";
		url += gp1.getLng();
		url += "&destination=";
		url += gp2.getLat();
		url += ",";
		url += gp2.getLng();
		url += "&sensor=false";

		Log.d("MYAPP", url);

		String jsonString = getConnect(url);
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray routes = json.getJSONArray("routes");

			JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
			JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");
			for (int i = 0; i < steps.length(); i++) {
				JSONObject singleStep = steps.getJSONObject(i);
				JSONObject start_location = singleStep
						.getJSONObject("start_location");
				double lat = start_location.getDouble("lat");
				double lng = start_location.getDouble("lng");
				LatLong point = new LatLong(lat,lng);
				points.add(point);

				if (i == steps.length() - 1) {
					JSONObject end_location = singleStep
							.getJSONObject("end_location");
					lat = end_location.getDouble("lat");
					lng = end_location.getDouble("lng");
					point = new LatLong(lat,lng);
					points.add(point);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return points;

	}
	
	public ArrayList<MyPlace> getPlaceSuggest(String query) {
		ArrayList<MyPlace> places = new ArrayList<MyPlace>();
		//https://maps.googleapis.com/maps/api/place/textsearch/json?query=%C4%90%C3%B4ng+%C3%81&sensor=true&key=AIzaSyBUVH4gXbur_kqF0sgsT2wG5mxoJKzeC_c
		String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
		url += URLEncoder.encode(query);
		url += "&sensor=true&key=";
		url += API_KEY;
		Log.d("MYAPP", url);
		String jsonString = getConnect(url);
		Log.d("MYAPP", jsonString);
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray results = json.getJSONArray("results");
			for (int i = 0; i < results.length(); i++) {
				if(i>5) break;
				JSONObject result = results.getJSONObject(i);	
				String formatted_address = result.getString("formatted_address");
				String name = result.getString("name");
				JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
				double lat = location.getDouble("lat");
				double lng = location.getDouble("lng");
				Log.d("MYAPP", name + " " + formatted_address + " " + lat + " " + lng);
				MyPlace place = new MyPlace(name, formatted_address, lat, lng);
				places.add(place);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return places;
	}
	
	

	public String getConnect(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d("MYAPP", "Can't connect to server");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
