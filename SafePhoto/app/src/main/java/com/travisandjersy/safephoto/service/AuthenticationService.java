package com.travisandjersy.safephoto.service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.travisandjersy.safephoto.R;

import java.util.Set;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class AuthenticationService extends Object {

    private static AuthenticationService shared = new AuthenticationService();
    private Context context;

    public static boolean isAuthenticated() {
        return (getAuthenticationToken() != null);
    }

    public static void setContext(Context context) {
        shared.context = context;
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
