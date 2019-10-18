package com.example.circle.postlogin;

public class messages {
    public String messagevalue;
    public String messagetype;
    public static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        messages.username = username;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

    public String sender;
    public String time;

    public messages(String messagevalue, String sender, String time,String messagetype) {
        this.messagevalue = messagevalue;
        this.sender = sender;
        this.time = time;
        this.messagetype=messagetype;
    }


    public String getMessagevalue() {
        return messagevalue;
    }

    public void setMessagevalue(String messagevalue) {
        this.messagevalue = messagevalue;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
