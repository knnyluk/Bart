package com.internet.bart.activities;

import android.app.Activity;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.TradeOffersListFragment;

/**
 * Created on 12/8/14.
 */
public class TradeOffersActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_fragholder);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentcontainer, new TradeOffersListFragment())
                    .commit();
        }
    }
}
