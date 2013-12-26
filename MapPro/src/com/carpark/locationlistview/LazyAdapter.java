package com.carpark.locationlistview;

import java.util.ArrayList;

import com.carpark.model.*;
import com.carpark.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<PlaceModel> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public String keywork;
    
    public LazyAdapter(Activity a, ArrayList<PlaceModel> d,String Keywork) {
    	keywork=Keywork;
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.lvlocationitem, null);

        //TextView text=(TextView)vi.findViewById(R.id.text);
        TextView text1=(TextView)vi.findViewById(R.id.small_text);
        ImageView image =(ImageView)vi.findViewById(R.id.image);
        
        //text.setText(data.get(position).getName());
        text1.setText(data.get(position).getAddress());
        
        if(keywork.equalsIgnoreCase("atm"))
        	image.setBackgroundResource(R.drawable.atmicon4);
        else if(keywork.equalsIgnoreCase("school"))
        	image.setBackgroundResource(R.drawable.school);
        else if(keywork.equalsIgnoreCase("hospital"))
        	image.setBackgroundResource(R.drawable.hospital_nurse);
        else if(keywork.equalsIgnoreCase("bus"))
        	image.setBackgroundResource(R.drawable.bus);
        else if(keywork.equalsIgnoreCase("bookstore"))
        	image.setBackgroundResource(R.drawable.bookstore);
        else if(keywork.equalsIgnoreCase("hotel"))
        	image.setBackgroundResource(R.drawable.restaurant);
        else
        	image.setBackgroundResource(R.drawable.icon);
        
        return vi;
    }
}