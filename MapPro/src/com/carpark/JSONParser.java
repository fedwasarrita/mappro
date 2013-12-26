package com.carpark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;;

public class JSONParser {

	public JSONObject getJson(String text) {
		JSONObject jObj = null;
		try {
			
			jObj = new JSONObject(text.toString());

		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		return jObj;
	}

	public JSONArray getJSONarray(String text){
	   
	    JSONArray jArray = null;
	    try
	    {
	    jArray = new JSONArray(text);    
	    }
	    catch (JSONException e) {
			e.printStackTrace();
		}
	    return jArray;
	}
}
