package com.doubleclick.marktinhome.Database.UserDatabase;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.doubleclick.marktinhome.Database.ChatDatabase.ChatDao;
import com.doubleclick.marktinhome.Database.ChatDatabase.ChatDatabase;
import com.doubleclick.marktinhome.Database.ChatDatabase.ChatDatabaseReopsitory;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/3/2022
 */
public class UserDatabaseReopsitory {

    private UserDao UserDao;
    private List<User> getAllUser;
    private User user;

    public UserDatabaseReopsitory(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        UserDao = db.EntitiesDAO();
        getAllUser = UserDao.getList();
    }


    //insert
    public void insert(User user) {
        new UserDatabaseReopsitory.InsertAsyncTask(UserDao).execute(user);
    }

    //delete
    public void delete(User user) {
        new UserDatabaseReopsitory.DeleteAsyncTask(UserDao).execute(user);
    }

    public LiveData<User> getUserById(String id) {
        return UserDao.getUserById(id);
    }

    //update
    public void update(User user) { //done

        new UserDatabaseReopsitory.UpdatetAsyncTask(UserDao).execute(user);

    }

    //getAllWords
    public List<User> getAllChat() {
        return getAllUser;
    }

    //deleteAllWords
    public void deleteAllUsers() {

        new UserDatabaseReopsitory.DeleteAllAsyncTask(UserDao).execute();

    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public InsertAsyncTask(UserDao userDao) {
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

    private static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public DeleteAsyncTask(UserDao userDao) {
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

    private static class UpdatetAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        public UpdatetAsyncTask(UserDao userDao) {
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

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;

        public DeleteAllAsyncTask(UserDao userDao) {
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

}
