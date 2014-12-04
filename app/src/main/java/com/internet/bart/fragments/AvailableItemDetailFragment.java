package com.internet.bart.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.activities.LoginActivity;
import com.internet.bart.activities.TradeProposalActivity;
import com.internet.bart.models.AvailableItem;
import com.parse.ParseUser;

/**
 * Created on 12/1/14.
 */
public class AvailableItemDetailFragment extends Fragment implements View.OnClickListener {

    private static final int LOGIN_REQUEST = 42;

    public static final String ARG_AVAILABLE_ITEM = "arg_available_item";
    AvailableItem currentAvailableItem;
    private TextView nameTextView, titleTextView, descriptionTextView;

    public static AvailableItemDetailFragment newInstance(AvailableItem availableItem) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_AVAILABLE_ITEM, availableItem);

        AvailableItemDetailFragment availableItemDetailFragment = new AvailableItemDetailFragment();
        availableItemDetailFragment.setArguments(args);

        return availableItemDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_available_item_detail, container ,false);

        nameTextView = (TextView) view.findViewById(R.id.available_item_name_textview);
        titleTextView = (TextView) view.findViewById(R.id.available_item_title_textview);
        descriptionTextView = (TextView) view.findViewById(R.id.available_item_description);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentAvailableItem = getArguments().getParcelable(ARG_AVAILABLE_ITEM);

        Button proposeTradeButton = (Button) getActivity().findViewById(R.id.propose_trade_button);
        proposeTradeButton.setOnClickListener(this);

        if (currentAvailableItem != null) {

            nameTextView.setText(currentAvailableItem.getName());
            titleTextView.setText(currentAvailableItem.getTitle());
            descriptionTextView.setText(currentAvailableItem.getFullDescription());

        } else {
            throw new IllegalStateException("Must supply a AvailableItem to AvailableItemDetailFragment");
        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.propose_trade_button) {
            if (ParseUser.getCurrentUser().getUsername() == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, LOGIN_REQUEST);
            } else {
                System.out.println("already logged in");
                Intent intent = new Intent(getActivity(), TradeProposalActivity.class);
                intent.putExtra(AvailableItem.ITEM_TO_TRADE_FOR, currentAvailableItem);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                System.out.println("Login successful");
                Intent intent = new Intent(getActivity(), TradeProposalActivity.class);
                startActivity(intent);
            }
        }
    }
}