package ie.wit.pubbuddy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.fragments.BeerFragment;
import ie.wit.pubbuddy.models.Beer;

public class Recommendations extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        if(app.beerList.isEmpty()) setupBeers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        beerFragment = BeerFragment.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, beerFragment)
                .commit(); // add it to the current activity
    }

    public void setupBeers(){
        app.beerList.add(new Beer("Heineken", "Masons",4.5,3.00,false));
        app.beerList.add(new Beer("Rockshore", "The Guest House",3.5,4.00,false));
        app.beerList.add(new Beer("Fosters", "Craftsman",4.0,4.00,false));
        app.beerList.add(new Beer("Miller", "Paddy Browns",4.5,4.50,false));
        app.beerList.add(new Beer("Budweiser", "Kitty Kiernans",4.5,4.50,false));
    }
}
