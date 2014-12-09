package com.internet.bart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.internet.bart.R;
import com.internet.bart.adapters.AvailableItemsAdapter;
import com.internet.bart.adapters.ChatMessageAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.AvailableItem;
import com.internet.bart.models.ChatMessage;

import java.util.List;

/**
 * Created on 12/9/14.
 */
public class ChatFragment extends Fragment implements ParseApiCallback {

    private ChatMessageAdapter chatMessageAdapter;
    private ListView chatMessageListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container ,false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        chatMessageAdapter = new ChatMessageAdapter(getActivity());
        chatMessageListView = (ListView) getActivity().findViewById(R.id.message_list_view);
        chatMessageListView.setAdapter(chatMessageAdapter);
        ParseRestApi.getParseRestApi().getChatMessages("test", this);
    }

    @Override
    public void onSuccess(String response) {
        if (isAdded()) {
            List<ChatMessage> chatMessageList = ChatMessage.fromJSONString(response);
            chatMessageAdapter.clear();
            chatMessageAdapter.addAll(chatMessageList);
            chatMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {

    }
}
