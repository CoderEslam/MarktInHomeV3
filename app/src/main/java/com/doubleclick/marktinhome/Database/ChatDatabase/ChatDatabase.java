package com.doubleclick.marktinhome.Database.ChatDatabase;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.doubleclick.marktinhome.Model.Chat;

/**
 * Created By Eslam Ghazy on 12/11/2021
 */
@Database(entities = Chat.class, version = 1)
public abstract class ChatDatabase extends RoomDatabase {

    private static ChatDatabase instance;

    public abstract ChatDao EntitiesDAO();

    //Singlton
    public static synchronized ChatDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext(),
                    ChatDatabase.class, "Chat-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
