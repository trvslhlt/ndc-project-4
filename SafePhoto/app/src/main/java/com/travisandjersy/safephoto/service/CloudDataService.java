package com.travisandjersy.safephoto.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.travisandjersy.safephoto.R;
import com.travisandjersy.safephoto.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trvslhlt on 4/30/17.
 */


public class CloudDataService {

    private static String TAG = "CloudDataService";
    private static CloudDataService shared = new CloudDataService();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    private ValueEventListener eventListener;
    private Context context;

    public static void enable(final Context context) {
        shared.context = context;
        DatabaseReference rootReference = shared.database.getReference();
        shared.reference = rootReference.child("junk");
        shared.eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Photo> photos = new ArrayList<Photo>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Photo photo = ds.getValue(Photo.class);
                    photos.add(photo);
                }
                PhotoService.setPhotos(photos);
                Intent intent = new Intent(context.getString(R.string.intent_photos_updated));
                LocalBroadcastManager.getInstance(shared.context).sendBroadcast(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        shared.reference.addValueEventListener(shared.eventListener);
    }

    public static void disable() {
        if (shared.eventListener != null) {
            shared.reference.removeEventListener(shared.eventListener);
        }
    }

    public interface UploadResult {
        public void didComplete(boolean success, String downloadURI, String message);
    }

    public static void uploadObject(Object object) {
        DatabaseReference rootReference = shared.database.getReference();
        DatabaseReference reference = rootReference.child("junk");

        DatabaseReference newPostRef = reference.push();
        newPostRef.setValue(object);
    }

    public static void downloadObjects() {

    }

}
