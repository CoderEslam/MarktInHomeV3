package com.doubleclick.marktinhome.Database.ChatListDatabase;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.doubleclick.marktinhome.Database.UserDatabase.UserDao;
import com.doubleclick.marktinhome.Database.UserDatabase.UserDatabase;
import com.doubleclick.marktinhome.Database.UserDatabase.UserDatabaseReopsitory;
import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
public class ChatListDatabaseRepository {

    private ChatListDao chatListDao;
    private LiveData<List<ChatList>> getAllUser;

    public ChatListDatabaseRepository(Application application) {
        ChatListDatabase db = ChatListDatabase.getInstance(application);
        chatListDao = db.EntitiesDAO();
        getAllUser = chatListDao.getList();
    }


    //insert
    public void insert(ChatList chatList) {
        new InsertAsyncTask(chatListDao).execute(chatList);
    }

    //delete
    public void delete(ChatList chatList) {
        new DeleteAsyncTask(chatListDao).execute(chatList);
    }

    public LiveData<User> getUserById(String id) {
        return getUserById(id);
    }

    //update
    public void update(ChatList chatList) { //done

        new UpdatetAsyncTask(chatListDao).execute(chatList);

    }

    //getAllWords
    public LiveData<List<ChatList>> getAllChatList() {
        return getAllUser;
    }

    //deleteAllWords
    public void deleteAllUsers() {

        new DeleteAllAsyncTask(chatListDao).execute();

    }

    private static class InsertAsyncTask extends AsyncTask<ChatList, Void, Void> {

        private ChatListDao chatListDao;

        public InsertAsyncTask(ChatListDao chatListDao) {
            this.chatListDao = chatListDao;
        }

        @Override
        protected Void doInBackground(ChatList... chatLists) {
            try {
                chatListDao.insert(chatLists[0]);
            } catch (Exception e) {
                Log.e("ExceptionInsert", e.getMessage());
            }

            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ChatList, Void, Void> {

        private ChatListDao chatListDao;

        public DeleteAsyncTask(ChatListDao chatListDao) {
            this.chatListDao = chatListDao;
        }

        @Override
        protected Void doInBackground(ChatList... chatLists) {
            try {
                chatListDao.delete(chatLists[0]);
            } catch (Exception e) {
                Log.e("ExceptionDelete", e.getMessage());
            }
            return null;
        }
    }

    private static class UpdatetAsyncTask extends AsyncTask<ChatList, Void, Void> {

        private ChatListDao chatListDao;

        public UpdatetAsyncTask(ChatListDao chatListDao) {
            this.chatListDao = chatListDao;
        }

        @Override
        protected Void doInBackground(ChatList... chatLists) {
            try {
                chatListDao.update(chatLists[0]);
            } catch (Exception e) {
                Log.e("ExceptionUpdate", e.getMessage());
            }
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private ChatListDao chatListDao;

        public DeleteAllAsyncTask(ChatListDao chatListDao) {
            this.chatListDao = chatListDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                chatListDao.deleteAllData();
            } catch (Exception e) {
                Log.e("ExceptionDeleteAll", e.getMessage());
            }


            return null;
        }
    }

}
