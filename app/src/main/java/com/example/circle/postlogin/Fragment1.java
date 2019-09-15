package com.example.circle.postlogin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.circle.postlogin.SendMessageToMultipleGroups.ListingAllGroups;
import com.example.circle.postlogin.helpers.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Fragment1 extends Fragment {

    ImageButton menu;
    ListView groups_list;
    static ArrayList<Groups> groupslist;
    GroupsAdapter groupsAdapter;
    SQLiteHelper sqLiteHelperGroups;
    TextView textView;
    RequestQueue MyRequestQueue;
    String[] menuitems = {"New Message", "Add Group", "Delete Group", ""};

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag1, container, false);
        menu = (ImageButton) view.findViewById(R.id.Menu);
        groups_list = (ListView) view.findViewById(R.id.originalgroupslist);
        textView = (TextView) view.findViewById(R.id.textView);
        groupslist = new ArrayList<>();
        groupsAdapter = new GroupsAdapter(getActivity(), groupslist);
        groups_list.setAdapter(groupsAdapter);
        sqLiteHelperGroups = new SQLiteHelper(getActivity(), "user.sqlite", null, 1);
        MyRequestQueue = Volley.newRequestQueue(getActivity());
        final int[] check = {0};
        groupxyhelper.setGroupx("");


        final String query2 = "SELECT * FROM groups";

        String groupname = "";

        final Cursor cursor3 = sqLiteHelperGroups.getData(query2);

        while (cursor3.moveToNext()) {
            try {
                groupname = cursor3.getString(1);
                Log.i("firsttime", groupname);
                StringTokenizer stringTokenizer = new StringTokenizer(groupname, ".");
                groupslist.add(new Groups(stringTokenizer.nextToken(), stringTokenizer.nextToken()));
            } catch (Exception e) {

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
                Log.i("firsttime", sStackTrace);
            }
        }

        final Handler handler = new Handler();
        // Define the code block to be executed
        final Runnable runnableCode =
                new Runnable() {
                    @Override
                    public void run() {
                        int groupid = 0;
                        Cursor cursor3 = null;
                        try {
                            try {
                                cursor3 = sqLiteHelperGroups.getData(query2);
                            } catch (Exception e) {
                            }
                            if (cursor3 != null) {
                                cursor3.moveToLast();
                                try {
                                    groupid = cursor3.getInt(0);
                                    pushid(groupid);
                                } catch (Exception e) {
                                    pushid(0);
                                }
                            } else {
                            }
                            Cursor cursor4 = null;
                            try {
                                cursor4 = sqLiteHelperGroups.getData(query2);

                                if (cursor4 != null) {
                                    groupslist.clear();
                                    while (cursor4.moveToNext()) {
                                        String groupname = cursor4.getString(1);
                                        StringTokenizer stringTokenizer = new StringTokenizer(groupname, ".");

                                        String year = stringTokenizer.nextToken();
                                        String name = stringTokenizer.nextToken();
                                        groupslist.add(new Groups(year, name));
                                        try {
                                            //      sqLiteHelperGroups.queryData("CREATE TABLE IF NOT EXISTS
                                            // "+year+name+"(messageid INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT,message
                                            // TEXT,time TEXT)");

                                            String sql =
                                                    "CREATE TABLE IF NOT EXISTS q"
                                                            + year
                                                            + name
                                                            + "(messageid INTEGER PRIMARY KEY AUTOINCREMENT, sender TEXT,messagetype TEXT,messagevalue TEXT,time TEXT)";
                                            sqLiteHelperGroups.queryData(sql);

                                            //   Toast.makeText(getActivity(),"Table Successfully created for
                                            // "+year+name,Toast.LENGTH_LONG).show();

                                        } catch (Exception e) {

                                        }
                                        groupid = cursor4.getInt(0);
                                    }
                                } else {

                                }
                            } catch (Exception e) {

                            }

                        } catch (Exception e) {

                        }
                        // groupsAdapter = new GroupsAdapter(getActivity(), groupslist);
                        //  groups_list.setAdapter(groupsAdapter);
                        groupsAdapter.notifyDataSetChanged();

                        //   Toast.makeText(getActivity(), String.valueOf(groupid), Toast.LENGTH_SHORT).show();

                        handler.postDelayed(this, 2000);
                    }
                };
        handler.post(runnableCode);

        groups_list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Groups groups = (Groups) parent.getItemAtPosition(position);
                        Toast.makeText(getActivity(), groups.getYear() + groups.getname(), Toast.LENGTH_SHORT)
                                .show();
                        Intent i = new Intent(getActivity(), chatactivity.class);
                        i.putExtra("groupname", groups.getYear() + groups.getname());
                        messageidhelper.setMessageid(0);
                        startActivity(i);
                    }
                });

        groups_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });
        final CustomSpinner customSpinner = (CustomSpinner) view.findViewById(R.id.spinner);
        final List<String> menulist = new ArrayList<>(Arrays.asList(menuitems));


        final ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.spinner_item2, menulist) {
                    @Override
                    public boolean isEnabled(int position) {

                        return true;

                    }

                };

        final ArrayAdapter<String> spinnerArrayAdapter2 =
                new ArrayAdapter<String>(getContext(), R.layout.spinner_item2, menulist) {
                    @Override
                    public boolean isEnabled(int position) {

                        return false;

                    }
                };
        customSpinner.setAdapter(spinnerArrayAdapter);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customSpinner.setSelection(3);
                customSpinner.performClick();
            }
        });

        customSpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened() {
                customSpinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSpinnerClosed() {
                customSpinner.setVisibility(View.INVISIBLE);
            }
        });
        final Context context = getContext();
        customSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++check[0] > 1) {
                    TextView textView = (TextView) view;
                    if (!textView.getText().equals("")) {
//                        Toast.makeText(context, textView.getText(), Toast.LENGTH_SHORT).show();
                        String s = textView.getText().toString();
                        switch (s) {
                            case "New Message":
                                if(!getadmin().equals("YES")){
                                    Toast.makeText(context,"Only admin can perform this action",Toast.LENGTH_SHORT).show();
                                }else{
                                    Intent k = new Intent(context, ListingAllGroups.class);
                                    startActivity(k);
                                    Toast.makeText(context, "New Msg", Toast.LENGTH_SHORT).show();
                                }

                                break;
                            case "Add Group":
                                Toast.makeText(context,"Add Grp", Toast.LENGTH_SHORT).show();
                                break;
                            case "Delete Group":
                                Toast.makeText(context,"Delete Grp",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
//        Toast.makeText(context,String.valueOf(check[0]),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//          Toast.makeText(context,customSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }


    public void pushid(final int id) {
        Log.i("huh", "I am in push id  groupid : " + String.valueOf(id) + " ? ");

        String url =
                "http://93.188.165.250/php_files/getgroups2.php"; // <----enter your post url here
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("response", response);
                                try {

                                    if (response.equals("error")) {

                                    } else {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray arr = null;
                                        arr = jsonObject.getJSONArray("groupnames");
                                        String group = "";
                                        for (int i = 0; i < arr.length(); i++) {
                                            JSONObject groupnames = arr.getJSONObject(i);
                                            group = groupnames.getString("groupname");
                                            try {
                                                sqLiteHelperGroups.queryData(
                                                        "INSERT INTO groups('groupname') VALUES('" + group + "')");
                                            } catch (Exception e) {
                                                StringWriter stringWriter = new StringWriter();
                                                PrintWriter printWriter = new PrintWriter(stringWriter);
                                                e.printStackTrace(printWriter);
                                                String sStackTrace = stringWriter.toString();
                                            }
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response
                                .ErrorListener() { // Create an error listener to handle errors appropriately.
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

                                // This code is executed if there is an error.
                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        MyData.put("domainname", getdomainname());
                        MyData.put("admin", getadmin().toLowerCase());
                        MyData.put("groupid", String.valueOf(id));

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
            cursor = sqLiteHelperGroups.getData("SELECT * FROM users");
            String a = "";
            while (cursor.moveToNext()) {
                domainmail = cursor.getString(6);
                break;
            }
            //  Toast.makeText(this,a,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
            // Toast.makeText(this, sStackTrace, Toast.LENGTH_SHORT).show();
        }

        StringTokenizer st = new StringTokenizer(domainmail.toLowerCase(), "@");
        return st.nextToken();
    }

    public String getadmin() {
        Cursor cursor;
        String admin = "";

        try {
            cursor = sqLiteHelperGroups.getData("SELECT * FROM users");
            String a = "";
            while (cursor.moveToNext()) {
                admin = cursor.getString(8);
                break;
            }
            //  Toast.makeText(this,a,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
            // Toast.makeText(this, sStackTrace, Toast.LENGTH_SHORT).show();
        }
        return admin;
    }
}
