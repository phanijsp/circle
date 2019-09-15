package com.example.circle.postlogin.SendMessageToMultipleGroups;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.circle.R;
import com.example.circle.SQLiteHelper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ListingAllGroups extends AppCompatActivity {

    ListView originallistview;
    FloatingActionButton send;
    SQLiteHelper sqLiteHelperGroups;
    GroupsAdapter_CheckBox groupsAdapter;
    ArrayList<Data_Model> groupslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_all_groups);
        originallistview = (ListView)findViewById(R.id.originalgroupslist);
        send = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        sqLiteHelperGroups = new SQLiteHelper(this, "user.sqlite", null, 1);
        groupslist=new ArrayList<>();
        final String query2 = "SELECT * FROM groups";
        String groupname = "";
        final Cursor cursor3 = sqLiteHelperGroups.getData(query2);

        while (cursor3.moveToNext()) {
            try {
                groupname = cursor3.getString(1);
                Log.i("firsttime", groupname);
                StringTokenizer stringTokenizer = new StringTokenizer(groupname, ".");
                groupslist.add(new Data_Model(stringTokenizer.nextToken(), stringTokenizer.nextToken(),false));
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String sStackTrace = sw.toString(); // stack trace as a string
                Log.i("firsttime", sStackTrace);
            }
            groupsAdapter = new GroupsAdapter_CheckBox(this, groupslist);
            originallistview.setAdapter(groupsAdapter);
            originallistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    groupslist.get(i).chechk_val=!groupslist.get(i).chechk_val;
                    groupsAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),i+""+groupslist.get(i).groupname,Toast.LENGTH_SHORT).show();
                }
            });


        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> grps = new ArrayList<>();
                try{
                    for(int i = 0; i< groupslist.size();i++){
                        if(groupslist.get(i).chechk_val==true){
                            String s = groupslist.get(i).groupyear+groupslist.get(i).groupname;
                            grps.add(s);
                        }
                    }
                    Intent intent = new Intent(ListingAllGroups.this,Multiple_grps_sender.class);
                    intent.putExtra("grps",grps);
                    startActivity(intent);
                    ListingAllGroups.this.finish();

                }catch (Exception e){

                }

            }
        });





    }
}
