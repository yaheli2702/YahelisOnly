package com.example.yahelis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class addtripactivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] darga = { "קל", "בינוני", "קשה"};
    private FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();


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

    private String selectedDifficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtripactivity);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener( this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,darga);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedDifficulty= darga[i];
        Log.d("spiiner darga", "onItemSelected: " + selectedDifficulty);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedDifficulty = darga[0];
        Log.d("spiiner darga nothing", "onItemSelected: " + selectedDifficulty);

    }

    public void readDataAndCreateTrip(View view)
    {
        EditText etInfor = findViewById(R.id.editTextTextMultiLine);
        DatePicker dtDate = findViewById(R.id.datePicker1);
        EditText etKilometer = findViewById(R.id.kilometer);
        EditText etTimee = findViewById(R.id.timee);
        EditText etName = findViewById(R.id.etName);

        ImageView imageView = findViewById(R.id.IVaddpicture);


        if (TextUtils.isEmpty(etInfor.getText()) || TextUtils.isEmpty(etKilometer.getText()) || TextUtils.isEmpty(etTimee.getText())) {
            Toast.makeText(this, "Please make sure all fields are full", Toast.LENGTH_LONG).show();
            return;
        }
        int day = dtDate.getDayOfMonth();
        int month = dtDate.getMonth() + 1;
        int year = dtDate.getYear();
        String information= etInfor.getText().toString();
        String name= etName.getText().toString();
        int km=Integer.valueOf(etKilometer.getText().toString());
        int time=Integer.valueOf(etTimee.getText().toString());

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        String photo= UUID.randomUUID().toString();

        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference imageRef = storageRef.child(photo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        Calendar calendar = Calendar.getInstance();
        DateFormat date= new SimpleDateFormat("EEEE", Locale.getDefault());
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
                    Toast.makeText(addtripactivity.this, "Uploading successes", Toast.LENGTH_SHORT);
                    Log.d("FIREBASE STORAGE", "STORAGE FIREBASE onSuccess: " + downloadUri);
                } else {
                    // Handle failures
                    Log.d("FIREBASE STORAGE", "STORAGE FIREBASE onComplete:  failed");
                    Toast.makeText(addtripactivity.this, "Uploading failed", Toast.LENGTH_SHORT);
                }
            }
        });
        ZoneId z = ZoneId.systemDefault() ;  // Get JVM’s current default time zone.
      /*  YearMonth currentYearMonth = YearMonth.now( z ) ;
        String input = dtDate.getMonth() + "/" + dtDate.getYear() ;
        DateTimeFormatter f = DateTimeFormatter.ofPattern( "MM/uuuu" ) ;
        YearMonth ym = YearMonth.parse( input , f ) ;

        boolean isAfterCurrentYearMonth = ym.isAfter( currentYearMonth ) ;

        if(!isAfterCurrentYearMonth)
            Toast.makeText(this,"plese enter a date in the future",Toast.LENGTH_LONG).show();
*/
        //String information, String dargatiul, int day, int month, int year, int km, int time
        Trip trup = new Trip(information,selectedDifficulty,day,month,year, photo, km,time, name);
        addTriptoFirestore(trup);
    }

    private void addTriptoFirestore (Trip trip){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Trips").add(trip).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FB SUCCESS", "onSuccess: perfect");
                        Toast.makeText(addtripactivity.this,"trip added",Toast.LENGTH_LONG).show();
                        //       Intent i = new Intent(LoggingIn.this, MainTraveling.class);
                   //     startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FaILED TRIP", "onFailure: " + e.getMessage());
                    }
                });
    }

    // Launcher




    public void addpicture(View view) {



        mGetContent.launch("image/*");


    }
}