package com.example.mohamed.popularmovie.Room;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private Executor diskIO;
    private Executor networkIO;
    private  Executor mainThread;

    private static AppExecutor mInstance;

    public AppExecutor (Executor mdisk,Executor mnet,Executor mmain){
        diskIO=mdisk;
        networkIO=mnet;
        mainThread=mmain;
    }

    public static AppExecutor getInstance(){

        if(mInstance==null){
            mInstance=new AppExecutor(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainClass());


        }
        return mInstance;
    }



    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainClass implements Executor {
        Handler handler=new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);

        }
    }
}
