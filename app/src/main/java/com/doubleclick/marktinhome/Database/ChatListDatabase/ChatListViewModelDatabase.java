package com.doubleclick.marktinhome.Database.ChatListDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.airbnb.lottie.L;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
public class ChatListViewModelDatabase extends AndroidViewModel {

    private ChatListDatabaseRepository mRepositry;
    private LiveData<List<ChatList>> mAllChatList;
    private LiveData<List<User>> userList;
    private LiveData<List<ChatListData>> chatListData;
    private LiveData<ChatListData> Limitation;

    public ChatListViewModelDatabase(@NonNull Application application) {
        super(application);
        mRepositry = new ChatListDatabaseRepository(application);
        mAllChatList = mRepositry.getAllChatList();
        userList = mRepositry.getUserList();
        chatListData = mRepositry.getChatListData();
        Limitation = mRepositry.getLimitation();
    }


    ////////////////////////////////////////CHATLIST//////////////////////////////////////////////
    public void insertChatList(ChatList chatList) {
        mRepositry.insertChatList(chatList);
    }

    public void updateChatList(ChatList chatList) {
        mRepositry.updateChatList(chatList);
    }

    public void deleteChatList(ChatList chatList) {
        mRepositry.deleteChatList(chatList);
    }


    public void deleteAllChatList() {
        mRepositry.deleteAllUsers();
    }

    public LiveData<List<ChatListData>> getChatListData() {
        return chatListData;
    }

    public LiveData<ChatListData> getLimitation(){
        return Limitation;
    }

    public LiveData<List<ChatList>> getAllChatList() {
        return mAllChatList;
    }

    ///////////////////////////////////////////////////CHATLIST///////////////////////////////////////


    ///////////////////////////////////////////USER/////////////////////////////////////////////////////


    public void insertUser(User user) {
        mRepositry.insertUser(user);
    }

    public void updateUser(User user) { //done
        mRepositry.updateUser(user);
    }

    public void deleteUser(User user) {
        mRepositry.deleteUser(user);
    }

    public LiveData<User> getUserById(String id) {
        return mRepositry.getUserById(id);
    }

    public void deleteAllUser() {
        mRepositry.deleteAllUsers();
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }


    /////////////////////////////////////////USER/////////////////////////////////////////////////////

}
