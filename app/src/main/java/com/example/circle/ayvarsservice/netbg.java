package com.example.circle.ayvarsservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.circle.R;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class netbg extends AsyncTask<String, String, String> {

    private String resp = "";
    String param1;
    String param2;
    SQLiteHelper sqLiteHelper;
    Context context;
    ProgressDialog progressDialog;
    String stop = "FALSE";
    public static Cursor cursor;
    String domainmail1;

    static final int[] l = {2};


    public netbg(String param1, String param2, Context context, SQLiteHelper sqLiteHelper) {
        this.param1 = param1;
        this.param2 = param2;
        this.context = context;
        this.sqLiteHelper = sqLiteHelper;
        if (param1.equals("2023IT")) {
        }
    }


    @Override
    protected String doInBackground(String... params2) {
        URL url = null;
        try {
            url = new URL("http://93.188.165.250/php_files/ayvarservice/getmessages.php");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("groupname", param1);
            params.put("messageid", param2);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
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
            resp = result;
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

        if (stop != "TRUE") {
            if (result == null) {
                Toast.makeText(context, "Network Error Occured !", Toast.LENGTH_SHORT).show();
            } else {
                if (result.equals("error")) {
                }

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

                            cursor = sqLiteHelper.getData("SELECT * FROM users");
                            while (cursor.moveToNext()) {
                                domainmail1 = cursor.getString(6);
                                break;
                            }

                            StringTokenizer st = new StringTokenizer(domainmail1, "@");
                            String s = st.nextToken();

                            if (!s.equals(sender)) {
                                NotificationChannel channel = null;
                                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    channel = new NotificationChannel("my_channel_01",
                                            "Channel human readable title",
                                            NotificationManager.IMPORTANCE_DEFAULT);
                                    mNotificationManager.createNotificationChannel(channel);
                                }

                                String messagevalue1 = decodeStringUrl(messagevalue);

                                Notification notification =
                                        new NotificationCompat.Builder(context, "notify_001")
                                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                                .setContentTitle(groupname)
                                                .setContentText(sender + "->" + messagevalue1)
                                                .setPriority(Notification.PRIORITY_HIGH)
                                                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                                                .setChannelId("my_channel_01").build();
                                mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                mNotificationManager.notify(l[0], notification);
                                l[0]++;
                            }

                        } catch (Exception e) {
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            e.printStackTrace(pw);
                            String sStackTrace = sw.toString(); // stack trace as a string
                            System.out.println(sStackTrace);
                        }
                    }

                    //            } catch (JSONException e1) {
                    //            e1.printStackTrace();


                    int messageid = getmessageid();
                    netbg netc = new netbg(param1, String.valueOf(messageid), context, sqLiteHelper);
                    netc.execute();
                } catch (JSONException e) {
                    int messageid = getmessageid();
                    netbg netc = new netbg(param1, String.valueOf(messageid), context, sqLiteHelper);
                    netc.execute();
                    e.printStackTrace();
                }
            }
        } else {
            Log.i("netbgbgbg", "Not running" + param1);
        }

    }

    public void netbg_savemessage() {

    }

    public int getmessageid() {
        String groupname = param1;
        int messageid = 0;
        String groupx = "q" + groupname;

        try {
            Cursor cursorx = sqLiteHelper.getData("SELECT * FROM " + groupx);

            try {
                cursorx.moveToLast();
                messageid = cursorx.getInt(0);

            } catch (Exception e) {
                messageid = 0;
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                String sStackTrace = stringWriter.toString();
            }
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
        }
        return messageid;
    }

    public static String decodeStringUrl(String encodedUrl) {
        String decodedUrl = null;
        try {
            decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
        } catch (Exception e) {
            return decodedUrl;
        }
        return decodedUrl;
    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onProgressUpdate(String... text) {
//        text[0]

    }
}