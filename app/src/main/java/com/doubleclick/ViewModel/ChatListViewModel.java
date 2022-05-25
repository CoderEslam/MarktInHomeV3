package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Repository.ChatListRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/23/2022
 */
public class ChatListViewModel extends ViewModel implements UserInter {


    ChatListRepository chatListRepository = new ChatListRepository(this);
    private MutableLiveData<ArrayList<User>> mutableLiveData = new MutableLiveData<>();

    public ChatListViewModel() {
        chatListRepository.ChatList();
    }


    public LiveData<ArrayList<User>> myChatList() {
        return mutableLiveData;
    }


    @Override
    public void ItemUser(@Nullable User user) {

    }

    @Override
    public void AllUser(@Nullable ArrayList<User> users) {
        mutableLiveData.setValue(users);
    }

    @Override
    public void OnUserLisitner(@NonNull User user) {

    }

    @Override
    public void ItemUserInfoById(@Nullable User user) {

    }
}
