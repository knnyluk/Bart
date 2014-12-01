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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.internet.bart.R;
import com.internet.bart.adapters.AvailableItemsAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.AvailableItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
    public static class PlaceholderFragment extends Fragment implements ParseApiCallback {

        AvailableItemsAdapter availableItemsAdapter;

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
            ParseRestApi parseRestApi = new ParseRestApi();
            parseRestApi.getAvailableItems(this);
            availableItemsAdapter = new AvailableItemsAdapter(getActivity());
            ListView availableItemsListView = (ListView)getActivity().findViewById(R.id.available_items_listview);
            availableItemsListView.setAdapter(availableItemsAdapter);
        }

        @Override
        public void onSuccess(String response) {
            if (isAdded()) {
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject)parser.parse(response);
                JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                Gson gson = new Gson();
                Type listType = new TypeToken<List<AvailableItem>>(){}.getType();
                List<AvailableItem> availableItemList = gson.fromJson(jsonArray,listType);
                availableItemsAdapter.addAll(availableItemList);
                availableItemsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError() {
            if (isAdded()) {
//                Toast.makeText(getActivity(), "Error loading Subreddit list", Toast.LENGTH_SHORT).show();
                System.out.println("callback error");
            }
        }
    }
}
