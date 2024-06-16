package com.example.yahelis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class reviewsActivity extends AppCompatActivity {

    private String mailOf;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fb =FirebaseFirestore.getInstance();
    EditText etWriteComments;
    Button bAddCom;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        mailOf=getIntent().getStringExtra("UserReviewsEmail");

        etWriteComments=findViewById(R.id.etWriteComments);
        bAddCom=findViewById(R.id.bAddCom);
        listView=findViewById(R.id.listView);

        bAddCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s=etWriteComments.getText().toString();
                String myName = mAuth.getCurrentUser().getDisplayName();
                comments c=new comments(mailOf,myName,s);
                fb.collection("comments").add(c).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                });
            }
        });
        displayListView(mailOf);
    }

    private void displayListView(String mailOf) {
        fb.collection("comments").whereEqualTo("OtherUserName",mailOf).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<comments> commentsArr = new ArrayList<>();

                for (DocumentSnapshot d:queryDocumentSnapshots.getDocuments()) {

                    comments com = d.toObject(comments.class);
                    commentsArr.add(com);
                }


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>((Context) reviewsActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, commentsArr.toArray());
                listView.setAdapter(adapter);


            }
        });
    }
}