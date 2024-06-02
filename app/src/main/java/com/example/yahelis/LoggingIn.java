package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
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

        CheckBox checkBox = findViewById(R.id.checkBox);
        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Please make sure you are not a robot", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etMail = findViewById(R.id.EmailAddress);
        EditText etPassword = findViewById(R.id.regpassword);
        EditText etUserName = findViewById(R.id.username);
        EditText etPhone = findViewById(R.id.Phone);
        EditText etAge = findViewById(R.id.age);
        EditText etIden = findViewById(R.id.iden);

        if (TextUtils.isEmpty(etAge.getText()) || TextUtils.isEmpty(etPhone.getText()) || TextUtils.isEmpty(etIden.getText()) || TextUtils.isEmpty(etPassword.getText()) || TextUtils.isEmpty(etUserName.getText())) {
            Toast.makeText(this, "Please make sure all fields are full", Toast.LENGTH_LONG).show();
            return;
        }
        String mail = etMail.getText().toString();
        String password = etPassword.getText().toString();


        String iden = etIden.getText().toString();
        if(iden.length() !=9)
        {
            Toast.makeText(this, "your id must have 9 numbers", Toast.LENGTH_LONG).show();
            return;
        }




        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // add all the data to the firestore database (PROFILE)


                    if (!TextUtils.isEmpty(etAge.getText()) && !TextUtils.isEmpty(etPhone.getText()) && !TextUtils.isEmpty(etIden.getText()) && !TextUtils.isEmpty(etPassword.getText()) && !TextUtils.isEmpty(etUserName.getText())) {
                        String username = etUserName.getText().toString();
                        int phone = Integer.valueOf(etPhone.getText().toString());
                        int age = Integer.valueOf(etAge.getText().toString());
                        int iden = Integer.valueOf(etIden.getText().toString());
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                        mAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    String email = mAuth.getCurrentUser().getEmail();
                                    User user = new User(username, email, age, phone, iden,mAuth.getCurrentUser().getUid());
                                    addUsertoFirestore(user);
                                }
                            }
                        });


                    }


                }
                else
                {
                    Toast.makeText(LoggingIn.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }

            }


        });
    }

            private void addUsertoFirestore (User user){
                FirebaseFirestore fb = FirebaseFirestore.getInstance();
                fb.collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("FB SUCCESS", "onSuccess: perfect");
                                Intent i = new Intent(LoggingIn.this, MainTraveling.class);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }


        }


