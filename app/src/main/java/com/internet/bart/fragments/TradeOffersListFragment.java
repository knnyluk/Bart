package com.internet.bart.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
