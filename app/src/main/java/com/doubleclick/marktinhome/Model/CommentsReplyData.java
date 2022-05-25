package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 4/28/2022
 */
public class CommentsReplyData {

    private CommentsReply commentsReply;
    private User user;

    public CommentsReplyData(CommentsReply commentsReply, User user) {
        this.commentsReply = commentsReply;
        this.user = user;
    }

    public CommentsReplyData() {
    }

    public CommentsReply getCommentsReply() {
        return commentsReply;
    }

    public void setCommentsReply(CommentsReply commentsReply) {
        this.commentsReply = commentsReply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CommentsReplyData{" +
                "commentsReply=" + commentsReply +
                ", user=" + user +
                '}';
    }
}
