package ie.wit.pubbuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ie.wit.pubbuddy.registration.Login;
import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.registration.Register;

public class Info extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
}
