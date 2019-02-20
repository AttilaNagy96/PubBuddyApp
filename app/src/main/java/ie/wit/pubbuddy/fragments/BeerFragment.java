package ie.wit.pubbuddy.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import ie.wit.pubbuddy.R;
import ie.wit.pubbuddy.activities.Base;
import ie.wit.pubbuddy.activities.Edit;
import ie.wit.pubbuddy.activities.Favourites;
import ie.wit.pubbuddy.adapters.BeerFilter;
import ie.wit.pubbuddy.adapters.BeerListAdapter;
import ie.wit.pubbuddy.models.Beer;

public class BeerFragment  extends ListFragment implements View.OnClickListener,
        AbsListView.MultiChoiceModeListener {

    public Base activity;
    public static BeerListAdapter listAdapter;
    public ListView listView;
    public BeerFilter beerFilter;

    public BeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("beerId", (String) v.getTag());
        Intent goEdit = new Intent(getActivity(), Edit.class); // Creates a new Intent

        /* Add the bundle to the intent here */

        goEdit.putExtras(activityInfo);
        getActivity().startActivity(goEdit); // Launch the Intent
    }

    public static BeerFragment newInstance() {
        BeerFragment fragment = new BeerFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        listAdapter = new BeerListAdapter(activity, this, activity.app.beerList);
        beerFilter = new BeerFilter(activity.app.beerList,"all",listAdapter);

        if (getActivity() instanceof Favourites) {
            beerFilter.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
            beerFilter.filter(null); // Filter the data, but don't use any prefix
            listAdapter.notifyDataSetChanged(); // Update the adapter
        }
        setListAdapter (listAdapter);
        setRandomBeer();
        checkEmptyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        listView = v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Beer)
        {
            onBeerDelete ((Beer) view.getTag());
        }
    }

    public void onBeerDelete(final Beer beer)
    {
        String stringName = beer.beerName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Beer\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.beerList.remove(beer); // remove from our list
                listAdapter.beerList.remove(beer); // update adapters data
                setRandomBeer();
                listAdapter.notifyDataSetChanged(); // refresh adapter
                checkEmptyList();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_beer:
                deleteBeers(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteBeers(ActionMode actionMode)
    {
        for (int i = listAdapter.getCount() -1 ; i >= 0; i--)
        {
            if (listView.isItemChecked(i))
            {
                activity.app.beerList.remove(listAdapter.getItem(i));
                if (activity instanceof Favourites)
                    listAdapter.beerList.remove(listAdapter.getItem(i));
            }
        }
        setRandomBeer();
        listAdapter.notifyDataSetChanged(); // refresh adapter
        checkEmptyList();

        actionMode.finish();
    }

    public void setRandomBeer() {

        ArrayList<Beer> beerList = new ArrayList<>();

        for(Beer c : activity.app.beerList)
            if (c.favourite)
                beerList.add(c);

        if (activity instanceof Favourites)
            if( !beerList.isEmpty()) {
                Beer randomBeer = beerList.get(new Random()
                        .nextInt(beerList.size()));

                ((TextView) getActivity().findViewById(R.id.favouriteBeerName)).setText(randomBeer.beerName);
                ((TextView) getActivity().findViewById(R.id.favouritePub)).setText(randomBeer.bar);
                ((TextView) getActivity().findViewById(R.id.favouriteBeerPrice)).setText("€ " + randomBeer.price);
                ((TextView) getActivity().findViewById(R.id.favouriteBeerRating)).setText(randomBeer.rating + " *");
            }
            else {
                ((TextView) getActivity().findViewById(R.id.favouriteBeerName)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouritePub)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBeerPrice)).setText("N/A");
                ((TextView) getActivity().findViewById(R.id.favouriteBeerRating)).setText("N/A");
            }
    }

    public void checkEmptyList()
    {
        TextView recentList = getActivity().findViewById(R.id.emptyList);

        if(activity.app.beerList.isEmpty())
            recentList.setText(getString(R.string.emptyMessageLbl));
        else
            recentList.setText("");
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked)
    {}
    /* ************ MultiChoiceModeListener methods (end) *********** */

}
