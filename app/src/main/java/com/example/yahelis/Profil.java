package com.example.yahelis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profil extends Fragment {
    //מטרת המחלקה היא להציג את מסך הפרופיל.
    // במחלקה זו אפשר לערוך את פרטי המשתמש (שם, מידע, גיל ותמונות).

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private User user;
    private boolean isSame=false;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();

    private DocumentReference docRef = null;
    EditText etNameOfMyProfile;
    EditText etAgeOfMyProfile;
    EditText etBio;
    ImageView ivEdit;
    ImageView ivProfile;
    ImageView ivFirst;
    ImageView ivSecond;
    ImageView ivThird;

    Bitmap bitmap;
    TextView tvSave;

    private int whichPicture = 0;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                //מטרת התכונה היא לצורך פתיחת התמונות בגלריה, בחירה והחזרה כקישור.
                // just an example of extracting an image
                // Handle the returned Uri
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), uri);
                    if (whichPicture==0)
                    {
                        ivProfile.setImageBitmap(bitmap);
                    } else if (whichPicture==1) {
                        ivFirst.setImageBitmap(bitmap);
                    } else if (whichPicture==2) {
                        ivSecond.setImageBitmap(bitmap);
                    }else if (whichPicture==3) {
                        ivThird.setImageBitmap(bitmap);
                    }


                    uploadImageToStroage(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    private void uploadImageToStroage(Bitmap bitmap) {
        //מטרת הפעולה היא להעלות תמונה לסטורג' בפיירבייס.
        if(whichPicture==0)
            bitmap = ((BitmapDrawable)ivProfile.getDrawable()).getBitmap();
        else if (whichPicture==1)
            bitmap = ((BitmapDrawable)ivFirst.getDrawable()).getBitmap();
        else if (whichPicture==2)
            bitmap = ((BitmapDrawable)ivSecond.getDrawable()).getBitmap();
        else if (whichPicture==3)
            bitmap = ((BitmapDrawable)ivThird.getDrawable()).getBitmap();
        // choose name by                  whichPicture  value
        // 0 - profile
        // 1,2,3 bottom
        // we open a directory with the user uniuqe id as name
        // eahc dir will hold the images
        String photo = "profilepic"+ whichPicture;

        String directory = mAuth.getCurrentUser().getUid();


        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference imageRef = storageRef.child(directory +"/" + photo);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("FIREBASE STORAGE", "STORAGE FIREBASE onSuccess: " + downloadUri);
                } else {
                    // Handle failures
                    Log.d("FIREBASE STORAGE", "STORAGE FIREBASE onComplete:  failed");
                }
            }
        });
    }


    public Profil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profil.
     */
    // TODO: Rename and change types and number of parameters
    public static Profil newInstance(String param1, String param2) {
        //מטרת הפעולה היא ליצור את הפרגמנט עם הפרמטרים המתאימים.
        Profil fragment = new Profil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //מטרת הפעולה היא ליצור את הפרגמנט עם הפרמטרים המתאימים.
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //מטרת הפעולה היא ליצור את הדרך תצוגה של הפרגמנט.
        view= inflater.inflate(R.layout.fragment_profil, container, false);
        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //מטרת הפעולה היא לאתחל את כל הרכיבים ולזמן את נתוני המשתמש מהפיירבייס.
        super.onViewCreated(view, savedInstanceState);
        Button signOutButton = view.findViewById(R.id.bsignout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        ivProfile = view.findViewById(R.id.ivProfile2);
        ivFirst = view.findViewById(R.id.ivFirst2);
        ivSecond = view.findViewById(R.id.ivSecond2);
        ivThird = view.findViewById(R.id.ivThird2);
        etNameOfMyProfile = view.findViewById(R.id.etNameOfMyProfile2);
        etAgeOfMyProfile = view.findViewById(R.id.etAgeOfMyProfile2);
        etBio = view.findViewById(R.id.etBio2);
        tvSave=view.findViewById(R.id.tvSave);
        ivEdit=view.findViewById(R.id.ivEdit2);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserDetails(view);
            }
        });
        getUserDetails();

    }

    private void getUserDetails() {
        //מטרת הפעולה היא לקבל את פרטי המשתמש מהפיירסטור ולעדכן את כל התכונות בהתאם.
        FirebaseFirestore fb=FirebaseFirestore.getInstance();
        fb.collection("Users")
                .whereEqualTo("email", mAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().isEmpty())
                                return;

                            // get the first docuemtn since thedre is only one
                            docRef = task.getResult().getDocuments().get(0).getReference();


                            user =  task.getResult().getDocuments().get(0).toObject(User.class);
                            etNameOfMyProfile.setText(user.getUsername());
                            etAgeOfMyProfile.setText(""+user.getAge());
                            etBio.setText(user.getInfo());


                            String photo = "profilepic";

                            String directory = mAuth.getCurrentUser().getUid();

                            String url = directory + "/" + photo + 0;
                            MyFirebaseStorage storage = new MyFirebaseStorage();
                            storage.getImage(ivProfile,url);

                            url = directory + "/" + photo + 1;
                            storage.getImage(ivFirst,url);
                            url = directory + "/" + photo + 2;

                                storage.getImage(ivSecond,url);
                            url = directory + "/" + photo + 3;
                                storage.getImage(ivThird,url);



                            if(user.getEmail().equals(mAuth.getCurrentUser().getEmail())){
                                isSame=true;
                                setFalse();
                            }


                        }
                    }
                });

        // ONLY HERE THE FRAGMENT IS ALREADY CREATED!


    }

    public void setTrue(){
        //מטרת הפעולה לאפשר עריכה של כל השדות הרלוונטים.
        etNameOfMyProfile.setFocusableInTouchMode(true);
        etNameOfMyProfile.setFocusable(true);
        etNameOfMyProfile.setClickable(true);
        etNameOfMyProfile.setCursorVisible(true);
        etAgeOfMyProfile.setFocusableInTouchMode(true);
        etAgeOfMyProfile.setFocusable(true);
        etAgeOfMyProfile.setClickable(true);
        etAgeOfMyProfile.setCursorVisible(true);
        etBio.setFocusableInTouchMode(true);
        etBio.setFocusable(true);
        etBio.setClickable(true);
        etBio.setCursorVisible(true);
        tvSave.setVisibility(View.VISIBLE);
    }
    public void setFalse(){
        //מטרת הפעולה למנוע עריכה של כל השדות הרלוונטים.
        etNameOfMyProfile.setFocusable(false);
        etNameOfMyProfile.setClickable(false);
        etNameOfMyProfile.setCursorVisible(false);
        etAgeOfMyProfile.setFocusable(false);
        etAgeOfMyProfile.setClickable(false);
        etAgeOfMyProfile.setCursorVisible(false);
        etBio.setFocusable(false);
        etBio.setClickable(false);
        etBio.setCursorVisible(false);
    }


    public void editUserDetails(View view) {
        //מטרת המפעולה היא לערוך את פרטי המשתמש ותמונות המשתמש כשהוא לוחץ על כתפור עריכה.
        if(isSame){
            setTrue();
            ivProfile.setEnabled(true);
            ivFirst.setEnabled(true);
            ivSecond.setEnabled(true);
            ivThird.setEnabled(true);
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    whichPicture = 0;
                    mGetContent.launch("image/*");
                }
            });
            ivThird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    whichPicture = 3;
                    mGetContent.launch("image/*");
                }
            });
            ivSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    whichPicture = 2;
                    mGetContent.launch("image/*");
                }
            });
            ivFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    whichPicture = 1;
                    mGetContent.launch("image/*");
                }
            });
            tvSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String age = etAgeOfMyProfile.getText().toString();
                    if(Integer.valueOf(age)>25||Integer.valueOf(age)<16){
                        Toast.makeText(getActivity(), "your age must be between 16-25", Toast.LENGTH_LONG).show();
                        return;
                    }
                    tvSave.setVisibility(View.INVISIBLE);
                    setFalse();
                    saveUsertoFB();
                    ivProfile.setEnabled(false);
                    ivFirst.setEnabled(false);
                    ivSecond.setEnabled(false);
                    ivThird.setEnabled(false);

                }
            });
        }
    }

    private void saveUsertoFB() {
        //מטרת הפעולה לשמור את פרטי המשתמש המעודכנים לפיירבייס.
        // document ref
        // user details -> read from edit text
        // update firebase
        String bio = etBio.getText().toString();
        user.setInfo(bio);
        String age = etAgeOfMyProfile.getText().toString();
        user.setAge(Integer.valueOf(age));
        String name = etNameOfMyProfile.getText().toString();
        user.setUsername(name);
        docRef.set(user);
        Toast.makeText(getActivity(),"saved",Toast.LENGTH_LONG).show();
    }

    public void signOut() {
        //מטרת הפעולה היא לאפשר התנתקות
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}