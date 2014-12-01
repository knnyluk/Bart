package com.internet.bart.models;

import org.json.JSONArray;

import java.util.Date;
import java.util.List;

/**
 * Created on 11/28/14.
 */
public class AvailableItem {
    private String objectId, name, title;
//    private Date createdAt, updatedAt;

//    public static List<AvailableItem> fromJSONArray(JSONArray jsonArray) {
//
//    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
