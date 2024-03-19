package com.example.yahelis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View fragView=null;

    ArrayList<Trip> trips = new ArrayList<>();

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        for (int i=0; i<25;i++){
            trips.add(new Trip("information","קל","27,2,2025","photo"+i,54,87, "trip number"+i+"","place","צפון",10));
        }


       // HomeAdapter homeAdapter= new HomeAdapter(trips);
      //  recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_home, container, false);

        return fragView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // ONLY HERE THE FRAGMENT IS ALREADY CREATED!
        setupRecyclerView();




    }
    //firebase
    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    private CollectionReference tripsRef =firebaseFirestore.collection("Trips");

    private HomeAdapter adapter;


    private void setupRecyclerView() {

        // set the recycler view display

        RecyclerView recyclerView = fragView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(LayoutManager);

        Query query = tripsRef;

        // get the data from firebase
        // display in the recycler view
        FirestoreRecyclerOptions<Trip> options = new FirestoreRecyclerOptions.Builder<Trip>().
            setQuery(query, Trip.class).build();

        adapter = new HomeAdapter(options);

        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public  void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
