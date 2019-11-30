package com.example.circle.postlogin;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroupInfoActivity extends AppCompatActivity {

    ImageView backbutton,addstudent;
    TextView title;
    ListView StudentListView;
    ArrayList<student> studentsarraylist;
    GroupInfoStudentsListAdapter studentsListAdapter;
    RequestQueue MyRequestQueue;
    ConstraintLayout addlayout;
    FloatingActionButton addstudentbtn,cancelbtn;
    EditText etaddstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        backbutton = (ImageView) findViewById(R.id.backbutton);
        addstudent = (ImageView) findViewById(R.id.addparticipant);
        StudentListView = (ListView)findViewById(R.id.students_list);
        title = (TextView) findViewById(R.id.textView3);
        addlayout = (ConstraintLayout) findViewById(R.id.addlayout);
        addstudentbtn = (FloatingActionButton) findViewById(R.id.addbutton);
        cancelbtn = (FloatingActionButton) findViewById(R.id.canceladd);
        etaddstudent = (EditText) findViewById(R.id.etstudentadd);

        title.setText(getIntent().getStringExtra("passoutyear")+getIntent().getStringExtra("branch"));
        MyRequestQueue = Volley.newRequestQueue(getApplicationContext());

        studentsarraylist = new ArrayList<>();
        studentsListAdapter = new GroupInfoStudentsListAdapter(this,studentsarraylist);
        StudentListView.setAdapter(studentsListAdapter);
        GetStudentslist(getIntent().getStringExtra("branch"),getIntent().getStringExtra("passoutyear"));


        addstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"huhu",Toast.LENGTH_SHORT).show();
                addlayout.setVisibility(View.VISIBLE);

            }
        });
        addstudentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rollnumber = etaddstudent.getText().toString();
                String s="";
                for(int i = 0 ; i < studentsarraylist.size() ; i ++){
                    if(studentsarraylist.get(i).getStudentname().toUpperCase().equals(rollnumber.toUpperCase())){
                        s="contains";
                        break;
                    }else{
                        s="";
                    }
                }
                if(s.equals("contains")){
                    Toast.makeText(getApplicationContext(),rollnumber+"is already a participant of this group",Toast.LENGTH_LONG).show();
                }else{
                    AddStudent(getIntent().getStringExtra("branch").toUpperCase(),getIntent().getStringExtra("passoutyear"),rollnumber.toLowerCase());
                }
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addlayout.setVisibility(View.GONE);
                etaddstudent.setText("");
            }
        });
    }
    public void GetStudentslist(final String branch,final String passoutyear) {
        String url =
                "http://93.188.165.250/php_files/getstudentslist.php"; // <----enter your post url here
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                if(response!=null){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray array = jsonObject.getJSONArray("students");
                                        studentsarraylist.clear();
                                        for(int i = 0 ; i < array.length() ; i ++){
                                            JSONObject obj = array.getJSONObject(i);
                                            String rollnumber = obj.getString("rollnumber");
                                            studentsarraylist.add(new student(rollnumber));
                                            Toast.makeText(getApplicationContext(),rollnumber,Toast.LENGTH_SHORT).show();
                                        }
                                        studentsListAdapter.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        StringWriter errors = new StringWriter();
                                        e.printStackTrace(new PrintWriter(errors));
                                       Log.i("abraka",errors.toString());
                                    }
                                }
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

                        MyData.put("branch", branch);
                        MyData.put("passoutyear",passoutyear);
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
    public void AddStudent(final String branch,final String passoutyear,final String rollnumber) {
        String url =
                "http://93.188.165.250/php_files/add_student_to_group.php"; // <----enter your post url here
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                addlayout.setVisibility(View.GONE);
                                GetStudentslist(getIntent().getStringExtra("branch"),getIntent().getStringExtra("passoutyear"));
                                etaddstudent.setText("");
                            }
                        },
                        new Response
                                .ErrorListener() { // Create an error listener to handle errors appropriately.
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                addlayout.setVisibility(View.GONE);
                                // This code is executed if there is an error.
                            }
                        }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        MyData.put("branch", branch);
                        MyData.put("passoutyear",passoutyear);
                        MyData.put("rollnumber",rollnumber);
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
