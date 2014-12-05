package com.internet.bart.receivers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.internet.bart.activities.MainActivity;
import com.parse.ParsePushBroadcastReceiver;

/**
 * Created on 12/4/14.
 */
public class ParseReceiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}