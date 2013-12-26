package com.carpark;

import java.util.ArrayList;

import com.googlemapapi.MyParking;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 
public class ResultAdapter extends BaseAdapter {
    Context context;
    ArrayList<MyParking> rowItems;
 
    public ResultAdapter(Context context, ArrayList<MyParking> items) {
    	Log.d("Custom Adapter","Create new adapter");
        this.context = context;
        this.rowItems = items;
    }
 
    /*private view holder class*/
    private class ViewHolder {
    	ImageView icon;
        TextView place;
        TextView address;
        TextView time;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater)
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_result_item, null);
            holder = new ViewHolder();
            holder.place = (TextView) convertView.findViewById(R.id.name);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        MyParking rowItem = (MyParking) getItem(position);
 
        holder.place.setText(rowItem.getPlace());
        holder.address.setText(rowItem.getAddress());
        
        
 
        return convertView;
    }
 
    @Override
    public int getCount() {
        return rowItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}