package com.googlemapapi;



import com.carpark.R;
import com.carpark.R.drawable;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class MyLocation extends Service implements LocationListener {

	private final Context context;
	boolean isGPSEnabled = false;
	boolean isInternetConnected = false;
	boolean canGetLocation = false;
	Location location = null;
	double latitude;
	double longtitude;
	protected LocationManager locationManager;

	public MyLocation(Context context) {
		this.context = context;
		latitude = 0;
		longtitude = 0;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isInternetConnected = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (!isGPSEnabled && !isInternetConnected) {
			} else {
				this.canGetLocation = true;
				if (isInternetConnected) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 0, 0, this);
					Log.d("Network", "Network Enabled");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longtitude = location.getLongitude();
						}
					}
				}
				if (isGPSEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 1000, 5, this);
					Log.d("GPS", "GPS Enabled");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longtitude = location.getLongitude();
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	public void disableGPS() {
		if (locationManager != null)
			locationManager.removeUpdates(MyLocation.this);
	}

	public void showSettingAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("Cài đặt GPS");
		alertDialog.setIcon(R.drawable.fail);
		alertDialog.setMessage("GPS chưa được kích hoạt.Kích hoạt ngay?");
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						context.startActivity(intent);
					}
				});
		alertDialog.setNegativeButton("Hủy",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		alertDialog.show();
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		latitude = location.getLatitude();
		longtitude = location.getLongitude();

	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
