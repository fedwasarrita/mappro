package com.googlemapapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleGeocoding {

	private String sURL = "http://maps.googleapis.com/maps/api/geocode/json?";
	private URL url;
	final StringBuilder sBuilder = new StringBuilder(sURL);
	private String city = null;
	private String address1 = null;
	public GoogleGeocoding(double lat, double lng) {
		sBuilder.append("latlng=");
		sBuilder.append(lat);
		sBuilder.append(",");
		sBuilder.append(lng);
		sBuilder.append("&sensor=true");
		try {
			url = new URL(sBuilder.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCity() {
		try {
			InputStream is = url.openConnection().getInputStream();
			String result = convertStreamToString(is);
			JSONObject json = new JSONObject(result);
			JSONObject jsonResult = json.getJSONArray("results").getJSONObject(
					0);
			String formattedAddress = jsonResult.getString("formatted_address");
			String[] address = formattedAddress.split(",");
			city = address[address.length-2];
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return city;
	}

	public String getaddress() {
		try {
			InputStream is = url.openConnection().getInputStream();
			String result = convertStreamToString(is);
			JSONObject json = new JSONObject(result);
			JSONObject jsonResult = json.getJSONArray("results").getJSONObject(
					0);
			String formattedAddress = jsonResult.getString("formatted_address");
			String[] address = formattedAddress.split(",");
			address1 = formattedAddress;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return address1;
	}

	public String convertStreamToString(final InputStream is) {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				is));
		final StringBuilder sBuilder = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sBuilder.toString();
	}

}
