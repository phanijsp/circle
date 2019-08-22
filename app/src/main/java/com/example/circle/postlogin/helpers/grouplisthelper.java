package com.example.circle.postlogin.helpers;

import android.content.Context;
import android.database.Cursor;

import com.example.circle.SQLiteHelper;
import com.example.circle.postlogin.Groups;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class grouplisthelper {
    static  SQLiteHelper sqLiteHelperGroups;
    public static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        grouplisthelper.context = context;
    }

    public static ArrayList<Groups>  groupslist;

    public static ArrayList<Groups> getGroupslist() {
        final String query2 = "SELECT * FROM groups";

        String groupname = "";

        sqLiteHelperGroups = new SQLiteHelper(getContext(), "user.sqlite", null, 1);

        final Cursor cursor3 = sqLiteHelperGroups.getData(query2);
        groupslist.clear();
        while (cursor3.moveToNext()) {
            try {
                groupname = cursor3.getString(1);
                StringTokenizer stringTokenizer = new StringTokenizer(groupname, ".");
                groupslist.add(new Groups(stringTokenizer.nextToken(), stringTokenizer.nextToken()));
            } catch (Exception e) {

            }
        }
        return groupslist;
    }

    public static void setGroupslist(ArrayList<Groups> groupslist) {
        grouplisthelper.groupslist = groupslist;
    }
}
