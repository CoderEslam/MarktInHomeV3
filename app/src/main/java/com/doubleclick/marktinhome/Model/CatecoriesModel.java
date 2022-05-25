package com.doubleclick.marktinhome.Model;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 3/3/2022
 */
public class CatecoriesModel {

    private ArrayList<ChildCategory> childCategories;
    private String name;
    private boolean isExpandable;

    public CatecoriesModel(ArrayList<ChildCategory> childCategories, String name, boolean isExpandable) {
        this.childCategories = childCategories;
        this.name = name;
        this.isExpandable = isExpandable;
    }

    public CatecoriesModel() {
    }


    public ArrayList<ChildCategory> getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(ArrayList<ChildCategory> childCategories) {
        this.childCategories = childCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
