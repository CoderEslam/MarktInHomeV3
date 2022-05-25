package com.doubleclick.marktinhome.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/26/2022
 */
public class PostData implements Serializable {

    private PostsGroup postsGroup;
    private User user;


    public PostData(PostsGroup postsGroup, User user) {
        this.postsGroup = postsGroup;
        this.user = user;
    }

    public PostData() {
    }

    public PostsGroup getPostsGroup() {
        return postsGroup;
    }

    public void setPostsGroup(PostsGroup postsGroup) {
        this.postsGroup = postsGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PostData{" +
                "postsGroup=" + postsGroup +
                ", user=" + user +
                '}';
    }
}
