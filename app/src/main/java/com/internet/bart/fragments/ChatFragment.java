package com.internet.bart.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.internet.bart.R;
import com.internet.bart.adapters.ChatMessageAdapter;
import com.internet.bart.apis.ParseRestApi;
import com.internet.bart.interfaces.ParseApiCallback;
import com.internet.bart.models.ChatMessage;
import com.internet.bart.models.TradeProposal;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created on 12/9/14.
 */
public class ChatFragment extends Fragment implements View.OnClickListener, ParseApiCallback {

    private ChatMessageAdapter chatMessageAdapter;
    private ListView chatMessageListView;
    private EditText textEditText;
    private String tradeId;

    private Timer refreshTimer;
    private static final long REFRESH_INTERVAL = TimeUnit.SECONDS.toMillis(3);

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
        startRefreshTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRefreshTimer();
    }

    private void startRefreshTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                autoRefreshList();
            }
        };

        refreshTimer = new Timer();
        refreshTimer.schedule(timerTask, 0, REFRESH_INTERVAL);
    }

    private void stopRefreshTimer() {
        refreshTimer.cancel();
        refreshTimer = null;
    }

    private void autoRefreshList() {
        if (isAdded()) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(getActivity(), "Auto refreshing messages", Toast.LENGTH_SHORT).show();
                }
            });
            loadChatMessages();
        }
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

    private void loadChatMessages() {
        if (tradeId != null) {
            ParseRestApi.getParseRestApi().getChatMessages(tradeId, this);
        } else {
            throw new IllegalStateException("Lost track of the trade");
        }
    }
}
