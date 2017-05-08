package com.travisandjersy.safephoto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travisandjersy.safephoto.service.AuthenticationService;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class SigninFragment extends Fragment {

    Button signInOrOutButton;
    Button signupButton;
    Button googleSignIn;
    EditText emailField;
    EditText passwordField;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signin_view, container, false);
        signInOrOutButton = (Button) view.findViewById(R.id.sign_in_or_out_button);
        signInOrOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AuthenticationService.isSignedIn()) {
                    signOut();
                } else {
                    signIn();
                }
            }
        });
        signupButton = (Button) view.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        emailField = (EditText) view.findViewById(R.id.sign_in_email_field);
        passwordField = (EditText) view.findViewById(R.id.sign_in_password_field);
        googleSignIn = (Button) view.findViewById(R.id.google_sign_in_button);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AuthenticationService.isSignedIn()) {
                    signOut();
                } else {
                    startActivity(new Intent(getActivity(), GoogleSignInActivity.class));
                }
            }
        });

        configureViewForAuthenticationState();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        configureViewForAuthenticationState();
    }

    private void enableUserActions(boolean enable) {
        signInOrOutButton.setEnabled(enable);
        signupButton.setEnabled(enable);
    }

    private void signOut() {
        AuthenticationService.signOut();
        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        navigation.getMenu().findItem(R.id.navigation_private_photos).setEnabled(false);
        configureViewForAuthenticationState();
    }

    private void signIn() {
        dismissKeyboard();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        enableUserActions(false);
        AuthenticationService.signIn(email, password, new AuthenticationService.Result() {
            @Override
            public void didComplete(boolean success, String message) {
                enableUserActions(true);
                showActionResult(success, message);
            }
        });
        Toast.makeText(
                getActivity(),
                getContext().getString(R.string.authentication_pending),
                Toast.LENGTH_LONG).show();
    }

    private void signup() {
        dismissKeyboard();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        enableUserActions(false);
        AuthenticationService.createUser(email, password, new AuthenticationService.Result() {
            @Override
            public void didComplete(boolean success, String message) {
                enableUserActions(true);
                showActionResult(success, message);
            }
        });
    }

    private void dismissKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = getActivity().getCurrentFocus();
        if(focus != null) {
            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showActionResult(boolean success, String message) {
        BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
        if (success)
            navigation.getMenu().findItem(R.id.navigation_private_photos).setEnabled(true);
        Toast.makeText(
                getActivity(),
                message,
                Toast.LENGTH_LONG).show();
        configureViewForAuthenticationState();
    }

    private void configureViewForAuthenticationState() {
        LinearLayout inputContainer = (LinearLayout) view.findViewById(R.id.sign_in_input_container);
        if (AuthenticationService.isSignedIn()) {
            signInOrOutButton.setText(R.string.authentication_signout);
            inputContainer.setVisibility(View.GONE);
            signupButton.setVisibility(View.GONE);
        } else {
            signInOrOutButton.setText(R.string.authentication_signin);
            inputContainer.setVisibility(View.VISIBLE);
            signupButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            configureViewForAuthenticationState();
        }
    }
}
