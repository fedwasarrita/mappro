package com.googledirection;

import android.util.Log;

import com.google.android.maps.GeoPoint;

public class Segment {
	private GeoPoint start;
	private String instruction;
	private int length;
	private double distance;

	public Segment() {
	}

	public void setInstruction(String ins) {
		this.instruction = ins;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public void setPoint(final GeoPoint point) {
		this.start = point;
	}

	public GeoPoint getPoint() {
		return this.start;
	}

	public void setLength(final int length) {
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return this.distance;
	}

	public Segment copy() {
		final Segment copy = new Segment();
		copy.start = this.start;
		copy.instruction = this.instruction;
		copy.length=this.length;
		copy.distance = this.distance;
		return copy;

	}

}
