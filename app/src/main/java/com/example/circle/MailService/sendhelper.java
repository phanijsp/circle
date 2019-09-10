package com.example.circle.MailService;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.circle.ForgotPassword.forgotreset;
import com.example.circle.MainActivity;
import com.example.circle.otpactivity;
import com.example.circle.otphelper;

import java.util.Random;

public class sendhelper extends AsyncTask<String,Integer,String> {
    Context context;
    int i1;
    Intent i;
    public static String dommail,password,type;
    public static int otp;

    public static String getDommail() {
        return dommail;
    }

    public static void setDommail(String dommail) {
        sendhelper.dommail = dommail;
    }

    public static String getPassword() { return password;}

    public static void setPassword(String password) { sendhelper.password=password;}

    public static int getOtp() { return otp;}

    public static void setOtp(int otp) { sendhelper.otp=otp;}

    public static String getType() { return type;}

    public static void setType(String type) { sendhelper.type=type;}

    public sendhelper(Context context){
        this.context=context;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        GMailSender sender = new GMailSender("saisampaths60@gmail.com", "9052157550S");
        try {
            if(getType().toString().equals("Signup"))
            {
                i1=0;
                otphelper.setOtpvalue(getOtp());
                sender.sendMail("OTP",
                        "Your Otp for registration is: "+getOtp(),
                        "saisampaths60@gmail.com",
                        getDommail());
            }
            else if(getType().toString().equals("Registered"))
            {
                i1=1;
                sender.sendMail("Login Details",
                        "Domain Mail: " + getDommail().toString()+"\n"+"Password: "+ getPassword().toString(),
                        "saisampaths60@gmail.com",
                        getDommail());
            }
            else if(getType().toString().equals("forgot"))
            {
                i1=2;
                otphelper.setOtpvalue(getOtp());
                sender.sendMail("OTP for Password Reset",
                        "Your OTP for resetting password is: " + getOtp(),
                        "saisampaths60@gmail.com",
                        getDommail());
            }
            else if(getType().toString().equals("reset"))
            {
                i1=3;
                sender.sendMail("New Login Details",
                        "Domain Mail: " + getDommail().toString()+"\n"+"Password: "+ getPassword().toString(),
                        "saisampaths60@gmail.com",
                        getDommail());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s){
        switch(i1)
        {
            case 0:
                    Toast.makeText(context,"OTP Sent Successfully",Toast.LENGTH_SHORT).show();
                    i=new Intent(context, otpactivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    break;
            case 1: Toast.makeText(context,"Login Details sent to Mail",Toast.LENGTH_SHORT).show();
                    break;
            case 2: Toast.makeText(context,"OTP Sent Successfully",Toast.LENGTH_SHORT).show();
                    i=new Intent(context, otpactivity.class);
                    context.startActivity(i);
                    break;
            case 3: Toast.makeText(context, "New Login Details Sent to Mail", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(context, MainActivity.class);
                    context.startActivity(i);
                    break;
        }

    }
}
