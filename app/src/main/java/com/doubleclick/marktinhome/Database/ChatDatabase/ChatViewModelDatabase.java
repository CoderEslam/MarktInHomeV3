package com.doubleclick.marktinhome.Database.ChatDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.doubleclick.marktinhome.Model.Chat;

import java.util.List;

/**
 * Created By Eslam Ghazy on 12/11/2021
 */
public class ChatViewModelDatabase extends AndroidViewModel {

    private ChatDatabaseReopsitory mRepositry;
    private Application context;
    private LiveData<List<Chat>> mAllChats;

    public ChatViewModelDatabase(@NonNull Application application) {
        super(application);
        mRepositry = new ChatDatabaseReopsitory(application);
    }

    public void getAllData(String myID, String FriendID) {
        mRepositry.Load(myID, FriendID);
        mAllChats = mRepositry.getAllChat();
    }

    public void insert(Chat chat) {
        mRepositry.insert(chat);
    }

    public void update(Chat chat) { //done
        mRepositry.update(chat);
    }

    public void delete(Chat chat) {
        mRepositry.delete(chat);
    }


    public void deleteAll() {
        mRepositry.deleteAllChats();
    }

//    public LiveData<Chat> getLastMassage(String id,String myId) {
//        return mRepositry.getLastMassage(id,myId);
//    }
    public LiveData<Chat> getLasRowMassage(String id,String myId) {
        return mRepositry.getLastRowMessage(myId,id);
    }


    public LiveData<List<Chat>> getAllChats() {
        return mAllChats;
    }

    public List<Chat> getListData(String myID,String FirendID){
        return  mRepositry.getListData(myID,FirendID);
    }


}
