package com.example.circle.ayvarsservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.circle.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


class netbg extends AsyncTask<String, String, String> {

    private String resp;
    String param1;
    String param2;
    SQLiteHelper sqLiteHelper;
    Context context;
    ProgressDialog progressDialog;
    static String stop="FALSE";

    public static String getStop() {
        return stop;
    }

    public static void setStop(String stop) {
        netbg.stop = stop;
    }

    public netbg(String param1, String param2, Context context, SQLiteHelper sqLiteHelper){
        this.param1=param1;
        this.param2=param2;
        this.context=context;
        this.sqLiteHelper=sqLiteHelper;
        if(param1.equals("2023IT")){
            Log.i("MYLOC",String.valueOf(getClass().hashCode()));
        }
    }


    @Override
    protected String doInBackground(String... params2) {
        URL url = null;
        try {
            url = new URL("http://93.188.165.250/php_files/ayvarservice/getmessages.php");
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("groupname", param1);
            params.put("messageid", param2);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            String urlParameters = postData.toString();
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(urlParameters);
            writer.flush();

            String result = "";
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                result += line;
            }
            writer.close();
            reader.close();
            System.out.println(result);
            resp=result;
//          return resp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String result) {
       if(stop!="TRUE"){
           Log.i("netbgbgbg","Running"+param1);
           if(result==null){
               Toast.makeText(context,"Network Error Occured !",Toast.LENGTH_SHORT).show();
           }else{
               if(result.equals("error")){
                   Log.i("ENDISHERE",result);
               }
//        textView.setText(result);
               JSONArray arr = null;
               try {
                   JSONObject obj = new JSONObject(result);
                   arr = obj.getJSONArray("messages");

                   for (int i = 0; i < arr.length(); i++) {

                       JSONObject jsonProductObject = arr.getJSONObject(i);
                       String messagevalue = jsonProductObject.getString("messagevalue");
                       String messagetype = jsonProductObject.getString("messagetype");
                       String sender = jsonProductObject.getString("sender");
                       String time = jsonProductObject.getString("time");
                       Log.i("dataxx:", messagevalue + "  " + messagetype + "  " + sender + " "+i+" "+arr.length()+param1+param2);
                       String groupname = param1;


                       try {
                           sqLiteHelper.queryData(
                                   "INSERT INTO q"
                                           + groupname
                                           + "(sender,messagetype,messagevalue,time) VALUES('"
                                           + sender
                                           + "','"
                                           + messagetype
                                           + "','"
                                           + messagevalue
                                           + "','"
                                           + time
                                           + "')");
                           Log.i("insertanalysis", groupname + sender + messagetype+" "+i+" "+messagevalue);

                       } catch (Exception e) {
                           StringWriter sw = new StringWriter();
                           PrintWriter pw = new PrintWriter(sw);
                           e.printStackTrace(pw);
                           String sStackTrace = sw.toString(); // stack trace as a string
                           System.out.println(sStackTrace);
                           Log.i("Sql error", sStackTrace);
                       }
                       //            } catch (JSONException e1) {
                       //            e1.printStackTrace();

                   }
                   int messageid=getmessageid();
                   netbg netc = new netbg(param1,String.valueOf(messageid),context,sqLiteHelper);
                   netc.execute();
               } catch (JSONException e) {
                   int messageid=getmessageid();
                   netbg netc = new netbg(param1,String.valueOf(messageid),context,sqLiteHelper);
                   netc.execute();
                   e.printStackTrace();
               }
           }
       }
       else{
           Log.i("netbgbgbg","Not running"+param1);
       }

    }
    public int getmessageid(){
        String groupname = param1;
        int messageid = 0;
        String groupx="q"+groupname;

        try {
            Cursor cursorx = sqLiteHelper.getData("SELECT * FROM " + groupx);

            try {
                cursorx.moveToLast();
                Log.i("gname", groupx);
                messageid = cursorx.getInt(0);

            } catch (Exception e) {
                messageid = 0;
                Log.i("alwaysbaba", String.valueOf(messageid) + groupx);
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                String sStackTrace = stringWriter.toString();
                Log.i("data", sStackTrace);
            }
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
            Log.i("data", sStackTrace);
        }
        Log.i("xcx",messageid+groupname);
        return messageid;
    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onProgressUpdate(String... text) {
//        text[0]

    }
}