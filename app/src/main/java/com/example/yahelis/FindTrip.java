package com.example.yahelis;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FindTrip#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FindTrip extends Fragment implements FirebaseHelper.IFirebaseResult{

    String[] darga = {"בחר.י", "קל", "בינוני", "קשה" };
    String[] area = { "בחר.י","צפון", "מרכז", "דרום", "ירושלים" };
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TripAdapter adapter;

    private String selectedDifficulty;
    private String selectedArea;
    private TextView eintiul;

    public FindTrip() {
        // Required empty public constructor
    }

    public static FindTrip newInstance(String param1, String param2) {
        FindTrip fragment = new FindTrip();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private View view;

    private ArrayList<Trip> tripsArr = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_find_trip, container, false);



        // Inflate the layout for this fragment
        return  view;  }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


      //  getDataFromFirebase();
        FirebaseHelper fbHelper = new FirebaseHelper(this);
        fbHelper.getDataFromFirebase();

        Button srch = view.findViewById(R.id.search);

        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        Spinner areaspin = view.findViewById(R.id.ezortiolspiner);
        ArrayAdapter<String> aaa = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, area);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaspin.setAdapter(aaa);
        areaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedArea = area[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedArea = area[0];
            }
        });

        Spinner dargaspin = view.findViewById(R.id.dargatiolspinner);
        ArrayAdapter<String> bbb = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, darga);
        bbb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dargaspin.setAdapter(bbb);
        dargaspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDifficulty = darga[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedDifficulty = darga[0];
            }
        });
    }
    public void search() {
        ArrayList<Trip> filter = new ArrayList<>();
        for (int i=0;i<tripsArr.size();i++)
        {
            Trip t=tripsArr.get(i);
            if((t.getArea().equals(selectedArea)||selectedArea.equals(area[0]))
                    &&
                    (t.getDargatiul().equals(selectedDifficulty)||selectedDifficulty.equals(darga[0]))){
                filter.add(t);
                Log.d("search", "working");

            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(LayoutManager);

        adapter = new TripAdapter(filter);
        eintiul=view.findViewById(R.id.eintiul);

        recyclerView.setAdapter(adapter);
        if(filter.size()==0)
        {
            eintiul.setVisibility(View.VISIBLE);
        }
        else
            eintiul.setVisibility(View.INVISIBLE);
    }

    @Override
    public void getData(ArrayList<Trip> arr) {
        tripsArr = arr;
    }
}
