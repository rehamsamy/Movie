package com.example.mohamed.popularmovie.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


import com.example.mohamed.popularmovie.*;


@Database(entities = {Model.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mInstance;
    private static String DATABASE_NAME="tasks";

    public static AppDatabase getmInstance(Context context){
        if(mInstance==null){
            mInstance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,AppDatabase.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return mInstance;
    }


    public abstract DaoTask daoTask();


}


