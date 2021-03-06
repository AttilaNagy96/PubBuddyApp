package ie.wit.pubbuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.google_sign_in.Google_Sign_in;


public class Home extends Base implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MapFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_map);
        }*/

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Information", Snackbar.LENGTH_LONG)
                        .setAction("More Info...", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });
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
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void add(View v) {
        startActivity(new Intent(this, Add.class));
    }

    public void beverages(View v) {
        startActivity(new Intent(this, Beverages.class));
    }

    public void recommendations(View v) {
        startActivity(new Intent(this, Recommendations.class));
    }

    public void favourites(View v) {
        startActivity(new Intent(this, Favourites.class));
    }

}
