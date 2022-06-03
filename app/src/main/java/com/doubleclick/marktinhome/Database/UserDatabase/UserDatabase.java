package com.doubleclick.marktinhome.Database.UserDatabase;

import android.app.Application;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.doubleclick.marktinhome.Database.ChatDatabase.ChatDao;
import com.doubleclick.marktinhome.Database.ChatDatabase.ChatDatabase;
import com.doubleclick.marktinhome.Model.Chat;
import com.doubleclick.marktinhome.Model.User;

/**
 * Created By Eslam Ghazy on 6/3/2022
 */
@Database(entities = User.class, version = 1)
public abstract class UserDatabase extends RoomDatabase {


    private static UserDatabase instance;

    public abstract UserDao EntitiesDAO();

    //Singlton
    public static synchronized UserDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext(),
                            UserDatabase.class, "User-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
