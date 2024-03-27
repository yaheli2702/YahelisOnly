package com.example.yahelis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class profileActivity extends AppCompatActivity {

    private User user;
    private boolean isSame=false;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

    EditText etNameOfMyProfile;
    EditText etAgeOfMyProfile;
    EditText etBio;
    ImageView ivEdit;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {

                // just an example of extracting an image
                // Handle the returned Uri
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ImageView imageView = findViewById(R.id.IVaddpicture);
                    imageView.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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
                             etNameOfMyProfile = findViewById(R.id.etNameOfMyProfile);
                             etAgeOfMyProfile = findViewById(R.id.etAgeOfMyProfile);
                             etBio = findViewById(R.id.etBio);
                             ivEdit=findViewById(R.id.ivEdit);
                            if(task.getResult().isEmpty())
                                return;

                           user =  task.getResult().getDocuments().get(0).toObject(User.class);
                            etNameOfMyProfile.setText(user.getUsername());
                            etAgeOfMyProfile.setText(""+user.getAge());

                            if(user.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                isSame=true;
                                etNameOfMyProfile.setEnabled(true);
                                etAgeOfMyProfile.setEnabled(true);
                                etBio.setEnabled(false);
                                ivEdit.setVisibility(View.VISIBLE);
                            } }
                    }
                });



    }

    public void editUserDetails(View view) {
        if(isSame){
            etBio.setVisibility(View.VISIBLE);
            etAgeOfMyProfile.setVisibility(View.VISIBLE);
            etNameOfMyProfile.setVisibility(View.VISIBLE);
//            Bitmap bitmap = ((BitmapDrawable)ivEdit.getDrawable()).getBitmap();
//            String photo= UUID.randomUUID().toString();
//
//            StorageReference storageRef = firebaseStorage.getReference();
//            StorageReference imageRef = storageRef.child(photo);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] data = baos.toByteArray();
//            UploadTask uploadTask = imageRef.putBytes(data);
        }
    }
}