package ie.wit.pubbuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.fragments.BeerFragment;
import ie.wit.pubbuddy.main.PubBuddyApp;
import ie.wit.pubbuddy.models.Beer;
import ie.wit.pubbuddy.registration.LoggedIn;
import ie.wit.pubbuddy.registration.Login;

public class Base extends AppCompatActivity {

    public PubBuddyApp app;
    public Bundle activityInfo; // Used for persistence (of sorts)
    public BeerFragment beerFragment; // How we'll 'share' our List of Items between Activities

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (PubBuddyApp) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.info:
                Toast.makeText(this, "Info Selected", Toast.LENGTH_SHORT).show();
                startActivity (new Intent(this, Info.class));
                break;
            case R.id.account:
                Toast.makeText(this, "My Account Selected", Toast.LENGTH_SHORT).show();
                startActivity (new Intent(this, Login.class));
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Not Available", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
