package com.example.yahelis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
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
    ImageView ivProfile;
    Bitmap bitmap;

    private int whichPicture = 0;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {

                // just an example of extracting an image
                // Handle the returned Uri
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ivProfile = findViewById(R.id.ivProfile);
                    ivProfile.setImageBitmap(bitmap);

                    uploadImageToStroage(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    private void uploadImageToStroage(Bitmap bitmap) {
        bitmap = ((BitmapDrawable)ivProfile.getDrawable()).getBitmap();

        // choose name by                  whichPicture  value
        // 0 - profile
        // 1,2,3 bottom
        // we open a directory with the user uniuqe id as name
        // eahc dir will hold the images
        String photo = "profilepic"+ whichPicture;

        String directory = mAuth.getCurrentUser().getUid();



        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference imageRef = storageRef.child(directory +"/" + photo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("FIREBASE STORAGE", "STORAGE FIREBASE onSuccess: " + downloadUri);
                } else {
                    // Handle failures
                    Log.d("FIREBASE STORAGE", "STORAGE FIREBASE onComplete:  failed");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String mail = getIntent().getStringExtra("ProfileEmail");

        ivProfile = findViewById(R.id.ivProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                whichPicture = 0;
                mGetContent.launch("image/*");
            }
        });


//        ivProfile.setImageDrawable(klnnnnn);


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
                             //   etNameOfMyProfile.setEnabled(true);
                         //       etAgeOfMyProfile.setEnabled(true);
                          //      etBio.setEnabled(false);
                                ivEdit.setVisibility(View.VISIBLE);
                            }
                            else {
                                etNameOfMyProfile.setFocusable(false);
                                etNameOfMyProfile.setClickable(false);
                                etNameOfMyProfile.setCursorVisible(false);
                                etAgeOfMyProfile.setFocusable(false);
                                etAgeOfMyProfile.setClickable(false);
                                etAgeOfMyProfile.setCursorVisible(false);
                                etBio.setFocusable(false);
                                etBio.setClickable(false);
                                etBio.setCursorVisible(false);
                            }

                        }
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