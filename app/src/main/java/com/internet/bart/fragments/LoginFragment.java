package com.internet.bart.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.internet.bart.R;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created on 12/2/14.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText usernameField;
    private EditText passwordField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login_form, container ,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity hostingActivity = getActivity();

        usernameField = (EditText) hostingActivity.findViewById(R.id.username);
        passwordField = (EditText) hostingActivity.findViewById(R.id.password);

        Button loginButton = (Button) hostingActivity.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_button) {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            loginToParse(username, password);
        }
    }

    private void loginToParse(String username, String password) {
        try {
            ParseUser.logIn(username, password);

            Intent intent = new Intent();
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
