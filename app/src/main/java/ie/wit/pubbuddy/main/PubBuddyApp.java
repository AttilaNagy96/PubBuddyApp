package ie.wit.pubbuddy.main;

import android.app.Application;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import ie.wit.pubbuddy.models.Beer;

public class PubBuddyApp extends Application {

    public List<Beer> beerList = new ArrayList<>();
    //public List<Beer2> beerList2 = new ArrayList<>;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.v("PubBuddy", "PubBuddy App Started");
    }
}
