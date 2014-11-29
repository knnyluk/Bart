package com.internet.bart.interfaces;

import org.json.JSONObject;

/**
 * Created on 11/28/14.
 */
public interface ParseApiCallback {

    public void onSuccess(String response);
    public void onError();

}
