package com.dicoding.tourismapp;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Created by Budiliauw87 on 2022-08-18.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@HiltAndroidApp
public class MyApp extends Application {
    private static MyApp mInstance;
    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
