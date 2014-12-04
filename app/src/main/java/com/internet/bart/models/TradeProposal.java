package com.internet.bart.models;

/**
 * Created on 12/4/14.
 */
public class TradeProposal {
    private String senderId, recipientId, soughtItemId, offeredItemId;

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
