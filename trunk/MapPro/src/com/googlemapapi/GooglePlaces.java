package com.googlemapapi;

import java.io.IOException;

import android.util.Log;

import com.carpark.BusStationsList;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

public class GooglePlaces {
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String API_KEY = "AIzaSyDDmpP8WuRfc5uEH87_bQrQNwaIIBf44rI";
	private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";

	public BusStationsList search(double latitude, double longtitude,
			double radius, String type) throws Exception {
		try {
			HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
			HttpRequest request = httpRequestFactory
					.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("location", latitude + "," + longtitude);
			request.getUrl().put("radius", radius);
			request.getUrl().put("sensor", "true");
			request.getUrl().put("types", type);
			BusStationsList list = request.execute().parseAs(
					BusStationsList.class);
			return list;

		} catch (HttpResponseException e) {
			Log.e("Error", e.getMessage());
			return null;
		}
	}

	public BusStationsList getMoreStation(String nextToken) throws Exception {
		HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
		HttpRequest request = httpRequestFactory
				.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
		request.getUrl().put("pagetoken", nextToken);
		request.getUrl().put("sensor", "true");
		request.getUrl().put("key", API_KEY);
		BusStationsList list = request.execute().parseAs(BusStationsList.class);
		return list;
	}


	public static HttpRequestFactory createRequestFactory(
			final HttpTransport transport) {
		return transport.createRequestFactory(new HttpRequestInitializer() {

			public void initialize(HttpRequest request) throws IOException {
				// TODO Auto-generated method stub
				GoogleHeaders headers = new GoogleHeaders();
				headers.setApplicationName("Bus Station");
				request.setHeaders(headers);
				JsonHttpParser parser = new JsonHttpParser(new JacksonFactory());
				request.addParser(parser);
			}
		});

	}
}
