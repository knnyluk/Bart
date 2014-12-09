package com.internet.bart.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.TradeOffersListFragment;
import com.internet.bart.interfaces.FragmentController;

/**
 * Created on 12/8/14.
 */
public class TradeOffersActivity extends Activity implements FragmentController {
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

    @Override
    public void changeFragment(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        fragmentTransaction.commit();
    }
}
