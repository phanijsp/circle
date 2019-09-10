package com.example.circle.ForgotPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.circle.MailService.sendhelper;
import com.example.circle.MainActivity;
import com.example.circle.R;

import java.util.HashMap;
import java.util.Map;

import static com.example.circle.MailService.sendhelper.getDommail;

public class forgotreset extends AppCompatActivity {

    String domainmail_str,new_password_str,confirm_new_password_str,role_str;
    int count;
    EditText new_password;
    EditText confirm_new_password;
    Button reset;
    Intent intent;
    RequestQueue MyRequestQueue;
    public static String role;

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        forgotreset.role = role;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotreset);

        intent=new Intent(forgotreset.this, sendhelper.class);

        new_password=(EditText) findViewById(R.id.newpassword);
        confirm_new_password=(EditText) findViewById(R.id.confirmnewpassword);
        reset=(Button) findViewById(R.id.verifybutton);

        domainmail_str=getDommail();
        role_str=getRole();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                new_password_str=new_password.getText().toString();
                confirm_new_password_str=confirm_new_password.getText().toString();
                if (new_password_str.equals("")) {
                    count++;
                    new_password.requestFocus();
                    new_password.setError("Password Can't be NUll");
                } else if (new_password_str.length() < 8) {
                    count++;
                    new_password.requestFocus();
                    new_password.setError("Password Length atleast 8");
                }
                if (confirm_new_password_str.equals("")) {
                    count++;
                    confirm_new_password.requestFocus();
                    confirm_new_password.setError("Confirm Password Can't be NUll");
                } else if (!confirm_new_password_str.equals(new_password_str)) {
                    count++;
                    confirm_new_password.requestFocus();
                    confirm_new_password.setError("Password not Matched");
                }
                if(count==0)
                {
                    /*Toast.makeText(forgotreset.this, "Password Reset Successful", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(forgotreset.this, MainActivity.class);
                    startActivity(i);*/
                    changepassword(role_str,domainmail_str,new_password_str);
                }
            }
        });
    }

    public void changepassword(final String role_str,final String domainmail_str,final String new_password_str)
    {
        MyRequestQueue = Volley.newRequestQueue(this);
        String url="http://93.188.165.250/php_files/changepassword.php";
        StringRequest MyStringRequest =
                new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("qwe",response);
                                if(response.equals("success"))
                                {
                                    Toast.makeText(forgotreset.this, "Password updated", Toast.LENGTH_SHORT).show();
                                    sendhelper.setType("reset");
                                    sendhelper.setDommail(domainmail_str);
                                    sendhelper.setPassword(new_password_str);
                                    new sendhelper(getApplicationContext()).execute();
                                }
                                else
                                {
                                    Toast.makeText(forgotreset.this, "Server Error Occured", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response
                                .ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(forgotreset.this, "Please check the netwrok and try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();

                        MyData.put("tablename",role_str);
                        MyData.put("domainmail", domainmail_str);
                        MyData.put("newpassword",new_password_str);
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
