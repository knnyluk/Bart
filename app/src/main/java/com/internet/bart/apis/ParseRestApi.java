package com.internet.bart.apis;

import android.net.Uri;
import android.os.AsyncTask;

import com.internet.bart.interfaces.ParseApiCallback;

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
    private static final String APPLICATION_ID = "OKUvImY1fCkgxWgKbzIoy0qm2EYJXY1VYqMTRdeY";
    private static final String REST_API_HEADER_KEY = "X-Parse-REST-API-Key";
    private static final String REST_API_KEY = "IqS68IyWoDaHjeVnLW71exFQ4YLuZcQGXnCJf9YZ";

    private static final String ROOT_URL = "api.parse.com";
    private static final String API_VERSION = "1";
    private static final String CLASSES_PATH = "classes";
    private static final String OWNED_ITEM_CLASSNAME = "OwnedItem";

    private static ParseRestApi parseRestApi;

    public static ParseRestApi getParseRestApi() {
        if (parseRestApi == null) {
            parseRestApi = new ParseRestApi();
        }

        return parseRestApi;
    }

    public void getAvailableItems(ParseApiCallback parseApiCallback) {
        new LoadDataInBackground(parseApiCallback).execute(getAvailableItemsUri());
    }

    public void getOwnedItems(ParseApiCallback parseApiCallback) {
        new LoadDataInBackground(parseApiCallback).execute(getUsersItemsUri());
    }

    private String getJSONStringFromUri(Uri uri) throws IOException, JSONException {
        URLConnection httpUrlConnection = new URL(uri.toString()).openConnection();
        httpUrlConnection.setRequestProperty(APPLICATION_ID_HEADER_KEY, APPLICATION_ID);
        httpUrlConnection.setRequestProperty(REST_API_HEADER_KEY, REST_API_KEY);

        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        int bytesRead;
        StringBuilder stringBuilder = new StringBuilder();

        while ((bytesRead = bufferedInputStream.read()) != -1) {
            stringBuilder.append((char)bytesRead);
        }
        bufferedInputStream.close();

        return stringBuilder.toString();

    }

    private Uri getAvailableItemsUri() {
        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority(ROOT_URL)
                .appendPath(API_VERSION)
                .appendPath(CLASSES_PATH)
                .appendPath(OWNED_ITEM_CLASSNAME)
                .appendQueryParameter("order", "-createdAt")
                .build();

        System.out.println(getUsersItemsUri());

        return uri;
    }

    private Uri getUsersItemsUri() {
        JSONObject ownerQueryParameter = new JSONObject();
        try {
            JSONObject innerJsonObject = new JSONObject();
            innerJsonObject.put("__type", "Pointer");
            innerJsonObject.put("className", "_User");
            innerJsonObject.put("objectId", "SSgrt5knzi");

            ownerQueryParameter.put("owner", innerJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority(ROOT_URL)
                .appendPath(API_VERSION)
                .appendPath(CLASSES_PATH)
                .appendPath(OWNED_ITEM_CLASSNAME)
                .appendQueryParameter("where", ownerQueryParameter.toString())
                .build();
        return uri;
    }

    public class LoadDataInBackground extends AsyncTask<Uri, Void, String> {

        ParseApiCallback parseApiCallback;

        private LoadDataInBackground(ParseApiCallback parseApiCallback) {
            this.parseApiCallback = parseApiCallback;
        }

        @Override
        protected String doInBackground(Uri... uris) {
            try {
                return getJSONStringFromUri(uris[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                this.parseApiCallback.onSuccess(result);
            } else {
                this.parseApiCallback.onError();
            }
        }
    }
}
