package com.internet.bart.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 12/9/14.
 */
public class ChatMessage {
    private String text;
    private User sender;

    public static List<ChatMessage> fromJSONString(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject rawJsonObject = (JsonObject)parser.parse(jsonString);
        JsonArray jsonArrayOfChatMessages = rawJsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ChatMessage>>(){}.getType();


        System.out.println(gson.fromJson(jsonArrayOfChatMessages,listType));


        return gson.fromJson(jsonArrayOfChatMessages,listType);
    }

    public String getText() {
        return text;
    }

    public String getSenderId() {
        return sender.getObjectId();
    }
}
