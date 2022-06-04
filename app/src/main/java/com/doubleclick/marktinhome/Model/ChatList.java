package com.doubleclick.marktinhome.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Created By Eslam Ghazy on 3/18/2022
 */
@Entity
public class ChatList {


    @NonNull
    @PrimaryKey()
    private String id;
    private long time;

    public ChatList() {
        id = "";
        time = 0;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatList{" +
                "id='" + id + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatList)) return false;
        ChatList chatList = (ChatList) o;
        return getId().equals(chatList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
