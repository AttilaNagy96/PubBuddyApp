package ie.wit.pubbuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.models.Beer;

public class Edit extends Base {

    public Context context;
    public boolean isFavourite;
    public Beer aBeer;
    public ImageView editFavourite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        context = this;
        activityInfo = getIntent().getExtras();
        aBeer = getBeerObject(activityInfo.getString("beerId"));

        ((TextView)findViewById(R.id.editTitleTV)).setText(aBeer.beerName);

        ((EditText)findViewById(R.id.editNameET)).setText(aBeer.beerName);
        ((TextView)findViewById(R.id.editPubET)).setText(aBeer.bar);
        ((EditText)findViewById(R.id.editPriceET)).setText(""+aBeer.price);
        ((RatingBar) findViewById(R.id.editRatingBar)).setRating((float)aBeer.rating);

        editFavourite = findViewById(R.id.editFavourite);

        if (aBeer.favourite == true) {
            editFavourite.setImageResource(R.drawable.favourites_72_on);
            isFavourite = true;
        } else {
            editFavourite.setImageResource(R.drawable.favourites72);
            isFavourite = false;
        }
    }

    private Beer getBeerObject(String id) {

        for (Beer c : app.beerList)
            if (c.beerId.equalsIgnoreCase(id))
                return c;

        return null;
    }

    public void saveBeer(View v) {

        String beerName = ((EditText) findViewById(R.id.editNameET)).getText().toString();
        String pub = ((EditText) findViewById(R.id.editPubET)).getText().toString();
        String beerPriceStr = ((EditText) findViewById(R.id.editPriceET)).getText().toString();
        double ratingValue =((RatingBar) findViewById(R.id.editRatingBar)).getRating();
        double beerPrice;

        try {
            beerPrice = Double.parseDouble(beerPriceStr);
        } catch (NumberFormatException e) {
            beerPrice = 0.0;
        }

        if ((beerName.length() > 0) && (pub.length() > 0) && (beerPriceStr.length() > 0)) {
            aBeer.beerName = beerName;
            aBeer.bar = pub;
            aBeer.price = beerPrice;
            aBeer.rating = ratingValue;

            startActivity(new Intent(this,Beverages.class));

        } else
            Toast.makeText(this, "You must Enter Something for Name and Pub",Toast.LENGTH_SHORT).show();
    }

    public void toggle(View view) {

        if (isFavourite) {
            aBeer.favourite = false;
            Toast.makeText(this,"Removed From Favourites",Toast.LENGTH_SHORT).show();
            isFavourite = false;
            editFavourite.setImageResource(R.drawable.favourites72);
        } else {
            aBeer.favourite = true;
            Toast.makeText(this,"Added to Favourites !!",Toast.LENGTH_SHORT).show();
            isFavourite = true;
            editFavourite.setImageResource(R.drawable.favourites_72_on);
        }
    }
}
