package com.example.ahmedrizk.clothesownerapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ahmed Rizk on 23/08/2017.
 */

public class application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        if(isOnline())
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private boolean isOnline()
    {
        try
        {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
