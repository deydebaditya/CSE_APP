package com.iashwin28.cse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

public class Notification_display extends AppCompatActivity {

    ArrayList<String> msg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        msg.add("hey 1st notification");
        msg.add("Hey 2nd notification");
        msg.add("3rd");

        NotificationAdapter adapter = new NotificationAdapter(Notification_display.this, msg);
        ListView ls = (ListView)findViewById(R.id.listView2);
        ls.setAdapter(adapter);
    }



}
