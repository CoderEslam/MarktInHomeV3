package com.doubleclick.marktinhome.Database.ChatListDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

import java.util.List;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
@Dao
public interface ChatListDao {

    @Insert
    void insert(ChatList chatList);

    @Update
    void update(ChatList chatList);

    @Delete
    void delete(ChatList chatList);

    @Query("DELETE FROM ChatList")
        // to delete all data in table
    void deleteAllData();

    @Query("SELECT * FROM ChatList  ORDER BY time ASC")
    List<ChatList> getList();

    @Query("SELECT * FROM ChatList WHERE id==:id")
    LiveData<ChatList> getUserById(String id);

}
