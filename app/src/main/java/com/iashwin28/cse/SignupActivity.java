package com.iashwin28.cse;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    HttpPost httppost;
    HttpResponse respons;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    private final String serverUrl = "http://cseapp.16mb.com/sign_up.php";
    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;
    EditText _phoneText;
    EditText _rollnum;
    Button _signupButton;
    String response;

    TextView _loginLink, photo_text;
    ImageView userimg;
    static final int Image_capture = 1;
    String Photopath;
    RadioGroup year;
    RadioButton rb;
    int sec;
    String name,email,password,phone,roll,yr="",imageencode;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Bitmap bmp;
    Bitmap photo,decoded;
    Uri uri;
    String imagepath;
    final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);


        _signupButton = (Button) findViewById(R.id.btn_signup);
        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _rollnum = (EditText) findViewById(R.id.input_roll);
        _phoneText = (EditText) findViewById(R.id.inputPhone);
        year = (RadioGroup) findViewById(R.id.rg);
        _loginLink = (TextView) findViewById(R.id.link_login);
        userimg = (ImageView) findViewById(R.id.userimg);
        photo_text = (TextView) findViewById(R.id.photo);

        TextView myTextview = (TextView) findViewById(R.id.textView4);
        Typeface mytypeface;
        mytypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/gh.ttf");
        myTextview.setTypeface(mytypeface);

        if (!hascamera()) {
            // userimg.setEnabled(false);
            photo_text.setEnabled(false);
            Toast.makeText(SignupActivity.this, "Camera nahi hai", Toast.LENGTH_SHORT).show();
        }
    }


    public void csesignup(View v) {


                    signup();

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }
        if(isNetworkAvailable()) {


            dialog = ProgressDialog.show(SignupActivity.this, "",
                    "Creating Account...", true);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        onSignupSuccess();

                    }
                    catch(Exception e)
                    {
                        Log.d("andro","8");
                    }
                }
            });
            thread.start();
        }
        else if(!isNetworkAvailable())
        {
            Log.d("andro","not connected");

            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setTitle("Connectivity Error.");
            builder.setMessage("Check your Internet Connection.")
                    .setCancelable(false)
                    .setPositiveButton("Goto Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final Context ctx = getApplicationContext();
                            Intent i = new Intent(Settings.ACTION_SETTINGS);
                            // i.setClassName("com.android.phone","com.android.phone.NetworkSetting");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ctx.startActivity(i);
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    return;
                }
            });
            builder.show();
        }
    }

    public void gotosignin(View view) {
        Intent myintent = new Intent(this, MainActivity.class);
        startActivity(myintent);
        finish();
    }



    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

         name = _nameText.getText().toString();
         email = _emailText.getText().toString();
         password = _passwordText.getText().toString();
        phone = _phoneText.getText().toString();
        roll =_rollnum.getText().toString();
       sec = year.getCheckedRadioButtonId();
        rb = (RadioButton)findViewById(sec);

        if(rb==(RadioButton)findViewById(R.id.FE))
            yr = "fe";
        else if(rb==(RadioButton)findViewById(R.id.SE))
            yr="se";
        else if(rb==(RadioButton)findViewById(R.id.TE))
            yr="te";
        else if(rb==(RadioButton)findViewById(R.id.BE))
            yr="be";

        if(yr=="")
        {
            Toast.makeText(SignupActivity.this,"Please Select Year of Study",Toast.LENGTH_LONG).show();
            valid=false;
        }

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            _passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if(phone.length()!=10)
        {
            _phoneText.setError("Enter 10 digit PhoneNumber");
            valid=false;
        }
        else
        {
            _phoneText.setError(null);
        }
        if(roll.length()==11)
        {
            roll=roll.toUpperCase();
            if((roll.charAt(0)=='B')&&(roll.charAt(1)=='E')&&(roll.charAt(5)=='0')&&(roll.charAt(6)=='5')&&(roll.charAt(7)=='F'))
            {
                _rollnum.setError(null);

            }
            else
            {
                _rollnum.setError("Not in the Specified Format");
            }

        }


        return valid;
    }

    private boolean hascamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
//    private File createImageFile() throws IOException {
//    //    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//       // String imageFileName = "JPEG_" + timeStamp + "_";
//      //  File storageDir = Environment.getExternalStoragePublicDirectory(
//           //     Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                "test",  /* prefix */
//                ".jpg",         /* suffix */
//                Environment.getRootDirectory()     /* directory */
//        );
//        Log.d("andro","path:test.jpg"+Environment.getRootDirectory());
//
//        // Save a file: path for use with ACTION_VIEW intents
//        Photopath = "file:" + image.getAbsolutePath();
//        return image;
//    }

    private File imageFile() throws IOException {
        // Create an image file name


      //  ContextWrapper cw = new ContextWrapper(getActivity());
        File directory = Environment.getExternalStorageDirectory();

        //   FileOperations.checkDirectory(directory, false);
        Photopath = directory.getAbsolutePath();
        return new File(directory,"profile.jpg");

    }

    public void userimage(View view) throws IOException {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                File photoFile = null;



                                try {
                                    photoFile = imageFile();
                                    imagepath = photoFile.getAbsolutePath();
                                    galleryAddPic();
                                } catch (IOException ex) {
                                    // Error occurred while creating the File
                                    Toast.makeText(SignupActivity.this, "Error creating file", Toast.LENGTH_SHORT).show();
                                }
                                if (photoFile != null) {
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                    startActivityForResult(intent, 1);

                                }

                            }
                        }
                        else if (options[item].equals("Choose from Gallery"))
                        {
                            Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);

                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }

                });
        AlertDialog alert = builder.create();
        alert.show();
                 


        Log.i("abc", "click zala");
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagepath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
//            Bundle extras = data.getExtras();
//            Bitmap photo = (Bitmap) extras.get("data");
                photo = BitmapFactory.decodeFile(imagepath);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG,200,out);
//            decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                Bitmap round = getRoundedShape(photo);
                imageencode = getStringImage(round);
                userimg.setImageBitmap(round);
                photo_text.setText("Photo Added Successfully");


            }
        else if (requestCode == 2) {

            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Bitmap round = getRoundedShape(thumbnail);
                imageencode = getStringImage(round);
                Log.i("path of image from gallery......******************.........", picturePath+"");
            userimg.setImageBitmap(round);
                photo_text.setText("Photo Added Successfully");
                File picture = new File(picturePath);
                String picturename = picture.getName();
                File dir = Environment.getExternalStorageDirectory();
                File f = new File(dir,"profile.jpg");
                try {
                    copyFile(picture,f);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }



    public Context getActivity() {
        Context activity = getApplicationContext();
        return activity;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

    }


    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 300;
        int targetHeight = 300;
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
    public void copyFile(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // ekek transfer max 5 mb cha pic
        byte[] buf = new byte[5000];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    public void onSignupSuccess() {

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//        if (isNetworkAvailable()) {
            try {

                    httpclient = new DefaultHttpClient();
                    httppost = new HttpPost(serverUrl); // make sure the url is correct.
                    Log.d("andro", "url passed");
                    nameValuePairs = new ArrayList<NameValuePair>(7);
                    // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                    nameValuePairs.add(new BasicNameValuePair("roll", roll));  // $Edittext_value = $_POST['Edittext_value'];
                    nameValuePairs.add(new BasicNameValuePair("name", name));
                    nameValuePairs.add(new BasicNameValuePair("pass", password));
                    nameValuePairs.add(new BasicNameValuePair("email", email));
                    nameValuePairs.add(new BasicNameValuePair("phone", phone));
                    nameValuePairs.add(new BasicNameValuePair("year", yr));
                    nameValuePairs.add(new BasicNameValuePair("image", imageencode));
                    Log.d("andro", "2");
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    Log.d("andro", "3");
                    respons = httpclient.execute(httppost);
                    Log.d("andro", "4");
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = httpclient.execute(httppost, responseHandler);
                    Log.d("andro", "response :" + response);
                    Log.d("andro", "5");
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // tv.setText("Response from PHP : " + response);
                            dialog.dismiss();
                        }
                    });
                String echo = EntityUtils.toString(respons.getEntity());
                Log.d("andro",echo);




                    if (echo.startsWith("A")) {
                        Log.i("andro", response);


//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString(Roll,name);
                      //  Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        runOnUiThread(new Runnable() {
                                          public void run() {
                                              AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                              builder.setTitle("Signup Successful");
                                              builder.setMessage("Account Created Successfully!")
                                                      .setCancelable(false)
                                                      .setPositiveButton("LOGIN NOW", new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog, int id) {
                                                              Intent menuIntent = new Intent(SignupActivity.this, LoginSuccesss.class);
                                                              menuIntent.putExtra("flag", 1);
                                                              startActivity(menuIntent);
                                                              finish();
                                                          }
                                                      });
                                              AlertDialog alert = builder.create();
                                              alert.show();



                                          }
                                      });

//                        Intent menuIntent = new Intent(SignupActivity.this, LoginSuccesss.class);
//                            menuIntent.putExtra("rollnum",name);



                        // startActivity(new Intent(AndroidPHPConnectionDemo.this, UserPage.class));
                    } else {
                        Log.d("andro", response);
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setTitle("Signup Error!.");
                                builder.setMessage("Account Not Created.")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                    }

                } catch (Exception e) {

                    Log.d("andro", "Exception :" + e.toString());
                }
            }


//        });
//        thread.start();
//    }
//        catch(Exception e)
//        {
//            Log.d("andro","Exception :"+e.toString());
//        }
//    }
//});
//        thread.start();




    public void showAlert() {
        SignupActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle("Signup Error!.");
                builder.setMessage("Account Not Created.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}

