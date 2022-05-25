package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 3/6/2022
 */
public class Rate {


    private String myId;
    private String productId;
    private String id;
    private String rate;


    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
                ", productId='" + productId + '\'' +
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
