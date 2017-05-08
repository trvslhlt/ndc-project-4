package com.travisandjersy.safephoto.model;

import java.util.UUID;

/**
 * Created by trvslhlt on 4/29/17.
 */

public class Photo extends Object {

    public String name;
    public String description;
    public boolean isPrivate;
    public String uploadBy;

    public Photo() {}

    public Photo(String description, boolean isPrivate, String by) {
        this.name = UUID.randomUUID().toString();
        this.description = description;
        this.isPrivate = isPrivate;
        this.uploadBy = by;
    }

}
