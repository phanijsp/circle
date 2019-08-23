package com.example.circle;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.circle.postlogin.postlogingmainactivity;

import java.io.PrintWriter;
import java.io.StringWriter;

public class splashActivity extends AppCompatActivity {

  SQLiteHelper sqLiteHelper;
  public static Cursor cursor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    sqLiteHelper = new SQLiteHelper(this, "user.sqlite", null, 1);
    sqLiteHelper.queryData(
        "CREATE TABLE IF NOT EXISTS users(name VARCHAR,rollnumber VARCHAR,passoutyear VARCHAR,branch VARCHAR,email VARCHAR, phone VARCHAR, domainmail VARCHAR, password VARCHAR, admin VARCHAR)");
    String countx = "SELECT count(*) FROM users";

    Cursor cursor2 = sqLiteHelper.getData(countx);
    cursor2.moveToFirst();
    int icount = cursor2.getInt(0);

    if (icount > 0) {
      Intent ix = new Intent(splashActivity.this, postlogingmainactivity.class);
      startActivity(ix);
      this.finish();
    } else {
      Toast.makeText(this, "Haven't logged in yet", Toast.LENGTH_SHORT).show();
      Intent ix = new Intent(splashActivity.this, MainActivity.class);
      startActivity(ix);
      this.finish();
    }
  }
}
