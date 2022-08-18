package com.dicoding.tourismapp;

import android.app.Application;

import com.dicoding.tourismapp.core.di.CoreComponent;
import com.dicoding.tourismapp.core.di.DaggerCoreComponent;
import com.dicoding.tourismapp.di.AppComponent;
import com.dicoding.tourismapp.di.DaggerAppComponent;

/**
 * Created by Budiliauw87 on 2022-08-18.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class MyApp extends Application {
    private static MyApp mInstance;
    public AppComponent appComponent;
    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CoreComponent coreComponent = DaggerCoreComponent.factory().create(this);
        appComponent = DaggerAppComponent.factory().create(coreComponent);
    }

}
