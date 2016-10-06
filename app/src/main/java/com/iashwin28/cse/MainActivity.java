package com.iashwin28.cse;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    HttpPost httppost;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Roll = "rollKey";
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    EditText rollnum;
    EditText pass;
    int flag = 0;
    String myJSON;
    JSONObject jsonobj;
    JSONArray student = null;
    String roll1, name1, pass1, year1, img1;


    String name, password;
    private final String serverUrl = "http://cseapp.16mb.com/login.php";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    //  @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View xyz = null;
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                xyz = inflater.inflate(R.layout.about_geca, container, false);
                TextView myTextview = (TextView) xyz.findViewById(R.id.titlegeca);
                Typeface mytypeface;
                mytypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/gh.ttf");
                myTextview.setTypeface(mytypeface);
                // Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

                Animation fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
                myTextview.startAnimation(fadein);
                TextView body = (TextView) xyz.findViewById(R.id.textView2);
                body.setText(Html.fromHtml("<b><i>Mission:</i></b><br /><small>In pursuit of global competitiveness ,the institute is committed to excel in engineering education and research with concern for environment and society.</small><br/><br/><b><i>Vision:</i></b><br /><small>1. To provide conductive environment for academic excellence in engineering education.<br />2. To enhance research and developement along with promotion to sponsored projects and industrial consultancy.<br />3. To foster developement of students by creating awareness for need of society, sustainable developement and human values.</small>"));
                Typeface mytypeface2;
                mytypeface2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/trebuc.ttf");
                body.setTypeface(mytypeface2);
            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                xyz = inflater.inflate(R.layout.login, container, false);
                BackgroundPainter backgroundPainter = new BackgroundPainter();


                int color1 = ContextCompat.getColor(getContext(), R.color.color1);
                int color2 = ContextCompat.getColor(getContext(), R.color.color2);
                int color3 = ContextCompat.getColor(getContext(), R.color.color3);
                int color31 = ContextCompat.getColor(getContext(), R.color.color31);
                int color4 = ContextCompat.getColor(getContext(), R.color.color4);
                int color5 = ContextCompat.getColor(getContext(), R.color.color5);
                int color6 = ContextCompat.getColor(getContext(), R.color.color6);
                int color7 = ContextCompat.getColor(getContext(), R.color.color7);
                int color8 = ContextCompat.getColor(getContext(), R.color.color8);
                int color9 = ContextCompat.getColor(getContext(), R.color.color9);
                int color10 = ContextCompat.getColor(getContext(), R.color.color10);
                int color11 = ContextCompat.getColor(getContext(), R.color.color11);
                int color12 = ContextCompat.getColor(getContext(), R.color.color12);
                int color13 = ContextCompat.getColor(getContext(), R.color.color13);
                int color14 = ContextCompat.getColor(getContext(), R.color.color14);
                int color15 = ContextCompat.getColor(getContext(), R.color.color15);
                int color16 = ContextCompat.getColor(getContext(), R.color.color16);
                int color17 = ContextCompat.getColor(getContext(), R.color.color17);
                int color18 = ContextCompat.getColor(getContext(), R.color.color18);
                int color19 = ContextCompat.getColor(getContext(), R.color.color19);
                int color20 = ContextCompat.getColor(getContext(), R.color.color20);
                int color21 = ContextCompat.getColor(getContext(), R.color.color21);
                int color22 = ContextCompat.getColor(getContext(), R.color.color22);
                int color23 = ContextCompat.getColor(getContext(), R.color.color23);
                int color24 = ContextCompat.getColor(getContext(), R.color.color24);
                int color25 = ContextCompat.getColor(getContext(), R.color.color25);
                int color26 = ContextCompat.getColor(getContext(), R.color.color26);
                int color27 = ContextCompat.getColor(getContext(), R.color.color27);

                backgroundPainter.animate(xyz, color20, color21, color22, color23, color24, color31, color6, color7, color10, color11, color12, color13, color14, color16, color18, color20);
                // backgroundPainter.animate(xyz,color21,color22,color23,color24,color31,color6,color7,color10,color11,color12,color13,color14,color16,color18,color20,color21);
                TextView myTextview = (TextView) xyz.findViewById(R.id.title);
                EditText username = (EditText) xyz.findViewById(R.id.editText);
                EditText password = (EditText) xyz.findViewById(R.id.editText2);
                Button login = (Button) xyz.findViewById(R.id.button);
                TextView forgot = (TextView) xyz.findViewById(R.id.forgot);
                TextView accnt = (TextView) xyz.findViewById(R.id.textView);
                Button signup = (Button) xyz.findViewById(R.id.button2);
                ImageView dots = (ImageView) xyz.findViewById(R.id.imageView);
                Typeface mytypeface;
                mytypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/gh.ttf");
                myTextview.setTypeface(mytypeface);
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
                myTextview.setAnimation(animation);
                Animation anim_move = AnimationUtils.loadAnimation(getActivity(), R.anim.move);
                Animation anim_move1 = AnimationUtils.loadAnimation(getActivity(), R.anim.move1);
                Animation zoom = AnimationUtils.loadAnimation(getActivity(), R.anim.zoomin);
                Animation fadein1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein1);
                Animation fadein2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein2);
                Animation zoom1 = AnimationUtils.loadAnimation(getActivity(), R.anim.zoomin1);
                Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                username.setAnimation(anim_move);
                password.setAnimation(anim_move);
                login.setAnimation(zoom);
                forgot.startAnimation(fadein1);
                accnt.startAnimation(fadein2);
                signup.startAnimation(zoom1);
                dots.startAnimation(rotate);


            }

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                xyz = inflater.inflate(R.layout.about_cse, container, false);

                TextView myTextview = (TextView) xyz.findViewById(R.id.titlecse);
                Typeface mytypeface;
                mytypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/gh.ttf");
                myTextview.setTypeface(mytypeface);
                Animation fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
                myTextview.startAnimation(fadein);
                TextView body = (TextView) xyz.findViewById(R.id.bodycse);
                body.setText(Html.fromHtml("<b><i>About Department:</i></b><br /><small>Computers have captured the world. Today there is no single field were computers have not left a mark. The Department of Computer Science and Engineering established in 1986 is dedicated to produce students who excel in this field. The department aims at IT based Learning, Development of Entrepreneurship among student and become a Centre of Excellence. The Department places high priority on establishing and maintaining innovative research programs that enhance the educational opportunity and encourage a broad base of extramural support to prepare future generations of computer professionals for long term careers in research, technical development and applications. The department has organized many series of expert lecture for the students on the latest developing trends in computer industry.</small><br/><br/><b><i>Vision:</i></b><br /><small>To develop cultured and technically competent computer professionals and scholars with sustained growth in employability, high impact research outcome and become genuine asset to industry and society.</small><br><br><b><i>Mission:</b></i><br><small>The mission of the Department of Computer Science and Engineering is to develop undergraduate and postgraduate students having professional accomplishment through:-" +
                        "<br>" +
                        "1. Developing Creativity and Logical Reasoning amongst the learner.<br>" +
                        "2. Updating curricula according to industry requirements and standards.<br>" +
                        "3. Promote leadership quality, social accountability and ethics in disciplined environment, quality Education.<br>" +
                        "4. Creating environment conducive to research.</small>"));
                Typeface mytypeface2;
                mytypeface2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/trebuc.ttf");
                body.setTypeface(mytypeface2);
            }


//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return xyz;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ABOUT GECA";
                case 1:
                    return "LOGIN";
                case 2:
                    return "ABOUT CSE";
            }
            return null;
        }
    }

    public void signin(View view) {
//

        if (isNetworkAvailable()) {
            rollnum = (EditText) findViewById(R.id.editText);
            pass = (EditText) findViewById(R.id.editText2);

            name = rollnum.getText().toString().trim();
            name.toUpperCase();
            password = pass.getText().toString().trim();

            if (name.isEmpty()) {
                rollnum.setError("Roll number should not empty");
            } else {
                rollnum.setError(null);
                flag++;
            }


            if (password.isEmpty() || password.length() < 3 || password.length() > 18) {
                pass.setError("between 3 and 18 alphanumeric characters");
            } else {
                pass.setError(null);
                flag++;

            }
            if (flag == 2) {


                dialog = ProgressDialog.show(MainActivity.this, "",
                        "Validating user...", true);
                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();

            }
        } else if (!isNetworkAvailable()) {
            Log.d("andro", "not connected");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            builder.show();
        }

    }

    public void signup(View view) {
        Intent menuIntent = new Intent(this, SignupActivity.class);
        startActivity(menuIntent);
        finish();
    }

    public void forgot(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Forgot Password!");
        builder.setMessage("Please call to system admin")
                .setCancelable(false)
                .setPositiveButton("Call Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:7798080437"));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);
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

    void login(){
        String result = null; InputStream is = null;
        try{

            httpclient=new DefaultHttpClient();
            httppost= new HttpPost(serverUrl); // make sure the url is correct.
            //add your data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
            nameValuePairs.add(new BasicNameValuePair("roll",name));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("pass",password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response=httpclient.execute(httppost);
            httpentity = response.getEntity();
            is = httpentity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line= reader.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            final String response = httpclient.execute(httppost, responseHandler);
//            System.out.println("Response : " + response);
            runOnUiThread(new Runnable() {
                public void run() {
                   // tv.setText("Response from PHP : " + response);
                    dialog.dismiss();
                }
            });

            if(!(result.startsWith("F"))){
                Log.i("andro",result);
                try {
                    jsonobj = new JSONObject(result);
                    student = jsonobj.getJSONArray("result");
                    for (int i = 0; i < student.length(); i++) {
                        JSONObject c = student.getJSONObject(i);
                        roll1 = c.getString("roll");
                        name1 = c.getString("name");
                        pass1 = c.getString("pass");
                        year1 = c.getString("year");
                        img1 = c.getString("image");
                        Log.d("andro",roll1+name1+pass1+year1);

                    }
                }catch(JSONException j)
                {
                    j.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        SaveSharedPreference.setUserName(MainActivity.this,name1,roll1,year1,img1);
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString(Roll,name);
                        Toast.makeText(MainActivity.this,"Login Success", Toast.LENGTH_SHORT).show();
                        Intent menuIntent = new Intent(MainActivity.this, LoginSuccesss.class);
                        menuIntent.putExtra("rollnum",name);
                        startActivity(menuIntent);
                        finish();
                    }
                });

               // startActivity(new Intent(AndroidPHPConnectionDemo.this, UserPage.class));
            }else{
                Log.d("andro",result);
                showAlert();
            }

        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert(){
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not Found.")
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


    public static class BackgroundPainter {

        private static final int MIN = 20000;
        private static final int MAX = 73000;

        private final Random random;

        public BackgroundPainter() {
            random = new Random();
        }

        public void animate(@NonNull final View target, @ColorInt final int color1,
                            @ColorInt final int color2, @ColorInt final int color3,@ColorInt final int color4,@ColorInt final int color5,@ColorInt final int color6,@ColorInt final int color7,@ColorInt final int color8,@ColorInt final int color9,@ColorInt final int color10,@ColorInt final int color11,@ColorInt final int color12,@ColorInt final int color13,
                            @ColorInt final int color14,@ColorInt final int color15,@ColorInt final int color16) {
//            ,@ColorInt final int color18,@ColorInt final int color19,@ColorInt final int color20,@ColorInt final int color21,@ColorInt final int color22,@ColorInt final int color23,@ColorInt final int color24,@ColorInt final int color25,@ColorInt final int color26,@ColorInt final int color27,@ColorInt final int color28
            final ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), color1, color2,color3,color4,color5,color6,color7,color8,color9,color10,color11,color12,color13,color14,color15,color16);
//            ,color18,color19,color20,color21,color22,color23,color24,color25,color26,color27,color28
            valueAnimator.setDuration(randInt(MIN, MAX));

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override public void onAnimationUpdate(ValueAnimator animation) {
                    target.setBackgroundColor((int) animation.getAnimatedValue());
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    //reverse animation
                    animate(target, color1, color2, color3,color4,color5,color6,color7,color8,color9,color10,color11,color12,color13,color14,color15,color16);
                }
            });

            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.start();
        }

        private int randInt(int min, int max) {
            return random.nextInt((max - min) + 1) + min;
        }
    }

}










