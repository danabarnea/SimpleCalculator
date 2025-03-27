package com.example.simplecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private ShapeableImageView imageView;
    private TextView name, mail;

    private final ActivityResultLauncher<Intent> googleSignInLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        if (account != null) {
                            firebaseAuthWithGoogle(account.getIdToken());
                        }
                    } catch (ApiException e) {
                        Toast.makeText(this, "שגיאה בהתחברות: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // אתחול Firebase
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        // חיבור לאלמנטים ב-XML
        imageView = findViewById(R.id.profileImage);
        name = findViewById(R.id.nameTV);
        mail = findViewById(R.id.mailTv);

        // הגדרת אפשרויות התחברות עם Google
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.cilent_id)) // יש לוודא ש-client_id מוגדר נכון
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // מאזין ללחיצה על כפתור ההתחברות
        findViewById(R.id.signIn).setOnClickListener(view -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            googleSignInLauncher.launch(signInIntent);
        });

        // לבדוק אם המשתמש כבר מחובר
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            updateUI(user);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            updateUI(user);
                        }
                        Toast.makeText(this, "התחברת בהצלחה!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "ההתחברות נכשלה: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Glide.with(this).load(user.getPhotoUrl()).into(imageView);
        name.setText(user.getDisplayName());
        mail.setText(user.getEmail());
    }
}
