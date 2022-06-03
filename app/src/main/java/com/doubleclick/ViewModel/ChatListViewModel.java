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
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> mutableLiveDataChanged = new MutableLiveData<>();
    private MutableLiveData<User> mutableLiveDataDeleted = new MutableLiveData<>();

    public ChatListViewModel() {
        chatListRepository.ChatList();
    }


    public LiveData<User> UserInserted() {
        return mutableLiveData;
    }

    public LiveData<User> UserChanged() {
        return mutableLiveDataChanged;
    }

    public LiveData<User> UserDeleted() {
        return mutableLiveDataDeleted;
    }


    @Override
    public void ItemUser(@Nullable User user) {
        mutableLiveData.setValue(user);

    }


    @Override
    public void OnUserLisitner(@NonNull User user) {

    }

    @Override
    public void ItemUserInfoById(@Nullable User user) {

    }

    @Override
    public void AllUser(@Nullable ArrayList<User> user) {

    }

    @Override
    public void ItemUserChanged(@NonNull User user) {
        mutableLiveDataChanged.setValue(user);
    }

    @Override
    public void ItemUserDeleted(@NonNull User user) {
        mutableLiveDataDeleted.setValue(user);
    }
}
