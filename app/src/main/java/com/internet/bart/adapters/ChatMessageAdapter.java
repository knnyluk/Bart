package com.internet.bart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.models.ChatMessage;

/**
 * Created on 12/9/14.
 */
public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    public ChatMessageAdapter(Context context) {
        super(context, R.layout.listitem_chat_message);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_chat_message, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChatMessage chatMessage = getItem(position);
        viewHolder.chatTextTextView.setText(chatMessage.toString());

        return convertView;
    }

    private static class ViewHolder {

        private TextView chatTextTextView;
//        private TextView usernameTextView;

        public ViewHolder(View rootView) {
            this.chatTextTextView = (TextView) rootView.findViewById(R.id.chat_text_text_view);
//            this.usernameTextView = (TextView) rootView.findViewById(R.id.username_text_view);
        }

    }
}
