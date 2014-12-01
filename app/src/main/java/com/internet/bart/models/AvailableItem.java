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
    private String objectId, name, title;
//    private Date createdAt, updatedAt;

    public static List<AvailableItem> fromJSONString(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject rawJsonObject = (JsonObject)parser.parse(jsonString);
        JsonArray jsonArrayOfAvailableItems = rawJsonObject.getAsJsonArray("results");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<AvailableItem>>(){}.getType();
        return gson.fromJson(jsonArrayOfAvailableItems,listType);
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
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
    }

    private AvailableItem(Parcel in) {
        this.objectId = in.readString();
        this.name = in.readString();
        this.title = in.readString();
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
