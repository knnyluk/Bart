package com.internet.bart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 11/28/14.
 */
public class AvailableItem implements Parcelable {

    public static final String ITEM_TO_TRADE_FOR = "item_to_trade_for";

    private String objectId, name, title, fullDescription;
    private com.internet.bart.models.Owner owner;
//    private Date createdAt, updatedAt;

    public static List<AvailableItem> fromJSONString(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject rawJsonObject = (JsonObject)parser.parse(jsonString);
        JsonArray jsonArrayOfAvailableItems = rawJsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AvailableItem>>(){}.getType();


        System.out.println(gson.fromJson(jsonArrayOfAvailableItems,listType));


        return gson.fromJson(jsonArrayOfAvailableItems,listType);
    }

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getOwnerId() {
        return owner.getObjectId();
    }

//    public Owner getOwner() {
//        return owner;
//    }

    public String toString() {
        return getOwnerId();
    }

    public AvailableItem() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.objectId);
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.fullDescription);
        dest.writeParcelable(this.owner, flags);
    }

    private AvailableItem(Parcel in) {
        this.objectId = in.readString();
        this.name = in.readString();
        this.title = in.readString();
        this.fullDescription = in.readString();
        this.owner = in.readParcelable(Owner.class.getClassLoader());
    }

    public static final Creator<AvailableItem> CREATOR = new Creator<AvailableItem>() {
        public AvailableItem createFromParcel(Parcel source) {
            return new AvailableItem(source);
        }

        public AvailableItem[] newArray(int size) {
            return new AvailableItem[size];
        }
    };
}
