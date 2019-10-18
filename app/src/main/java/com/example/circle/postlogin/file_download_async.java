package com.example.circle.postlogin;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.circle.SQLiteHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


public class file_download_async extends AsyncTask<String, String, String> {


    private String file_url = "";
    private String message_type;
    private String sender;
    private String time;
    private Context context;
    private SQLiteHelper sqLiteHelper;
    private String group_name;
    private int msg_id;
    private ArrayList<messages> messageslist;
    private messagesAdapter messageadapter;


    static final int[] l = {2};


    public file_download_async(String file_url, String message_type, String sender, String time, Context context, String group_name,int msg_id,ArrayList<messages> messageslist,messagesAdapter messageadapter) {
        this.file_url = file_url;
        this.message_type = message_type;
        this.sender = sender;
        this.time = time;
        this.context = context;
        this.group_name = group_name;
        this.msg_id = msg_id;
        this.messageslist = messageslist;
        this.messageadapter = messageadapter;
    }


    @Override
    protected String doInBackground(String... params2) {
        sqLiteHelper = new SQLiteHelper(context, "user.sqlite", null, 1);
        UFileDownloader uFileDownloader = new UFileDownloader(file_url);
        String status = uFileDownloader.begin();
        return status;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("DOWNLOAD_DONE")) {
            try{
                sqLiteHelper.queryData("UPDATE q" + group_name + " SET messagevalue = '" +
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/8kwallpapers/" + getFileName() +
                        "', messagetype = '" + message_type +
                        "_downloaded' WHERE messagetype = '" +
                        message_type + "' AND messagevalue = '" +
                        file_url + "' AND sender = '" + sender
                        + "' AND time = '" + time+"'");
                messageslist.set(msg_id,new messages(Environment.getExternalStorageDirectory().getAbsolutePath()+"/8kwallpapers/"+getFileName(),sender,time,message_type+"_downloaded"));
                messageadapter.notifyDataSetChanged();


            }catch (Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
                System.out.println(sStackTrace);
                Toast.makeText(context,sStackTrace,Toast.LENGTH_LONG).show();
                Log.i("blabla",sStackTrace);
            }

        } else {
            Toast.makeText(context, "DOWNLOAD_FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    public String getFileName() {
        String s = "";
        char c[] = file_url.toCharArray();
        int i = 0;

        while (i < c.length) {
            char v = c[i];
            if (v == '/') {
                s = "";
            } else {
                s = s + String.valueOf(v);
            }
            i++;
        }
        System.out.println(s);
        return s;

    }

}