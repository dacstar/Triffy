package com.example.triffyandroid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.triffyandroid.Model.Exchange;
import com.example.triffyandroid.Model.Hotel;
import com.example.triffyandroid.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HotelAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Hotel> hotels;
    double exchangeRate;

    public HotelAdapter(Context context, ArrayList<Hotel> data, Exchange exchange) {
        mContext = context;
        hotels = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        exchangeRate = exchange.getBuy();
    }

    @Override
    public int getCount() {
        return hotels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Hotel getItem(int position) {
        return hotels.get(position);
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent){
        View view = mLayoutInflater.inflate(R.layout.listitem_city, null);

        final ImageView imageView = (ImageView)view.findViewById(R.id.img);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(hotels.get(position).getPhoto_url());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(bm);
                } catch(Exception e){

                }

            }
        });

        t.start();

        TextView hotel_name = (TextView)view.findViewById(R.id.hotel_name);
        TextView review_score = (TextView)view.findViewById(R.id.review_score);
        TextView min_price = (TextView)view.findViewById(R.id.min_price);
        TextView KRW = (TextView)view.findViewById(R.id.KRW);

        hotel_name.setText(hotels.get(position).getHotel_name());
        review_score.setText("평점: " + hotels.get(position).getReview_score());
        min_price.setText("달러: " + hotels.get(position).getMin_price() +"$");
        int krw = (int)(hotels.get(position).getMin_price() * exchangeRate);
        KRW.setText("원화: " + krw +"원");

        return view;
    }
}
