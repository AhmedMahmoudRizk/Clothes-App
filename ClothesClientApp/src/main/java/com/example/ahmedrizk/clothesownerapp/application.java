package com.example.ahmedrizk.clothesownerapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ahmed Rizk on 23/08/2017.
 */
public class application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
