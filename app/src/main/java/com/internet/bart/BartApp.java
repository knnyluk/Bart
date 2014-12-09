package com.internet.bart;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class BartApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "OKUvImY1fCkgxWgKbzIoy0qm2EYJXY1VYqMTRdeY", "IZBzyHgMiIpzQtpRkdteNuERrecJyF809Siagq9Q");


        ParseUser.enableAutomaticUser();
//        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.

//        ParseACL.setDefaultACL(defaultACL, true);


        // Parse push notification subscription
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        if (ParseUser.getCurrentUser().getUsername() != null) {
            ParseInstallation.getCurrentInstallation().put("user", ParseUser.getCurrentUser());
        }
    }
}
