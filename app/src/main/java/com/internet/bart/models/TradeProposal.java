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
 * Created on 12/4/14.
 */
public class TradeProposal {

    private User sender, recipient;
    private AvailableItem offeredItem, soughtItem;

    public AvailableItem getOfferedItem() {
        return offeredItem;
    }

    public static List<TradeProposal> fromJSONString(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject rawJsonObject = (JsonObject)parser.parse(jsonString);
        JsonArray jsonArrayOfTradeProposals = rawJsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TradeProposal>>(){}.getType();
        return gson.fromJson(jsonArrayOfTradeProposals,listType);
    }

    public static void writeToParse(String recipientId, String soughtItemId, String offeredItemId) {
        ParseObject parseTradeProposal = new ParseObject("TradeProposal");
        parseTradeProposal.put("sender", ParseUser.getCurrentUser());
        parseTradeProposal.put("recipient", ParseObject.createWithoutData("_User", recipientId));
        parseTradeProposal.put("soughtItem", ParseObject.createWithoutData("OwnedItem", soughtItemId));
        parseTradeProposal.put("offeredItem", ParseObject.createWithoutData("OwnedItem", offeredItemId));
        parseTradeProposal.saveInBackground();
    }

    public String toString() {
        return soughtItem.toString();
    }

}
