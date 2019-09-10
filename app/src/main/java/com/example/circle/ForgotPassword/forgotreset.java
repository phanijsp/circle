package com.example.circle.ForgotPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.circle.MailService.sendhelper;
import com.example.circle.MainActivity;
import com.example.circle.R;

import static com.example.circle.MailService.sendhelper.getDommail;

public class forgotreset extends AppCompatActivity {

    String domainmail_str,new_password_str,confirm_new_password_str;
    int count;
    EditText new_password;
    EditText confirm_new_password;
    Button reset;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotreset);

        intent=new Intent(forgotreset.this, sendhelper.class);

        new_password=(EditText) findViewById(R.id.newpassword);
        confirm_new_password=(EditText) findViewById(R.id.confirmnewpassword);
        reset=(Button) findViewById(R.id.verifybutton);

        domainmail_str=getDommail();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                new_password_str=new_password.getText().toString();
                confirm_new_password_str=confirm_new_password.getText().toString();
                if (new_password_str.equals("")) {
                    count++;
                    new_password.requestFocus();
                    new_password.setError("Password Can't be NUll");
                } else if (new_password_str.length() < 8) {
                    count++;
                    new_password.requestFocus();
                    new_password.setError("Password Length atleast 8");
                }
                if (confirm_new_password_str.equals("")) {
                    count++;
                    confirm_new_password.requestFocus();
                    confirm_new_password.setError("Confirm Password Can't be NUll");
                } else if (!confirm_new_password_str.equals(new_password_str)) {
                    count++;
                    confirm_new_password.requestFocus();
                    confirm_new_password.setError("Password not Matched");
                }
                if(count==0)
                {
                    Toast.makeText(forgotreset.this, "Password Reset Successful", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(forgotreset.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });



    }
}
