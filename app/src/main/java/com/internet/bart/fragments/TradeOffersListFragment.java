package com.internet.bart.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.parse.ParseUser;

/**
 * Created on 12/8/14.
 */
public class TradeOffersListFragment extends ListFragment implements ParseApiCallback{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseRestApi.getParseRestApi().getTradesOfferedToUser(currentUserId, this);
    }

    @Override
    public void onSuccess(String response) {
        if (isAdded()) {
            System.out.println(response);
//            List<AvailableItem> availableItemList = AvailableItem.fromJSONString(response);
//            availableItemsAdapter.clear();
//            availableItemsAdapter.addAll(availableItemList);
//            availableItemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {
        if (isAdded()) {
//            Toast.makeText(getActivity(), "Sorry, there was trouble fetching the available items", Toast.LENGTH_SHORT).show();
        }
    }
}
