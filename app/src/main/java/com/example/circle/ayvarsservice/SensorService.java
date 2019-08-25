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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Boolean.FALSE;

public class SensorService extends Service {

    public static Cursor cursor;
    String FLAG;
    public SQLiteHelper sqLiteHelper;
    RequestQueue MyRequestQueue;

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
        Log.i("serviceid", String.valueOf(this.getClass()));
        Log.i("MYLOC", "constructor");
    }

    public SensorService() {
    }

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
        Log.i("MYLOC", "Service onstart");
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

    ArrayList<pushtoserver> pushtoservers = new ArrayList<pushtoserver>();

    public void startTimer() {
        Log.i("MYLOC", "startTimer");

        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000); //
    }

    public void initializeTimerTask() {
        final ArrayList<String>[] groupslistfinal1 = new ArrayList[]{new ArrayList<String>()};
        final ArrayList<String>[] groupslistfinal2 = new ArrayList[]{new ArrayList<String>()};
        Log.i("MYLOC", "Initialize timer task");
        timerTask =
                new TimerTask() {
                    public void run() {
                        groupslistfinal1[0] = new ArrayList<String>();
                        String groupname = "";
                        try {
                            cursor = sqLiteHelper.getData("SELECT * FROM groups");
                            while (cursor.moveToNext()) {
                                groupname = cursor.getString(1);
                                groupslistfinal1[0].add(groupname);
                            }
                        } catch (Exception e) {
                            StringWriter stringWriter = new StringWriter();
                            PrintWriter printWriter = new PrintWriter(stringWriter);
                            e.printStackTrace(printWriter);
                            String sStackTrace = stringWriter.toString();
                            Log.i("data", sStackTrace);
                        }
                        String equality = AreGroupsFinalEqual(groupslistfinal1[0], groupslistfinal2[0]);
                        if(equality.equals("YES")){
                        }
                        else if(equality.equals("NO")){
                            ArrayList<String> x = new ArrayList<String>(groupslistfinal1[0]);
                            Log.i("YouFoundIt", x.toString()+"\n"+ groupslistfinal1[0]);
                            ArrayList<String> y =new ArrayList<String>(groupslistfinal1[0]);
                            ArrayList<String> z=new ArrayList<String>(groupslistfinal2[0]);
                            CreateNewThreadObjects(groupslistfinal1[0], groupslistfinal2[0]);
                            DeleteOldThreadObjects(y,z);
                            Log.i("YouFoundIt", x.toString()+"\n"+ groupslistfinal1[0]);
                            groupslistfinal2[0] = x;
                            Log.i("IfoundYou", groupslistfinal2[0].toString()+"\n"+ groupslistfinal1[0]);
//                            InitializeThreadObjects(groupslistfinal1);
                        }
                    }
                };
    }
    public String AreGroupsFinalEqual(ArrayList<String> groupslistfinal1x,ArrayList<String> groupslistfinal2x){
        Collections.sort(groupslistfinal1x);
        Collections.sort(groupslistfinal2x);
        if(groupslistfinal1x.equals(groupslistfinal2x)){
            return "YES";
        }
        else{
            Log.i("AreGroupsEqualxno",groupslistfinal1x.toString()+"\n"+groupslistfinal2x.toString());
            return "NO";
        }
    }
    public void CreateNewThreadObjects(ArrayList<String> groupslistfinaly1,ArrayList<String> groupslistfinaly2){
        ArrayList<String> groupslistfinalx1=new ArrayList<String>();
        ArrayList<String> groupslistfinalx2 = new ArrayList<String>();
        groupslistfinalx1=groupslistfinaly1;
        groupslistfinalx2=groupslistfinaly2;
        for (int i = 0; i < groupslistfinalx2.size(); i++) {
            for(int j = 0; j<groupslistfinalx1.size(); j++){
                if(groupslistfinalx1.get(j).equals(groupslistfinalx2.get(i))){
                    groupslistfinalx1.remove(j);

                }
            }
        }
        if(groupslistfinalx1.size()>0){
            Log.i("AreGroupsEqualxnoincrtn",groupslistfinalx1.toString());
            InitializeThreadObjects(groupslistfinalx1);
        }
    }
    public void DeleteOldThreadObjects(ArrayList<String> groupslistfinal1,ArrayList<String> groupslistfinal2){
        for(int i = 0 ; i< groupslistfinal1.size(); i++){
            for(int j = 0 ; j< groupslistfinal2.size();j++){
                if(groupslistfinal2.get(j).equals(groupslistfinal1.get(i))){
                    groupslistfinal2.remove(j);
                }
            }
        }
        if(groupslistfinal2.size()>0){
            Log.i("AreGroupsEqualxnoindelo",groupslistfinal2.toString());
            FinalizeThreadObjects(groupslistfinal2);
        }else{
            Log.i("AreGroupsEqualxnodelno",groupslistfinal2.toString());

        }
    }
    public void InitializeThreadObjects(ArrayList<String> groupslistfinalx){
                pushtoserver[] pushgroups = new pushtoserver[groupslistfinalx.size()];
                for(int i = 0 ; i < groupslistfinalx.size() ; i++ ){
                    StringTokenizer stringTokenizer = new StringTokenizer(groupslistfinalx.get(i), ".");
                    String groupname = stringTokenizer.nextToken()+stringTokenizer.nextToken();
                    pushgroups[i] = new pushtoserver(groupname,sqLiteHelper,this);
                    pushtoservers.add(pushgroups[i]);
                    pushgroups[i].start();
                    pushgroups[i].setName(groupname);
                }

    }
    public void FinalizeThreadObjects(ArrayList<String> groupslistfinalx){
                for(int i = 0;i< groupslistfinalx.size();i++){
                    for(int j = 0; j<pushtoservers.size();j++){
                        pushtoserver pushtoserver = pushtoservers.get(j);
                        if(pushtoserver.getName().equals(groupslistfinalx.get(i))){
                            pushtoserver.stopthread();
                        }
                    }
                }
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
