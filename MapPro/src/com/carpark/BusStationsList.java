package com.carpark;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;

public class BusStationsList implements Serializable {
	@Key
	public String status;

	@Key
	public List<BusStation> results;

	@Key
	public String next_page_token;
}
