package ie.wit.pubbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.models.Beer;

public class BeerItem {

    View view;

    public BeerItem(Context context, ViewGroup parent,
                      View.OnClickListener deleteListener, Beer beer)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.beerrow, parent, false);
        view.setTag(beer.beerId);

        updateControls(beer);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(beer);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Beer beer) {
        ((TextView) view.findViewById(R.id.rowBeerName)).setText(beer.beerName);

        ((TextView) view.findViewById(R.id.rowPub)).setText(beer.bar);
        ((TextView) view.findViewById(R.id.rowRating)).setText(beer.rating + " *");
        ((TextView) view.findViewById(R.id.rowPrice)).setText("€" +
                new DecimalFormat("0.00").format(beer.price));

        ImageView imgIcon = view.findViewById(R.id.rowFavouriteImg);

        if (beer.favourite == true)
            imgIcon.setImageResource(R.drawable.favourites_72_on);
        else
            imgIcon.setImageResource(R.drawable.favourites72);
    }
}
