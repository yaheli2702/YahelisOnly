package com.example.yahelis;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.BlockingDeque;

public class FirebaseHelper {
//מטרת המחלקה היא לעזור באינטראקציה עם פיירסטור בפיירבייס, בשביל לקבל נתונים בנוגע לטיולים.


    private IFirebaseResult fbResult;

    public FirebaseHelper(IFirebaseResult result)
    {
        //מטרת הפעולה היא לאתחל את המחלקה ולהגדיר משתנה שיקבל את התוצאה של הנתונים.
        this.fbResult = result;
    }

    public interface IFirebaseResult
    {
        //ממשק שמגדיר פעולה לקבל את הנתונים מהפיירבייס.
        void getData(ArrayList<Trip> arr);
    }

    public void getDataFromFirebase() {
        //מטרת הפעולה לאחזר נתונים מהפיירסטור בפיירבייס.
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        fb.collection("Trips").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<Trip> tripsArr = new ArrayList<>();

                for (DocumentSnapshot d:queryDocumentSnapshots.getDocuments()) {

                    Trip t = d.toObject(Trip.class);

                    if(!isDateInPast(t.getDate())){
                        tripsArr.add(t);
                    }
                }

                fbResult.getData(tripsArr);
            }
        });

    }

    public static boolean isDateInPast(String dateString) {
        //מטרת הפעולה לבדוק אם התאריך הנתון נמצא בעבר.
        String startDateString = dateString.split("–")[0].trim()+", "+dateString.split(",")[1].trim();
        Log.d("DATE CHECK ", "isDateInPast: "+startDateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        try {
            LocalDate endDate = LocalDate.parse(startDateString, formatter);
            LocalDate today = LocalDate.now();
            return endDate.isBefore(today);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
