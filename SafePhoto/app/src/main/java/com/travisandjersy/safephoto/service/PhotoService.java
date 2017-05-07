package com.travisandjersy.safephoto.service;

import android.os.AsyncTask;

import com.travisandjersy.safephoto.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class PhotoService {

    public interface UploadResult {
        public void didComplete(boolean success);
    }

    private List<Photo> photos = new ArrayList<Photo>();
    private static PhotoService shared = new PhotoService();

    public static void uploadPhoto(Photo photo, final UploadResult result) {
        CloudStorageService.uploadFile(photo.name, new CloudStorageService.UploadResult() {
            @Override
            public void didComplete(boolean success, String downloadURI, String message) {
                result.didComplete(success);
            }
        });
    }

    public static List<String>getPhotoNames() {
        List<String> photoNames = new ArrayList<String>();
        List<Photo> photos =getPhotos();
        for (Photo photo : photos) {
            photoNames.add(photo.name);
        }
        return photoNames;
    }

    public static List<Photo> getPhotos() {
        if (AuthenticationService.isSignedIn()) {
            return shared.photos;
        } else {
            return shared.getPublicPhotos();
        }
    }

    private List<Photo> getPublicPhotos() {
        List<Photo> privatePhotos = new ArrayList<Photo>();
        for (Photo photo : photos) {
            if (!photo.isPrivate) {
                privatePhotos.add(photo);
            }
        }
        return privatePhotos;
    }

    public static void setPhotos(List<Photo> photos) {
        shared.photos = photos;
    }



}
