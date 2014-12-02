package com.internet.bart.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.internet.bart.R;
import com.internet.bart.fragments.AvailableItemsListFragment;
import com.internet.bart.interfaces.FragmentController;
import com.parse.ParseUser;


public class MainActivity extends Activity implements FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AvailableItemsListFragment availableItemsListFragment = new AvailableItemsListFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentcontainer, availableItemsListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
            ParseUser.getCurrentUser().logOut();
            System.out.println("should be logged out");
            System.out.println(ParseUser.getCurrentUser());
        }
        return super.onOptionsItemSelected(item);
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
