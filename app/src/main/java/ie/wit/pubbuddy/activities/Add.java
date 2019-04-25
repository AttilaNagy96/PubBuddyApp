package ie.wit.pubbuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import ie.wit.pubbuddy.registration.LoggedIn;
import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.models.Beer;

public class Add extends Base {

    private String 		beerName, pub;
    private double 		beerPrice, ratingValue;
    private EditText name, bar, price;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.addNameET);
        bar =  findViewById(R.id.addPubET);
        price =  findViewById(R.id.addPriceET);
        ratingBar =  findViewById(R.id.addRatingBar);
    }

    public void addBeer(View v) {

        beerName = name.getText().toString();
        pub = bar.getText().toString();
        try {
            beerPrice = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e) {
            beerPrice = 0.0;
        }
        ratingValue = ratingBar.getRating();

        if ((beerName.length() > 0) && (pub.length() > 0)
                && (price.length() > 0)) {
            Beer c = new Beer(beerName, pub, ratingValue,
                    beerPrice, false);

            app.beerList.add(c);
            startActivity(new Intent(this, Beverages.class));
        } else
            Toast.makeText(
                    this,
                    "You must Enter Something for "
                            + "\'Name\', \'Pub\' and \'Price\'",
                    Toast.LENGTH_SHORT).show();
    }
}
