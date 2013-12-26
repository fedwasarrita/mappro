package com.googledirection;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class PathItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	private Context context;

	public PathItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}

	public PathItemizedOverlay(Drawable marker, Context context) {
		super(boundCenterBottom(marker));
		this.context = context;
	}

	@Override
	protected boolean onTap(int index) {
		// TODO Auto-generated method stub
		OverlayItem item = mapOverlays.get(index);
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(item.getTitle());
		alert.setMessage(item.getSnippet());
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		alert.show();
		return true;
	}

	public void addOverlayItem(OverlayItem item) {
		mapOverlays.add(item);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return mapOverlays.size();
	}

	public void populateNow() {
		this.populate();
	}
}
