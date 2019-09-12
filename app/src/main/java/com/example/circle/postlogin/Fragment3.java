package com.example.circle.postlogin;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circle.R;
import com.example.circle.SQLiteHelper;

import org.w3c.dom.Text;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by HeTingwei on 2017/10/24.
 */

public class Fragment3 extends Fragment {

    TextView tv;
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
            admin,
            le1,
            le2;
    TextView nametv,rollnumbertv,xrollnumbertv,passoutyeartv,xpassoutyeartv,branchtv,emailtv,phonetv,domainmailtv,admintv;

    public static String rolenumber;
    public static void setRoleNumber(String rolenumber){
        Fragment3.rolenumber=rolenumber;
    }

    public static String getRoleNumber()
    {
        return rolenumber;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag3, container, false);
        //tv=(TextView)view.findViewById(R.id.textView1);
        LinearLayout le1 = (LinearLayout) view.findViewById(R.id.le1);
        LinearLayout le2 = (LinearLayout) view.findViewById(R.id.le2);
        nametv=(TextView)view.findViewById(R.id.namevalue);
        rollnumbertv=(TextView)view.findViewById(R.id.rollvalue);
        passoutyeartv=(TextView)view.findViewById(R.id.passoutvalue);
        branchtv=(TextView)view.findViewById(R.id.branchvalue);
        emailtv=(TextView)view.findViewById(R.id.emailvalue);
        phonetv=(TextView)view.findViewById(R.id.phonvalue);
        domainmailtv=(TextView)view.findViewById(R.id.domainvalue);
        admintv=(TextView)view.findViewById(R.id.adminvalue);
        xpassoutyeartv=(TextView)view.findViewById(R.id.passoutyear);
        xrollnumbertv=(TextView)view.findViewById(R.id.rollno);
        sqLiteHelper = new SQLiteHelper(accountdetailshelper.getContext(), "user.sqlite", null, 1);
        sqLiteHelper.queryData(
                "CREATE TABLE IF NOT EXISTS users(name VARCHAR,rollnumber VARCHAR,passoutyear VARCHAR,branch VARCHAR,email VARCHAR, phone VARCHAR, domainmail VARCHAR, password VARCHAR, admin VARCHAR)");

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


                nametv.setText(name);
                if(!rollnumber.equals("not defined")){
                    rollnumbertv.setText(rollnumber);
                }
                else{
                    le1.setVisibility(View.GONE);
                    // rollnumbertv.setVisibility(View.GONE);
                    // xrollnumbertv.setVisibility(View.GONE);
                }
                if(!passoutyear.equals("not defined")){
                    passoutyeartv.setText(passoutyear);
                }
                else{
                    le2.setVisibility(View.GONE);
                    // passoutyeartv.setVisibility(View.GONE);
                    // xpassoutyeartv.setVisibility(View.GONE);
                }
                branchtv.setText(branch);
                emailtv.setText(email);
                phonetv.setText(phone);
                domainmailtv.setText(domainmail);
                admintv.setText(admin);
                a =
                        a
                                + "Name : "
                                + name
                                + "\nEmail : "
                                + email
                                + "\nPhone : "
                                + phone
                                + "\nDomainMail : "
                                + domainmail
                                + "\nPassword : "
                                + password
                                + "\nAdmin : "
                                + admin
                                + "\nRollNumber : "
                                + rollnumber
                                + "\nPassoutYear : "
                                + passoutyear
                                + "\nBranch : "
                                + branch
                                + "\n";
                break;
            }
            setRoleNumber(rollnumber);
            tv.setText(a);
            //  Toast.makeText(this,a,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            String sStackTrace = stringWriter.toString();
            // Toast.makeText(this, sStackTrace, Toast.LENGTH_SHORT).show();
        }

        return view;


    }
}
