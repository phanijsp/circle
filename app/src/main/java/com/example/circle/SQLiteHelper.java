package com.example.circle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

  public SQLiteHelper(
      Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  public void queryData(String sql) {
    SQLiteDatabase database = getWritableDatabase();
    database.execSQL(sql);
  }


  public void insertData(
      String name,
      String rollnumber,
      String passoutyear,
      String branch,
      String email,
      String phone,
      String domainmail,
      String password,
      String admin) {
    SQLiteDatabase database = getWritableDatabase();
    String sql = "INSERT INTO users VALUES (?, ?,?,?,?,?,?,?,?)";

    SQLiteStatement statement = database.compileStatement(sql);
    statement.clearBindings();

    statement.bindString(1, name);
    statement.bindString(2, rollnumber);
    statement.bindString(3, passoutyear);
    statement.bindString(4, branch);
    statement.bindString(5, email);
    statement.bindString(6, phone);
    statement.bindString(7, domainmail);
    statement.bindString(8, password);
    statement.bindString(9, admin);
    statement.executeInsert();
  }

  public void updateData(String name, String price, byte[] image, int id) {
    SQLiteDatabase database = getWritableDatabase();

    String sql = "UPDATE FOOD SET name = ?, price = ?, image = ? WHERE id = ?";
    SQLiteStatement statement = database.compileStatement(sql);

    statement.bindString(1, name);
    statement.bindString(2, price);
    statement.bindBlob(3, image);
    statement.bindDouble(4, (double) id);

    statement.execute();
    database.close();
  }

  public void deleteData(int id) {
    SQLiteDatabase database = getWritableDatabase();

    String sql = "DELETE FROM FOOD WHERE id = ?";
    SQLiteStatement statement = database.compileStatement(sql);
    statement.clearBindings();
    statement.bindDouble(1, (double) id);

    statement.execute();
    database.close();
  }

  public Cursor getData(String sql) {
    SQLiteDatabase database = getReadableDatabase();
    return database.rawQuery(sql, null);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {}

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
