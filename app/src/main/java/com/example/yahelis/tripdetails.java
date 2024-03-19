package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class tripdetails extends AppCompatActivity {
    MyFirebaseStorage storage = new MyFirebaseStorage();
    public TextView tvDarga;
    public TextView tvNameing;
    public TextView tvTimeing;
    public TextView tvDetailOfTrip;
    public TextView tvDateOfTrip;
    public TextView tvKmOfTrip;
    public TextView tvOwnerOfTrip;
    public TextView tvParticipentsOfTrip;
    public TextView tvMaxNumberOfTravelers;
    public TextView tvAreaOfTrip;
    public TextView tvplaceOfTrip;


    private ArrayAdapter<String> itemsAdapter;



    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();



    public  ImageView ivTripPic;

    private String tripID;
    private Trip t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripdetails);

        tripID=getIntent().getStringExtra("tripID");

        Log.d("tripdetails ", "onCreate: " + tripID);


        setUIElements();

        getDetailsFromFB();
    }

    private void getDetailsFromFB() {

        fb.collection("Trips").document(tripID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                     t = documentSnapshot.toObject(Trip.class);

                    tvDetailOfTrip.setText(tvDetailOfTrip.getText().toString() + t.getInformation());
                    tvDarga.setText(tvDarga.getText().toString() + t.getDargatiul());
                    tvDateOfTrip.setText(tvDateOfTrip.getText().toString() + t.getDate());
                    tvKmOfTrip.setText(tvKmOfTrip.getText().toString() + t.getKm());
                    tvNameing.setText(tvNameing.getText().toString() + t.getName());
                    tvTimeing.setText(tvTimeing.getText().toString() + t.getTime());
                    tvplaceOfTrip.setText(tvplaceOfTrip.getText().toString() + t.getPlace());
                    tvAreaOfTrip.setText(tvAreaOfTrip.getText().toString() + t.getArea());
                    tvMaxNumberOfTravelers.setText(tvMaxNumberOfTravelers.getText().toString() + t.getTotalTravelers());

                    tvOwnerOfTrip.setText(tvOwnerOfTrip.getText().toString() + t.getOwnerName());

                    tvOwnerOfTrip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.d("TV click", "onClick: " + t.getOwnerName());

                        }
                    });

                    ListView lv = findViewById(R.id.lvUsers);
                    lv.setOnTouchListener(new View.OnTouchListener() {


                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            ScrollView scrollView = findViewById(R.id.scrollView3);
                            scrollView.requestDisallowInterceptTouchEvent(true);
                            int action = event.getActionMasked();
                            switch (action) {
                                case MotionEvent.ACTION_UP:
                                    scrollView.requestDisallowInterceptTouchEvent(false);
                                    break;
                            }
                            return false;
                        }
                    });
                    ArrayAdapter<String> itemsAdapter =
                            new ArrayAdapter<String>(tripdetails.this, android.R.layout.simple_list_item_1, t.getParticipantsNames());


                    lv.setAdapter(itemsAdapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d("lv click ", "onItemClick: " + i);
                            // i stand for username and also email
                        }

                    });
                  //  tvParticipentsOfTrip.setText(tvParticipentsOfTrip.getText().toString() + .toString());


                    storage.getImage(ivTripPic,t.getPhoto());

                }
            }
        });

    }

    private void setUIElements() {
        tvDarga = findViewById(R.id.tvDargaOfTrip);
        tvDetailOfTrip = findViewById(R.id.tvDetailOfTrip);
        tvDateOfTrip = findViewById(R.id.tvDateOfTrip);
        tvKmOfTrip = findViewById(R.id.tvKmOfTrip);
        tvNameing = findViewById(R.id.tvNameOfTrip);
        tvTimeing = findViewById(R.id.tvTimeOfTrip);
        tvplaceOfTrip = findViewById(R.id.tvplaceOfTrip);
        tvAreaOfTrip = findViewById(R.id.tvAreaOfTrip);
        tvMaxNumberOfTravelers = findViewById(R.id.tvMaxNumberOfTravelers);
        ivTripPic = findViewById(R.id.choosepic);
        tvParticipentsOfTrip = findViewById(R.id.tvParticipentsOfTrip);
        tvOwnerOfTrip = findViewById(R.id.tvOwnerOfTrip);

    }

    public void addmetothetriplist(View view) {

        // check I am not currently registeered...
        String myEmail=mAuth.getCurrentUser().getEmail();
        String myName=mAuth.getCurrentUser().getDisplayName();
        if(!t.getParticipantsEmails().contains(myEmail)){
            t.addParticipantsNames(myName);
            t.addParticipantsEmails(myEmail);

            fb.collection("Trips").document(tripID).update("",t.getParticipantsNames());
            fb.collection("Trips").document(tripID).update("",t.getParticipantsEmails());

            // user name...
            itemsAdapter.notifyDataSetChanged();
        }
        else {
            Log.d("lv click ", "you already exist");
            Toast.makeText(tripdetails.this,"you already exist",Toast.LENGTH_LONG).show();
        }

    }
}