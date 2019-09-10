package com.example.circle;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.example.circle.ForgotPassword.forgothelper;
import com.example.circle.MailService.sendhelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import com.example.circle.postlogin.postlogingmainactivity;

public class MainActivity extends AppCompatActivity {
  View viewx, viewsignup;
  LottieAnimationView lottieAnimationView1, lottieAnimationView2;
  Button signupback;
  Button signupbutton;
  Button loginbutton;
  ImageView gvplogo;
  TextView signup;
  TextView forgotpassword, signnuphead;
  ConstraintLayout constraintLayout;
  Spinner spinner;
  Spinner spinner2;
  Spinner spinner3;
  TextInputLayout rollno, domainmail, name, phonenumber, email, password, confirmpassword;
  TextInputLayout loginmail, loginpassword;
  ScrollView scrollView1;
  int count;
  SQLiteHelper sqLiteHelper;
  public static Cursor cursor;

  String[] roles = new String[] {"Select Your Role", "Student", "Faculty"};
  String[] branches =
      new String[] {"Select Your Branch", "CSE", "ECE", "IT", "MECH", "CIVIL", "CHEMICAL"};
  String[] dates;

  public static Activity act;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sqLiteHelper = new SQLiteHelper(this, "user.sqlite", null, 1);
    sqLiteHelper.queryData(
        "CREATE TABLE IF NOT EXISTS users(name VARCHAR,rollnumber VARCHAR,passoutyear VARCHAR,branch VARCHAR,email VARCHAR, phone VARCHAR, domainmail VARCHAR, password VARCHAR, admin VARCHAR)");
    String countx = "SELECT count(*) FROM users";

    Cursor cursor2 = sqLiteHelper.getData(countx);
    cursor2.moveToFirst();
    int icount = cursor2.getInt(0);

    if (icount > 0) {
      Intent ix = new Intent(MainActivity.this, postlogingmainactivity.class);
      //     startActivity(ix);
      try {
        cursor = sqLiteHelper.getData("SELECT * FROM users");
        String a = "";
        while (cursor.moveToNext()) {
          String name = cursor.getString(0);
          String rollnumber = cursor.getString(1);
          String passoutyear = cursor.getString(2);
          String branch = cursor.getString(3);
          String email = cursor.getString(4);
          String phone = cursor.getString(5);
          String domainmail = cursor.getString(6);
          String password = cursor.getString(7);
          String admin = cursor.getString(8);
          a =
              a
                  + "name : "
                  + name
                  + "\n"
                  + email
                  + "\n"
                  + phone
                  + "\n"
                  + domainmail
                  + "\n"
                  + password
                  + "\n"
                  + admin
                  + "\n"
                  + rollnumber
                  + "\n"
                  + passoutyear
                  + "\n"
                  + branch
                  + "\n";
          Toast.makeText(
                  this,
                  "name : "
                      + name
                      + "\n"
                      + email
                      + "\n"
                      + phone
                      + "\n"
                      + domainmail
                      + "\n"
                      + password
                      + "\n"
                      + admin,
                  Toast.LENGTH_SHORT)
              .show();
        }
        Toast.makeText(this, a, Toast.LENGTH_LONG).show();
      } catch (Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String sStackTrace = stringWriter.toString();
        Toast.makeText(this, sStackTrace, Toast.LENGTH_SHORT).show();
      }

    } else {
      Toast.makeText(this, "Haven't logged in yet", Toast.LENGTH_LONG).show();
    }
    // leave
    // populate table

    // sqLiteHelper.queryData("SELECT * FROM user");

    // net connectivity checking section
    ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
    NetworkInfo nInfo = cm.getActiveNetworkInfo();
    if (nInfo != null && nInfo.isConnected()) {

    } else {
      AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
      a_builder
          .setMessage("Please enable internet connection !!!")
          .setCancelable(false)
          .setNegativeButton(
              "Close",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  MainActivity.this.finish();
                }
              });
      AlertDialog alert = a_builder.create();
      alert.setTitle("No Internet Connection");
      alert.show();
    }
    // net connectivity checking section completed

    act = this;
    viewx = (View) findViewById(R.id.view1);
    viewsignup = (View) findViewById(R.id.view1signup);
    signup = (TextView) findViewById(R.id.signup);
    signnuphead = (TextView) findViewById(R.id.signuphead);
    signupback = (Button) findViewById(R.id.button4);
    loginbutton = (Button) findViewById(R.id.loginbutton);
    gvplogo = (ImageView) findViewById(R.id.imageView);
    forgotpassword = (TextView) findViewById(R.id.forgotpassword);
    constraintLayout = (ConstraintLayout) findViewById(R.id.cl2);
    spinner = (Spinner) findViewById(R.id.spinner1);
    spinner2 = (Spinner) findViewById(R.id.spinner2);
    spinner3 = (Spinner) findViewById(R.id.spinner3);
    name = (TextInputLayout) findViewById(R.id.name);
    rollno = (TextInputLayout) findViewById(R.id.Roll_Number);
    phonenumber = (TextInputLayout) findViewById(R.id.phonenumber);
    email = (TextInputLayout) findViewById(R.id.email);
    domainmail = (TextInputLayout) findViewById(R.id.domainmail);
    password = (TextInputLayout) findViewById(R.id.password);
    confirmpassword = (TextInputLayout) findViewById(R.id.confirmpassword);
    scrollView1 = (ScrollView) findViewById(R.id.scrollview1);
    signupbutton = (Button) findViewById(R.id.signupbutton);
    lottieAnimationView1 = (LottieAnimationView) findViewById(R.id.animation_view);
    lottieAnimationView2 = (LottieAnimationView) findViewById(R.id.animation_view2);
    loginmail = (TextInputLayout) findViewById(R.id.loginmail);
    loginpassword = (TextInputLayout) findViewById(R.id.loginpassword);

    final Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
    final Animation myAnim2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomin);
    final Animation myAnim3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomout);

    viewx.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            viewx.startAnimation(myAnim);
          }
        });
    signup.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            viewx.startAnimation(myAnim2);
            constraintLayout.startAnimation(myAnim2);
            viewx.setVisibility(View.INVISIBLE);
            // viewx.startAnimation(myAnim3);
            constraintLayout.setVisibility(View.INVISIBLE);
            gvplogo.setVisibility(View.INVISIBLE);
            scrollView1.setVisibility(View.VISIBLE);
            viewsignup.setVisibility(View.VISIBLE);

            scrollView1.startAnimation(myAnim3);
            viewsignup.startAnimation(myAnim3);
            signupback.setVisibility(View.VISIBLE);
            signnuphead.setVisibility(View.VISIBLE);
          }
        });
    signupback.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            signnuphead.setVisibility(View.INVISIBLE);
            scrollView1.startAnimation(myAnim2);
            viewsignup.startAnimation(myAnim2);
            scrollView1.setVisibility(View.INVISIBLE);
            viewsignup.setVisibility(View.INVISIBLE);
            signupback.setVisibility(View.INVISIBLE);
            viewx.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
            viewx.startAnimation(myAnim3);
            constraintLayout.startAnimation(myAnim3);
            gvplogo.setVisibility(View.VISIBLE);
          }
        });

    signupbutton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            final String name_str = name.getEditText().getText().toString();
            final String Roll_Number_str = rollno.getEditText().getText().toString();
            final String phonenumber_str = phonenumber.getEditText().getText().toString();
            final String email_str = email.getEditText().getText().toString();
            final String domainmail_str =
                domainmail.getEditText().getText().toString().toLowerCase();
            final String password_str = password.getEditText().getText().toString();
            final String confirmpassword_str = confirmpassword.getEditText().getText().toString();
            int sum, i, rem;
            Random r;
            count = 0;
            name.setError(null);
            rollno.setError(null);
            phonenumber.setError(null);
            email.setError(null);
            domainmail.setError(null);
            password.setError(null);
            confirmpassword.setError(null);

            if (spinner.getSelectedItem().equals("Select Your Role")) {
              count++;
              Toast.makeText(MainActivity.this, "Select Your Role To Proceed", Toast.LENGTH_SHORT)
                  .show();
            }
            if (spinner.getSelectedItem().equals("Student")
                && spinner2.getSelectedItem().equals("Select Your Branch")) {
              count++;
              Toast.makeText(MainActivity.this, "Select Your Branch To Proceed", Toast.LENGTH_SHORT)
                  .show();
            }
            if (spinner.getSelectedItem().equals("Student")
                && spinner3.getSelectedItem().equals("Select your Passout Year")) {
              count++;
              Toast.makeText(
                      MainActivity.this, "Select Your Passout year To Proceed", Toast.LENGTH_SHORT)
                  .show();
            }
            loginmail.getEditText().getText().toString().toLowerCase();
            if (!name_str.matches("^[a-zA-Z ]+$") || name_str.equals("")) {
              count++;
              name.requestFocus();
              name.setError("Enter a Valid Name");
            }
            if (spinner.getSelectedItem().equals("Student")) {
              if (!Roll_Number_str.matches("^[A-Z0-9]+$")
                  || Roll_Number_str.equals("")
                  || Roll_Number_str.length() < 10) {
                count++;
                rollno.requestFocus();
                rollno.setError("Enter a Valid Roll No");
              }
            }

            if (!(phonenumber_str.matches("^[0-9]+$") && phonenumber_str.length() == 10)
                || phonenumber_str.equals("")) {
              count++;
              phonenumber.requestFocus();
              phonenumber.setError("Enter a Valid Phone Number");
            }
            if (!email_str.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}+$")
                || email_str.equals("")) {
              count++;
              email.requestFocus();
              email.setError("Enter a Valid Email Address");
            }
            if (spinner.getSelectedItem().equals("Student")) {
              char dm[] = domainmail_str.toCharArray();
              if (domainmail_str.equals("")) {
                count++;
                domainmail.requestFocus();
                domainmail.setError("Domain Mail Can't be Null");
              } else if (!domainmail_str.matches("^[a-zA-Z0-9._%+-]+@gvpce.ac.in+$")
                  || !(dm[0] >= '1' && dm[0] <= '9')
                  || !(dm[1] >= '1' && dm[1] <= '9')) {
                count++;
                domainmail.requestFocus();
                domainmail.setError("Enter Valid Student domain Mail");
              }
            } else if (spinner.getSelectedItem().equals("Faculty")) {
              char dm[] = domainmail_str.toCharArray();
              if (domainmail_str.equals("")) {
                count++;
                domainmail.requestFocus();
                domainmail.setError("Domain Mail Can't be Null");
              } else if (!domainmail_str.matches("^[a-zA-Z0-9._%+-]+@gvpce.ac.in+$")
                  || (dm[0] >= '1' && dm[0] <= '9')
                  || (dm[1] >= '1' && dm[1] <= '9')) {
                count++;
                domainmail.requestFocus();
                domainmail.setError("Enter Valid Faculty domain Mail");
              }
            }
            if (password_str.equals("")) {
              count++;
              password.requestFocus();
              password.setError("Password Can't be NUll");
            } else if (password_str.length() < 8) {
              count++;
              password.requestFocus();
              password.setError("Password Length atleast 8");
            }
            if (confirmpassword_str.equals("")) {
              count++;
              confirmpassword.requestFocus();
              confirmpassword.setError("Confirm Password Can't be NUll");
            } else if (!confirmpassword_str.equals(password_str)) {
              count++;
              confirmpassword.requestFocus();
              confirmpassword.setError("Password not Matched");
            }
            if (count == 0) {
              signupbutton.setVisibility(View.INVISIBLE);
              lottieAnimationView2.setVisibility(View.VISIBLE);

              StringTokenizer st = new StringTokenizer(domainmail_str, "@");

              Registrationhelper.setName(name_str);
              Registrationhelper.setRole(spinner.getSelectedItem().toString());
              Registrationhelper.setBranch(spinner2.getSelectedItem().toString());
              Registrationhelper.setPassout_year(spinner3.getSelectedItem().toString());
              Registrationhelper.setRoll_number(Roll_Number_str);
              Registrationhelper.setPhone(phonenumber_str);
              Registrationhelper.setEmail(email_str);
              Registrationhelper.setDomain_mail(domainmail_str);
              Registrationhelper.setPassword(password_str);
              Registrationhelper.setDisplayname(st.nextToken());

              String domain_mail_id_1 = domainmail.getEditText().getText().toString();
              sendhelper.setDommail(domain_mail_id_1);
              sum = 0;
              for (i = 0; i < 5; i++) {
                r = new Random();
                rem = r.nextInt(9) + 1;
                sum = sum * 10 + rem;
              }
              sendhelper.setOtp(sum);
              sendhelper.setType("Signup");
              new sendhelper(getApplicationContext()).execute();
            }
          }
        });

    forgotpassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendhelper.setType("forgot");
        Intent i1=new Intent(MainActivity.this, forgothelper.class);
        startActivity(i1);
      }
    });

    loginbutton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            loginbutton.setVisibility(View.INVISIBLE);
            lottieAnimationView1.setVisibility(View.VISIBLE);
            String loginmail_str = loginmail.getEditText().getText().toString().toLowerCase();
            String loginpassword_str = loginpassword.getEditText().getText().toString();
            /// logincheck(loginmail_str,loginpassword_str);
            count = 0;
            if (loginmail_str.equals("")) {
              count++;
              loginmail.setError("Fill the Details");
              lottieAnimationView1.setVisibility(View.INVISIBLE);
              loginbutton.setVisibility(View.VISIBLE);
            }
            if (loginpassword_str.equals("")) {
              count++;
              loginpassword.setError("Fill the Details");
              lottieAnimationView1.setVisibility(View.INVISIBLE);
              loginbutton.setVisibility(View.VISIBLE);
            }
            if (count == 0) {
              char lm[] = loginmail_str.toCharArray();
              if ((lm[0] >= '1' && lm[0] <= '9') && (lm[1] >= '1' && lm[1] <= '9')) {
                Student_logincheck(loginmail_str, loginpassword_str);
              } else {
                Faculty_logincheck(loginmail_str, loginpassword_str);
              }
            }
          }
        });



    Date dNow = new Date();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy");
    dates =
        new String[] {
          "Select your Passout Year",
          String.valueOf(Integer.parseInt(ft.format(dNow))),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 1),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 2),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 3),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 4),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 5),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 6),
          String.valueOf(Integer.parseInt(ft.format(dNow)) + 7)
        };

    final List<String> rolesList = new ArrayList<>(Arrays.asList(roles));
    final List<String> brancheslist = new ArrayList<>(Arrays.asList(branches));
    final List<String> dateslist = new ArrayList<>(Arrays.asList(dates));
    // Initializing an ArrayAdapter
    final ArrayAdapter<String> spinnerArrayAdapter =
        new ArrayAdapter<String>(this, R.layout.spinner_item, rolesList) {
          @Override
          public boolean isEnabled(int position) {
            if (position == 0) {
              // Disable the first item from Spinner
              // First item will be use for hint
              return false;
            } else {
              return true;
            }
          }

          @Override
          public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if (position == 0) {
              // Set the hint text color gray
              tv.setTextColor(Color.GRAY);
            } else {
              tv.setTextColor(Color.BLACK);
            }
            return view;
          }
        };

    final ArrayAdapter<String> spinnerArrayAdapter2 =
        new ArrayAdapter<String>(this, R.layout.spinner_item, brancheslist) {
          @Override
          public boolean isEnabled(int position) {
            if (position == 0) {
              return false;
            } else {
              return true;
            }
          }

          @Override
          public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if (position == 0) {
              // Set the hint text color gray
              tv.setTextColor(Color.GRAY);
            } else {
              tv.setTextColor(Color.BLACK);
            }
            return view;
          }
        };

    final ArrayAdapter<String> spinnerArrayAdapter3 =
        new ArrayAdapter<String>(this, R.layout.spinner_item, dateslist) {
          @Override
          public boolean isEnabled(int position) {
            if (position == 0) {
              // Disable the first item from Spinner
              // First item will be use for hint
              return false;
            } else {
              return true;
            }
          }

          @Override
          public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if (position == 0) {
              // Set the hint text color gray
              tv.setTextColor(Color.GRAY);
            } else {
              tv.setTextColor(Color.BLACK);
            }
            return view;
          }
        };

    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
    spinner.setAdapter(spinnerArrayAdapter);

    spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
    spinner2.setAdapter(spinnerArrayAdapter2);

    spinnerArrayAdapter3.setDropDownViewResource(R.layout.spinner_item);
    spinner3.setAdapter(spinnerArrayAdapter3);

    spinner.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItemText = (String) parent.getItemAtPosition(position);
            // If user change the default selection
            // First item is disable and it is used for hint
            if (position > 0) {
              // Notify the selected item text
              Toast.makeText(
                      getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                  .show();
              if (selectedItemText.equals("Student")) {
                rollno.setVisibility(View.VISIBLE);
                spinner3.setVisibility(View.VISIBLE);
              }
              if (selectedItemText.equals("Faculty")) {
                rollno.setVisibility(View.GONE);
                spinner3.setVisibility(View.GONE);
              }
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {}
        });

    spinner2.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItemText = (String) parent.getItemAtPosition(position);
            // If user change the default selection
            // First item is disable and it is used for hint
            if (position > 0) {
              // Notify the selected item text
              Toast.makeText(
                      getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                  .show();
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {}
        });

    spinner3.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItemText = (String) parent.getItemAtPosition(position);
            // If user change the default selection
            // First item is disable and it is used for hint
            if (position > 0) {
              // Notify the selected item text
              Toast.makeText(
                      getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                  .show();
            }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {}
        });
  }

  public void logincheck(final String loginmail, final String loginpassword) {
    RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    String url =
        "http://93.188.165.250/php_files/logincheck.php"; // <----enter your post url here
    StringRequest MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                // This code is executed if the server responds, whether or not the response
                // contains data.
                // The String 'response' contains the server's response.
              }
            },
            new Response
                .ErrorListener() { // Create an error listener to handle errors appropriately.
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                // This code is executed if there is an error.
              }
            }) {
          protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();

            MyData.put("loginmail", loginmail);
            MyData.put("loginpassword", loginpassword);

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

  public void Student_logincheck(final String loginmail, final String loginpassword) {
    RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    String url =
        "http://93.188.165.250/php_files/studentlogincheck.php"; // <----enter your post url
    // here
    StringRequest MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                if (response.equals("error")) {
                  Toast.makeText(
                          MainActivity.this, "Invalid Domain mail/Password", Toast.LENGTH_SHORT)
                      .show();
                  lottieAnimationView1.setVisibility(View.INVISIBLE);
                  loginbutton.setVisibility(View.VISIBLE);
                } else {
                  //
                  // Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                  lottieAnimationView1.setVisibility(View.INVISIBLE);
                  loginbutton.setVisibility(View.VISIBLE);
                  JSONArray arr = null;
                  try {

                    JSONObject obj = new JSONObject(response);
                    arr = obj.getJSONArray("userdetails");

                    for (int i = 0; i < arr.length(); i++) {

                      JSONObject userdetails = arr.getJSONObject(i);
                      String name = userdetails.getString("name");
                      String rollnumber = userdetails.getString("rollnumber");
                      String passoutyear = userdetails.getString("passoutyear");
                      String branch = userdetails.getString("branch");
                      String phone = userdetails.getString("phone");
                      String email = userdetails.getString("email");
                      String domainmail = userdetails.getString("domainmail");
                      String password = userdetails.getString("password");
                      String admin = userdetails.getString("admin");

                      try {
                        sqLiteHelper.insertData(
                            name,
                            rollnumber,
                            passoutyear,
                            branch,
                            email,
                            phone,
                            domainmail,
                            password,
                            admin);
                        Intent ix = new Intent(MainActivity.this, postlogingmainactivity.class);
                        startActivity(ix);
                        finish();

                        Toast.makeText(MainActivity.this, "Added successfully!", Toast.LENGTH_SHORT)
                            .show();
                      } catch (Exception e) {

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        System.out.println(sStackTrace);
                        Toast.makeText(MainActivity.this, sStackTrace, Toast.LENGTH_LONG).show();
                      }

                      Log.i("datass:", name + "  " + branch + "  " + password + " ");
                      Toast.makeText(
                              MainActivity.this,
                              name
                                  + "  "
                                  + branch
                                  + "  "
                                  + passoutyear
                                  + " "
                                  + rollnumber
                                  + " "
                                  + phone
                                  + " "
                                  + password
                                  + " "
                                  + email
                                  + " "
                                  + domainmail
                                  + " "
                                  + admin,
                              Toast.LENGTH_SHORT)
                          .show();
                    }
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }
                }

                // This code is executed if the server responds, whether or not the response
                // contains data.
                // The String 'response' contains the server's response.
              }
            },
            new Response
                .ErrorListener() { // Create an error listener to handle errors appropriately.
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                lottieAnimationView1.setVisibility(View.INVISIBLE);
                loginbutton.setVisibility(View.VISIBLE);

                // This code is executed if there is an error.
              }
            }) {
          protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();

            MyData.put("loginmail", loginmail);
            MyData.put("loginpassword", loginpassword);

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

  public void Faculty_logincheck(final String loginmail, final String loginpassword) {
    RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
    String url =
        "http://93.188.165.250/php_files/facultylogincheck.php"; // <----enter your post url
    // here
    StringRequest MyStringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                if (response.equals("error")) {
                  Toast.makeText(
                          MainActivity.this, "Invalid Domain mail/Password", Toast.LENGTH_SHORT)
                      .show();
                  lottieAnimationView1.setVisibility(View.INVISIBLE);
                  loginbutton.setVisibility(View.VISIBLE);
                } else {
                  JSONArray arr = null;
                  try {

                    JSONObject obj = new JSONObject(response);
                    arr = obj.getJSONArray("userdetails");

                    for (int i = 0; i < arr.length(); i++) {

                      JSONObject userdetails = arr.getJSONObject(i);
                      String name = userdetails.getString("name");
                      String branch = userdetails.getString("branch");
                      String phone = userdetails.getString("phone");
                      String email = userdetails.getString("email");
                      String domainmail = userdetails.getString("domainmail");
                      String password = userdetails.getString("password");
                      String admin = userdetails.getString("admin");
                      String rollnumber = "not defined";
                      String passoutyear = "not defined";

                      try {
                        sqLiteHelper.insertData(
                            name,
                            rollnumber,
                            passoutyear,
                            branch,
                            email,
                            phone,
                            domainmail,
                            password,
                            admin);
                        Intent ix = new Intent(MainActivity.this, postlogingmainactivity.class);
                        startActivity(ix);
                        finish();
                        Toast.makeText(MainActivity.this, "Added successfully!", Toast.LENGTH_SHORT)
                            .show();
                      } catch (Exception e) {

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        e.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        System.out.println(sStackTrace);
                        Toast.makeText(MainActivity.this, sStackTrace, Toast.LENGTH_LONG).show();
                      }

                      Log.i("datass:", name + "  " + branch + "  " + password + " ");
                      Toast.makeText(
                              MainActivity.this,
                              name
                                  + "  "
                                  + branch
                                  + "  "
                                  + password
                                  + " "
                                  + email
                                  + " "
                                  + domainmail
                                  + " "
                                  + admin,
                              Toast.LENGTH_SHORT)
                          .show();
                    }
                  } catch (JSONException e) {
                    e.printStackTrace();
                  }

                  //
                  // Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                  lottieAnimationView1.setVisibility(View.INVISIBLE);
                  loginbutton.setVisibility(View.VISIBLE);
                }

                // This code is executed if the server responds, whether or not the response
                // contains data.
                // The String 'response' contains the server's response.
              }
            },
            new Response
                .ErrorListener() { // Create an error listener to handle errors appropriately.
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                lottieAnimationView1.setVisibility(View.INVISIBLE);
                loginbutton.setVisibility(View.VISIBLE);

                // This code is executed if there is an error.
              }
            }) {
          protected Map<String, String> getParams() {
            Map<String, String> MyData = new HashMap<String, String>();

            MyData.put("loginmail", loginmail);
            MyData.put("loginpassword", loginpassword);

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
