package com.example.yahelis;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    //מטרת המחלקה היא לשמור את כל הריסייקל ויו.
    // מחלקה זו היא זו שמאפשרת להשתמש בריסייקל ויו
    // והיא מתאמת בינו לבין המקום האחר שבו אנו רוצים שהריסייקל ויו יהיה.
    private ArrayList<Trip> trips;
    MyFirebaseStorage storage = new MyFirebaseStorage();
    public TripAdapter(ArrayList<Trip> tripArray)
    {
        //מטרת הפעולה היא להיות בנאי,
        // הפעולה מקבלת רשימת טיולים ומעדכנת את תכונת רשימת הטיולים בהתאם.
        this.trips = tripArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //מטרת הפעולה היא לייצר את הויו הולדר החדש.
        // take the row xml file -> create object and make it displayable

        LayoutInflater inflater =LayoutInflater.from(parent.getContext());

        View view =  inflater.inflate(R.layout.recyclerviewitem,parent,false);

        return new TripAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripAdapter.ViewHolder holder, int position) {
        //מטרת הפעולה היא לחבר בין נתוני הטיול לבין פריטי התצוגה.
        // היא שמה כל אחד מהם במיקום הנכון בריסייקל ויו.
            Trip model = trips.get(position);

        holder.tvDarga.setText(model.getDargatiul());
        holder.tvEzoring.setText(model.getArea());
        holder.tvDateing.setText(""+model.getDate());
        holder.tripID = model.getTripID();
        storage.getImage(holder.ivTripPic,model.getPhoto());
    }



    @Override
    public int getItemCount() {
        //מטרת הפעולה להחזיר את מספר הטיולים ברשימה.
        return trips.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        //מטרת המחלקה היא לנהל את התצוגה של הריסייקל ויו.
        public final TextView tvDarga;
        public final TextView tvEzoring;
        public final TextView tvDateing;

        public final ImageView ivTripPic;

        public String tripID;

        public ViewHolder(@NonNull View itemView) {
            //מטרת הפעולה לאתחל את התצוגות.
            super(itemView);

            tvDarga = itemView.findViewById(R.id.tvRecDarga);
            tvEzoring = itemView.findViewById(R.id.tvEzoring);
            tvDateing = itemView.findViewById(R.id.tvDateing);
            ivTripPic = itemView.findViewById(R.id.tripicture);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //מטרת הפעולה לקבוע לחיצה ולהעביר ברגע שלוחצים למסך חדש עם פרטי הטיול.
                    Log.d("Recycler click", "onClick: " + tvDarga.getText().toString());
                    Intent intent = new Intent(itemView.getContext(), tripdetails.class);

                    intent.putExtra("tripID", tripID);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
    }
