package com.internet.bart.activities;

import android.app.Activity;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.CreateAvailableItemFragment;

/**
 * Created on 12/5/14.
 */
public class CreateAvailableItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_fragholder);

        CreateAvailableItemFragment createAvailableItemFragment = new CreateAvailableItemFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentcontainer, createAvailableItemFragment)
                    .commit();
        }
    }

}
