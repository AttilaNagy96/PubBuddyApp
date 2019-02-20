package ie.wit.pubbuddy.adapters;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ie.wit.pubbuddy.models.Beer;

public class BeerFilter extends Filter {

    public List<Beer> originalBeerList;
    public String filterText;
    public BeerListAdapter adapter;

    public BeerFilter(List<Beer> originalBeerList, String filterText,
                        BeerListAdapter adapter) {
        super();
        this.originalBeerList = originalBeerList;
        this.filterText = filterText;
        this.adapter = adapter;
    }

    public void setFilter(String filterText) {
        this.filterText = filterText;
    }

    @Override
    protected FilterResults performFiltering(CharSequence prefix) {
        FilterResults results = new FilterResults();

        List<Beer> newBeers;
        String beerName;

        if (prefix == null || prefix.length() == 0) {
            newBeers = new ArrayList<>();
            if (filterText.equals("all")) {
                results.values = originalBeerList;
                results.count = originalBeerList.size();
            } else {
                if (filterText.equals("favourites")) {
                    for (Beer c : originalBeerList)
                        if (c.favourite)
                            newBeers.add(c);
                }
                results.values = newBeers;
                results.count = newBeers.size();
            }
        } else {
            String prefixString = prefix.toString().toLowerCase();
            newBeers = new ArrayList<>();

            for (Beer c : originalBeerList) {
                beerName = c.beerName.toLowerCase();
                if (beerName.contains(prefixString)) {
                    if (filterText.equals("all")) {
                        newBeers.add(c);
                    } else if (c.favourite) {
                        newBeers.add(c);
                    }}}
            results.values = newBeers;
            results.count = newBeers.size();
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence prefix, FilterResults results) {

        adapter.beerList = (ArrayList<Beer>) results.values;

        if (results.count >= 0)
            adapter.notifyDataSetChanged();
        else {
            adapter.notifyDataSetInvalidated();
            adapter.beerList = originalBeerList;
        }
        Log.v("PubBuddy", "publishResults : " + adapter.beerList);
    }
}
