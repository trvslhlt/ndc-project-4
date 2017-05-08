package com.travisandjersy.safephoto.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.travisandjersy.safephoto.R;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class AuthenticationService extends Object {

    public interface Result {
        void didComplete(boolean success, String message);
    }

    private static AuthenticationService shared = new AuthenticationService();
    private static String TAG = "AuthenticationService";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    public static boolean isSignedIn() {
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    public static String getUserUID() {
        if (isSignedIn())
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        else
            return "a";
    }

    public static void enable(Context context) {
        shared.context = context;
        shared.mAuth = FirebaseAuth.getInstance();
        shared.mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // don't think this is being called. but keeping code here until we figure this out
                // would like for all signin/out to funnel through a single callback
                Intent intent = new Intent(shared.context.getString(R.string.intent_photos_updated));
                LocalBroadcastManager.getInstance(shared.context).sendBroadcast(intent);
            }
        };
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
                    String message = null;
                    if (task.getException() != null) {
                        message = task.getException().getMessage();
                    }
                    result.didComplete(task.isSuccessful(), message);
                }
            });
    }

    public static void signIn(String email, String password, final Result result) {
        shared.mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String message = task.isSuccessful() ?
                            shared.context.getString(R.string.authentication_success) :
                            shared.context.getString(R.string.authentication_failure);
                    result.didComplete(task.isSuccessful(), message);

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(shared.context.getString(R.string.intent_photos_updated));
                        LocalBroadcastManager.getInstance(shared.context).sendBroadcast(intent);
                    }
                }
            });
    }



    public static void signOut() {
        shared.mAuth.signOut();
    }
}
