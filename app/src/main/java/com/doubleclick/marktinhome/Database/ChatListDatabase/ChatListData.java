package com.doubleclick.marktinhome.Database.ChatListDatabase;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;
import java.util.Objects;

/**
 * Created By Eslam Ghazy on 6/6/2022
 */
public class ChatListData {

    @Embedded
    private User user;
    @Relation(parentColumn = "id", entityColumn = "id")
    private List<ChatList> chatList;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ChatList> getChatList() {
        return chatList;
    }

    public void setChatList(List<ChatList> chatList) {
        this.chatList = chatList;
    }

    @Override
    public String toString() {
        return "ChatListData{" +
                "user=" + user +
                ", chatList=" + chatList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatListData)) return false;
        ChatListData that = (ChatListData) o;
        return getUser().equals(that.getUser()) && getChatList().equals(that.getChatList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getChatList());
    }
}
