package com.carpark;

import java.io.Serializable;

import com.google.api.client.util.Key;

public class BusStation implements Serializable {
	@Key
	public String id;

	@Key
	public String name;

	@Key
	public String reference;

	@Key
	public String icon;

	@Key
	public String vicinity;

	@Key
	public Geometry geometry;

	@Key
	public String[] types;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name + " - " + id + " - " + reference;
	}

	public static class Geometry implements Serializable {
		@Key
		public Location location;
	}

	public static class Location implements Serializable {

		@Key
		public double lat;

		@Key
		public double lng;
	}
}
