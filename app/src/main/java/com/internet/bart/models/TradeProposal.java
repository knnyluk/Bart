package com.internet.bart.models;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created on 12/4/14.
 */
public class TradeProposal {

    public static void writeToParse(String recipientId, String soughtItemId, String offeredItemId) {
        ParseObject parseTradeProposal = new ParseObject("TradeProposal");
        parseTradeProposal.put("sender", ParseUser.getCurrentUser());
        parseTradeProposal.put("recipient", ParseObject.createWithoutData("_User", recipientId));
        parseTradeProposal.put("soughtItem", ParseObject.createWithoutData("OwnedItem", soughtItemId));
        parseTradeProposal.put("offeredItem", ParseObject.createWithoutData("OwnedItem", offeredItemId));
        parseTradeProposal.saveInBackground();
    }

}
