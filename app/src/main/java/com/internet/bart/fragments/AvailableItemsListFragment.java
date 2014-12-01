package com.internet.bart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.internet.bart.R;
import com.internet.bart.adapters.AvailableItemsAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.AvailableItem;

import java.util.List;

/**
 * Created on 12/1/14.
 */
public class AvailableItemsListFragment extends Fragment implements ParseApiCallback {

    private AvailableItemsAdapter availableItemsAdapter;

    public AvailableItemsListFragment() {
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
            List<AvailableItem> availableItemList = AvailableItem.fromJSONString(response);
            availableItemsAdapter.addAll(availableItemList);
            availableItemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {
        if (isAdded()) {
                Toast.makeText(getActivity(), "Sorry, there was trouble fetching the available items", Toast.LENGTH_SHORT).show();
        }
    }
}
