package com.rage.clamber;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Application class to initialize JodaTime
 */
public class MyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
