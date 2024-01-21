package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        // already signed in
        /*
        if(mAuth.getCurrentUser() != null)
        {
            Intent i = new Intent(this, MainTraveling.class);
            startActivity(i);
        }

         */


       // mAuth.getCurrentUser() == null  -> never signed UP

    }

    public void signUp(View view) {


        Intent intent=new Intent(this, LoggingIn.class);
        startActivity(intent);
    }

    public void signIn(View view) {

        EditText etMail=findViewById(R.id.email);
        EditText etPassword=findViewById(R.id.password);
        String mail=etMail.getText().toString();
        String password=etPassword.getText().toString();


        // try to login, if we fail -> thjis means user does not exist

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // if success - there is such a user, maybe swtiched phones or re installed
                if(task.isSuccessful())
                {
                    Intent i = new Intent(MainActivity.this, MainTraveling.class);
                    startActivity(i);

                }
                else
                    Toast.makeText(MainActivity.this,"please sign up first",Toast.LENGTH_LONG).show();


            }
        });


    }
}