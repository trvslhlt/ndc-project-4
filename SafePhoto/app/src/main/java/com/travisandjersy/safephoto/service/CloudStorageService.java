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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        public void didComplete(boolean success, String message);
    }

    public static void uploadFile(String name, final UploadResult result) {
        Uri file = Uri.fromFile(new File(name));
        StorageReference photosRef = shared.mStorageRef.child(name);

        photosRef.putFile(file)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    result.didComplete(true, null);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    result.didComplete(false, exception.getMessage());
                }
            });
    }


    public static void uploadImage(Bitmap image, String imageName, final UploadResult result) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference imageReference = shared.mStorageRef.child(imageName);
        UploadTask uploadTask = imageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                result.didComplete(false, exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                result.didComplete(true, null);
            }
        });
    }


    public static void downloadImagesForPhotos(List<Photo> photos) {
        for (final Photo photo : photos) {
            if (LocalStorageService.getImageWithName(photo.name) != null) {
                continue;
            }
            StorageReference imageReference = shared.mStorageRef.child(photo.name);
            final long ONE_MEGABYTE = 1024 * 1024;
            imageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (image != null) {
                        LocalStorageService.setImageWithName(photo.name, image);
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
