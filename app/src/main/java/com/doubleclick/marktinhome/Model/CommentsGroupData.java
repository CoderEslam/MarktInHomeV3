package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 4/27/2022
 */
public class CommentsGroupData {

    private CommentsGroup commentsGroup;
    private User user;

    public CommentsGroupData(CommentsGroup commentsGroup, User user) {
        this.commentsGroup = commentsGroup;
        this.user = user;
    }

    public CommentsGroupData() {
    }

    public CommentsGroup getCommentsGroup() {
        return commentsGroup;
    }

    public void setCommentsGroup(CommentsGroup commentsGroup) {
        this.commentsGroup = commentsGroup;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
