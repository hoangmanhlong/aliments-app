package com.example.loginapp;

import android.app.Application;

import com.example.loginapp.data.local.AppDatabase;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstances() {
        return instance;
    }
}
