package com.example.triffyandroid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triffyandroid.Model.Airline;
import com.example.triffyandroid.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Handler;

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
    public View getView(final int position, View converView, ViewGroup parent){
        View view = mLayoutInflater.inflate(R.layout.listitem_airline, null);

        final ImageView imageView = (ImageView)view.findViewById(R.id.img);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(airlines.get(position).getImg());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bm);
                } catch(Exception e){

                }

            }
        });

        t.start();

        TextView out_departure = (TextView)view.findViewById(R.id.out_departure);
        TextView out_arrival = (TextView)view.findViewById(R.id.out_arrival);
        TextView in_departure = (TextView)view.findViewById(R.id.in_departure);
        TextView in_arrival = (TextView)view.findViewById(R.id.in_arrival);
        TextView price = (TextView)view.findViewById(R.id.price);

        out_departure.setText(airlines.get(position).getOutdeparture());
        in_departure.setText(airlines.get(position).getIndeparture());
        out_arrival.setText(airlines.get(position).getOutarrival());
        in_arrival.setText(airlines.get(position).getInarrival());
        price.setText(airlines.get(position).getPrice()+"원");

        return view;
    }

}
