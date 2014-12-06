package com.internet.bart.activities;

import android.app.Activity;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.AvailableItemsListFragment;

/**
 * Created on 12/5/14.
 */
public class CreateAvailableItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_available_item);
//        AvailableItemsListFragment availableItemsListFragment = new AvailableItemsListFragment();
//        if (savedInstanceState == null) {
//            getFragmentManager().beginTransaction()
//                    .add(R.id.fragmentcontainer, availableItemsListFragment)
//                    .commit();
//        }
    }

}
