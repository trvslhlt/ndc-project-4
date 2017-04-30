package com.travisandjersy.safephoto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.travisandjersy.safephoto.model.Photo;
import com.travisandjersy.safephoto.service.AuthenticationService;
import com.travisandjersy.safephoto.service.CloudDataService;
import com.travisandjersy.safephoto.service.CloudStorageService;

public class MainActivity extends AppCompatActivity {

    private Fragment currentFragment;

    // MARK: Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuthenticationService.configure(getApplicationContext());

        Photo photo = new Photo("Photo Name", "local/Filepath", true);
        photo.remoteURI = "reomte/URI";
        CloudDataService.uploadObject(photo);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        transitionToFragment(new PhotosFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        AuthenticationService.enable();
        AuthenticationService.createUser("tlh99@cornell.edu", "bad_password", new AuthenticationService.Result() {
            @Override
            public void didComplete(boolean success) {
                int c = success ? Color.GREEN : Color.RED;
                currentFragment.getView().setBackgroundColor(c);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        AuthenticationService.disable();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_photos:
                    if (currentFragment instanceof PhotosFragment) { return false; }
                    transitionToFragment(new PhotosFragment());
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

    private void transitionToFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (currentFragment != null) {
                    transaction.remove(currentFragment);
                }
                transaction.add(R.id.content, newFragment)
                .commit();
        currentFragment = newFragment;
    }
}
