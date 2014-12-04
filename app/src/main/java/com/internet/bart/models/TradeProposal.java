package com.internet.bart.models;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created on 12/4/14.
 */
public class TradeProposal {
    private String senderId, recipientId, soughtItemId, offeredItemId;

    public static void writeToParse(String recipientId, String soughtItemId, String offeredItemId) {
        ParseObject parseTradeProposal = new ParseObject("TradeProposal");
        parseTradeProposal.put("sender", ParseUser.getCurrentUser());
        parseTradeProposal.put("recipient", ParseObject.createWithoutData("_User", recipientId));
        parseTradeProposal.put("soughtItem", ParseObject.createWithoutData("OwnedItem", soughtItemId));
        parseTradeProposal.put("offeredItem", ParseObject.createWithoutData("OwnedItem", offeredItemId));
        parseTradeProposal.saveInBackground();
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getSoughtItemId() {
        return soughtItemId;
    }

    public String getOfferedItemId() {
        return offeredItemId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setSoughtItemId(String soughtItemId) {
        this.soughtItemId = soughtItemId;
    }

    public void setOfferedItemId(String offeredItemId) {
        this.offeredItemId = offeredItemId;
    }


}
