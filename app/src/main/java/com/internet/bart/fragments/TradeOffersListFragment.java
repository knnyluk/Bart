package com.internet.bart.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.internet.bart.adapters.TradeProposalAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.FragmentController;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.AvailableItem;
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (getActivity() instanceof FragmentController) {

            TradeProposal tradeProposal = (TradeProposal) l.getAdapter().getItem(position);
            TradeOfferDetailFragment tradeOfferDetailFragment = TradeOfferDetailFragment.newInstance(tradeProposal);
//            System.out.println("item " + Integer.toString(position) + " clicked");


//            AvailableItem availableItem = (AvailableItem) l.getAdapter().getItem(position);
//            AvailableItemDetailFragment availableItemDetailFragment = AvailableItemDetailFragment.newInstance(availableItem);
//
            FragmentController fragmentController = (FragmentController) getActivity();
            fragmentController.changeFragment(tradeOfferDetailFragment, true);

        } else {
            throw new IllegalArgumentException("Your activity must implement the FragmentController interface");
        }
    }

    @Override
    public void onSuccess(String response) {
        if (isAdded()) {
            List<TradeProposal> tradeProposalsList = TradeProposal.fromJSONString(response);
            tradeProposalAdapter.clear();
            tradeProposalAdapter.addAll(tradeProposalsList);
            tradeProposalAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {
        if (isAdded()) {
            Toast.makeText(getActivity(), "Sorry, there was trouble fetching the trades offered to you", Toast.LENGTH_SHORT).show();
        }
    }
}
