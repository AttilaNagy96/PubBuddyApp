package ie.wit.pubbuddy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.registration.Login;
import ie.wit.pubbuddy.registration.Register;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, Home.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
