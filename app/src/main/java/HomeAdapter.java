import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yahelis.Trip;

import java.util.ArrayList;

public class HomeAdapter {
    private ArrayList<Trip> trips;
    public HomeAdapter(ArrayList<Trip> trips){
    this.trips=trips;
    }
    public static class TripViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.tripicture);
        }
    }
}
