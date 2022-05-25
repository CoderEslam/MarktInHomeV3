package com.doubleclick.marktinhome.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class ClassificationPC implements Serializable {

    private boolean isExpendable;
    private String ParentPushId;
    private String image;
    private ArrayList<ChildCategory> childCategory;
    private String name;

    public ClassificationPC(ArrayList<ChildCategory> childCategory, String name) {
        this.childCategory = childCategory;
        this.name = name;
    }

    public ClassificationPC(ArrayList<ChildCategory> childCategory, String name, String image) {
        this.childCategory = childCategory;
        this.name = name;
        this.image = image;
    }

    public ClassificationPC(ArrayList<ChildCategory> childCategory, String name, String image, boolean isExpendable) {
        this.childCategory = childCategory;
        this.name = name;
        this.image = image;
        this.isExpendable = isExpendable;
    }

    public ClassificationPC(ArrayList<ChildCategory> childCategory, String name, String image, boolean isExpendable, String parentPushId) {
        this.isExpendable = isExpendable;
        ParentPushId = parentPushId;
        this.image = image;
        this.childCategory = childCategory;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChildCategory> getChildCategory() {
        return childCategory;
    }

    public void setChildCategory(ArrayList<ChildCategory> childCategory) {
        this.childCategory = childCategory;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public boolean isExpendable() {
        return isExpendable;
    }

    public void setExpendable(boolean expendable) {
        isExpendable = expendable;
    }


    @Override
    public String toString() {
        return "ClassificationPC{" +
                "childCategory=" + childCategory +
                ", name='" + name + '\'' +
                '}';
    }

    public String getParentPushId() {
        return ParentPushId;
    }

    public void setParentPushId(String parentPushId) {
        ParentPushId = parentPushId;
    }
}
