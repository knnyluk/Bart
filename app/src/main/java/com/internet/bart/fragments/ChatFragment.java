package com.internet.bart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.internet.bart.R;
import com.internet.bart.adapters.ChatMessageAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.ChatMessage;
import com.internet.bart.models.TradeProposal;

import java.util.List;

/**
 * Created on 12/9/14.
 */
public class ChatFragment extends Fragment implements View.OnClickListener, ParseApiCallback {

    private ChatMessageAdapter chatMessageAdapter;
    private ListView chatMessageListView;
    private EditText textEditText;
    private String tradeId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container ,false);

        tradeId = getActivity().getIntent().getStringExtra(TradeProposal.TRADE_PROPOSAL_KEY);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textEditText = (EditText) view.findViewById(R.id.new_message_edit_text);
        Button sendButton = (Button) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_button:
//                System.out.println("send button clicked");
                String text = textEditText.getText().toString();
                ChatMessage newMessage = new ChatMessage(text);
                chatMessageAdapter.add(newMessage);
                chatMessageAdapter.notifyDataSetChanged();
                textEditText.setText("");
                newMessage.writeToParse(tradeId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        chatMessageAdapter = new ChatMessageAdapter(getActivity());
        chatMessageListView = (ListView) getActivity().findViewById(R.id.message_list_view);
        chatMessageListView.setAdapter(chatMessageAdapter);
        ParseRestApi.getParseRestApi().getChatMessages(tradeId, this);
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
