package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class Rate {


    private String myId;
    private String id;
    private String rate;


    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "myId='" + myId + '\'' +
                ", id='" + id + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
