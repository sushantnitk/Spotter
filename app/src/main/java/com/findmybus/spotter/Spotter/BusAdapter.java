package com.findmybus.spotter.Spotter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.findmybus.spotter.R;

import java.util.List;
/**
 * Created by sushantkumar on 14/4/16.
 */
public class BusAdapter extends BaseAdapter {

    Context context;
    List<SpotterBean> busInfo;
    TextView busnumber;
    ImageView busimage;

    public BusAdapter(Context context,List<SpotterBean> busInfo){
        this.context=context;
        this.busInfo=busInfo;
    }

    @Override
    public int getCount() {
        return busInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return busInfo.get(position);
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
        busimage = (ImageView)convertView.findViewById(R.id.busimage);
        busnumber.setText(busInfo.get(position).getRoute());
        return convertView;
    }
}