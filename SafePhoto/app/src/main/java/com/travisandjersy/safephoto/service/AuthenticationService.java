package com.travisandjersy.safephoto.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.travisandjersy.safephoto.R;

import java.util.Set;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class AuthenticationService extends Object {

    public interface Result {
        public void didComplete(boolean success);
    }

    private static AuthenticationService shared = new AuthenticationService();
    private static String TAG = "AuthenticationService";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static boolean isAuthenticated() {
        return (getAuthenticationToken() != null);
    }

    public static void setConfiguration(Context context) {
        shared.context = context;
        shared.mAuth = FirebaseAuth.getInstance();
        shared.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public static void enable() {
        shared.mAuth.addAuthStateListener(shared.mAuthListener);
    }

    public static void disable() {
        if (shared.mAuthListener != null) {
            shared.mAuth.removeAuthStateListener(shared.mAuthListener);
        }
    }

    public static void createUser(String email, String password, final Result result) {
        shared.mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                    result.didComplete(task.isSuccessful());
                }
            });
    }

    public static void setAuthenticationToken(String token) {
        SharedPreferences preferences = shared.getPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(shared.getAuthenticationTokenKey(), token);
        editor.apply();
    }

    public static String getAuthenticationToken() {
        SharedPreferences preferences = shared.getPreferences();
        return preferences.getString(shared.getAuthenticationTokenKey(), null);
    }

    private SharedPreferences getPreferences() {
        String authenticationFileKey = shared.context.getResources().getString(R.string.authentication_file);
        return shared.context.getSharedPreferences(authenticationFileKey, Context.MODE_PRIVATE);
    }

    private String getAuthenticationTokenKey() {
        return shared.context.getResources().getString(R.string.authentication_token);
    }

}
