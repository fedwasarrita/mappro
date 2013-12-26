package com.carpark.model;

public class DrivingDirectionModel {
	
	public DrivingDirectionModel() {
		this.drivingName="";
		this.distance="";
	}
	
	public DrivingDirectionModel(String drivingName, String distance) {	
		this.drivingName = drivingName;
		this.distance = distance;
	}
	
	private String drivingName;
	public String getDrivingName() {
		return drivingName;
	}
	public void setDrivingName(String drivingName) {
		this.drivingName = drivingName;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	private String distance;;
}
