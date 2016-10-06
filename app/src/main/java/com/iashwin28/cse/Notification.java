package com.iashwin28.cse;

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

/**
 * Created by Ashwin on 24/09/16.
 */
public class Notification {

    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    HttpEntity httpentity;
    List<NameValuePair> nameValuePairs;
    InputStream isr;
    String serverUrl="http://cseapp.16mb.com/notification.php";
    String yr;
    int nid;
    String id;
    String result;
    String a[]= new String[25];
    String b[]= new String[25];
    JSONObject jsonobj1;
    JSONArray stu = null;
    Notification(String year, int id)
    {
        yr=year;
        nid=id;
       // id= ((String) nid);
    }

    public int search()
    {
        try
        {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(serverUrl);
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
            nameValuePairs.add(new BasicNameValuePair("year", yr));
          //  nameValuePairs.add(new BasicNameValuePair("id",(String)nid));
            // $Edittext_value = $_POST['Edittext_value'];
           // nameValuePairs.add(new BasicNameValuePair("id", (String)nid));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            httpentity = response.getEntity();
            isr = httpentity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line= reader.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();
            if(!(result.startsWith("N")))
            {
                try
                {
                    jsonobj1 = new JSONObject(result);
                    stu = jsonobj1.getJSONArray("result");
                    for (int i = 0; i < stu.length(); i++)
                    {
                        JSONObject c = stu.getJSONObject(i);
                        nid++;
                        a[nid+i] = c.getString("id");
                        b[nid+i]= c.getString("message");
                    }
                }catch(JSONException j)
                {
                    j.printStackTrace();
                }
            }

        }
        catch(Exception e)
        {}

        return nid;
    }

    public String display(int i)
    {
        return b[i];
    }
}
