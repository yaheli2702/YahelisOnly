package com.example.yahelis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //מטרת המחלקה הזו היא להיות אחראית על כל החלק של ההרשמה לאפליקציה. המחלקה נותנת למשתמשת להיכנס לאפליקציה אם המייל והסיסמא נכונים.

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //מטרת הפעולה היא לאתחל את הכל, היא קובעת את התצוגה ובודקת אם המשתמש כבר מחובר.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // already signed in
        if(mAuth.getCurrentUser() != null )
        {
            Intent i = new Intent(this, MainTraveling.class);
            startActivity(i);
        }
       // mAuth.getCurrentUser() == null  -> never signed UP
        TextView tvForgotPassword=findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivity", "Forgot Password clicked");
                    Intent intent=new Intent(MainActivity.this, ForgotPasswordActivity.class);
                    startActivity(intent);
            }
        });
    }

    public void signUp(View view) {
        //מטרת הפעולה היא לעבור למחלקה אחרת (Logging in(,
        // מזמנים את פעולה זו אם המשתמש לחץ על כפתור "חדש? הרשם", והיא מעבירה לעמוד ההרשמה.
        Intent intent=new Intent(this, LoggingIn.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        //מטרת הפעולה היא להתחבר עם מייל וסיסמה שהוזנו על ידי המשתמש.
        // אם ההתחברות מצליחה, המשתמש עובר לעמוד הבית של האפליקציה (Main Traveling).
        // אם נכשל, מוצגת הודעת שגיאה.

        EditText etMail=findViewById(R.id.email);
        EditText etPassword=findViewById(R.id.password);
        String mail=etMail.getText().toString();
        String password=etPassword.getText().toString();
        if(mail.equals("")||password.equals(""))
            Toast.makeText(MainActivity.this,"please make sure you fill all the fields",Toast.LENGTH_LONG).show();
        else
        {
            // try to login, if we fail -> this means user does not exist
            mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // if success - there is such a user, maybe swtiched phones or re installed
                    if(task.isSuccessful())
                    {
                        Intent i = new Intent(MainActivity.this, MainTraveling.class);
                        startActivity(i);

                    }
                    else
                        Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();


                }
            });
        }
    }
}