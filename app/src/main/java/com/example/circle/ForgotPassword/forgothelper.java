package com.example.circle.ForgotPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.circle.MailService.sendhelper;
import com.example.circle.MainActivity;
import com.example.circle.R;

import java.util.Random;

public class forgothelper extends AppCompatActivity {

    Button submit;
    EditText domainmail;
    String domainmail_str;
    int sum,i,rem;
    Random r;
    Intent i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgothelper);

        i1=new Intent(forgothelper.this, MainActivity.class);
        domainmail=(EditText) findViewById(R.id.entereddomainmail);
        submit=(Button) findViewById(R.id.verifybutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;
                domainmail_str=domainmail.getText().toString().toLowerCase();
                if(domainmail_str.equals(""))
                {
                    count++;
                    domainmail.requestFocus();
                    domainmail.setError("Field Can't be Null");
                }
                else if(!domainmail_str.matches("^[a-zA-Z0-9._%+-]+@gvpce.ac.in+$"))
                {
                    count++;
                    domainmail.requestFocus();
                    domainmail.setError("Enter a valid domainmail");
                }
                if(count==0)
                {
                    String domain_mail_id_1 = domainmail.getText().toString();
                    sendhelper.setDommail(domain_mail_id_1);
                    sum = 0;
                    for (i = 0; i < 5; i++) {
                        r = new Random();
                        rem = r.nextInt(9) + 1;
                        sum = sum * 10 + rem;
                    }
                    sendhelper.setOtp(sum);
                    sendhelper.setType("forgot");
                    new sendhelper(getApplicationContext()).execute();
                }
            }
        });
    }
}
