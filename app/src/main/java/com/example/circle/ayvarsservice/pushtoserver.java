package com.example.circle.ayvarsservice;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.RequestFuture;

import com.android.volley.toolbox.Volley;
import com.example.circle.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;

public class pushtoserver   extends Thread {
    int messageid=0;
    String groupname;
    String url = "http://phantomassassin.tk/php_files/ayvarservice/getmessages.php";
    StringRequest mStringRequest;
    RequestQueue mRequestQueue;
    RequestFuture mRequestFuture;
    SQLiteHelper sqLiteHelper;
    Context context;


    pushtoserver(String groupname, SQLiteHelper sqLiteHelper, Context context){
        this.groupname=groupname;
        this.sqLiteHelper=sqLiteHelper;
        this.context=context;
        mRequestQueue = Volley.newRequestQueue(context);
//        startop();
    }
    public void  run(){
        int messageid2 = 0;
        messageid2 = getmessageid();
        netbg netc = new netbg(groupname,String.valueOf(messageid2),context,sqLiteHelper);
        netc.execute();
    }


    public void run2(){
        mStringRequest =
                new  StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String s = response.toString();
                                Log.i("data", s);

                                if (response.equals("error")) {
                                    startop();
                                } else {
                                    JSONArray arr = null;
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        arr = obj.getJSONArray("messages");

                                        for (int i = 0; i < arr.length(); i++) {

                                            JSONObject jsonProductObject = arr.getJSONObject(i);
                                            String messagevalue = jsonProductObject.getString("messagevalue");
                                            String messagetype = jsonProductObject.getString("messagetype");
                                            String sender = jsonProductObject.getString("sender");
                                            String time = jsonProductObject.getString("time");
                                            Log.i("datass:", messagevalue + "  " + messagetype + "  " + sender + " ");
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
                                                startop();

                                            } catch (Exception e) {
                                                StringWriter sw = new StringWriter();
                                                PrintWriter pw = new PrintWriter(sw);
                                                e.printStackTrace(pw);
                                                String sStackTrace = sw.toString(); // stack trace as a string
                                                System.out.println(sStackTrace);
                                                Log.i("Sql error", sStackTrace);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {


                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("Volleyboom", error.toString());
                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();
                        String mes = String.valueOf(messageidhelper.getMessageid(groupname));
                        MyData.put("messageid", mes);
                        MyData.put("groupname", groupname);
                        Log.i("finally", mes + "  " + groupname);
                        return MyData;
                    }
                };
        mStringRequest.setShouldCache(FALSE);
        mRequestQueue.add(mStringRequest);
        mStringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        15000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    public void startop(){
        Log.i("INSTARTOP",groupname);
        mStringRequest =
                new  StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                FLAG = "DOWN";

                                String s = response.toString();
                                Log.i("data", s);

                                if (response.equals("error")) {
                                    startop();
//                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

//                                    Log.i("in responser FLAG:", FLAG);
                                } else {
                                    //  FLAG="DOWN";

                                    JSONArray arr = null;
                                    try {

                                        JSONObject obj = new JSONObject(response);
                                        arr = obj.getJSONArray("messages");

                                        for (int i = 0; i < arr.length(); i++) {

                                            JSONObject jsonProductObject = arr.getJSONObject(i);
                                            String messagevalue = jsonProductObject.getString("messagevalue");
                                            String messagetype = jsonProductObject.getString("messagetype");
                                            String sender = jsonProductObject.getString("sender");
                                            String time = jsonProductObject.getString("time");
                                            Log.i("datass:", messagevalue + "  " + messagetype + "  " + sender + " ");
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
//                                                Log.i("insertanalysis", groupname + sender + messagetype+" "+i+" "+messagevalue);
                                                        startop();
                                                //      FLAG="DOWN";
                                            } catch (Exception e) {
                                                //    FLAG="DOWN";
                                                StringWriter sw = new StringWriter();
                                                PrintWriter pw = new PrintWriter(sw);
                                                e.printStackTrace(pw);
                                                String sStackTrace = sw.toString(); // stack trace as a string
                                                System.out.println(sStackTrace);
//                                                Toast.makeText(
//                                                        pushtoserver.this, "Beebom !" + sStackTrace, Toast.LENGTH_SHORT)
//                                                        .show();
                                                Log.i("Sql error", sStackTrace);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        //        FLAG="DOWN";
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {


                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                FLAG = "DOWN";

                                Log.i("Volleyboom", error.toString());
                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();
                        String mes = String.valueOf(messageidhelper.getMessageid(groupname));
                        MyData.put("messageid", mes);
                        MyData.put("groupname", groupname);
                        Log.i("finally", mes + "  " + groupname);
                        return MyData;
                    }
                };
        mStringRequest.setShouldCache(FALSE);
        mRequestQueue.add(mStringRequest);
        mStringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        15000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public int getmessageid(){
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

}
