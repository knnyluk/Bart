package com.internet.bart.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

import com.internet.bart.adapters.AvailableItemsAdapter;
import com.internet.bart.adapters.TradeProposalAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.TradeProposal;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created on 12/8/14.
 */
public class TradeOffersListFragment extends ListFragment implements ParseApiCallback{

    private TradeProposalAdapter tradeProposalAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        ParseRestApi.getParseRestApi().getTradesOfferedToUser(currentUserId, this);
        tradeProposalAdapter = new TradeProposalAdapter(getActivity());
        setListAdapter(tradeProposalAdapter);
    }

    @Override
    public void onSuccess(String response) {
        if (isAdded()) {
//            System.out.println(response);
            System.out.println(TradeProposal.fromJSONString(response));

            List<TradeProposal> tradeProposalsList = TradeProposal.fromJSONString(response);

            tradeProposalAdapter.clear();
            tradeProposalAdapter.addAll(tradeProposalsList);
            tradeProposalAdapter.notifyDataSetChanged();

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
