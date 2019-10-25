package com.example.flyhighadminchat;

import android.app.Application;
import android.util.Log;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        AndroidThreeTen.init(this);
    }
}
