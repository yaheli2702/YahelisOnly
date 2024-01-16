package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoggingIn extends AppCompatActivity {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
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

    public void signup(View view) {
        EditText etMail=findViewById(R.id.email);
        EditText etPassword=findViewById(R.id.password);
        String mail=etMail.getText().toString();
        String password=etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // add all the data to the firestore database (PROFILE)
                }
            }
        });
    }



}