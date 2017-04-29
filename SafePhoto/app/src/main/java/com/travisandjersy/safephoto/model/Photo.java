package com.travisandjersy.safephoto.model;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class Photo extends Object {

    public String name;
    public boolean isPrivate;

    public Photo(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

}
