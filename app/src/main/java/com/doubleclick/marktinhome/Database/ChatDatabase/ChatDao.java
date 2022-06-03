package com.doubleclick.marktinhome.Database.ChatDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.doubleclick.marktinhome.Model.Chat;

import java.util.List;

/**
 * Created By Eslam Ghazy on 5/10/2022
 */
@Dao
public interface ChatDao {

    @Insert
    void insert(Chat chat);

    @Update
    void update(Chat chat);

    @Delete
    void delete(Chat chat);

    @Query("DELETE FROM Chat")
        // to delete all data in table
    void deleteAllData();

    @Query("SELECT * FROM Chat  WHERE ((sender==:myID AND receiver ==:friendID) OR (sender==:friendID AND receiver ==:myID)) ORDER BY date ASC")
    LiveData<List<Chat>> getAllChat(String myID, String friendID);


    @Query("SELECT * FROM Chat  WHERE ((sender==:myID AND receiver == :friendID) OR (sender==:friendID AND receiver ==:myID)) ORDER BY date DESC LIMIT 1")
    LiveData<List<Chat>> GetChat(String myID, String friendID);

    @Query("SELECT * FROM Chat  WHERE (((sender==:myID AND receiver == :friendID) OR (sender==:friendID AND receiver ==:myID))) ORDER BY date ASC")
    List<Chat> getList(String myID, String friendID);

//    @Query("SELECT * FROM Chat  WHERE (sender ==:friendID AND receiver == :myID) ORDER BY date DESC LIMIT 1")
//    LiveData<Chat> getLastMassege(String friendID, String myID);

    @Query("SELECT * FROM Chat  WHERE ((sender==:myID AND receiver == :friendID) OR (sender==:friendID AND receiver ==:myID)) ORDER BY date DESC LIMIT 1")
    LiveData<Chat> getLastRowMessage(String friendID, String myID);

}
