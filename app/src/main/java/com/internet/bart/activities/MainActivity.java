package com.internet.bart.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListView;
import android.widget.TextView;

import com.internet.bart.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("OwnedItem");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> ownedItemsList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + ownedItemsList.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ParseQueryAdapter<ParseObject> availableItemsAdapter;


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            availableItemsAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), "OwnedItem") {
                @Override
                public View getItemView(ParseObject object, View v, ViewGroup parent) {
                    if (v == null) {
                        v = View.inflate(getContext(), R.layout.listitem_available_item, null);
                    }

                    // Take advantage of ParseQueryAdapter's getItemView logic for
                    // populating the main TextView/ImageView.
                    // The IDs in your custom layout must match what ParseQueryAdapter expects
                    // if it will be populating a TextView or ImageView for you.
                    super.getItemView(object, v, parent);

                    // Do additional configuration before returning the View.
                    TextView itemNameTextView = (TextView) v.findViewById(R.id.itemNametextView);
                    itemNameTextView.setText(object.getString("name"));

                    TextView itemDescriptionTextView = (TextView) v.findViewById(R.id.itemDescriptionTextView);
                    itemDescriptionTextView.setText("Condition: " + object.getString("description"));
                    return v;
                }
            };

            ListView availableItemsListView = (ListView) getActivity().findViewById(R.id.available_items_listview);
            availableItemsListView.setAdapter(availableItemsAdapter);

        }
    }
}
