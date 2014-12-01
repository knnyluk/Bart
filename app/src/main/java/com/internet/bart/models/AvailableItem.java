package com.internet.bart.models;

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
public class AvailableItem {
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
}
