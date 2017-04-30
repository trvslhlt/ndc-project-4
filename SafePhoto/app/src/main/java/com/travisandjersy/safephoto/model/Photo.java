package com.travisandjersy.safephoto.model;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class Photo extends Object {

    public String name;
    public String localFilepath;
    public String remoteURI;
    public boolean isPrivate;

    public Photo() {}

    public Photo(String name, String localFilepath, boolean isPrivate) {
        this.name = name;
        this.localFilepath = localFilepath;
        this.isPrivate = isPrivate;
    }

}
