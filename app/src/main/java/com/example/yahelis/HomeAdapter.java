package com.example.yahelis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yahelis.Trip;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class HomeAdapter extends FirestoreRecyclerAdapter<Trip,HomeAdapter.ViewHolder> {
    private ArrayList<Trip> trips;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HomeAdapter(@NonNull FirestoreRecyclerOptions<Trip> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Trip model) {

                holder.tvDarga.setText(model.getDargatiul());



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // take the row xml file -> create object and make it displayable

        LayoutInflater inflater =LayoutInflater.from(parent.getContext());

       View view =  inflater.inflate(R.layout.recyclerviewitem,parent,false);

        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvDarga;
        public final ImageView ivTripPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDarga=   itemView.findViewById(R.id.tvRecDarga);
            ivTripPic=  itemView.findViewById(R.id.tripicture);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "hi",Toast.LENGTH_LONG).show();
                }
            });


        }



    }
    }



