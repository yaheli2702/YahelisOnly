package com.example.yahelis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoggingIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_in);
        getSupportActionBar().hide();
    }
}