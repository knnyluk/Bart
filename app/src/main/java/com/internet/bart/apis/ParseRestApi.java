package com.internet.bart.apis;

import android.net.Uri;
import android.os.AsyncTask;

import com.internet.bart.interfaces.ParseApiCallback;
import com.parse.ParseUser;

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
    private static final String TRADE_PROPOSAL_CLASSNAME = "TradeProposal";

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

    public void getOwnedItems(String ownerUserId, ParseApiCallback parseApiCallback) {
        new LoadDataInBackground(parseApiCallback).execute(getUsersItemsUri(ownerUserId));
    }

    public void getTradesOfferedToUser(String recipientUserId, ParseApiCallback parseApiCallback) {
        new LoadDataInBackground(parseApiCallback).execute(getTradesOfferedToUri(recipientUserId));
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

    private Uri.Builder getRootUriBuilder() {
        return new Uri.Builder()
                .scheme("https")
                .authority(ROOT_URL)
                .appendPath(API_VERSION)
                .appendPath(CLASSES_PATH);
    }

    private Uri getAvailableItemsUri() {
        return getRootUriBuilder()
                .appendPath(OWNED_ITEM_CLASSNAME)
                .appendQueryParameter("order", "-createdAt")
                .build();
    }

    private String getUserQueryParameter(String ownerUserId, String pointerFieldName) {
        JSONObject userQueryParameter = new JSONObject();
        try {
            JSONObject innerJsonObject = new JSONObject();
            innerJsonObject.put("__type", "Pointer");
            innerJsonObject.put("className", "_User");
            innerJsonObject.put("objectId", ownerUserId);

            userQueryParameter.put(pointerFieldName, innerJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userQueryParameter.toString();
    }

    private Uri getUsersItemsUri(String ownerUserId) {
        return getRootUriBuilder()
                .appendPath(OWNED_ITEM_CLASSNAME)
                .appendQueryParameter("where", getUserQueryParameter(ownerUserId, "owner"))
                .build();
    }

    private Uri getTradesOfferedToUri(String recipientUserId) {
        return getRootUriBuilder()
                .appendPath(TRADE_PROPOSAL_CLASSNAME)
                .appendQueryParameter("where", getUserQueryParameter(recipientUserId, "recipient"))
                .appendQueryParameter("include", "offeredItem,soughtItem")
                .build();
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
