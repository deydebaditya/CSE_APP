package com.iashwin28.cse;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List msg;
    public NotificationAdapter(Activity context,
                      ArrayList msg) {
        super(context, R.layout.list_single_notification, msg);
        this.context = context;
        this.msg = msg;


    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single_notification, null, true);
        rowView.setMinimumHeight(100);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.notification1);


        txtTitle.setText((CharSequence) msg.get(position));


        return rowView;
    }
}