package com.travisandjersy.safephoto.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class AuthenticationService extends Object {

    public interface Result {
        void didComplete(boolean success);
    }

    private static AuthenticationService shared = new AuthenticationService();
    private static String TAG = "AuthenticationService";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public static boolean isSignedIn() {
        return (FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    public static void enable(Context context) {
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

        if (shared.mAuth != null) {
            shared.mAuth.addAuthStateListener(shared.mAuthListener);
        } else {
            Log.d(TAG, "nothing");
        }

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

    public static void signIn(String email, String password, final Result result) {
        shared.mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                    result.didComplete(task.isSuccessful());
                }
            });
    }

    public static void signOut() {
        shared.mAuth.signOut();
    }

}
