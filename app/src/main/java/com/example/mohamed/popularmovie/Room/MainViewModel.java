package com.example.mohamed.popularmovie.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mohamed.popularmovie.Model;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    LiveData<List<Model>> tasks;


    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database=AppDatabase.getmInstance(getApplication());
        tasks=database.daoTask().loadAllTasks();

    }

    public LiveData<List<Model>> getAllTasks(){
        return tasks;
    }
}
