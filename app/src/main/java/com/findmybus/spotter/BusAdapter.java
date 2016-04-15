package com.findmybus.spotter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sushantkumar on 14/4/16.
 */
public class BusAdapter extends BaseAdapter {

    static Context context;
    List<String> busInfo;
    TextView busnumber;
    ImageView busImage;

    public BusAdapter(Context context,List<String> busInfo){
        this.context=context;
        this.busInfo=busInfo;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView= LayoutInflater.from(context).inflate(R.layout.buses_list,parent,false);

        busnumber=(TextView)convertView.findViewById(R.id.busnumber);
        busImage=(ImageView)convertView.findViewById(R.id.busimage);
        return convertView;
    }
}
