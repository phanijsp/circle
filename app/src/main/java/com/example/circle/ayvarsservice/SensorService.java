package com.example.circle.ayvarsservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circle.SQLiteHelper;
import com.example.circle.postlogin.Groups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Boolean.FALSE;

public class SensorService extends Service {
  public int counter = 0;
  public static Cursor cursor;
  public static String reccount = "";
  public ArrayList<Groups> groupslist;
  public static Context servicecontext;
  private RequestQueue mRequestQueue;
  String FLAG;
  private StringRequest mStringRequest;
  private String url = "http://93.188.165.250/php_files/studentlogincheck.php";
  public SQLiteHelper sqLiteHelper;
  RequestQueue MyRequestQueue;
  StringRequest MyStringRequest;

  public SensorService(Context applicationContext) {
    super();
    Log.i("HERE", "here I am!");
    Log.i("serviceid",String.valueOf(this.getClass()));
    Log.i("MYLOC","constructor");
  }

  public SensorService() {}

  @Override
  public void onCreate() {
    super.onCreate();
    FLAG = "DOWN";
    MyRequestQueue = Volley.newRequestQueue(this);
    sqLiteHelper = new SQLiteHelper(SensorService.this, "user.sqlite", null, 1);
    messageidhelper.setSqLiteHelper(sqLiteHelper);
    if (Build.VERSION.SDK_INT >= 26) {
      String CHANNEL_ID = "my_channel_01";
      NotificationChannel channel =
          new NotificationChannel(
              CHANNEL_ID, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);

      ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
          .createNotificationChannel(channel);

      Notification notification =
          new NotificationCompat.Builder(this, CHANNEL_ID)
              .setContentTitle("")
              .setContentText("")
              .build();

      startForeground(1, notification);
    }
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i("MYLOC","Service onstart");
    super.onStartCommand(intent, flags, startId);
    startTimer();
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i("MYLOC", "ondestroy!");
    Intent broadcastIntent =
        new Intent(getApplicationContext(), SensorRestarterBroadcastReceiver.class);
    sendBroadcast(broadcastIntent);
    stoptimertask();
  }

  private Timer timer;
  private TimerTask timerTask;
  long oldTime = 0;
  final ArrayList<Groups>[] groupslist2 = new ArrayList[] {new ArrayList<>()};

  public void startTimer() {
    // set a new Timer
    Log.i("MYLOC","startTimer");
    String groupname2 = "";
    try {
      Cursor cursor2 = sqLiteHelper.getData("SELECT * FROM groups");
      while (cursor2.moveToNext()) {
        groupname2 = cursor2.getString(1);
        StringTokenizer stringTokenizer2 = new StringTokenizer(groupname2, ".");
//        groupslist2[0].add(new Groups(stringTokenizer2.nextToken(), stringTokenizer2.nextToken()));
      }
    } catch (Exception e) {

    }

    timer = new Timer();
    initializeTimerTask();
    // schedule the timer, to wake up every 1 second
    timer.schedule(timerTask, 1000, 1000); //
  }

  /** it sets the timer to print the counter every x seconds */
  public void initializeTimerTask() {
Log.i("MYLOC","Initialize timer task");
    timerTask =
        new TimerTask() {
          public void run() {
            groupslist = new ArrayList<>();
            String groupname = "";
            try {
              cursor = sqLiteHelper.getData("SELECT * FROM groups");
              while (cursor.moveToNext()) {
                groupname = cursor.getString(1);
                StringTokenizer stringTokenizer = new StringTokenizer(groupname, ".");
                groupslist.add(
                    new Groups(stringTokenizer.nextToken(), stringTokenizer.nextToken()));
              }
            } catch (Exception e) {
              StringWriter stringWriter = new StringWriter();
              PrintWriter printWriter = new PrintWriter(stringWriter);
              e.printStackTrace(printWriter);
              String sStackTrace = stringWriter.toString();
              Log.i("data", sStackTrace);
            }
            String s = AreGroupsEqual(groupslist2[0], groupslist);
            if (s.equals("True")) {
              Log.i("AREEqual", "YES");
              reccount = reccount + "y";
            }
            if (s.equals("False")) {
              Log.i("AREEqual", "NO" + groupslist + groupslist2[0]);
              groupslist2[0] = groupslist;
              if(groupslist.size()>0){
                InitializeGroupObjects(groupslist);
              }
            }
          }
        };
  }

  public String AreGroupsEqual(ArrayList<Groups> groupslist1, ArrayList<Groups> groupslist2) {
    String Flag = "Y";
    if (groupslist1.size() != groupslist2.size()) {
      Flag = "N";
    } else {
      for (int i = 0; i < groupslist1.size(); i++) {
        Groups group = groupslist1.get(i);
        Groups group2 = groupslist2.get(i);
        if (group.getYear().equals(group2.getYear()) && group.getname().equals(group2.getname())) {
          Flag = "Y";
        } else {
          Flag = "N";
          Log.i("VAR", group.getYear() + group2.getYear() + group.getname() + group2.getname());

          break;
        }
      }
    }

    if (Flag.equals("Y")) {
      return "True";
    } else {
      return "False";
    }
  }

  public void InitializeGroupObjects(ArrayList<Groups> groupslistfinal) {
    pushtoserver[] pushgroups=new pushtoserver[groupslistfinal.size()];
    int count=0;
    for(int i=0;i<groupslistfinal.size();i++){
      Groups group= groupslistfinal.get(i);
      final String gh = group.getYear()+group.getname();
      Log.i("final String",gh);
      count++;
      Log.i("INFOR",String.valueOf(count));
      pushgroups[i]=new pushtoserver(gh,sqLiteHelper,this);
      pushgroups[i].start();
    }
  }

  private void gpushtoserver(final String groupname) {

    String url = "http://93.188.165.250/php_files/ayvarservice/getmessages.php";
    MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                FLAG = "DOWN";

                String s = response.toString();
                Log.i("data", s);

                if (response.equals("error")) {
                  Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

                  Log.i("in responser FLAG:", FLAG);
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
                        Log.i(
                            "insertanalysis",
                            groupname + sender + messagetype + " " + i + " " + messagevalue);

                        //      FLAG="DOWN";

                      } catch (Exception e) {
                        //    FLAG="DOWN";

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        System.out.println(sStackTrace);
                        Toast.makeText(
                                SensorService.this, "Beebom !" + sStackTrace, Toast.LENGTH_SHORT)
                            .show();
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
                FLAG = "DOWN";

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
    MyStringRequest.setShouldCache(FALSE);
    MyRequestQueue.add(MyStringRequest);
    MyStringRequest.setRetryPolicy(
        new DefaultRetryPolicy(
            15000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
  }

  public void stoptimertask() {
    // stop the timer, if it's not already null
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onTaskRemoved(Intent intent) {
    super.onDestroy();
    Log.i("MYLOC", "ondestroy! in onTaskremoved");
    Intent broadcastIntent =
        new Intent(getApplicationContext(), SensorRestarterBroadcastReceiver.class);
//    sendBroadcast(broadcastIntent);
    stoptimertask();
  }
}
