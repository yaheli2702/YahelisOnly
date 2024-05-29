package com.example.yahelis;

import static com.google.android.material.datepicker.DateValidatorPointForward.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class addtripactivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] darga = { "קל", "בינוני", "קשה"};
    String[] area={ "צפון", "מרכז",  "דרום", "ירושלים"};
    private FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
    TextView dateRangeText;
    Button calender;
    String s="";

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
    private String selectedArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtripactivity);
        dateRangeText=findViewById(R.id.changeDate);
        calender=findViewById(R.id.chooseDate);

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        long todayInMillis = MaterialDatePicker.todayInUtcMilliseconds();

        constraintsBuilder.setStart(todayInMillis);

        constraintsBuilder.setValidator(DateValidatorPointForward.now());

        CalendarConstraints constraints = constraintsBuilder.build();

        MaterialDatePicker.Builder<Pair<Long, Long>> builder =
                MaterialDatePicker.Builder.dateRangePicker();

        builder.setCalendarConstraints(constraints);

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locale currentLocale = getResources().getConfiguration().locale;

                // Set the locale to English
                LocaleHelper.setLocale(addtripactivity.this, Locale.ENGLISH);

                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        s = materialDatePicker.getHeaderText();
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        if (!s.contains(","))
                            s += ", " + currentYear;

                        dateRangeText.setText(s);
                    }
                });
                materialDatePicker.addOnDismissListener(dialog -> LocaleHelper.setLocale(addtripactivity.this, currentLocale));

            }
        });

        Spinner dargaspin = (Spinner) findViewById(R.id.spinner);
        dargaspin.setOnItemSelectedListener( this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,darga);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dargaspin.setAdapter(aa);

        Spinner areaspin = (Spinner) findViewById(R.id.ezorspiner);
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,area);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaspin.setAdapter(aaa);
        areaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArea=area[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedArea=area[0];
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedDifficulty= darga[i];
        Log.d("spiiner darga", "onItemSelected: " + selectedDifficulty);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedDifficulty = darga[0];
    }

    public void readDataAndCreateTrip(View view)
    {
        EditText etInfor = findViewById(R.id.editTextTextMultiLine);
        EditText etKilometer = findViewById(R.id.kilometer);
        EditText etTimee = findViewById(R.id.timee);
        EditText etName = findViewById(R.id.etName);
        EditText etNumberOfTravelers = findViewById(R.id.etNumberOfTravelers);
        EditText etPlace = findViewById(R.id.etPlace);
        EditText etWh = findViewById(R.id.etWhatsapp);



        ImageView imageView = findViewById(R.id.IVaddpicture);

        if (TextUtils.isEmpty(etWh.getText())||TextUtils.isEmpty(etNumberOfTravelers.getText()) ||TextUtils.isEmpty(etPlace.getText()) ||TextUtils.isEmpty(etInfor.getText()) ||s==""|| TextUtils.isEmpty(etKilometer.getText()) || TextUtils.isEmpty(etTimee.getText())) {

            etInfor.setHint("please fill the filed");
            etNumberOfTravelers.setHint("please fill the filed");
            etKilometer.setHint("please fill the filed");
            etTimee.setHint("please fill the filed");
            etName.setHint("please fill the filed");
            etPlace.setHint("please fill the filed");
            etWh.setHint("please fill the filed");
            Toast.makeText(addtripactivity.this, "Please make sure all fields are full", Toast.LENGTH_LONG).show();
            return;
        }
        if(!etWh.getText().toString().startsWith("https://chat.whatsapp.com/"))
        {
            Toast.makeText(addtripactivity.this, "Please make sure the whatsapp code id true", Toast.LENGTH_LONG).show();
            return;
        }




        String information= etInfor.getText().toString();
        String place= etPlace.getText().toString();
        String name= etName.getText().toString();
        String what= etWh.getText().toString();
        int km=Integer.valueOf(etKilometer.getText().toString());
        int NumberOfTravelers=Integer.valueOf(etNumberOfTravelers.getText().toString());
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

        Trip trup = new Trip(information,selectedDifficulty,s, photo, km,time, name, place,  selectedArea,  NumberOfTravelers,what);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        trup.setOwnerEmail(email);

        // get the owner username from firebase....

        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Users").whereEqualTo("email",email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                User u= queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                String userName = u.getUsername();
                trup.setOwnerName(userName);
                addTriptoFirestore(trup);

            }
        });
    }

    private void addTriptoFirestore (Trip trip){
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Trips").add(trip).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        documentReference.update("tripID",documentReference.getId());
                        Log.d("FB SUCCESS", "onSuccess: perfect");
                        Toast.makeText(addtripactivity.this,"trip added",Toast.LENGTH_LONG).show();
                   //     ();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FaILED TRIP", "onFailure: " + e.getMessage());
                    }
                });
    }

    public void addpicture(View view) {
        mGetContent.launch("image/*");
    }
}