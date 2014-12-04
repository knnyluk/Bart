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
import com.internet.bart.models.TradeProposal;
import com.parse.ParseObject;
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

//        TradeProposal tradeProposal = new TradeProposal();
//        tradeProposal.setSoughtItemId(itemToTradeFor.getObjectId());
//        System.out.println("the object we want to trade for is");
//        System.out.println(tradeProposal.getSoughtItemId());
//        System.out.println("aka " + itemToTradeFor.getName());
//        System.out.println("which is owned by " + itemToTradeFor.getOwnerId());
//        System.out.println("and we are offering" + ownedAvailableItemsAdapter.getItem(position).getName());

        TradeProposal.writeToParse(itemToTradeFor.getOwnerId(),
                                   itemToTradeFor.getObjectId(),
                                   ownedAvailableItemsAdapter.getItem(position).getObjectId());

//        ParseObject parseTradeProposal = new ParseObject("TradeProposal");
//        parseTradeProposal.put("soughtItem", ParseObject.createWithoutData("OwnedItem", tradeProposal.getSoughtItemId()));
//        parseTradeProposal.put("sender", ParseUser.getCurrentUser());
//        parseTradeProposal.put("offeredItem", ParseObject.createWithoutData("OwnedItem", ownedAvailableItemsAdapter.getItem(position).getObjectId()));
//        parseTradeProposal.put("recipient", ParseObject.createWithoutData("_User", itemToTradeFor.getOwnerId()));
//        parseTradeProposal.saveInBackground();
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
