package com.example.circle.postlogin;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class AddGroupActivity extends AppCompatActivity {

    ImageView backbutton;
    EditText yearet,branchet;
    FloatingActionButton addbutton;
    RequestQueue MyRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        backbutton = (ImageView) findViewById(R.id.backbutton);
        yearet = (EditText) findViewById(R.id.yearet);
        branchet = (EditText) findViewById(R.id.branchet);
        addbutton = (FloatingActionButton) findViewById(R.id.addbutton);
        MyRequestQueue = Volley.newRequestQueue(getApplicationContext());

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = yearet.getText().toString();
                String branch = branchet.getText().toString().toUpperCase();
                if(year.length()>1&&branch.length()>1){
                    AddGroup(branch,year);
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


    }
    public void AddGroup(final String branch,final String passoutyear) {
        String url =
                "http://93.188.165.250/php_files/add_group.php"; // <----enter your post url here
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response!=null){
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                    finish();
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
}
