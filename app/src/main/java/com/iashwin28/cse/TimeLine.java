package com.iashwin28.cse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeLine extends AppCompatActivity  {

    ListView ls;
    String message;
    int i=8;
    CustomList1 ad;
    int flag=0;
    int hr,date,min;
    String image,name,year;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
//    String response;
    String serverUrl = "http://cseapp.16mb.com/timshow.php";
    ProgressDialog dialog;
    InputStream isr;
    String result;

    JSONArray list;
    JSONObject jsonobj;
    String stuname,stumsg,stuimage,timeh,timed,day,hour,minute,timem;
    Calendar c;
    SwipeRefreshLayout swipeRefreshLayout;


    List<String> Names = new ArrayList<String>();
    ArrayList<Integer> imageId = new ArrayList<Integer>();
    List<String> msg = new ArrayList<String>();
    List<String> times = new ArrayList<String>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("image");
        year = getIntent().getStringExtra("year");
        c = Calendar.getInstance();
        hr = c.get(Calendar.HOUR_OF_DAY);
        date = c.get(Calendar.DATE);
        min = c.get(Calendar.MINUTE);
        day = String.valueOf(date);
        hour = String.valueOf(hr);
        minute = String.valueOf(min);
        Log.d("hour", String.valueOf(hr));
        final InputStream[] is = {null};
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);



//        dialog = ProgressDialog.show(TimeLine.this, "",
//                "Wait a moment, Fetching messages...", true);
        swipeRefreshLayout.setRefreshing(true);

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(serverUrl); // make sure the url is correct.
                    //add your data
                    nameValuePairs = new ArrayList<NameValuePair>(1);
                    // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                    nameValuePairs.add(new BasicNameValuePair("year", year));

                    // $Edittext_value = $_POST['Edittext_value'];

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.d("andro", "1" + year);
                    //Execute HTTP Post Request
                    response = httpclient.execute(httppost);
                    Log.d("andro", "2");
                    httpentity = response.getEntity();
                    is[0] = httpentity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is[0], "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            final String response = httpclient.execute(httppost, responseHandler);
//            System.out.println("Response : " + response);

//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            // tv.setText("Response from PHP : " + response);
//                            dialog.dismiss();
//                        }
//                    });
                    swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(TimeLine.this,"Refreshed Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });


                    if (!(result.startsWith("F"))) {
                        Log.i("andro", result);
                        try {
                            jsonobj = new JSONObject(result);
                            list = jsonobj.getJSONArray("result");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject c = list.getJSONObject(i);
                                stuname = c.getString("name");
                                stumsg = c.getString("msg");
                                timed = c.getString("day");
                                timeh = c.getString("hour");
                                timem = c.getString("minute");

                                Log.d("andro", stuname + stumsg + timed + timeh);

                                        Log.d("andro","8");

                                        imageId.add(R.mipmap.user);
                                        Names.add(stuname);
                                Log.d("list",Names.get(0));
                                        msg.add(stumsg);
                                        int temph = Integer.parseInt(timeh);
                                        int temp1h = Integer.parseInt(timed);
                                        int temp2h = Integer.parseInt(timem);

                                        Log.i("hour", String.valueOf(temph));
                                        temph = hr - temph;
                                        temp1h = date - temp1h ;
                                        int temp3h = min - temp2h;
                                        if (temp1h==0) {
                                            if (temph == 0) {
                                                if (temp3h < 3) {
                                                    times.add("Just a moment Ago");
                                                } else {
                                                    times.add(String.valueOf(temp3h) + " minutes Ago");
                                                }
                                            } else if (temph == 1) {
                                                if (((min + 60) - temp2h) < 3) {
                                                    times.add("Just a moment Ago");
                                                } else if (((min + 60) - temp2h) > 59) {
                                                    times.add("1 hour Ago");
                                                } else {
                                                    times.add(String.valueOf(((min + 60) - temp2h)) + " minutes Ago");
                                                }
                                            } else {
                                                times.add(String.valueOf(temph) + "Hours Ago");
                                            }
                                        }
                                        else if(temp1h==1)
                                        {
                                            times.add("Yesterday");
                                        }
                                        else if(temp1h==2)
                                        {
                                            times.add("2 days Ago");
                                        }
                                        else if (temp1h==3)
                                        {
                                            times.add("3 days Ago");
                                        }
                                        else
                                        {
                                            times.add("Loooonngg");
                                        }

                                    }

                            TimeLine.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ad = new
                                            CustomList1(TimeLine.this, Names, imageId, msg, times);
                                    ls = (ListView) findViewById(R.id.timelinelistView);
                                    ls.setAdapter(ad);
                                }
                            });




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(TimeLine.this, "Cant fetch timeline", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("andro", e.toString());
                }
            }

        });t.start();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //backbutton pressed
                Intent i = new Intent(TimeLine.this, LoginSuccesss.class);
                startActivity(i);
                finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TimeLine.this, TimeLine_msg.class);
                i.putExtra("image", image);
                i.putExtra("name", name);
                i.putExtra("year", year);
                Log.i("andro", year);
                startActivity(i);

            }

        });




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                Thread t = new Thread(new Runnable() {
                    public void run() {

                        Intent i = new Intent(TimeLine.this,TimeLine.class);
                        startActivity(i);
                        finish();

                        swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(TimeLine.this,"Refreshed Successfully",Toast.LENGTH_SHORT).show();
                                }
                            });

//                        try {
//                            httpclient = new DefaultHttpClient();
//                            httppost = new HttpPost(serverUrl); // make sure the url is correct.
//                            //add your data
//                            nameValuePairs = new ArrayList<NameValuePair>(1);
//                            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
//                            nameValuePairs.add(new BasicNameValuePair("year", year));
//
//                            // $Edittext_value = $_POST['Edittext_value'];
//
//                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                            Log.d("andro", "1" + year);
//                            //Execute HTTP Post Request
//                            response = httpclient.execute(httppost);
//                            Log.d("andro", "2");
//                            httpentity = response.getEntity();
//                            is[0] = httpentity.getContent();
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(is[0], "UTF-8"), 8);
//                            StringBuilder sb = new StringBuilder();
//                            String line = null;
//                            while ((line = reader.readLine()) != null) {
//                                sb.append(line + "\n");
//                            }
//                            result = sb.toString();
////            ResponseHandler<String> responseHandler = new BasicResponseHandler();
////            final String response = httpclient.execute(httppost, responseHandler);
////            System.out.println("Response : " + response);
//
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    // tv.setText("Response from PHP : " + response);
//                                    dialog.dismiss();
//                                }
//                            });
//                            swipeRefreshLayout.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    swipeRefreshLayout.setRefreshing(false);
//                                    Toast.makeText(TimeLine.this,"Refreshed Successfully",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//
//                            if (!(result.startsWith("F"))) {
//                                Log.i("andro", result);
//                                try {
//                                    jsonobj = new JSONObject(result);
//                                    list = jsonobj.getJSONArray("result");
//                                    for (int i = 0; i < list.length(); i++) {
//                                        JSONObject c = list.getJSONObject(i);
//                                        stuname = c.getString("name");
//                                        stumsg = c.getString("msg");
//                                        timed = c.getString("day");
//                                        timeh = c.getString("hour");
//                                        timem = c.getString("minute");
//
//                                        Log.d("andro", stuname + stumsg + timed + timeh +timem);
//
//                                        Log.d("andro","8");
//
//                                        imageId.add(R.mipmap.user);
//                                        Names.add(stuname);
//                                        Log.d("list",Names.get(0));
//                                        msg.add(stumsg);
//                                        int temph = Integer.parseInt(timeh);
//                                        int temp1h = Integer.parseInt(timed);
//                                        int temp2h = Integer.parseInt(timem);
//
//                                        Log.i("hour", String.valueOf(temph));
//                                        temph = hr - temph;
//                                        temp1h = date - temp1h ;
//                                        int temp3h = min - temp2h;
//                                        if (temp1h==0) {
//                                            if (temph == 0) {
//                                                if (temp3h < 3) {
//                                                    times.add("Just a moment Ago");
//                                                } else {
//                                                    times.add(String.valueOf(temp3h) + " minutes Ago");
//                                                }
//                                            } else if (temph == 1) {
//                                                if (((min + 60) - temp2h) < 3) {
//                                                    times.add("Just a moment Ago");
//                                                } else if (((min + 60) - temp2h) > 59) {
//                                                    times.add("1 hour Ago");
//                                                } else {
//                                                    times.add(String.valueOf(((min + 60) - temp2h)) + " minutes Ago");
//                                                }
//                                            } else {
//                                                times.add(String.valueOf(temph) + "Hours Ago");
//                                            }
//                                        }
//                                        else if(temp1h==1)
//                                        {
//                                            times.add("Yesterday");
//                                        }
//                                        else if(temp1h==2)
//                                        {
//                                            times.add("2 days Ago");
//                                        }
//                                        else if (temp1h==3)
//                                        {
//                                            times.add("3 days Ago");
//                                        }
//                                        else
//                                        {
//                                            times.add("Loooonngg");
//                                        }
//
//                                    }
//
//                                    TimeLine.this.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            ad = new
//                                                    CustomList1(TimeLine.this, Names, imageId, msg,times);
//                                            ls = (ListView) findViewById(R.id.timelinelistView);
//                                            ls.setAdapter(ad);
////                                            ad.notifyDataSetChanged();
//                                        }
//                                    });
//
//
//
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                Toast.makeText(TimeLine.this, "Cant fetch timeline", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (Exception e) {
//                            Log.d("andro", e.toString());
//                        }
                    }

                });t.start();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

}
