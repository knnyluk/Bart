package com.internet.bart.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 12/9/14.
 */
public class ChatMessage {
    private String text;
    private User sender;

    public ChatMessage(String text) {
        this.text = text;
        sender = new User(ParseUser.getCurrentUser().getObjectId().toString());
    }

    public static List<ChatMessage> fromJSONString(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject rawJsonObject = (JsonObject)parser.parse(jsonString);
        JsonArray jsonArrayOfChatMessages = rawJsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ChatMessage>>(){}.getType();


        System.out.println(gson.fromJson(jsonArrayOfChatMessages,listType));


        return gson.fromJson(jsonArrayOfChatMessages,listType);
    }

//    public static void writeToParse(String tradeId, String text) {
//        ParseObject chatMessage = new ParseObject("ChatMessage");
//        chatMessage.put("sender", ParseUser.getCurrentUser());
//        chatMessage.put("acceptedTrade", ParseObject.createWithoutData("TradeProposal", tradeId));
//        chatMessage.put("text", text);
//    }

    public void writeToParse(String tradeId) {
        ParseObject chatMessage = new ParseObject("ChatMessage");
        chatMessage.put("sender", ParseUser.getCurrentUser());
        chatMessage.put("acceptedTrade", ParseObject.createWithoutData("TradeProposal", tradeId));
        chatMessage.put("text", text);
        chatMessage.saveInBackground();
    }

    public String getText() {
        return text;
    }

    public String getSenderId() {
        return sender.getObjectId();
    }

    public String toString() {
        return getSenderId() + ": " + getText();
    }
}
