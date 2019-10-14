package com.example.circle.postlogin.FileSender;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.circle.postlogin.chatactivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class sendx extends AsyncTask<String, String, String> {
    Context context;
    InputStream inputStream;
    String Filename;
   public sendx(Context context,InputStream inputStream,String Filename) {
        this.context = context;
        this.inputStream = inputStream;
        this.Filename = Filename;
    }

    @Override
    protected String doInBackground(String... strings) {

        String resp = "";
        int maxBufferSize = 1 * 1024 * 1024;
        URL url = null;

        try {
            int bytesAvailable = inputStream.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte b[] = new byte[bufferSize];
            int bytesRead = inputStream.read(b, 0, bufferSize);

            url = new URL("http://93.188.165.250/php_files/save_file/savex.php?data="
                    +URLEncoder.encode(Filename,"UTF-8")
                    +"&groupname="+URLEncoder.encode(chatactivity.selected_file_groupname,"UTF-8")
                    +"&pushmessagetype="+URLEncoder.encode(chatactivity.selected_file_type,"UTF-8")
                    +"&sender="+URLEncoder.encode(chatactivity.selected_file_sender,"UTF-8")
                    +"&time="+URLEncoder.encode(chatactivity.selected_file_time,"UTF-8"));
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());

            while (bytesRead > 0) {
                writer.write(b, 0, bufferSize);
                bytesAvailable = inputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = inputStream.read(b, 0, bufferSize);
            }
            writer.flush();
            String result = "";
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            resp = result;
            writer.close();

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            Log.i("blabla",errors.toString());

        }
        return resp;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context,"Executed : "+s,Toast.LENGTH_LONG).show();

                        chatactivity.setLoading_screen_gone();
                        //add your code here

    }
}
