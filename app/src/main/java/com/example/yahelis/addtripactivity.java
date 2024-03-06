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
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class addtripactivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] darga = { "קל", "בינוני", "קשה"};
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtripactivity);
        dateRangeText=findViewById(R.id.changeDate);
        calender=findViewById(R.id.chooseDate);

        //long twoYears = 1000*3600*24*365*2;
        Date currentTime = Calendar.getInstance().getTime();

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setStart(Calendar.getInstance().getTimeInMillis())
         //       .setEnd(1000*3600*24*7*10+Calendar.getInstance().getTimeInMillis())
                .build();
//        CalendarConstraints constraintsBuilder= new CalendarConstraints.Builder()
//                .setValidator(DateValidatorPointForward.from((long)currentTime));
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
              .setCalendarConstraints(constraints)
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds()+1000*24*3600,MaterialDatePicker.todayInUtcMilliseconds())).build();

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        dateRangeText.setText(materialDatePicker.getHeaderText());
                        s=materialDatePicker.getHeaderText();
                    }
                });
            }
        });
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
        //DatePicker dtDate = findViewById(R.id.datePicker1);
        EditText etKilometer = findViewById(R.id.kilometer);
        EditText etTimee = findViewById(R.id.timee);
        EditText etName = findViewById(R.id.etName);

        ImageView imageView = findViewById(R.id.IVaddpicture);


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
        if (TextUtils.isEmpty(etInfor.getText()) ||s==""||photo==""|| TextUtils.isEmpty(etKilometer.getText()) || TextUtils.isEmpty(etTimee.getText())) {

            etInfor.setHint("please fill the fild");
            etKilometer.setHint("please fill the fild");
            etTimee.setHint("please fill the fild");
            etName.setHint("please fill the fild");
            Toast.makeText(addtripactivity.this, "Please make sure all fields are full", Toast.LENGTH_LONG).show();
            return;
        }
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
        Trip trup = new Trip(information,selectedDifficulty,s, photo, km,time, name);
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