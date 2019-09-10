package com.example.circle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circle.ForgotPassword.forgotreset;
import com.example.circle.MailService.sendhelper;

import java.util.HashMap;
import java.util.Map;

public class otpactivity extends AppCompatActivity {

  TextView errordisplay;
  Button verifybutton;
  EditText enteredotp;
  Intent i;
  String type;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_otpactivity);
    errordisplay = (TextView) findViewById(R.id.errordisplay);
    verifybutton = (Button) findViewById(R.id.verifybutton);
    enteredotp = (EditText) findViewById(R.id.enteredotp);

    i = new Intent(otpactivity.this, MainActivity.class);

    verifybutton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              type=sendhelper.getType();
            String value = enteredotp.getText().toString();
            int count = 0;
            if (value.length() != 5) {
              count++;
              enteredotp.requestFocus();
              enteredotp.setError("Otp invalid");
            }
            if(type=="Signup")
            {
                if (count == 0) {
                    if (otphelper.getOtpvalue() == Integer.parseInt(value)) {

                        sendhelper.setType("Registered");
                        sendhelper.setDommail(Registrationhelper.getDomain_mail());
                        sendhelper.setPassword(Registrationhelper.getPassword());
                        new sendhelper(getApplicationContext()).execute();

                        MainActivity.act.finish();
                        Toast.makeText(otpactivity.this, "Successfully Registered", Toast.LENGTH_SHORT)
                                .show();
                        if (Registrationhelper.getRole().equals("Student")) {
                            registerstudent();
                        }
                        if (Registrationhelper.getRole().equals("Faculty")) {
                            registerfaculty();
                        }

                    } else {
                        errordisplay.setVisibility(View.VISIBLE);
                    }
                }
            }
            else if(type=="forgot")
            {
                if(count==0)
                {
                    if(otphelper.getOtpvalue()== Integer.parseInt(value))
                    {
                        Intent i=new Intent(otpactivity.this, forgotreset.class);
                        startActivity(i);
                    }
                    else
                    {
                        errordisplay.setVisibility(View.VISIBLE);
                    }
                }

            }

          }
        });
  }

  public void callvolly() {
    RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    String url = "http://93.188.165.250/php_files/sample.php"; // <----enter your post url here
    StringRequest MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Toast.makeText(otpactivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                // This code is executed if the server responds, whether or not the response
                // contains data.
                // The String 'response' contains the server's response.
              }
            },
            new Response
                .ErrorListener() { // Create an error listener to handle errors appropriately.
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(otpactivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                // This code is executed if there is an error.
              }
            }) {
          protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();

            MyData.put("name", Registrationhelper.getName());
            // MyData.put("role",Registrationhelper.getRole());
            MyData.put("branch", Registrationhelper.getBranch());
            MyData.put("passoutyear", Registrationhelper.getPassout_year());
            MyData.put("rollnumber", Registrationhelper.getRoll_number());
            MyData.put("phone", Registrationhelper.getPhone());
            MyData.put("email", Registrationhelper.getEmail());
            MyData.put("domainmail", Registrationhelper.getDomain_mail());
            MyData.put("password", Registrationhelper.getPassword());
            MyData.put("admin", "no");

            return MyData;
          }
        };

    MyRequestQueue.add(MyStringRequest);
  }

  public void registerstudent() {
    RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    String url =
        "http://93.188.165.250/php_files/studentregister.php"; // <----enter your post url here
    StringRequest MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Toast.makeText(otpactivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
                // This code is executed if the server responds, whether or not the response
                // contains data.
                // The String 'response' contains the server's response.
              }
            },
            new Response
                .ErrorListener() { // Create an error listener to handle errors appropriately.
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(otpactivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                // This code is executed if there is an error.
              }
            }) {
          protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();

            MyData.put("name", Registrationhelper.getName());
            // MyData.put("role",Registrationhelper.getRole());
            MyData.put("branch", Registrationhelper.getBranch());
            MyData.put("passoutyear", Registrationhelper.getPassout_year());
            MyData.put("rollnumber", Registrationhelper.getRoll_number());
            MyData.put("phone", Registrationhelper.getPhone());
            MyData.put("email", Registrationhelper.getEmail());
            MyData.put("domainmail", Registrationhelper.getDomain_mail());
            MyData.put("password", Registrationhelper.getPassword());
            MyData.put("admin", "no");
            MyData.put("displayname", Registrationhelper.getDisplayname());

            return MyData;
          }
        };

    MyRequestQueue.add(MyStringRequest);
  }

  public void registerfaculty() {
    RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    String url =
        "http://93.188.165.250/php_files/facultyregister.php"; // <----enter your post url here
    StringRequest MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Toast.makeText(otpactivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
                // This code is executed if the server responds, whether or not the response
                // contains data.
                // The String 'response' contains the server's response.
              }
            },
            new Response
                .ErrorListener() { // Create an error listener to handle errors appropriately.
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(otpactivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                // This code is executed if there is an error.
              }
            }) {
          protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();

            MyData.put("name", Registrationhelper.getName());
            MyData.put("branch", Registrationhelper.getBranch());
            MyData.put("phone", Registrationhelper.getPhone());
            MyData.put("email", Registrationhelper.getEmail());
            MyData.put("domainmail", Registrationhelper.getDomain_mail());
            MyData.put("password", Registrationhelper.getPassword());
            MyData.put("admin", "yes");

            return MyData;
          }
        };

    MyRequestQueue.add(MyStringRequest);
  }
}
