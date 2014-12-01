package com.internet.bart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.models.AvailableItem;

/**
 * Created on 12/1/14.
 */
public class AvailableItemDetailFragment extends Fragment implements View.OnClickListener {

    public static final String ARG_AVAILABLE_ITEM = "arg_available_item";
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

        AvailableItem clickedAvailableItem = getArguments().getParcelable(ARG_AVAILABLE_ITEM);

        Button proposeTradeButton = (Button) getActivity().findViewById(R.id.propose_trade_button);
        proposeTradeButton.setOnClickListener(this);

        if (clickedAvailableItem != null) {

            nameTextView.setText(clickedAvailableItem.getName());
            titleTextView.setText(clickedAvailableItem.getTitle());
            descriptionTextView.setText(clickedAvailableItem.getFullDescription());

        } else {
            throw new IllegalStateException("Must supply a AvailableItem to AvailableItemDetailFragment");
        }

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.propose_trade_button) {
            System.out.println("Someone wants to propose a trade");
        }
    }
}