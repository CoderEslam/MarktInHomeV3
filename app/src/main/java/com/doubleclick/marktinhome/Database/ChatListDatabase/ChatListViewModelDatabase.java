package com.doubleclick.marktinhome.Database.ChatListDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.doubleclick.marktinhome.Database.UserDatabase.UserDatabaseReopsitory;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
public class ChatListViewModelDatabase extends AndroidViewModel {

    private ChatListDatabaseRepository mRepositry;
    private Application context;
    private List<ChatList> mAllUsers;

    public ChatListViewModelDatabase(@NonNull Application application) {
        super(application);
        mRepositry = new ChatListDatabaseRepository(application);
        mAllUsers = mRepositry.getAllChatList();
    }


    public void insert(ChatList chatList) {
        mRepositry.insert(chatList);
    }

    public void update(ChatList chatList) {
        mRepositry.update(chatList);
    }

    public void delete(ChatList chatList) {
        mRepositry.delete(chatList);
    }

    public LiveData<User> getUserById(String id) {
        return mRepositry.getUserById(id);
    }

    public void deleteAll() {
        mRepositry.deleteAllUsers();
    }


    public List<ChatList> getAllUsers() {
        return mAllUsers;
    }


}
