package com.internet.bart.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.internet.bart.adapters.AvailableItemsAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.AvailableItem;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created on 12/4/14.
 */
public class OfferTradeListFragment extends ListFragment implements ParseApiCallback {
    private AvailableItemsAdapter ownedAvailableItemsAdapter;
    private AvailableItem itemToTradeFor;

    public static OfferTradeListFragment newInstance(AvailableItem availableItem) {

//        Bundle args = new Bundle();
//        args.putParcelable(AvailableItem.ITEM_TO_TRADE_FOR, availableItem);

        OfferTradeListFragment offerTradeFragment = new OfferTradeListFragment();
        offerTradeFragment.itemToTradeFor = availableItem;

        return offerTradeFragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ParseRestApi.getParseRestApi().getOwnedItems(ParseUser.getCurrentUser().getObjectId(), this);
        ownedAvailableItemsAdapter = new AvailableItemsAdapter(getActivity());
        setListAdapter(ownedAvailableItemsAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    @Override
    public void onSuccess(String response) {
        if (isAdded()) {
            List<AvailableItem> availableItemList = AvailableItem.fromJSONString(response);
            ownedAvailableItemsAdapter.addAll(availableItemList);
            ownedAvailableItemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {
        if (isAdded()) {
            Toast.makeText(getActivity(), "Sorry, there was trouble fetching the items you own", Toast.LENGTH_SHORT).show();
        }
    }
}
