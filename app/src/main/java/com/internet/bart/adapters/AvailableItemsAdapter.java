package com.internet.bart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.models.AvailableItem;

/**
 * Created on 11/28/14.
 */
public class AvailableItemsAdapter extends ArrayAdapter<AvailableItem> {

    public AvailableItemsAdapter(Context context) {
        super(context, R.layout.listitem_available_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_available_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AvailableItem availableItem = getItem(position);

        viewHolder.itemNameTextView.setText(availableItem.getName());
        viewHolder.itemTitleTextView.setText(availableItem.getTitle());

        return convertView;
    }

    private static class ViewHolder {

        private TextView itemNameTextView;
        private TextView itemTitleTextView;

        public ViewHolder(View rootView) {
            this.itemNameTextView = (TextView) rootView.findViewById(R.id.itemNametextView);
            this.itemTitleTextView = (TextView) rootView.findViewById(R.id.itemTitleTextView);
        }

    }

}
