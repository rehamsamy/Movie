package com.example.mohamed.popularmovie.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.mohamed.popularmovie.Model;

import java.util.List;

@Dao
public interface DaoTask {

    @Insert
    void insertTask(Model model);


    @Query("SELECT * FROM task ORDER BY idTable")
    LiveData<List<Model>> loadAllTasks();






}
