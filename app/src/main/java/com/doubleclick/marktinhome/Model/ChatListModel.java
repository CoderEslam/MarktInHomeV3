package com.doubleclick.marktinhome.Model;

import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Database.ChatListDatabase.ChatListData;
import com.doubleclick.marktinhome.Views.storyview.storyview.StoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Eslam Ghazy on 7/1/2022
 */
public class ChatListModel {

    public static final int STORIES = 200;
    public static final int USERS = 300;

    public UserInter onUser;
    private List<ChatListData> chatListData;
    private ArrayList<ArrayList<StoryModel>> storyModels;
    private int type;


    public ChatListModel(UserInter onUser, List<ChatListData> chatListData, int type) {
        this.onUser = onUser;
        this.chatListData = chatListData;
        this.type = type;
    }

    public ChatListModel(ArrayList<ArrayList<StoryModel>> storyModels, int type) {
        this.storyModels = storyModels;
        this.type = type;
    }

    public ChatListModel() {
    }

    public UserInter getOnUser() {
        return onUser;
    }

    public void setOnUser(UserInter onUser) {
        this.onUser = onUser;
    }

    public List<ChatListData> getChatListData() {
        return chatListData;
    }

    public void setChatListData(List<ChatListData> chatListData) {
        this.chatListData = chatListData;
    }

    public ArrayList<ArrayList<StoryModel>> getStoryModels() {
        return storyModels;
    }

    public void setStoryModels(ArrayList<ArrayList<StoryModel>> storyModels) {
        this.storyModels = storyModels;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
