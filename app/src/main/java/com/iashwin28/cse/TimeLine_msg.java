package com.iashwin28.cse;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeLine_msg extends AppCompatActivity {
    EditText msg;
    String image,name,year,day,minute,hour,message,id="1";
    HttpPost httppost;
    HttpResponse respons;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    InputStream isr;
    ImageView img ;
    int flag=0;
    String response;
    String serverUrl = "http://cseapp.16mb.com/timeinsert.php";
    ProgressDialog dialog;
    Calendar c;
    String finalresponse,echo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line_msg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image = getIntent().getStringExtra("image");
        name = getIntent().getStringExtra("name");
        year = getIntent().getStringExtra("year");
        c= Calendar.getInstance();
        int hr = c.get(Calendar.HOUR_OF_DAY);
        int date = c.get(Calendar.DATE);
        int min = c.get(Calendar.MINUTE);
        day = String.valueOf(date);
        hour = String.valueOf(hr);
        minute = String.valueOf(min);
        Log.d("andro",hour+" "+day);


        img = (ImageView)findViewById(R.id.image);
        try {
            Bitmap bmp = DecodeBase64(image);
            img.setImageBitmap(bmp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //backbutton pressed
                Intent i = new Intent(TimeLine_msg.this, TimeLine.class);
                startActivity(i);
                finish();
            }
        });



    }


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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void post(View view) {

        msg = (EditText) findViewById(R.id.editText3);
        message = msg.getText().toString();
        String length = String.valueOf(message.length());
        Log.d("andro", length);
        if (message.length() != 0) {

            dialog = ProgressDialog.show(TimeLine_msg.this, "",
                    "Posting...", true);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        httpclient = new DefaultHttpClient();
                        httppost = new HttpPost(serverUrl);
                        nameValuePairs = new ArrayList<NameValuePair>(6);
                        // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                        //   nameValuePairs.add(new BasicNameValuePair("id",id));
                        nameValuePairs.add(new BasicNameValuePair("year", year));
                        nameValuePairs.add(new BasicNameValuePair("name", name));
                        nameValuePairs.add(new BasicNameValuePair("msg", message));
                        nameValuePairs.add(new BasicNameValuePair("day", day));
                        nameValuePairs.add(new BasicNameValuePair("hour", hour));
                        nameValuePairs.add(new BasicNameValuePair("minute",minute));

                        Log.d("andro",minute);

                        Log.d("andro", "2");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        Log.d("andro", "3");
                        respons = httpclient.execute(httppost);
                        Log.d("andro", "4");
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                      //  response = httpclient.execute(httppost, responseHandler);
                     //   Log.d("andro", "response :" + response);
                        Log.d("andro", "5");
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // tv.setText("Response from PHP : " + response);
                                dialog.dismiss();
                            }
                        });
                        echo = EntityUtils.toString(respons.getEntity());
                        Log.d("andro",echo);

                        finalresponse=echo;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (finalresponse.startsWith("S")) {
//                                    Log.i("andro", response);

                                    Toast.makeText(TimeLine_msg.this,"Posted Successfully!",Toast.LENGTH_SHORT).show();
                                    Intent menuIntent = new Intent(TimeLine_msg.this, TimeLine.class);
                                    menuIntent.putExtra("year",year);
                                    menuIntent.putExtra("name",name);
                                 //   Toast.makeText(TimeLine_msg.this,"Posted Successfully!",Toast.LENGTH_SHORT);
                                    startActivity(menuIntent);

                                    finish();
                                }


                                 else {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(TimeLine_msg.this);
                                            builder.setTitle("Unsuccessful Post!");
                                            builder.setMessage("Due to some reasons your post cant posted")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Try Again!", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {

                                                        }
                                                    });
                                            AlertDialog alert = builder.create();
                                            alert.show();


                                        }
                                    });
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
        }
        else {
            msg.setError("This Field Should not empty!");
        }
    }




            static Bitmap DecodeBase64(String img) {
                byte[] decodebytes = Base64.decode(img.getBytes(), Base64.DEFAULT);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inDither = false;
                options.inPurgeable = true;
                options.inInputShareable = true;
                options.inTempStorage = new byte[1024 * 32];
                options.inSampleSize = 2;
                Log.d("andro", "return zali" + decodebytes.length);
                ByteArrayInputStream bs = new ByteArrayInputStream(decodebytes);


                Bitmap a = BitmapFactory.decodeByteArray(decodebytes, 0, decodebytes.length, options);

                Log.d("andro", "bitmap" + a.toString());
                return a;
            }

        }