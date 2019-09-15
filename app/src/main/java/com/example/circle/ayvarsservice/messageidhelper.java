package com.example.circle.ayvarsservice;

import android.database.Cursor;
import android.util.Log;

import com.example.circle.SQLiteHelper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class messageidhelper {
    public static SQLiteHelper sqLiteHelper;
    public static int messageid = 0;

    public static SQLiteHelper getSqLiteHelper() {
        return sqLiteHelper;
    }

    public static void setSqLiteHelper(SQLiteHelper sqLiteHelper) {
        messageidhelper.sqLiteHelper = sqLiteHelper;
    }

    public static int getMessageid(String groupname) {
        String groupx = "q" + groupname;

        try {
            Cursor cursorx = sqLiteHelper.getData("SELECT * FROM " + groupx);

            try {
                cursorx.moveToLast();
                Log.i("gname", groupx);
                messageid = cursorx.getInt(0);

            } catch (Exception e) {
                messageid = 0;
                Log.i("alwaysbaba", String.valueOf(messageid) + groupx);
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                String sStackTrace = stringWriter.toString();
                Log.i("data", sStackTrace);
            }
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
            Log.i("data", sStackTrace);
        }
        Log.i("xcx", messageid + groupname);
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }
}
