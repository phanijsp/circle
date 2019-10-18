package com.example.circle.postlogin;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.*;
import java.io.*;

public class UFileDownloader {
    private String fileurl;

    UFileDownloader(String fileurl) {
        this.fileurl = fileurl;
    }
    private  String s = "";
    public String getFileType() {
        char c[] = fileurl.toCharArray();
        int i = 0;

        while (i < c.length) {
            char v = c[i];
            if (v == '/') {
                s = "";
            } else {
                s = s + String.valueOf(v);
            }
            i++;
        }
        System.out.println(s);
        if(s.contains(".")){
            String[] f = s.split("\\.");
            return f[f.length - 1];
        }else{
            return "";
        }

    }

    public String begin() {
        try {
            URL url = new URL("http://"+fileurl);
            URLConnection con = url.openConnection();
            int filesize = con.getContentLength();
            if (filesize < 0) {
                System.out.println("Could not get file size");
            } else {
                System.out.println("File Size : " + filesize);
            }
            if(getFileType().length()>0){
                InputStream inputstream = new BufferedInputStream(url.openStream());

                // String filename = "x." + getFileType();
                String filename = s;

                File filex = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/8kwallpapers");
                filex.mkdirs();
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/8kwallpapers",filename);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
//                 fileOutputStream.write(inputstream.readAllBytes());
                byte data[] = new byte[1024];
                int count;
                double sumCount = 0.0;
                while ((count = inputstream.read(data, 0, 1024)) != -1) {
                    fileOutputStream.write(data, 0, count);
                    sumCount += count;
                    if (filesize > 0) {
                        System.out.println("Percentace: " + (sumCount / filesize * 100.0) + "%");
                    }
                }
                fileOutputStream.close();
            }else{
                System.out.println("File type undefined in URL");
            }
            return "DOWNLOAD_DONE";

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println(sStackTrace);
            Log.i("download_file", sStackTrace);
            return "DOWNLOAD_FAILED";
        }
    }
}