package com.doubleclick.marktinhome.Database.ChatListDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/3/2022
 */
@Dao
public interface UserDao {

    @Insert()
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User")
        // to delete all data in table
    void deleteAllData();

    @Query("SELECT * FROM User")
    LiveData<List<User>> getUserList();

    @Query("SELECT * FROM User WHERE id==:id")
    @Transaction
    LiveData<User> getUserById(String id);

}
