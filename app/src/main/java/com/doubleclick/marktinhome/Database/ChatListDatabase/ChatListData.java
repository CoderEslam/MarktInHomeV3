package com.doubleclick.marktinhome.Database.ChatListDatabase;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

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
}
