package com.iashwin28.cse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;

public class LoginSuccesss extends AppCompatActivity {

    ImageView background,profile;
    ListView ls;
    TextView name;
    TextView rollnumber;
    TextView yr;
    int id =0;
    Notification n;
    String Name;
    String roll;
    String year;
    String image;
    Bitmap a;
    TextView rol;
    ImageView tryimg;


    String[] web = {
            "TIME-TABLE",
            "Notifications",
            "TimeLine",
            "Discussion Forum",
            "File Sharing",
            "Your Profile",
            "Logout"
    } ;
    Integer[] imageId = {
            R.drawable.calendar,
            R.drawable.notifications,
            R.drawable.tl,
            R.drawable.discuss,
            R.drawable.share,
            R.drawable.profile,
            R.drawable.logout

    };
    Integer[] back = {
           R.drawable.tt,
            R.drawable.tt,
            R.drawable.tt,
            R.drawable.tt,
            R.drawable.tt,
            R.drawable.tt,
            R.drawable.tt
    };


    Threadnoti threadN = new Threadnoti();

    NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
    int[] uniqueID = {1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_successs);


        tryimg = (ImageView)findViewById(R.id.imagetool);
        Name = SaveSharedPreference.getUserName(LoginSuccesss.this);
        roll = SaveSharedPreference.getUserRoll(LoginSuccesss.this);
        year = SaveSharedPreference.getUserYear(LoginSuccesss.this);
        image = SaveSharedPreference.getUserImage(LoginSuccesss.this);

        n= new Notification(year,id);
        threadN.start();

        if(SaveSharedPreference.getUserName(LoginSuccesss.this).length() == 0)
        {
            Intent i = new Intent(LoginSuccesss.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else
        {

        }
        rol=(TextView)findViewById(R.id.roll);
        rol.setText(roll);

//        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
//                | ActionBar.DISPLAY_SHOW_CUSTOM);
//        ImageView imageView = new ImageView(actionBar.getThemedContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER);
//        imageView.setImageResource(R.mipmap.user);
//        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
//                | Gravity.CENTER_VERTICAL);
//        layoutParams.rightMargin = 40;
//        imageView.setLayoutParams(layoutParams);
//        actionBar.setCustomView(imageView);

        CustomList adapter = new
                CustomList(LoginSuccesss.this, web, imageId,back);
        Log.d("andro",image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
     //   profile=(ImageView)findViewById(R.id.imageView4);

//        background =(ImageView)findViewById(R.id.gecaimg);
//       name = (TextView)findViewById(R.id.textname);
//        rollnumber=(TextView)findViewById(R.id.rolltext);
//        yr=(TextView)findViewById(R.id.yeartext);
//        name.setText(Name);
//        rollnumber.setText(roll);
//        if(year.startsWith("f")) {
//            yr.setText("FE-CSE");
//        }
//        else if(year.startsWith("s"))
//        {
//            yr.setText("SE-CSE");
//        }
//        else if(year.startsWith("t"))
//        {
//            yr.setText("TE-CSE");
//        }
//        else if(year.startsWith("b"))
//        {
//            yr.setText("BE-CSE");
//        }

        ls = (ListView)findViewById(R.id.listView);
        ls.setAdapter(adapter);
//        final NotificationCompat.Builder notification;
//        final int[] uniqueID = {1};
        //notification = new NotificationCompat.Builder(this);
        //notification.setAutoCancel(true);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
              //  Toast.makeText(LoginSuccesss.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                if(position==0)
                {
                    Intent i = new Intent(LoginSuccesss.this,TimeTable.class);
                    startActivity(i);

                }
                if(position==1)
                {
                    Intent i = new Intent(LoginSuccesss.this, Notification_display.class);
                    startActivity(i);
                }
//               if(position==3)
//               {
//                   Toast.makeText(LoginSuccesss.this,"New notification generated!",Toast.LENGTH_SHORT ).show();
//                   notification.setSmallIcon(R.mipmap.user);
//                   notification.setTicker("New Record is Added");
//                   notification.setWhen(SystemClock.currentThreadTimeMillis());
//                   notification.setContentTitle("My Project");
//                   notification.setContentText("One new Record is Added");
//                   //Toast.makeText(getApplicationContext(),"New record added",Toast.LENGTH_SHORT).show();
//
//                   Intent intent = new Intent(LoginSuccesss.this, LoginSuccesss.class);
//                   PendingIntent pendingIntent = PendingIntent.getActivity(LoginSuccesss.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                   notification.setContentIntent(pendingIntent);
//
//                   NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                   nm.notify(uniqueID[0], notification.build());
//                   uniqueID[0]++;
//               }

                if(position==2)
                {
                    Intent i = new Intent(LoginSuccesss.this,TimeLine.class);
                    i.putExtra("name",Name);
                    i.putExtra("image",image);
                    i.putExtra("year",year);
                    Log.i("andro","year"+year);
                    startActivity(i);
                }
                if(position==6)
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginSuccesss.this);
                    builder.setTitle("Logout!");
                    builder.setMessage("Are you sure want to Logout?")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SaveSharedPreference.clearUserName(LoginSuccesss.this);
                                    Intent i = new Intent(LoginSuccesss.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            return;
                        }
                    });
                    builder.show();


                }

            }
        });

        try {
            File myfile = new File(Environment.getExternalStorageDirectory(), "profile.jpg");
            BitmapDrawable d = new BitmapDrawable(getResources(), myfile.getAbsolutePath());
            Bitmap a = d.getBitmap();
            Log.d("andro",image);
//            byte[] decodebytes = Base64.decode(image.getBytes(),Base64.NO_PADDING);
//
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            ByteArrayInputStream bs = new ByteArrayInputStream(decodebytes);
//
//
//            //  options.inTempStorage = new byte[1024 *32];
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            options.inSampleSize=2;
//            options.inJustDecodeBounds = true;
//            Log.d("andro","return zali"+decodebytes.length);
//
//
//             a= BitmapFactory.decodeByteArray(decodebytes,0,decodebytes.length,options);
//          //  a = BitmapFactory.decodeStream(bs);
//            Log.d("andro","bitmap"+a.getHeight()+","+a.getWidth());
//        //    Bitmap b = Bitmap.createBitmap(a,10,10 ,50 ,50);
//
//            Log.d("andro","bitmap"+image.getBytes());

         // a=  DecodeBase64(image);


            Bitmap round = getRoundedShape(a);
           // String text = a.toString();
        //    Log.d("andro","imagecode"+a.toString());
            tryimg.setImageBitmap(round);
        }
        catch(NullPointerException ex) {

            Log.d("andro",ex.toString());
        }


    }
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 200;
        int targetHeight = 200;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
    static Bitmap DecodeBase64(String img) {
        byte[] decodebytes = Base64.decode(img.getBytes(),Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[1024 *32];
        options.inSampleSize=2;
        Log.d("andro","return zali"+decodebytes.length);
        ByteArrayInputStream bs = new ByteArrayInputStream(decodebytes);


        Bitmap a= BitmapFactory.decodeByteArray(decodebytes,0,decodebytes.length,options);

        Log.d("andro","bitmap"+a.toString());
        return a;
    }


    private class Threadnoti extends Thread
    {
        public void run()
        {
            int x=id;
            Notification n= new Notification(year,id);
            id=n.search();
            if(id!=-1)
            {
                while(x<=id)
                {
                    String s =n.display(x);
//                    Log.d("andro",s);
                    notification.setAutoCancel(true);
                    notification.setSmallIcon(R.mipmap.user);
                    notification.setTicker("New Record is Added");
                    notification.setWhen(SystemClock.currentThreadTimeMillis());
                    notification.setContentTitle("My Project");
                    notification.setContentText("One new Record is Added");

                    Intent intent = new Intent(LoginSuccesss.this, LoginSuccesss.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(LoginSuccesss.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pendingIntent);

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(uniqueID[0], notification.build());
                    uniqueID[0]++;
                    x++;
                }
            }
        }
    }


    }





