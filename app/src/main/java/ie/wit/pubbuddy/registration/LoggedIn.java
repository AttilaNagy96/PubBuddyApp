package ie.wit.pubbuddy.registration;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.activities.Base;
import ie.wit.pubbuddy.activities.Info;

public class LoggedIn extends Base {

    private ImageView addImage;
    private Button logOut, deleteAccount;
    private TextView email;
    private FirebaseAuth auth;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.showEmail);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(LoggedIn.this, Login.class));
                    finish();
                }
            }
        };

        logOut = (Button) findViewById(R.id.logOut);
        deleteAccount = (Button) findViewById(R.id.deleteAccount);
        addImage = findViewById(R.id.addImage);

        addImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (Build.VERSION.SDK_INT >= 22){
                    CheckAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }
            }
        });

        deleteAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (user != null) {

                    user.delete()

                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override

                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoggedIn.this, "Your account was deleted!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoggedIn.this, Register.class));
                                        finish();
                                    } else {
                                        Toast.makeText(LoggedIn.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void openGallery() {
        //open gallery inent and wait for user to pick an image!
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);
    }

    private void CheckAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(LoggedIn.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoggedIn.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(LoggedIn.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(LoggedIn.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else
            openGallery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            //user has successfully picked an image - reference needs to be saved
            pickedImgUri = data.getData();
            addImage.setImageURI(pickedImgUri);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText("Email: " + user.getEmail());
    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(LoggedIn.this, Login.class));
                finish();
            } else {
                setDataToView(user);
            }
        }
    };

    //sign out method
    public void signOut() {
        auth.signOut();

        // this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(LoggedIn.this, Login.class));
                    finish();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
