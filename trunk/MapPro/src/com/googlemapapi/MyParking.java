package com.googlemapapi;

public class MyParking {
	private int id;
	private String address;
	private String place;
	private double lat;
	private double lng;
	private int status;
	
	
	private double distance;
	
	private int b_id;
	private int c_id;
	private int d_id;

	public MyParking() {

	}

	public MyParking(int id, String address, String place, double lat, double lng,
			String time, int num, double distance, boolean favorite, int b_id,
			int c_id, int d_id) {
		super();
		this.id = id;
		this.address = address;
		
		this.lat = lat;
		this.lng = lng;
		
		this.distance = distance;
		
		this.b_id = b_id;
		this.c_id = c_id;
		this.d_id = d_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}


	

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	

	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	


	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	
}
