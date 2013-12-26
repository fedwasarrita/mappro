package com.googledirection;

import java.util.Iterator;
import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class RouteOverlay extends Overlay {
	private final List<GeoPoint> routePoints;
	private int color;
	private final Path path;
	private final Point point;
	private final Paint paint;
	OverlayItem overlayItem;

	public RouteOverlay(final Route route, final int color) {
		super();
		routePoints = route.getPoints();
		this.color = color;
		path = new Path();
		paint = new Paint();
		point = new Point();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		paint.setColor(color);
		paint.setAlpha(120);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		redrawPath(mapView,canvas);
		canvas.drawPath(path, paint);

	}

	public final void setColor(final int color) {
		this.color = color;
	}

	public final void clear() {
		routePoints.clear();
	}

	private void redrawPath(final MapView mapView,Canvas canvas) {
		final Projection projection = mapView.getProjection();
		path.rewind();
		final Iterator<GeoPoint> it = routePoints.iterator();
		projection.toPixels(it.next(), point);
		path.moveTo(point.x, point.y);
		while (it.hasNext()) {
			projection.toPixels(it.next(), point);
			path.lineTo(point.x, point.y);
		}
		path.setLastPoint(point.x, point.y);
	}
}
