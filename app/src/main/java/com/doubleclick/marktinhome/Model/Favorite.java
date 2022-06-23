package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class Favorite {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Favorite{" +
                "id='" + id + '\'' +
                '}';
    }
}
