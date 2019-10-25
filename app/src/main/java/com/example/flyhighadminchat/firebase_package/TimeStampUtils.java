package com.example.flyhighadminchat.firebase_package;

import com.google.firebase.database.ServerValue;

public class TimeStampUtils {

    public static long getLongTimeStamp() {
        String tmstmp = String.valueOf(ServerValue.TIMESTAMP);
        return Long.parseLong(tmstmp);
    }

}
