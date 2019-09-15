package com.example.circle.ForgotPassword;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.circle.MailService.sendhelper;
import com.example.circle.MainActivity;
import com.example.circle.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class forgothelper extends AppCompatActivity {

    Button submit;
    EditText domainmail;
    String domainmail_str;
    int sum, i, rem;
    Random r;
    Intent i1;
    Spinner spinner;
    String[] roles = new String[]{"Select Your Role", "Student", "Faculty"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgothelper);

        i1 = new Intent(forgothelper.this, MainActivity.class);
        domainmail = (EditText) findViewById(R.id.entereddomainmail);
        submit = (Button) findViewById(R.id.verifybutton);
        spinner = (Spinner) findViewById(R.id.spinner1);
        final List<String> rolesList = new ArrayList<>(Arrays.asList(roles));

        final ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, rolesList) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItemText = (String) parent.getItemAtPosition(position);
                        // If user change the default selection
                        // First item is disable and it is used for hint
                        if (position > 0) {
                            // Notify the selected item text
                            Toast.makeText(
                                    getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                domainmail_str = domainmail.getText().toString().toLowerCase();
                if (spinner.getSelectedItem().equals("Select Your Role")) {
                    count++;
                    Toast.makeText(forgothelper.this, "Select Your Role To Proceed", Toast.LENGTH_SHORT)
                            .show();
                }
                if (domainmail_str.equals("")) {
                    count++;
                    domainmail.requestFocus();
                    domainmail.setError("Field Can't be Null");
                } else if (!domainmail_str.matches("^[a-zA-Z0-9._%+-]+@gvpce.ac.in+$")) {
                    count++;
                    domainmail.requestFocus();
                    domainmail.setError("Enter a valid domainmail");
                }
                if (count == 0) {
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
                    if (spinner.getSelectedItem().equals("Student")) {
                        forgotreset.setRole("studentusers");
                    } else {
                        forgotreset.setRole("facultyusers");
                    }
                    new sendhelper(getApplicationContext()).execute();
                }
            }
        });
    }
}
