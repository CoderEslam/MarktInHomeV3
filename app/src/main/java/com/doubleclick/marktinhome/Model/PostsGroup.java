package com.doubleclick.marktinhome.Model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created By Eslam Ghazy on 4/25/2022
 */
public class PostsGroup implements Comparable<PostsGroup> , Serializable {

    private String id;
    private String adminId;
    private long time;
    private String text;
    private String type;
    private String meme;
    private String groupId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public static Comparator<PostsGroup> postsGroupComparator = new Comparator<PostsGroup>() {
        @Override
        public int compare(PostsGroup o1, PostsGroup o2) {
            return (int) (o1.getTime() - o2.getTime());
        }

    };

    @Override
    public int compareTo(PostsGroup o) {
        return (int) (this.time - o.getTime());
    }

    public String getMeme() {
        return meme;
    }

    public void setMeme(String meme) {
        this.meme = meme;
    }

    @Override
    public String toString() {
        return "PostsGroup{" +
                "id='" + id + '\'' +
                ", adminId='" + adminId + '\'' +
                ", time=" + time +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                ", meme='" + meme + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
