package com.example.ahmedrizk.clothesownerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutApp extends AppCompatActivity {

    TextView text;
    String aboutappstring="Developer : Ahmed Rizk\n" +
            "\n" +
            "(Student at faculty of engineering , computer and system department)\n" +
            "\n" +
            " Tested by Eng : Ahmed Ezzat ElMaghawry\n" +
            "\n" +
            "(Android and IOS developer in \" Ibtikar technology \" )\n" +
            "\n" +
            "Application owner :\n" +
            "\n" +
            "address :\n" +
            "\n" +
            "phone number :\n" +
            "       ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        text=(TextView)findViewById(R.id.textt);
        Intent intent=getIntent();
        text.setText(aboutappstring);
    }
}
