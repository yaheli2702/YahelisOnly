package com.example.yahelis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyFirebaseStorage {
    //מטרת המחלקה היא לעשות את כל הדברים שקשורים להעלאת תמונות ולהורדת תמונות.

    FirebaseStorage storage = FirebaseStorage.getInstance();
    public void getImage(ImageView iv,String url)
    {
        //מטרת הפעולה היא להוריד תמונה לפי קישור
        // נתון מהפיירבייס סטורג' ולהציג אותה ברכיב התמונה הנתון.
        StorageReference imageRef = storage.getReference().child(url);
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                /* Use the bytes to display the image */
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("Storage image", "onFailure: " + exception.getMessage());
            }
        });
    }
}
