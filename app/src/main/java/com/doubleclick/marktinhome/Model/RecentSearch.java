package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 3/11/2022
 */
public class RecentSearch {

    private String recentSearch;


    public RecentSearch(String recentSearch) {
        this.recentSearch = recentSearch;
    }

    public RecentSearch() {
    }

    public String getRecentSearch() {
        return recentSearch;
    }

    public void setRecentSearch(String recentSearch) {
        this.recentSearch = recentSearch;
    }
    @Override
    public String toString() {
        return recentSearch;
    }



}
