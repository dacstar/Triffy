package com.example.triffyandroid.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.Model.AirlineKey;
import com.example.triffyandroid.R;

import java.util.ArrayList;

public class AirlineAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Airline> airlines;

    public AirlineAdapter(Context context, ArrayList<Airline> data) {
        mContext = context;
        airlines = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return airlines.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Airline getItem(int position) {
        return airlines.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent){
        View view = mLayoutInflater.inflate(R.layout.listitem_airline, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.img);

        TextView out_departure = (TextView)view.findViewById(R.id.out_departure);
        TextView in_departure = (TextView)view.findViewById(R.id.in_departure);

        imageView.setImageURI(Uri.parse(airlines.get(position).getImg()));
//        out_departure.setText(airlines.get(position).getOut());
//        in_departure.setText(airlines.get(position).getIn()+"");

        return view;
    }

}
