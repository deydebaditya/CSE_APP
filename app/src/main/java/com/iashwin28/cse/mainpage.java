package com.iashwin28.cse;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;

public class mainpage extends AppCompatActivity {

    ImageView img;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        img = (ImageView)findViewById(R.id.imageView2);

//        Intent intent = getIntent();
//        String path = intent.getExtras().getString("imagepath");
//        photo = BitmapFactory.decodeFile(path);
//        img.setImageBitmap(Bitmap.createScaledBitmap(photo,130,130,false));
        try {
            File myfile = new File(Environment.getExternalStorageDirectory(), "profile.jpg");
            BitmapDrawable d = new BitmapDrawable(getResources(), myfile.getAbsolutePath());
            Bitmap a = d.getBitmap();
            Bitmap round = getRoundedShape(a);
            img.setImageBitmap(round);
        }
        catch(NullPointerException ex) {

            ex.printStackTrace();
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
}

