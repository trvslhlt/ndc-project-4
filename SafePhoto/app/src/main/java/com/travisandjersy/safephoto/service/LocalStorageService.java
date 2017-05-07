package com.travisandjersy.safephoto.service;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trvslhlt on 5/7/17.
 */

public class LocalStorageService {

    private Map<String, Bitmap> images = new HashMap<String, Bitmap>();

    private static LocalStorageService shared = new LocalStorageService();

    private LocalStorageService() {}

    public static Bitmap getImageWithName(String name) {
        return shared.images.get(name);
    }

    public static void setImageWithName(String name, Bitmap image) {
        shared.images.put(name, image);
    }

}
