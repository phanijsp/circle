package com.example.circle.postlogin.SendMessageToMultipleGroups;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.circle.R;
import com.example.circle.SQLiteHelper;
import com.example.circle.postlogin.SendMessageToMultipleGroups.Multiple_grps_sender;

import org.w3c.dom.Text;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Multiple_grps_sender extends AppCompatActivity {

    TextView header;
    LottieAnimationView sendbutton;
    EditText messagedata;
    ArrayList<String> grps;
    RequestQueue MyRequestQueue;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
        header = (TextView)findViewById(R.id.viewhead);
        sendbutton = (LottieAnimationView) findViewById(R.id.animation_view2);
        messagedata = (EditText) findViewById(R.id.messageview);
        sqLiteHelper = new SQLiteHelper(this, "user.sqlite", null, 1);



        grps = (ArrayList<String>) getIntent().getSerializableExtra("grps");
        header.setText(grps.toString());
        Toast.makeText(this,grps.toString(),Toast.LENGTH_SHORT).show();

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendbutton.resumeAnimation();
                if(messagedata.getEditableText()!=null){
                    send_message(messagedata.getEditableText().toString());
                }
            }
        });

    }
    public void send_message(String message){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        String time= dateFormat.format(date);
        for(int i =0 ; i< grps.size(); i++){
            pushmessagetoserver(message,"text",getdomainname(),grps.get(i),time);
        }
    }


    @Override
    public void onBackPressed() {
       super.onBackPressed();
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
                                    sendbutton.setMinAndMaxProgress(0.42f,1.0f);
                                    sendbutton.playAnimation();
                                    messagedata.setText("");
                                    Toast.makeText(Multiple_grps_sender.this,"Message Sent Successfully !",Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                                else{
                                    Toast.makeText(Multiple_grps_sender.this,"Server Error Occured!"+response,Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response
                                .ErrorListener() { // Create an error listener to handle errors appropriately.
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Multiple_grps_sender.this,"Please Check Your Network And Try Again!",Toast.LENGTH_SHORT).show();
                                sendbutton.setMinAndMaxProgress(0.0f,0.0f);
                                sendbutton.playAnimation();


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

}
