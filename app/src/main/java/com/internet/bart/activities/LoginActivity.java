package com.internet.bart.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.AvailableItemsListFragment;
import com.internet.bart.fragments.LoginFragment;
import com.internet.bart.interfaces.FragmentController;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created on 12/2/14.
 */
public class LoginActivity extends Activity implements FragmentController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment = new LoginFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentcontainer, loginFragment)
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

    public void signup(String username, String password) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        try {
            newUser.signUp();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
