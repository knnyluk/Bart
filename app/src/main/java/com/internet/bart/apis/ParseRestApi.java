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


    public void getAvailableItems(ParseApiCallback parseApiCallback) {
        new loadDataInBackground(parseApiCallback).execute(getAvailableItemsUri());
    }

    private JSONObject getJSONObjectFromUri(Uri uri) throws IOException, JSONException {
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

        return new JSONObject(stringBuilder.toString());

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

    public class loadDataInBackground extends AsyncTask<Uri, Void, JSONObject> {

        ParseApiCallback parseApiCallback;

        private loadDataInBackground(ParseApiCallback parseApiCallback) {
            this.parseApiCallback = parseApiCallback;
        }

        @Override
        protected JSONObject doInBackground(Uri... uris) {
            try {
                System.out.println(getJSONObjectFromUri(uris[0]));
                return getJSONObjectFromUri(uris[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

//        @Override
//        protected void onPostExecute(JSONObject result) {
//            if (result != null) {
//                this.redditApiCallback.onSuccess(result);
//            } else {
//                this.redditApiCallback.onError();
//            }
//        }
    }
}
