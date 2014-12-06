package com.internet.bart.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.internet.bart.adapters.AvailableItemsAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.FragmentController;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.AvailableItem;

import java.util.List;

/**
 * Created on 12/1/14.
 */
public class AvailableItemsListFragment extends ListFragment implements ParseApiCallback {

    private AvailableItemsAdapter availableItemsAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseRestApi.getParseRestApi().getAvailableItems(this);
        availableItemsAdapter = new AvailableItemsAdapter(getActivity());
        setListAdapter(availableItemsAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (getActivity() instanceof FragmentController) {

            AvailableItem availableItem = (AvailableItem) l.getAdapter().getItem(position);
            AvailableItemDetailFragment availableItemDetailFragment = AvailableItemDetailFragment.newInstance(availableItem);

            FragmentController fragmentController = (FragmentController) getActivity();
            fragmentController.changeFragment(availableItemDetailFragment, true);

        } else {
            throw new IllegalArgumentException("Your activity must implement the FragmentController interface");
        }
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
