package ie.wit.pubbuddy.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.models.Beer;

public class BeerListAdapter extends ArrayAdapter<Beer> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<Beer> beerList;

    public BeerListAdapter(Context context, View.OnClickListener deleteListener, List<Beer> beerList) {
        super(context, R.layout.beerrow, beerList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.beerList = beerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BeerItem item = new BeerItem(context, parent,
                deleteListener, beerList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return beerList.size();
    }

    @Override
    public Beer getItem(int position) {
        return beerList.get(position);
    }
}