package com.travisandjersy.safephoto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.travisandjersy.safephoto.service.AuthenticationService;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class SigninFragment extends Fragment {

    Button signInOrOutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signin_view, container, false);
        signInOrOutButton = (Button) view.findViewById(R.id.sign_in_or_out);
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
        configureViewForAuthenticationState();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void signOut() {
        AuthenticationService.signOut();
        configureViewForAuthenticationState();
    }

    private void signIn() {
        AuthenticationService.signIn("tlh99@cornell.edu", "bad_password", new AuthenticationService.Result() {
            @Override
            public void didComplete(boolean success, String message) {
                showSignIn(success);
            }
        });
        Toast.makeText(
                getActivity(),
                getContext().getString(R.string.authentication_pending),
                Toast.LENGTH_LONG).show();

    }

    private void showSignIn(boolean success) {
        String message = success ? "You're in!" : "It didn't work";
        Toast.makeText(
                getActivity(),
                message,
                Toast.LENGTH_LONG).show();
        configureViewForAuthenticationState();
    }

    private void configureViewForAuthenticationState() {
        if (AuthenticationService.isSignedIn()) {
            signInOrOutButton.setText(R.string.authentication_signout);
        } else {
            signInOrOutButton.setText(R.string.authentication_signin);
        }
    }
}
