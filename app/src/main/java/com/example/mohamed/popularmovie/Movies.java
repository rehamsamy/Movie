package com.example.mohamed.popularmovie;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable{
   // int idTable;
    String title;
    String poster;
    String overview;
    String vote;
    String release;
    String id;


    public Movies(String title, String poster, String overview, String vote, String release,String id) {

        //this.idTable=idTable;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.vote = vote;
        this.release = release;
        this.id=id;
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




    public Movies(Parcel in) {
        poster = in.readString();
        overview = in.readString();
        release = in.readString();
        title = in.readString();
         id= in.readString();
        vote = in.readString();

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(poster);
        out.writeString(title);
        out.writeString(overview);
        out.writeString(release);
        out.writeString(vote);
        out.writeString(id);



    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

}
