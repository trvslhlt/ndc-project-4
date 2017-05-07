package com.travisandjersy.safephoto.service;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.travisandjersy.safephoto.model.Photo;

import java.io.File;

/**
 * Created by trvslhlt on 4/30/17.
 */

public class CloudStorageService {

    private static CloudStorageService shared = new CloudStorageService();
    private StorageReference mStorageRef;

    private CloudStorageService() {
        this.mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public interface UploadResult {
        public void didComplete(boolean success, String downloadURI, String message);
    }

    public static void uploadFile(String filename, final UploadResult result) {
        Uri file = Uri.fromFile(new File(localFilepath));
        StorageReference photosRef = shared.mStorageRef.child(filename);

        photosRef.putFile(file)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests")String downloadURI = taskSnapshot.getDownloadUrl().toString();
                    result.didComplete(true, downloadURI, null);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    result.didComplete(false, null, exception.getMessage());
                }
            });
    }

}
