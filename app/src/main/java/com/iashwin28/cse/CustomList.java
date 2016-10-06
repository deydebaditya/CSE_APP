package com.iashwin28.cse;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;

    private final Integer[] imageId;
    private final Integer[] back;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId, Integer[] back) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.back = back;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        rowView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        rowView.setPadding(16,16,16,16);
        rowView.setElevation(20);
        TextView timedisp = (TextView)rowView.findViewById(R.id.time);
       // rowView.setMinimumHeight(180);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setTextColor(R.color.color2);
        txtTitle.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

//       imageView.setImageResource(imageId[position]);
        rowView.setBackgroundResource(back[position]);
        rowView.getBackground().setAlpha(85);
        return rowView;
    }
}