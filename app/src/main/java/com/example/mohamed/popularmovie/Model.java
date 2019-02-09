package com.example.mohamed.popularmovie;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;
import android.view.Display;

import java.util.List;

@Entity(tableName = "task")
public class Model {

    @PrimaryKey(autoGenerate=true)
    int idTable;
    String title;
    String poster;
    String overview;
    String vote;
    String release;
     String id;



//String TAG= getClass().getSimpleName();



@Ignore
    public Model(String title, String poster, String overview, String vote, String release,String id) {

        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.vote = vote;
        this.release = release;
        this.id=id;



    }


    public Model(int idTable,String title, String poster, String overview, String vote, String release,String id) {

        this.idTable=idTable;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.vote = vote;
        this.release = release;
        this.id=id;



    }



    public int getIdTable() {
        return idTable;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote() {
        return vote;
    }

    public String getRelease() {
        return release;
    }

    public String getId() {
        return id;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setId(String id) {
        this.id = id;
    }
}