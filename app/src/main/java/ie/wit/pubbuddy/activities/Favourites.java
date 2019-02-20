package ie.wit.pubbuddy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.fragments.BeerFragment;


public class Favourites extends Base {

    private TextView emptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        emptyList = findViewById(R.id.emptyList);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(app.beerList.isEmpty())
            emptyList.setText(getString(R.string.emptyMessageLbl));
        else
            emptyList.setText("");

        beerFragment = BeerFragment.newInstance(); //get a new Fragment instance
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, beerFragment)
                .commit(); // add it to the current activity
    }
}
