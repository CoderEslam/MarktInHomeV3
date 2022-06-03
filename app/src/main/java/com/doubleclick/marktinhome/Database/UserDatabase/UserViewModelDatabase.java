package com.doubleclick.marktinhome.Database.UserDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.doubleclick.marktinhome.Database.ChatDatabase.ChatDatabaseReopsitory;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/3/2022
 */
public class UserViewModelDatabase extends AndroidViewModel {

    private UserDatabaseReopsitory mRepositry;
    private Application context;
    private List<User> mAllUsers;

    public UserViewModelDatabase(@NonNull Application application) {
        super(application);
        mRepositry = new UserDatabaseReopsitory(application);
        mAllUsers = mRepositry.getAllChat();
    }

    public void insert(User user) {
        mRepositry.insert(user);
    }

    public void update(User user) { //done
        mRepositry.update(user);
    }

    public void delete(User user) {
        mRepositry.delete(user);
    }

    public void deleteAll() {
        mRepositry.deleteAllUsers();
    }


    public List<User> getAllUsers() {
        return mAllUsers;
    }

}
