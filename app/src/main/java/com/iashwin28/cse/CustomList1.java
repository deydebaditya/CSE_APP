package com.iashwin28.cse;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomList1 extends ArrayAdapter<String> {

    private final Activity context;
    private final List Names;
    private final List<Integer> imageId;
    private final List times;
    private final List msg;
    public CustomList1(Activity context,
                       List Names, ArrayList<Integer> imageId, List msg, List times) {
        super(context, R.layout.lis_single_timeline, Names);
        this.context = context;
        this.Names = Names;
        this.imageId = imageId;
        this.msg = msg;
        this.times = times;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.lis_single_timeline, null, true);
        rowView.setMinimumHeight(80);
        rowView.setBackgroundColor(Color.WHITE);
        
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView textmsg = (TextView)rowView.findViewById(R.id.textView6);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView time = (TextView) rowView.findViewById(R.id.time);
      //  String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        DateFormat.getDateTimeInstance().format() = new Date();
//        int date = dt.getDay();
//        int hr = dt.getHours();
//        int min = dt.getMinutes();
// textView is the TextView view that should display it
       // time.setText(currentDateTimeString);
        rowView.setBackgroundColor(Color.WHITE);
        time.setText((CharSequence)times.get(position));
        txtTitle.setText((CharSequence) Names.get(position));
        textmsg.setText((CharSequence) msg.get(position));
        imageView.setImageResource(imageId.get(position));
        return rowView;
    }
}