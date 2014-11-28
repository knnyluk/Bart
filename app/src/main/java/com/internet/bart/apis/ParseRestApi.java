package com.internet.bart.apis;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created on 11/28/14.
 */

public class ParseRestApi {

    private static final String APPLICATION_ID_HEADER_KEY = "X-Parse-Application-Id";
    private static final String REST_API_HEADER_KEY = "X-Parse-REST-API-Key";
    private static final String APPLICATION_ID = "OKUvImY1fCkgxWgKbzIoy0qm2EYJXY1VYqMTRdeY";
    private static final String REST_API_KEY = "IqS68IyWoDaHjeVnLW71exFQ4YLuZcQGXnCJf9YZ";
    private static final String API_VERSION = "1";
    private static final String CLASSES_PATH = "classes";
    private static final String OWNED_ITEM_CLASSNAME = "OwnedItem";
    private static final String ROOT_URL = "api.parse.com";
//    private String getRootURL() {
//        return APPLICATION_ID + "\u003A" + "javascript-key" + "\u003D" + JAVASCRIPT_API_KEY + "@api.parse.com";
//    }

    public JSONObject getAvailableItemsJSON() {
        return getJSONObjectFromUri(getAvailableItemsUri());
    }

    public void testMethod() {
        new getAvailableItemsAsync().execute(getAvailableItemsUri());
    }

    private JSONObject getJSONObjectFromUri(Uri uri) {

        StringBuilder stringBuilder = null;

        try {
            URLConnection httpUrlConnection = new URL("https://api.parse.com/1/classes/OwnedItem?order=-name").openConnection();
            httpUrlConnection.setRequestProperty(APPLICATION_ID_HEADER_KEY, APPLICATION_ID);
            httpUrlConnection.setRequestProperty(REST_API_HEADER_KEY, REST_API_KEY);
            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            int bytesRead;
            stringBuilder = new StringBuilder();

            while ((bytesRead = bufferedInputStream.read()) != -1) {
                stringBuilder.append((char)bytesRead);
            }
            System.out.println("sout is working");
            System.out.println(new JSONObject(stringBuilder.toString()));
            return new JSONObject(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Uri getAvailableItemsUri() {
        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority(ROOT_URL)
                .appendPath(API_VERSION)
                .appendPath(CLASSES_PATH)
                .appendPath(OWNED_ITEM_CLASSNAME)
                .build();
        return uri;
    }

    public class getAvailableItemsAsync extends AsyncTask<Uri, Void, Void> {

        @Override
        protected Void doInBackground(Uri... uris) {
            getJSONObjectFromUri(uris[0]);
            return null;
        }
    }
}
