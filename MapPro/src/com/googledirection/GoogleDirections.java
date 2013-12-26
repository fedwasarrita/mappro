package com.googledirection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.maps.GeoPoint;

public class GoogleDirections extends XMLParser {
	private int distance;

	public GoogleDirections(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public Route parse() {
		// TODO Auto-generated method stub
		final String result = convertStreamToString(this.getInputStream());
		final Route route = new Route();
		final Segment segment = new Segment();
		try {
			final JSONObject json = new JSONObject(result);
			final JSONObject jsonRoute = json.getJSONArray("routes")
					.getJSONObject(0);
			final JSONObject jsonLeg = jsonRoute.getJSONArray("legs")
					.getJSONObject(0);
			final JSONArray steps = jsonLeg.getJSONArray("steps");
			final int numSteps = steps.length();
			route.setName(jsonLeg.getString("start_address") + " to "
					+ jsonLeg.getString("end_address"));
			route.setLength(jsonLeg.getJSONObject("distance").getInt("value"));
			route.setCopyright(jsonRoute.getString("copyrights"));
			if (!jsonRoute.getJSONArray("warnings").isNull(0)) {
				route.setWarning(jsonRoute.getJSONArray("warnings")
						.getString(0));
			}
			for (int i = 0; i < numSteps; i++) {
				final JSONObject step = steps.getJSONObject(i);
				final JSONObject start = step.getJSONObject("start_location");
				final GeoPoint position = new GeoPoint(
						(int) (start.getDouble("lat") * 1E6),
						(int) (start.getDouble("lng") * 1E6));
				segment.setPoint(position);
				final int length = step.getJSONObject("distance").getInt(
						"value");
				distance += length;
				segment.setLength(length);
				segment.setDistance(distance / 1000);
				segment.setInstruction(step.getString("html_instructions")
						.replaceAll("<(.*?)*>", ""));
				route.addPoints(decodePolyline(step.getJSONObject("polyline")
						.getString("points")));
				route.addSegment(segment.copy());
			}
		} catch (JSONException e) {
			Log.d("113", "Google JSON Parser: " + url);
		}
		return route;
	}

	private static String convertStreamToString(final InputStream is) {
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

	private List<GeoPoint> decodePolyline(final String polyline) {
		int length = polyline.length();
		int index = 0;
		List<GeoPoint> decoded = new ArrayList<GeoPoint>();
		int lat = 0;
		int lng = 0;
		while (index < length) {
			int b;
			int shift = 0;
			int result = 0;
			do {
				b = polyline.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;
			shift = 0;
			result = 0;
			do {
				b = polyline.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;
			decoded.add(new GeoPoint((int) (lat * 1E6 / 1E5),
					(int) (lng * 1E6 / 1E5)));
		}
		return decoded;
	}
}
