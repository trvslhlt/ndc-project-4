package com.travisandjersy.safephoto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class PhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this is where "extras" are retrieved
//        Intent intent = getIntent();
//        String value = intent.getStringExtra("key"); //if it's a string you stored.
//        Log.d("PhotosActivity");
        this.findViewById(android.R.id.content).setBackgroundColor(Color.BLUE);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }
}
