package com.internet.bart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 12/4/14.
 */
public class TradeProposal implements Parcelable {

    public static final String[] STATUSES = {"Rejected", "Proposed", "Accepted"};
    public static final int STATUS_REJECTED = 0;
    public static final int STATUS_PROPOSED = 1;
    public static final int STATUS_ACCEPTED = 2;

    private String objectId;
    private User sender, recipient;
    private AvailableItem offeredItem, soughtItem;
    private int status;

    public String getObjectId() {
        return objectId;
    }

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
        return STATUSES[status];
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

//    public String toString() {
//        return soughtItem.toString();
//    }

    public void updateStatus(int statusCode) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProposal");
        final int sC = statusCode;
        System.out.println(sC);

        query.getInBackground(getObjectId(), new GetCallback<ParseObject>() {
            public void done(ParseObject gameScore, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    System.out.println(gameScore);
                    gameScore.put("status", sC);
                    try {
                        gameScore.save();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sender, 0);
        dest.writeParcelable(this.recipient, 0);
        dest.writeParcelable(this.offeredItem, 0);
        dest.writeParcelable(this.soughtItem, 0);
        dest.writeInt(this.status);
    }

    public TradeProposal() {
    }

    private TradeProposal(Parcel in) {
        this.sender = in.readParcelable(User.class.getClassLoader());
        this.recipient = in.readParcelable(User.class.getClassLoader());
        this.offeredItem = in.readParcelable(AvailableItem.class.getClassLoader());
        this.soughtItem = in.readParcelable(AvailableItem.class.getClassLoader());
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<TradeProposal> CREATOR = new Parcelable.Creator<TradeProposal>() {
        public TradeProposal createFromParcel(Parcel source) {
            return new TradeProposal(source);
        }

        public TradeProposal[] newArray(int size) {
            return new TradeProposal[size];
        }
    };
}
