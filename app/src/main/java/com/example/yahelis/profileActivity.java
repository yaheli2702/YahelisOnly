package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class profileActivity extends AppCompatActivity {

    private User user;
    private boolean isSame=false;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String mail = getIntent().getStringExtra("ProfileEmail");


        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        fb.collection("Users")
                .whereEqualTo("email", mail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                           user =  task.getResult().getDocuments().get(0).toObject(User.class);
                        }
                    }
                });
        EditText etNameOfMyProfile = findViewById(R.id.etNameOfMyProfile);
        EditText etAgeOfMyProfile = findViewById(R.id.etAgeOfMyProfile);
        EditText etBio = findViewById(R.id.etBio);
        ImageView ivEdit=findViewById(R.id.ivEdit);

        etNameOfMyProfile.setText(user.getUsername());
        etAgeOfMyProfile.setText(user.getAge());

        if(user.getEmail()==mAuth.getCurrentUser().getEmail()){
            isSame=true;
            etNameOfMyProfile.setEnabled(true);
            etAgeOfMyProfile.setEnabled(true);
            etBio.setEnabled(false);
            ivEdit.setVisibility(View.VISIBLE);
        }


    }

    public void editUserDetails(View view) {
    }
}