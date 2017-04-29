package com.travisandjersy.safephoto.service;

import com.travisandjersy.safephoto.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class PhotoService {

    private List<Photo> photos = new ArrayList<Photo>();
    private static PhotoService shared = new PhotoService();

    public static List<String>getPhotoNames() {
        List<String> photoNames = new ArrayList<String>();
        List<Photo> photos =getPhotos();
        for (Photo photo : photos) {
            photoNames.add(photo.name);
        }
        return photoNames;
    }

    public static List<Photo> getPhotos() {
        if (shared.photos.isEmpty()) {
            shared.createPhotos();
        }

        if (AuthenticationService.isAuthenticated()) {
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

    private void createPhotos() {
        photos.add(new Photo("TravisPriavte", true));
        photos.add(new Photo("JersyPrivate", true));
        photos.add(new Photo("MarisaPublic", false));
    }

}
