package com.carpark.locationlistview;

import java.util.ArrayList;

import com.carpark.model.DrivingDirectionModel;
import com.carpark.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrivingAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<DrivingDirectionModel> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public DrivingAdapter(Activity a, ArrayList<DrivingDirectionModel> d) {
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
        //text.setTextSize(18);
        
        TextView text1=(TextView)vi.findViewById(R.id.small_text);
        ImageView image =(ImageView)vi.findViewById(R.id.image);
        
       // text.setText(data.get(position).getDrivingName());
        text1.setText(data.get(position).getDistance());
        
        String rightleft = data.get(position).getDrivingName();
        
        if(rightleft.contains("Rẽ phải"))
        	image.setBackgroundResource(R.drawable.turnright);
        else if(rightleft.contains("Rẽ trái"))
        	image.setBackgroundResource(R.drawable.turnleft);
        else
        	image.setBackgroundResource(R.drawable.goahead);
        
        return vi;
    }
}