package com.example.mohamed.popularmovie.Review;

public class ReviewModel {
    String mreview;
    String mcontent;

    public ReviewModel(String review, String content) {
        mreview = review;
        mcontent = content;
    }


    public String getMreview() {
        return mreview;
    }

    public String getMcontent() {
        return mcontent;
    }
}
