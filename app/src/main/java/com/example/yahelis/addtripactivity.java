package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;

public class addtripactivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] darga = { "קל", "בינוני", "קשה"};

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
        String photo="photophoto";
        Calendar calendar = Calendar.getInstance();
        DateFormat date= new SimpleDateFormat("EEEE", Locale.getDefault());

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
}