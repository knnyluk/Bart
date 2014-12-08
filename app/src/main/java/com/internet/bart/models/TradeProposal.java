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

    public static final int STATUS_REJECTED = 0;
    public static final int STATUS_PROPOSED = 1;
    public static final int STATUS_ACCEPTED = 2;

    private User sender, recipient;
    private AvailableItem offeredItem, soughtItem;
    private int status;

    public AvailableItem getOfferedItem() {
        return offeredItem;
    }

    public AvailableItem getSoughtItem() {
        return soughtItem;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusText() {
        switch (status) {
            case STATUS_REJECTED:
                return "Rejected";
            case STATUS_PROPOSED:
                return "Proposed";
            case STATUS_ACCEPTED:
                return "Accepted";
            default:
                return "Unknown";
        }
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
