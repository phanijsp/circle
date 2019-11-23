package com.example.circle.postlogin.SendMessageToMultipleGroups;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circle.R;
import com.example.circle.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ListingAllGroups extends AppCompatActivity {

    ListView originallistview;
    FloatingActionButton send;
    FloatingActionButton delete;
    SQLiteHelper sqLiteHelperGroups;
    ImageButton backbutton;
    GroupsAdapter_CheckBox groupsAdapter;
    ArrayList<Data_Model> groupslist;
    RequestQueue MyRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_all_groups);
        originallistview = (ListView) findViewById(R.id.originalgroupslist);
        send = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        delete = (FloatingActionButton) findViewById(R.id.floatingActionButtondelete);
        backbutton = (ImageButton) findViewById(R.id.backbutton);
        MyRequestQueue = Volley.newRequestQueue(getApplicationContext());

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getIntent().getStringExtra("key").equals("delete")) {
            send.setVisibility(View.GONE);
            delete.setVisibility(View.VISIBLE);
        }
        sqLiteHelperGroups = new SQLiteHelper(this, "user.sqlite", null, 1);
        groupslist = new ArrayList<>();
        final String query2 = "SELECT * FROM groups";
        String groupname = "";
        final Cursor cursor3 = sqLiteHelperGroups.getData(query2);

        while (cursor3.moveToNext()) {
            try {
                groupname = cursor3.getString(1);
                Log.i("firsttime", groupname);
                StringTokenizer stringTokenizer = new StringTokenizer(groupname, ".");
                groupslist.add(new Data_Model(stringTokenizer.nextToken(), stringTokenizer.nextToken(), false));
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
                Log.i("firsttime", sStackTrace);
            }
            groupsAdapter = new GroupsAdapter_CheckBox(this, groupslist);
            originallistview.setAdapter(groupsAdapter);
            originallistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    groupslist.get(i).chechk_val = !groupslist.get(i).chechk_val;
                    groupsAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), i + "" + groupslist.get(i).groupname, Toast.LENGTH_SHORT).show();
                }
            });


        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "send clicked", Toast.LENGTH_SHORT).show();
                ArrayList<String> grps = new ArrayList<>();
                try {
                    for (int i = 0; i < groupslist.size(); i++) {
                        if (groupslist.get(i).chechk_val == true) {
                            String s = groupslist.get(i).groupyear + groupslist.get(i).groupname;
                            grps.add(s);
                        }
                    }
                    Intent intent = new Intent(ListingAllGroups.this, Multiple_grps_sender.class);
                    intent.putExtra("grps", grps);
                    startActivity(intent);
                    ListingAllGroups.this.finish();

                } catch (Exception e) {

                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "delete clicked", Toast.LENGTH_SHORT).show();
                ArrayList<String> SelectedGroups = new ArrayList<String>();
                for (int i = 0; i < groupslist.size(); i++) {
                    if (groupslist.get(i).chechk_val == true) {
                        String s = groupslist.get(i).groupyear + "." + groupslist.get(i).groupname;
                        SelectedGroups.add(s);
                    }
                }
                for (int i = 0; i < SelectedGroups.size(); i++) {
                    DeleteGroupOnServer(SelectedGroups.get(i));
                }
                onBackPressed();

            }
        });


    }

    public void DeleteGroupOnServer(final String groupname) {
        String url =
                "http://93.188.165.250/php_files/delete_group.php"; // <----enter your post url here
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        },
                        new Response
                                .ErrorListener() { // Create an error listener to handle errors appropriately.
                            @Override
                            public void onErrorResponse(VolleyError error) {


                                // This code is executed if there is an error.
                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        MyData.put("groupname", groupname);
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
}
