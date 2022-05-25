package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class Comments {

    private String image;
    private String id;
    private float rateStar;
    private String comment;
    private long date;
    private String userName;
    private String idProduct;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRateStar() {
        return rateStar;
    }

    public void setRateStar(float rateStar) {
        this.rateStar = rateStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }
}
