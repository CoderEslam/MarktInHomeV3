package com.doubleclick.marktinhome.Database.ChatListDatabase;

import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.doubleclick.marktinhome.Database.UserDatabase.UserDao;
import com.doubleclick.marktinhome.Database.UserDatabase.UserDatabase;

/**
 * Created By Eslam Ghazy on 6/4/2022
 */
public abstract class ChatListDatabase extends RoomDatabase {

    private static ChatListDatabase instance;

    public abstract ChatListDao EntitiesDAO();

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
