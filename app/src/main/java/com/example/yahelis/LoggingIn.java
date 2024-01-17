package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        EditText etMail = findViewById(R.id.EmailAddress);
        EditText etPassword = findViewById(R.id.regpassword);
        String mail = etMail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // add all the data to the firestore database (PROFILE)
                    EditText etUserName = findViewById(R.id.username);
                    EditText etPassword = findViewById(R.id.regpassword);
                    EditText etEmailAddress = findViewById(R.id.EmailAddress);
                    EditText etPhone = findViewById(R.id.Phone);
                    EditText etAge = findViewById(R.id.age);
                    EditText etIden = findViewById(R.id.iden);

                    if (!TextUtils.isEmpty(etAge.getText()) && !TextUtils.isEmpty(etPhone.getText()) && !TextUtils.isEmpty(etIden.getText()) && !TextUtils.isEmpty(etPassword.getText()) && !TextUtils.isEmpty(etUserName.getText())) {
                        String username = etUserName.getText().toString();
                        int phone = Integer.valueOf(etPhone.getText().toString());
                        int age = Integer.valueOf(etAge.getText().toString());
                        int iden = Integer.valueOf(etIden.getText().toString());
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        String email = mAuth.getCurrentUser().getEmail();
                        User user = new User(username, email, age, phone, iden);
                        addUsertoFirestore(user);
                    }


                }
            }
        });


    }

    private void addUsertoFirestore(User user) {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("FB SUCCESS", "onSuccess: perfect");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


}