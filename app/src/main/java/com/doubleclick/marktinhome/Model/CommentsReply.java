package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 4/28/2022
 */
public class CommentsReply {

    private String id;
    private String replyComment;
    private String groupId;
    private String commentId;
    private long time;
    private String userId;

    public CommentsReply() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CommentsReply{" +
                "id='" + id + '\'' +
                ", replyComment='" + replyComment + '\'' +
                ", groupId='" + groupId + '\'' +
                ", commentId='" + commentId + '\'' +
                ", time=" + time +
                ", userId='" + userId + '\'' +
                '}';
    }
}
