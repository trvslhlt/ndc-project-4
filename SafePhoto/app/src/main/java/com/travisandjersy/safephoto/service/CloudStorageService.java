package com.travisandjersy.safephoto.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.travisandjersy.safephoto.model.Photo;

import java.io.File;
import java.util.List;

/**
 * Created by trvslhlt on 4/30/17.
 */

public class CloudStorageService {

    private static String TAG = "CloudStorageService";
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

    public static void downloadImagesForPhotos(List<Photo> photos) {
        for (final Photo photo : photos) {
            if (LocalStorageService.getImageWithFilepath(photo.localFilepath) != null) {
                continue;
            }
            StorageReference imageReference = shared.mStorageRef.child(photo.name);
            final long ONE_MEGABYTE = 1024 * 1024;
            imageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (image != null) {
                        LocalStorageService.setImageWithFilepath(photo.localFilepath, image);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(TAG, "Failure to download image");
                }
            });
        }
    }

}
