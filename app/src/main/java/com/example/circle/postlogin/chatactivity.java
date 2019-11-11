package com.example.circle.postlogin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.circle.postlogin.FileSender.sendx;
import com.example.circle.postlogin.helpers.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
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
    Animation slide_in_anim, slide_out_anim;
    TextView viewhead;
    SQLiteHelper sqLiteHelper;
    ListView messageslistview;
    ArrayList<messages> messageslist;
    EditText messageview;
    Cursor cursor;
    public int messageid = 0;
    ImageView attachbutton, detachbutton;
    LinearLayout image_selector, video_selector, audio_selector, document_selector;
    public static ConstraintLayout loading_screen;
    private int ACTIVITY_CHOOSE_FILE = 12345;
    public static String selected_file_type;
    public static String selected_file_time;
    public static String selected_file_sender;
    public static String selected_file_groupname;


    public static void setLoading_screen_visible() {


        loading_screen.setVisibility(View.VISIBLE);

    }


    public static void setLoading_screen_gone() {
        loading_screen.setVisibility(View.GONE);
    }

    messagesAdapter messagesadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
        imagebackButton = (ImageButton) findViewById(R.id.backbutton);
        imageinfoButton = (ImageButton) findViewById(R.id.infobutton);
        viewhead = (TextView) findViewById(R.id.viewhead);
        messageslistview = (ListView) findViewById(R.id.messages);
        messageslist = new ArrayList<messages>();
        messageview = (EditText) findViewById(R.id.messageview);
        attachbutton = (ImageView) findViewById(R.id.attach);
        detachbutton = (ImageView) findViewById(R.id.detach);
        image_selector = (LinearLayout) findViewById(R.id.imageselector);
        video_selector = (LinearLayout) findViewById(R.id.videoselector);
        audio_selector = (LinearLayout) findViewById(R.id.audioselector);
        document_selector = (LinearLayout) findViewById(R.id.docselector);
        loading_screen = (ConstraintLayout) findViewById(R.id.loading_screen);
        messagesadapter = new messagesAdapter(this, messageslist);

        sqLiteHelper = new SQLiteHelper(this, "user.sqlite", null, 1);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view2);
        slide_in_anim = AnimationUtils.loadAnimation(this, R.anim.slide_in);
        slide_out_anim = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        final View view2 = (View) findViewById(R.id.view2);


        final String domainname = getdomainname();
        messages.setUsername(getdomainname());
        messageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageslistview.setSelection(messageslistview.getCount());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslistview.setSelection(messageslistview.getCount());

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
                                messageslistview.setSelection(messageslistview.getCount());

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
                                messageslistview.setSelection(messageslistview.getCount());

                                //add your code here
                            }
                        }, 500);

                    }
                });
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslistview.setSelection(messageslistview.getCount());

                                //add your code here
                            }
                        }, 100);

                    }
                });
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslistview.setSelection(messageslistview.getCount());

                                //add your code here
                            }
                        }, 50);

                    }
                });
            }

        });
        messageview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                messageslistview.setSelection(messageslistview.getCount());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageslistview.setSelection(messageslistview.getCount());

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
                                messageslistview.setSelection(messageslistview.getCount());

                                //add your code here
                            }
                        }, 1000);

                    }
                });

            }
        });
        messageview.setText("");
        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pushmessage = messageview.getText().toString();
                pushmessage = encodeStringUrl(pushmessage);
                if (messageview.getText().toString().equals("")) {

                } else {
                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                    Date date = new Date();
                    String time = dateFormat.format(date);
                    pushmessagetoserver(pushmessage, "text", domainname, getIntent().getStringExtra("groupname"), time);
                    lottieAnimationView.setMinAndMaxProgress(0.2f, 0.42f);
                    lottieAnimationView.playAnimation();
                }


            }
        });

        attachbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view2.setVisibility(View.VISIBLE);
                view2.startAnimation(slide_in_anim);
                attachbutton.setVisibility(View.INVISIBLE);
                detachbutton.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Clicked On Attach ! ", Toast.LENGTH_SHORT).show();
            }
        });
        detachbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view2.startAnimation(slide_out_anim);
                detachbutton.setVisibility(View.INVISIBLE);
                attachbutton.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Clicked On Detach ! ", Toast.LENGTH_SHORT).show();

            }
        });
        image_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detachbutton.performClick();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date date = new Date();
                String time = dateFormat.format(date);


                selected_file_groupname = getIntent().getStringExtra("groupname");
                selected_file_type = "image";
                selected_file_sender = domainname;
                selected_file_time = time;
                SelectFile("image/*");
            }
        });
        video_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detachbutton.performClick();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date date = new Date();
                String time = dateFormat.format(date);

                selected_file_time = time;
                selected_file_sender = domainname;
                selected_file_groupname = getIntent().getStringExtra("groupname");
                selected_file_type = "video";
                SelectFile("video/*");
            }
        });
        audio_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detachbutton.performClick();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date date = new Date();
                String time = dateFormat.format(date);

                selected_file_groupname = getIntent().getStringExtra("groupname");
                selected_file_sender = domainname;
                selected_file_time = time;
                selected_file_type = "audio";
                SelectFile("audio/*");
            }
        });
        document_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detachbutton.performClick();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                Date date = new Date();
                String time = dateFormat.format(date);

                selected_file_groupname = getIntent().getStringExtra("groupname");
                selected_file_time = time;
                selected_file_sender = domainname;
                selected_file_type = "document";
                SelectFile("*/*");

            }
        });

        String groupname = getIntent().getStringExtra("groupname");
        viewhead.setText(groupname);
        messageslistview.setAdapter(messagesadapter);
        messagesadapter.notifyDataSetChanged();

        messageslistview.setStackFromBottom(true);

        //  messageslistview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        final Handler handler = new Handler();
        // Define the code block to be executed
        final Runnable runnableCode =
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int xid = messageid;

                            cursor = sqLiteHelper.getData("SELECT * FROM q" + getIntent().getStringExtra("groupname") + " WHERE messageid > " + String.valueOf(xid));
//                            Log.i("ggg", getIntent().getStringExtra("groupname") + String.valueOf(xid));
                            while (cursor.moveToNext()) {
                                int messageid = cursor.getInt(0);
                                String sender = cursor.getString(1);
                                String messagetype = cursor.getString(2);
                                String messagevalue = cursor.getString(3);
                                String time = cursor.getString(4);
                                messageslist.add(new messages(decodeStringUrl(messagevalue), sender, time, messagetype, "not_downloaded"));
                                messagesadapter.notifyDataSetChanged();

                                messageslistview.setSelection(messageslistview.getCount());

                            }

                            //messagesadapter.notifyDataSetChanged();


                            cursor.moveToLast();
                            try {
                                int sid = cursor.getInt(0);
                                messageid = sid;
                                Log.i("if reached", String.valueOf(messageidhelper.getMessageid()));

                            } catch (Exception e) {
//                                Log.i("THis is it", "Main error here");
                                messageidhelper.setMessageid(messageidhelper.getMessageid());
//                                Log.i("MEssage id", String.valueOf(messageidhelper.getMessageid()));
                            }
                        } catch (Exception e) {
                            Toast.makeText(chatactivity.this, "Boom ! ", Toast.LENGTH_SHORT).show();
                            StringWriter stringWriter = new StringWriter();
                            PrintWriter printWriter = new PrintWriter(stringWriter);
                            e.printStackTrace(printWriter);
                            String sStackTrace = stringWriter.toString();
//                            Log.i("sqlite boom : ", sStackTrace);
                        }
                        cursor.close();
                        handler.postDelayed(this, 5);
                    }
                };
        handler.post(runnableCode);
        messageslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                messages currentmessage = (messages) messagesadapter.getItem(i);
                if (currentmessage.getMessagetype().equals("image")) {
                    messageslist.set(i, new messages(currentmessage.getMessagevalue(), currentmessage.getSender(), currentmessage.getTime(), currentmessage.getMessagetype(), "downloading"));
                    messagesadapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), currentmessage.getMessagetype() + currentmessage.messagevalue, Toast.LENGTH_SHORT).show();
                    file_download_async fileDownloadAsync = new file_download_async(currentmessage.getMessagevalue(),
                            currentmessage.getMessagetype(),
                            currentmessage.getSender(), currentmessage.getTime(),
                            getApplicationContext(),
                            getIntent().getStringExtra("groupname"),
                            i,
                            messageslist,
                            messagesadapter);
                    fileDownloadAsync.execute();
                } else if (currentmessage.getMessagetype().equals("image_downloaded")) {
                    Toast.makeText(getApplicationContext(), "Already_Downloaded", Toast.LENGTH_SHORT).show();
                } else if (currentmessage.getMessagetype().equals("video")) {
                    messageslist.set(i, new messages(currentmessage.getMessagevalue(), currentmessage.getSender(), currentmessage.getTime(), currentmessage.getMessagetype(), "downloading"));
                    messagesadapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), currentmessage.getMessagetype() + currentmessage.messagevalue, Toast.LENGTH_SHORT).show();
                    file_download_async fileDownloadAsync = new file_download_async(currentmessage.getMessagevalue(),
                            currentmessage.getMessagetype(),
                            currentmessage.getSender(), currentmessage.getTime(),
                            getApplicationContext(),
                            getIntent().getStringExtra("groupname"),
                            i,
                            messageslist,
                            messagesadapter);
                    fileDownloadAsync.execute();
                } else if (currentmessage.getMessagetype().equals("video_downloaded")) {
                    Toast.makeText(getApplicationContext(), "Already_Downloaded", Toast.LENGTH_SHORT).show();
                } else if (currentmessage.getMessagetype().equals("audio")) {
                    messageslist.set(i, new messages(currentmessage.getMessagevalue(), currentmessage.getSender(), currentmessage.getTime(), currentmessage.getMessagetype(), "downloading"));
                    messagesadapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), currentmessage.getMessagetype() + currentmessage.messagevalue, Toast.LENGTH_SHORT).show();
                    file_download_async fileDownloadAsync = new file_download_async(currentmessage.getMessagevalue(),
                            currentmessage.getMessagetype(),
                            currentmessage.getSender(), currentmessage.getTime(),
                            getApplicationContext(),
                            getIntent().getStringExtra("groupname"),
                            i,
                            messageslist,
                            messagesadapter);
                    fileDownloadAsync.execute();

                } else if (currentmessage.getMessagetype().equals("audio_downloaded")) {
                    Toast.makeText(getApplicationContext(), "Already_Downloaded", Toast.LENGTH_SHORT).show();
                } else if (currentmessage.getMessagetype().equals("document")) {

                } else if (currentmessage.getMessagetype().equals("document_downloaded")) {

                }

            }
        });

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

    public void pushmessagetoserver(final String pushmessage, final String pushmessagetype, final String sender, final String desgroup, final String time) {
        MyRequestQueue = Volley.newRequestQueue(this);
        String url =
                "http://93.188.165.250/php_files/ayvarservice/pushmessagetoserver.php";
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("successs")) {
                                    lottieAnimationView.setMinAndMaxProgress(0.42f, 1.0f);
                                    lottieAnimationView.playAnimation();
                                    messageview.setText("");
                                } else {
                                    Toast.makeText(chatactivity.this, "Server Error Occured!" + response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response
                                .ErrorListener() { // Create an error listener to handle errors appropriately.
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(chatactivity.this, "Please Check Your Network And Try Again!", Toast.LENGTH_SHORT).show();
                                lottieAnimationView.setMinAndMaxProgress(0.0f, 0.0f);
                                lottieAnimationView.playAnimation();


                                // This code is executed if there is an error.
                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        MyData.put("pushmessage", pushmessage);
                        MyData.put("pushmessagetype", pushmessagetype);
                        MyData.put("sender", sender);
                        MyData.put("groupname", desgroup);
                        MyData.put("time", time);
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
        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(url, "UTF-8");
        } catch (Exception e) {
            return encodedUrl;
        }
        return encodedUrl;
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


    public void SelectFile(String FileType) {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType(FileType);
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            Uri uri = data.getData();

            String FilePath = uri.getPath();// should the path be here in this string
            String Filename = getFileName(uri);
            Toast.makeText(this, Filename, Toast.LENGTH_LONG).show();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                sendx Sendx = new sendx(this, inputStream, Filename);
                chatactivity.setLoading_screen_visible();
                Sendx.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(Uri uri) {
        String fileName = "default_file_name";
        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        try {
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            fileName = returnCursor.getString(nameIndex);
        } catch (Exception e) {
            //handle the failure cases here
        } finally {
            returnCursor.close();
        }
        return fileName;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
