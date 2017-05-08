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

    private List<Photo> publicPhotos = new ArrayList<Photo>();
    private List<Photo> privatePhotos = new ArrayList<Photo>();
    private static PhotoService shared = new PhotoService();

    /*
    public static void uploadPhoto(Photo photo, final UploadResult result) {
        CloudStorageService.uploadFile(photo.name, new CloudStorageService.UploadResult() {
            @Override
            public void didComplete(boolean success, String message) {
                result.didComplete(success);
            }
        });
    }
    */

    public static List<String>getPublicPhotoNames() {
        List<String> photoNames = new ArrayList<String>();
        List<Photo> photos = getPublicPhotos();
        for (Photo photo : photos) {
            photoNames.add(photo.description);
        }
        return photoNames;
    }

    public static List<Photo> getPublicPhotos() {
        return shared.publicPhotos;
    }

    public static List<String>getPrivatePhotoNames() {
        List<String> photoNames = new ArrayList<String>();
        List<Photo> photos = getPrivatePhotos();
        for (Photo photo : photos) {
            photoNames.add(photo.description);
        }
        return photoNames;
    }

    public static List<Photo> getPrivatePhotos() {
        return shared.privatePhotos;
    }

    /*
    private List<Photo> getPublicPhotos() {
        List<Photo> privatePhotos = new ArrayList<Photo>();
        for (Photo photo : photos) {
            if (!photo.isPrivate) {
                privatePhotos.add(photo);
            }
        }
        return privatePhotos;
    }
    */

    public static void setPublicPhotos(List<Photo> photos) {
        shared.publicPhotos = photos;
    }
    public static void setPrivatePhotos(List<Photo> photos) { shared.privatePhotos = photos; }




}
