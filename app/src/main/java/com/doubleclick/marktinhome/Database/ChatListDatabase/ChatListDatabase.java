package com.doubleclick.marktinhome.Database.ChatListDatabase;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.doubleclick.marktinhome.Model.ChatList;
import com.doubleclick.marktinhome.Model.User;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
@Database(entities = {ChatList.class, User.class}, version = 1, exportSchema = false)
public abstract class ChatListDatabase extends RoomDatabase {

    private static ChatListDatabase instance;

    public abstract ChatListDao EntitiesChatListDAO();

    public abstract UserDao EntitiesUserDaoDAO();


    //Singlton
    public static synchronized ChatListDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext(),
                            ChatListDatabase.class, "ChatList-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
