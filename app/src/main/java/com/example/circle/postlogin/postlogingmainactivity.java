package com.example.circle.postlogin;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.circle.MainActivity;
import com.example.circle.R;
import com.example.circle.SQLiteHelper;
import com.example.circle.ayvarsservice.SensorService;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleToggleView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class postlogingmainactivity extends AppCompatActivity {
  Context ctx;
  Intent mServiceIntent;
  private SensorService mSensorService;
  SQLiteHelper sqLiteHelper;
  public static Cursor cursor;
  public static String name,
      rollnumber,
      passoutyear,
      branch,
      email,
      phone,
      domainmail,
      password,
      admin;
  ViewPager viewPager;
  BottomNavigationView navigation;
  List<Fragment> listFragment;
  BubbleNavigationConstraintView bubbleNavigationConstraintView;
  BubbleToggleView bubbleToggleViewinbox;
  BubbleToggleView bubbleToggleViewtimeline;
  BubbleToggleView bubbleToggleViewaccount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_postlogingmainactivity);
    ctx=this;
   // SensorService.setServicecontext(this);
    sqLiteHelper = new SQLiteHelper(this, "user.sqlite", null, 1);
    sqLiteHelper.queryData(
        "CREATE TABLE IF NOT EXISTS users(name VARCHAR,rollnumber VARCHAR,passoutyear VARCHAR,branch VARCHAR,email VARCHAR, phone VARCHAR, domainmail VARCHAR, password VARCHAR, admin VARCHAR)");
    Context context = this;
    accountdetailshelper.setContext(context);
    try {
      cursor = sqLiteHelper.getData("SELECT * FROM users");
      String a = "";
      while (cursor.moveToNext()) {
        name = cursor.getString(0);
        rollnumber = cursor.getString(1);
        passoutyear = cursor.getString(2);
        branch = cursor.getString(3);
        email = cursor.getString(4);
        phone = cursor.getString(5);
        domainmail = cursor.getString(6);
        password = cursor.getString(7);
        admin = cursor.getString(8);
        a = a + "name : " + name + "\n" + email + "\n" + phone + "\n" + domainmail + "\n" + password + "\n" + admin + "\n" + rollnumber + "\n" + passoutyear + "\n" + branch + "\n";
        Toast.makeText(this, "In post login activity \n name : " + name + "\n" + "rollnumber : " + rollnumber + "passoutyear :" + passoutyear + email + "\n" + phone + "\n" + domainmail + "\n" + password + "\n" + admin, Toast.LENGTH_SHORT).show();
        break;
      }
    } catch (Exception e) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      e.printStackTrace(printWriter);
      String sStackTrace = stringWriter.toString();
      Toast.makeText(this, sStackTrace, Toast.LENGTH_SHORT).show();
    }
    sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS groups(id INTEGER PRIMARY KEY AUTOINCREMENT,groupname VARCHAR UNIQUE)");





    initView();

    mSensorService = new SensorService(getCtx());
    mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
    if (!isMyServiceRunning(mSensorService.getClass())) {
      startService(mServiceIntent);
    }

  }
  private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        Log.i ("isMyServiceRunning?", true+"");
        return true;
      }
    }
    Log.i ("isMyServiceRunning?", false+"");
    return false;
  }
  @Override
  protected void onDestroy() {
    //stopService(mServiceIntent);
    Log.i("MAINACT", "onDestroy!");
    super.onDestroy();

  }
  public Context getCtx() {
    return ctx;
  }

  private void initView() {
    bubbleNavigationConstraintView =
        (BubbleNavigationConstraintView) findViewById(R.id.top_navigation_constraint);
    viewPager = (ViewPager) findViewById(R.id.view_pager);
    listFragment = new ArrayList<>();
    listFragment.add(new Fragment1());
    listFragment.add(new Fragment2());
    listFragment.add(new Fragment3());
    MyFragAdapter myAdapter = new MyFragAdapter(getSupportFragmentManager(), this, listFragment);
    viewPager.setAdapter(myAdapter);

    viewPager.addOnPageChangeListener(
        new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int i, float v, int i1) {}

          @Override
          public void onPageSelected(int i) {
            bubbleNavigationConstraintView.setCurrentActiveItem(i);
          }

          @Override
          public void onPageScrollStateChanged(int i) {}
        });

    bubbleNavigationConstraintView.setNavigationChangeListener(
        new BubbleNavigationChangeListener() {
          @Override
          public void onNavigationChanged(View view, int position) {
            viewPager.setCurrentItem(position, true);
          }
        });
  }



}
