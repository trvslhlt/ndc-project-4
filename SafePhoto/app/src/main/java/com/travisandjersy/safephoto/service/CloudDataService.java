package com.travisandjersy.safephoto.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by trvslhlt on 4/30/17.
 */

public class CloudDataService {

    private static CloudDataService shared = new CloudDataService();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public interface UploadResult {
        public void didComplete(boolean success, String downloadURI, String message);
    }

    public static void uploadObject(Object object) {
        DatabaseReference rootReference = shared.database.getReference();
        DatabaseReference reference = rootReference.child("junk");

        DatabaseReference newPostRef = reference.push();
        newPostRef.setValue(object);
    }

}
