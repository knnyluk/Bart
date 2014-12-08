package com.internet.bart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 11/28/14.
 */
public class AvailableItem implements Parcelable {

    public static final String ITEM_TO_TRADE_FOR = "item_to_trade_for";
    private String objectId, name, title, fullDescription;
    private User owner;
    private Photo thumbnailPhoto;


    public static List<AvailableItem> fromJSONString(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject rawJsonObject = (JsonObject)parser.parse(jsonString);
        JsonArray jsonArrayOfAvailableItems = rawJsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AvailableItem>>(){}.getType();


//        System.out.println(gson.fromJson(jsonArrayOfAvailableItems,listType));


        return gson.fromJson(jsonArrayOfAvailableItems,listType);
    }

    public static void writeToParse(String itemName, String itemTitle, String itemFullDescription, ParseFile thumbnail) {
        ParseObject availableItem = new ParseObject("OwnedItem");
        availableItem.put("name", itemName);
        availableItem.put("title", itemTitle);
        availableItem.put("fullDescription", itemFullDescription);
        availableItem.put("owner", ParseUser.getCurrentUser());
        availableItem.put("thumbnailPhoto", thumbnail);
        availableItem.saveInBackground();
    }

    public AvailableItem() {
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

    public String getThumbnailUrl() {
        if (hasImage()) {
            return thumbnailPhoto.getUrl();
        } else {
            return "";
        }
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getOwnerId() {
        return owner.getObjectId();
    }

    public boolean hasImage() {
        if (thumbnailPhoto != null) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return getName();
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
        dest.writeParcelable(this.owner, 0);
        dest.writeParcelable(this.thumbnailPhoto, 0);
    }

    private AvailableItem(Parcel in) {
        this.objectId = in.readString();
        this.name = in.readString();
        this.title = in.readString();
        this.fullDescription = in.readString();
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.thumbnailPhoto = in.readParcelable(Photo.class.getClassLoader());
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
