package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/17/2022
 */
public class Comments {

    @NonNull
    private String id;
    @NonNull
    private String comment;
    private long date;
    @NonNull
    private String userId;
    @NonNull
    private String idProduct;

    public Comments() {
        id = "";
        comment = "";
        userId = "";
        idProduct = "";
        date = 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comments)) return false;
        Comments comments = (Comments) o;
        return getDate() == comments.getDate() && getId().equals(comments.getId()) && getComment().equals(comments.getComment()) && getUserId().equals(comments.getUserId()) && getIdProduct().equals(comments.getIdProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getComment(), getDate(), getUserId(), getIdProduct());
    }
}
