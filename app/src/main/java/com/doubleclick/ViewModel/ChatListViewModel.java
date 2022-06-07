package com.doubleclick.ViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.ChatListInter;
import com.doubleclick.UserInter;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;
import com.doubleclick.marktinhome.Repository.ChatListRepository;

import java.util.ArrayList;

/**
 * Created By Eslam Ghazy on 4/23/2022
 */
public class ChatListViewModel extends ViewModel implements ChatListInter {


    ChatListRepository chatListRepository = new ChatListRepository(this);
    private MutableLiveData<ChatList> mutableLiveDataChatListInsert = new MutableLiveData<>();
    private MutableLiveData<ChatList> mutableLiveDataChatListUpdate = new MutableLiveData<>();
    private MutableLiveData<ChatList> mutableLiveDataChatListDeleted = new MutableLiveData<>();

    public ChatListViewModel() {
        chatListRepository.ChatList();
    }

    public LiveData<ChatList> ChatListInserted() {
        return mutableLiveDataChatListInsert;
    }

    public LiveData<ChatList> ChatListUpdate() {
        return mutableLiveDataChatListUpdate;
    }

    public LiveData<ChatList> ChatListDeleted() {
        return mutableLiveDataChatListDeleted;
    }

    @Override
    public void insert(@NonNull ChatList chatList) {
        mutableLiveDataChatListInsert.setValue(chatList);
    }

    @Override
    public void update(@NonNull ChatList chatList) {
        mutableLiveDataChatListUpdate.setValue(chatList);
    }

    @Override
    public void delete(@NonNull ChatList chatList) {
        mutableLiveDataChatListDeleted.setValue(chatList);
    }
}
