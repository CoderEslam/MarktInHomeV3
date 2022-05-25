package com.doubleclick.marktinhome.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created By Eslam Ghazy on 3/1/2022
 */
public class ParentCategory implements Serializable  {

    private String name;
    private String image;
    private String pushId;
    private String order;

    public HashMap<String, Object> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String, Object> children) {
        this.children = children;
    }

    private HashMap<String,Object> children;
    private boolean isOpen = false;



    public String getPushId() {
        return pushId;
    }
    public void setPushId(String pushId) {
        this.pushId = pushId;
    }


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
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


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    @Override
    public String toString() {
        return "ParentCategory{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", pushId='" + pushId + '\'' +
                ", childCategories=" + children +
                ", isOpen=" + isOpen +
                '}';
    }


}
