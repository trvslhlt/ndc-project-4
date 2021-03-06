package com.travisandjersy.safephoto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.travisandjersy.safephoto.service.AuthenticationService;
import com.travisandjersy.safephoto.service.CloudDataService;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;
    private BroadcastReceiver broadcastReceiver;

    // MARK: Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if (!AuthenticationService.isSignedIn())
            navigation.getMenu().findItem(R.id.navigation_private_photos).setEnabled(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        AuthenticationService.enable(getApplicationContext());
        CloudDataService.enable(getApplicationContext());
        transitionToFragment(new PublicPhotosFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateFragmentData();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(getString(R.string.intent_photos_updated)));

        //transitionToFragment(new PublicPhotosFragment());

        //AuthenticationService.enable(getApplicationContext());
        //CloudDataService.enable(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        AuthenticationService.disable();
        CloudDataService.disable();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    // MARK: Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_photos:
                    if (currentFragment instanceof PublicPhotosFragment) { return false; }
                    transitionToFragment(new PublicPhotosFragment());
                    return true;
                case R.id.navigation_private_photos:
                    if (currentFragment instanceof PrivatePhotosFragment) { return false; }
                    transitionToFragment(new PrivatePhotosFragment());
                    return true;
                case R.id.navigation_upload:
                    if (currentFragment instanceof UploadFragment) { return false; }
                    transitionToFragment(new UploadFragment());
                    return true;
                case R.id.navigation_search:
                    if (currentFragment instanceof SearchFragment) { return false; }
                    transitionToFragment(new SearchFragment());
                    return true;
                case R.id.navigation_signin:
                    if (currentFragment instanceof SigninFragment) { return false; }
                    transitionToFragment(new SigninFragment());
                    return true;
            }
            return false;
        }

    };

    private void updateFragmentData() {
        if (currentFragment == null) {
            return;
        }
        if (currentFragment instanceof PublicPhotosFragment) {
            PublicPhotosFragment photosFragment = (PublicPhotosFragment) currentFragment;
            photosFragment.update();
        }
        if (currentFragment instanceof PrivatePhotosFragment) {
            PrivatePhotosFragment photosFragment = (PrivatePhotosFragment) currentFragment;
            photosFragment.update();
        }
    }

    private void transitionToFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.remove(currentFragment);
        }
        transaction.add(R.id.content, newFragment).commit();
        currentFragment = newFragment;
    }

}
