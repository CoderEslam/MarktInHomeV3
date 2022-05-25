package com.doubleclick.marktinhome.Model;

/**
 * Created By Eslam Ghazy on 3/18/2022
 */
public class ChatList {

    private String id;
    private long time;

    public ChatList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatList{" +
                "id='" + id + '\'' +
                ", time=" + time +
                '}';
    }
}
