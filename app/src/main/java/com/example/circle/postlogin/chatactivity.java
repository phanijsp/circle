package com.example.circle.postlogin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circle.MainActivity;
import com.example.circle.R;
import com.example.circle.SQLiteHelper;
import com.example.circle.postlogin.helpers.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class chatactivity extends AppCompatActivity {
    RequestQueue MyRequestQueue;
    LottieAnimationView lottieAnimationView;
    ImageButton imagebackButton;
    ImageButton imageinfoButton;
    TextView viewhead;
    SQLiteHelper sqLiteHelper;
   ListView messageslitview;
   ArrayList<messages> messageslist;
   EditText messageview;
    Cursor cursor;
    public  int messageid;


    messagesAdapter messagesadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
        imagebackButton = (ImageButton)findViewById(R.id.backbutton);
        imageinfoButton = (ImageButton)findViewById(R.id.infobutton);
        viewhead = (TextView) findViewById(R.id.viewhead);
        messageslitview = (ListView) findViewById(R.id.messages);
        messageslist = new ArrayList<messages>();
        messageview= (EditText)findViewById(R.id.messageview);
        messagesadapter = new messagesAdapter(this, messageslist);
        sqLiteHelper = new SQLiteHelper(this, "user.sqlite", null, 1);
        lottieAnimationView =(LottieAnimationView)  findViewById(R.id.animation_view2);

        final String domainname=getdomainname();
        messages.setUsername(getdomainname());
        messageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageslitview.setSelection(messageslitview.getCount());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslitview.setSelection(messageslitview.getCount());

                                //add your code here
                            }
                        }, 1500);

                    }
                });
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslitview.setSelection(messageslitview.getCount());

                                //add your code here
                            }
                        }, 1000);

                    }
                });
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslitview.setSelection(messageslitview.getCount());

                                //add your code here
                            }
                        }, 3000);

                    }
                });
            }

        });
       messageview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               messageslitview.setSelection(messageslitview.getCount());
               runOnUiThread(new Runnable() {

                   @Override
                   public void run() {
                       final Handler handler = new Handler();
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               messageslitview.setSelection(messageslitview.getCount());

                               //add your code here
                           }
                       }, 1500);

                   }
               });
               runOnUiThread(new Runnable() {

                   @Override
                   public void run() {
                       final Handler handler = new Handler();
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               messageslitview.setSelection(messageslitview.getCount());

                               //add your code here
                           }
                       }, 1000);

                   }
               });
               runOnUiThread(new Runnable() {

                   @Override
                   public void run() {
                       final Handler handler = new Handler();
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               messageslitview.setSelection(messageslitview.getCount());

                               //add your code here
                           }
                       }, 3000);

                   }
               });
           }
       });
        messageview.setText("");
       lottieAnimationView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              String pushmessage=messageview.getText().toString();
              pushmessage=encodeStringUrl(pushmessage);
              if(messageview.getText().toString().equals("")){

              }
              else{
                  DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                  Date date = new Date();
                  String time= dateFormat.format(date);
                  pushmessagetoserver(pushmessage,"text",domainname,getIntent().getStringExtra("groupname"),time);
                  lottieAnimationView.setMinAndMaxProgress(0.2f,0.42f);
                  lottieAnimationView.playAnimation();
              }


           }
       });


        String groupname=getIntent().getStringExtra("groupname");
        viewhead.setText(groupname);
        messageslitview.setAdapter(messagesadapter);
        messagesadapter.notifyDataSetChanged();

        messageslitview.setStackFromBottom(true);

      //  messageslitview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messageid=0;
        final Handler handler = new Handler();
        // Define the code block to be executed
        final Runnable runnableCode =
                new Runnable() {
                    @Override
                    public void run() {
                        try{
                            int xid= messageid;

                            cursor=sqLiteHelper.getData("SELECT * FROM q"+getIntent().getStringExtra("groupname")+" WHERE messageid > "+String.valueOf(xid));
                            Log.i("ggg",getIntent().getStringExtra("groupname")+String.valueOf(xid));
                            while (cursor.moveToNext()) {
                               int messageid = cursor.getInt(0);
                                String sender = cursor.getString(1);
                                String messagetype = cursor.getString(2);
                                String messagevalue = cursor.getString(3);
                                String time = cursor.getString(4);
                                messageslist.add(new messages(decodeStringUrl(messagevalue),sender,time,messagetype));
                                messagesadapter.notifyDataSetChanged();

                                messageslitview.setSelection(messageslitview.getCount());

                            }

                            //messagesadapter.notifyDataSetChanged();


                            cursor.moveToLast();
                            try{
                                int sid=cursor.getInt(0);
                                messageid=sid;
                                Log.i("if reached",String.valueOf(messageidhelper.getMessageid()));

                            }catch(Exception e){
                                Log.i("THis is it","Main error here");
                                messageidhelper.setMessageid(messageidhelper.getMessageid());
                                Log.i("MEssage id",String.valueOf(messageidhelper.getMessageid()));
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(chatactivity.this,"Boom ! ",Toast.LENGTH_SHORT).show();
                            StringWriter stringWriter = new StringWriter();
                            PrintWriter printWriter = new PrintWriter(stringWriter);
                            e.printStackTrace(printWriter);
                            String sStackTrace = stringWriter.toString();
                            Log.i("sqlite boom : ", sStackTrace);
                        }
                        cursor.close();
                        handler.postDelayed(this, 5);
                    }
                };
        handler.post(runnableCode);
   imageinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imagebackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chatactivity.super.onBackPressed();
               finish();
            }
        });
        imageinfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public String getdomainname() {
        Cursor cursor;
        String domainmail = "";

        try {
            cursor = sqLiteHelper.getData("SELECT * FROM users");
            String a = "";
            while (cursor.moveToNext()) {
                domainmail = cursor.getString(6);
                break;
            }
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
        }

        StringTokenizer st = new StringTokenizer(domainmail.toLowerCase(), "@");
        return st.nextToken();
    }

    public void pushmessagetoserver(final String pushmessage, final String pushmessagetype, final String sender, final String desgroup, final String time){
            MyRequestQueue  = Volley.newRequestQueue(this);
            String url =
                    "http://93.188.165.250/php_files/ayvarservice/pushmessagetoserver.php";
            StringRequest MyStringRequest =
                    new StringRequest(
                            Request.Method.POST,
                            url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                   if(response.equals("successs")){
                                       lottieAnimationView.setMinAndMaxProgress(0.42f,1.0f);
                                       lottieAnimationView.playAnimation();
                                       messageview.setText("");
                                   }
                                   else{
                                       Toast.makeText(chatactivity.this,"Server Error Occured!"+response,Toast.LENGTH_SHORT).show();
                                   }
                                    }
                            },
                            new Response
                                    .ErrorListener() { // Create an error listener to handle errors appropriately.
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(chatactivity.this,"Please Check Your Network And Try Again!",Toast.LENGTH_SHORT).show();
                                    lottieAnimationView.setMinAndMaxProgress(0.0f,0.0f);
                                    lottieAnimationView.playAnimation();


                                    // This code is executed if there is an error.
                                }
                            }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<String, String>();

                            MyData.put("pushmessage", pushmessage);
                            MyData.put("pushmessagetype",pushmessagetype);
                            MyData.put("sender",sender);
                            MyData.put("groupname",desgroup);
                            MyData.put("time",time);
                            return MyData;
                        }
                    };

            MyRequestQueue.add(MyStringRequest);
            MyStringRequest.setRetryPolicy(
                    new DefaultRetryPolicy(
                            15000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }


    public static String encodeStringUrl(String url) {
        String encodedUrl =null;
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (Exception e) {
            return encodedUrl;
        }
        return encodedUrl;
    }

    public static String decodeStringUrl(String encodedUrl) {
        String decodedUrl =null;
        try {
            decodedUrl = URLDecoder.decode(encodedUrl, "UTF-8");
        } catch (Exception e) {
            return decodedUrl;
        }
        return decodedUrl;
    }


    @Override
    public void onResume(){
        super.onResume();
    }

}
