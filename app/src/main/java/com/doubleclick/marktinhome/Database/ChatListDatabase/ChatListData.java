package com.doubleclick.marktinhome.Database.ChatListDatabase;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

/**
 * Created By Eslam Ghazy on 6/6/2022
 */
public class ChatListData {

    @Embedded
    private User user;
    @Relation(parentColumn = "id", entityColumn = "id")
    private ChatList chatList;


}
