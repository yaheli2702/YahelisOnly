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
public class Home extends Fragment implements FirebaseHelper.IFirebaseResult{
    //מטרת המחלקה היא לבנות ולהציג את הפרגמנט שמאפשר למשתמשים לראות את כל הטיולים שקיימים (ולא עבר זמנם). ממנה ניתן לגשת למסך אחר שמפרט על כל טיול וטיול.
    // היא משתמשת בריסייקל ויו ולוקחת נתונים מהפיירבייס כדי להציג את הטיולים בצורה הנכונה.

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View fragView=null;

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
        //מטרת הפעולה היא ליצור את הפרגמנט עם הנתונים המתאימים.
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //מטרת הפעולה היא לאתחל את הפרגמנט.
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //מטרת הפעולה היא ליצור את התצוגה של הפרגמנט.
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_home, container, false);
        return fragView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //מטרת הפעולה להגדיר את הריסייקל ויו עם הפרגמנט.
        super.onViewCreated(view, savedInstanceState);
        // ONLY HERE THE FRAGMENT IS ALREADY CREATED!
        setupRecyclerView();
    }
   private TripAdapter adapter;


    private void setupRecyclerView() {
        //מטרת הפעולה היא להכין את הריסייקל ויו .
        FirebaseHelper fbHelper = new FirebaseHelper(this);
        fbHelper.getDataFromFirebase();
    }


    @Override
    public void onStart() {
        //מטרת הפעולה היא להתחיל את הפרגמנט
        super.onStart();
    }

    @Override
    public  void onStop() {
        //מטרת הפעולה היא לעצור את הפרגמנט
        super.onStop();
    }

    @Override
    public void getData(ArrayList<Trip> arr) {
        //מטרת הפעולה היא לאתחל את הריסייקל ויו בהתאם לנתונים שהתקבלו מהפיירבייס.
        // set the recycler view display

        RecyclerView recyclerView = fragView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager LayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(LayoutManager);

        adapter = new TripAdapter(arr);

        recyclerView.setAdapter(adapter);


    }
}
