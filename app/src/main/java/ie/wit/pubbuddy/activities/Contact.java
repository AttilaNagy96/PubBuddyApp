package ie.wit.pubbuddy.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.google_sign_in.Google_Sign_in;

public class Contact extends Base implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Call toolbar since original toolbar was disabled for this activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final EditText to = (EditText) findViewById(R.id.sendTo);
        final EditText subject = (EditText) findViewById(R.id.subject);
        final EditText message = (EditText) findViewById(R.id.emailText);

        Button submitButton = (Button) findViewById(R.id.sendEmail);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                String toS = to.getText().toString();
                String subS = subject.getText().toString();
                String mesS = message.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, toS);
                email.putExtra(Intent.EXTRA_SUBJECT, subS);
                email.putExtra(Intent.EXTRA_TEXT, mesS);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose App to send Email"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_map:
                startActivity (new Intent(this, Map.class));
                Toast.makeText(this,"Google Map Page",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_video:
                startActivity (new Intent(this, Video.class));
                Toast.makeText(this,"Youtube Video Page",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sign_in:
                startActivity (new Intent(this, Google_Sign_in.class));
                Toast.makeText(this,"Google Sign In Page",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_contact:
                startActivity (new Intent(this, Contact.class));
                Toast.makeText(this,"Contact Page",Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
