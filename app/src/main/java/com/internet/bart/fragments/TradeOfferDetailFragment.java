package com.internet.bart.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.activities.ChatActivity;
import com.internet.bart.models.TradeProposal;

/**
 * Created on 12/9/14.
 */
public class TradeOfferDetailFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_TRADE_PROPOSAL = "arg_trade_proposal";

    TradeProposal currentTradeProposal;

    TextView offered_item_text_view;
    TextView sought_item_text_view;

    public static TradeOfferDetailFragment newInstance(TradeProposal tradeProposal) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_TRADE_PROPOSAL, tradeProposal);

        TradeOfferDetailFragment tradeOfferDetailFragment = new TradeOfferDetailFragment();
        tradeOfferDetailFragment.setArguments(args);

        return tradeOfferDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_trade_offer_detail, container ,false);

        offered_item_text_view = (TextView) view.findViewById(R.id.offered_item_name_text_view);
        sought_item_text_view = (TextView) view.findViewById(R.id.sought_item_name_text_view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentTradeProposal = getArguments().getParcelable(ARG_TRADE_PROPOSAL);

        offered_item_text_view.setText(currentTradeProposal.getOfferedItem().getName());
        sought_item_text_view.setText(currentTradeProposal.getSoughtItem().getName());

        switch (currentTradeProposal.getStatus()) {
            case TradeProposal.STATUS_PROPOSED:
                enableButtons(view);
                break;
            case TradeProposal.STATUS_ACCEPTED:
                enableCommunication(view);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accept_button:
                System.out.println("accept button clicked");
                currentTradeProposal.updateStatus(TradeProposal.STATUS_ACCEPTED);
                break;
            case R.id.reject_button:
                System.out.println("reject button clicked");
                currentTradeProposal.updateStatus(TradeProposal.STATUS_REJECTED);
                break;
            case R.id.communicate_button:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
        }
    }

    private void enableButtons(View view) {
        LinearLayout buttonWrapper = (LinearLayout) view.findViewById(R.id.button_container);
        buttonWrapper.setVisibility(View.VISIBLE);

        Button acceptButton = (Button) view.findViewById(R.id.accept_button);
        Button rejectButton = (Button) view.findViewById(R.id.reject_button);

        acceptButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);
    }

    private void enableCommunication(View view) {
        Button communicateButton = (Button) view.findViewById(R.id.communicate_button);
        communicateButton.setVisibility(View.VISIBLE);

        communicateButton.setOnClickListener(this);
    }
}
