package com.travisandjersy.safephoto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment currentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (currentFragment instanceof PhotosFragment) {
                        return false;
                    }
                    transitionToFragment(new PhotosFragment());
                    return true;
                case R.id.navigation_dashboard:
                    if (currentFragment instanceof UploadFragment) {
                        return false;
                    }
                    transitionToFragment(new UploadFragment());
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        transitionToFragment(new PhotosFragment());
    }

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
