package com.example.yahelis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashScreen extends AppCompatActivity {
    //מטרת המחלקה הזו היא ליצור מסך ראשוני שיופיע ברגע שנכנסים לאפליקציה.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //מטרת הפעולה היא ליצור את המסך ולהשאיר אותו כמות מסויימת של זמן עד שהמסך יעבור למסך הבא.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        CountDownTimer cdt = new CountDownTimer(2000,500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);

            }
        }.start();
    }
}