package com.doubleclick.marktinhome.Model;

import java.io.Serializable;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class ChildCategory implements Serializable {


    private String name;
    private String image;
    private String pushId;
    private String PushIdParents;

    public String getPushIdParents() {
        return PushIdParents;
    }

    public void setPushIdParents(String pushIdParents) {
        PushIdParents = pushIdParents;
    }


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "ChildCategory{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", pushId='" + pushId + '\'' +
                ", PushIdParents='" + PushIdParents + '\'' +
                '}';
    }
}
