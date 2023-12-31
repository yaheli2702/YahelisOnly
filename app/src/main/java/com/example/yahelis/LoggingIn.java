package com.example.yahelis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoggingIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_in);
        getSupportActionBar().hide();
    }

    public void back(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}