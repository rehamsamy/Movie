package com.example.mohamed.popularmovie;


import android.view.Display;

import java.util.List;

public class Model {

    String mtitle, mposter, moverview, mvote, mrelease;




    public Model(String title, String poster_image, String overview, String vote_average, String realse_data) {

        mtitle = title;
        mposter = poster_image;
        moverview = overview;
        mvote = vote_average;
        mrelease = realse_data;



    }



    public String getMtitle() {
        return mtitle;
    }

    public String getMposter() {
        return mposter;
    }

    public String getMoverview() {
        return moverview;
    }

    public String getMvote() {
        return mvote;
    }

    public String getMrelease() {
        return mrelease;
    }


}