package com.internet.bart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.models.AvailableItem;
import com.internet.bart.models.TradeProposal;

/**
 * Created on 12/8/14.
 */
public class TradeProposalAdapter extends ArrayAdapter<TradeProposal> {

    public TradeProposalAdapter(Context context) {
        super(context, R.layout.listitem_trade_proposal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_trade_proposal, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TradeProposal tradeProposal = getItem(position);

        viewHolder.offeredItemTextView.setText(tradeProposal.getOfferedItem().getName());
//        viewHolder.itemTitleTextView.setText(tradeProposal.getTitle());

        return convertView;
    }

    private static class ViewHolder {

        private TextView offeredItemTextView;
        private TextView itemTitleTextView;

        public ViewHolder(View rootView) {
            this.offeredItemTextView = (TextView) rootView.findViewById(R.id.offered_item_name_text_view);
//            this.itemTitleTextView = (TextView) rootView.findViewById(R.id.itemTitleTextView);
        }

    }

}
