package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Created By Eslam Ghazy on 5/31/2022
 */
public class CommentsProductData {

    @NonNull
    private Comments comments;
    @NonNull
    private User user;

    public CommentsProductData(Comments comments, User user) {
        this.comments = comments;
        this.user = user;
    }

    public CommentsProductData() {
        comments = new Comments();
        user = new User();
    }

    @NonNull
    public Comments getComments() {
        return comments;
    }

    public void setComments(@NonNull Comments comments) {
        this.comments = comments;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    @NonNull
    @Override
    public String toString() {
        return "CommentsProductData{" +
                "comments=" + comments +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentsProductData)) return false;
        CommentsProductData that = (CommentsProductData) o;
        return getComments().getId().equals(that.getComments().getId()) && getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getComments(), getUser());
    }
}
