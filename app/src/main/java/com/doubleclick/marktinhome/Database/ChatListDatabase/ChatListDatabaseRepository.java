package com.doubleclick.marktinhome.Database.ChatListDatabase;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
public class ChatListDatabaseRepository {

    private ChatListDao chatListDao;
    private UserDao userDao;
    private LiveData<List<ChatList>> getAllChatList;
    private LiveData<List<User>> userList;
    private LiveData<List<ChatListData>> chatListData;

    public ChatListDatabaseRepository(Application application) {
        ChatListDatabase db = ChatListDatabase.getInstance(application);
        chatListDao = db.EntitiesChatListDAO();
        userDao = db.EntitiesUserDaoDAO();
        getAllChatList = chatListDao.getChatList();
        userList = userDao.getUserList();
        chatListData = chatListDao.getChatListWithUser();
    }


    ///////////////////////////////////ChatList///////////////////////////////////////
    //insert
    public void insertChatList(ChatList chatList) {
        new InsertChatListAsyncTask(chatListDao).execute(chatList);
    }

    //delete
    public void deleteChatList(ChatList chatList) {
        new DeleteChatListAsyncTask(chatListDao).execute(chatList);
    }


    //update
    public void updateChatList(ChatList chatList) { //done

        new UpdatetChatListAsyncTask(chatListDao).execute(chatList);

    }

    public LiveData<List<ChatList>> getAllChatList() {
        return getAllChatList;
    }

    public LiveData<List<ChatListData>> getChatListData() {
        return chatListData;
    }

    public void deleteAllChatList() {
        new DeleteAllChatListAsyncTask(chatListDao).execute();
    }

    private static class InsertChatListAsyncTask extends AsyncTask<ChatList, Void, Void> {

        private ChatListDao chatListDao;

        public InsertChatListAsyncTask(ChatListDao chatListDao) {
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

    private static class DeleteChatListAsyncTask extends AsyncTask<ChatList, Void, Void> {

        private ChatListDao chatListDao;

        public DeleteChatListAsyncTask(ChatListDao chatListDao) {
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

    private static class UpdatetChatListAsyncTask extends AsyncTask<ChatList, Void, Void> {

        private ChatListDao chatListDao;

        public UpdatetChatListAsyncTask(ChatListDao chatListDao) {
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

    private static class DeleteAllChatListAsyncTask extends AsyncTask<Void, Void, Void> {

        private ChatListDao chatListDao;

        public DeleteAllChatListAsyncTask(ChatListDao chatListDao) {
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

    ///////////////////////////////////ChatList///////////////////////////////////////

    /////////////////////////////////////////USER///////////////////////////


    //insert
    public void insertUser(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    //delete
    public void deleteUser(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<User> getUserById(String id) {
        return userDao.getUserById(id);
    }

    //update
    public void updateUser(User user) { //done

        new UpdatetUserAsyncTask(userDao).execute(user);

    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    //deleteAllWords
    public void deleteAllUsers() {

        new DeleteAllUserAsyncTask(userDao).execute();

    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            try {
                userDao.insert(users[0]);
            } catch (Exception e) {
                Log.e("ExceptionInsert", e.getMessage());
            }

            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            try {
                userDao.delete(users[0]);
            } catch (Exception e) {
                Log.e("ExceptionDelete", e.getMessage());
            }
            return null;
        }
    }

    private static class UpdatetUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public UpdatetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            try {
                userDao.update(users[0]);
            } catch (Exception e) {
                Log.e("ExceptionUpdate", e.getMessage());
            }
            return null;
        }
    }

    private static class DeleteAllUserAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;

        public DeleteAllUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                userDao.deleteAllData();
            } catch (Exception e) {
                Log.e("ExceptionDeleteAll", e.getMessage());
            }


            return null;
        }
    }


    ////////////////////////////////////////USER////////////////////////////

}
