package com.example.yahelis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
}