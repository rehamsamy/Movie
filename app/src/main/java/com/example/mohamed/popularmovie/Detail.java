package com.example.mohamed.popularmovie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    TextView title, overview, release, vote;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        release = (TextView) findViewById(R.id.release);
        vote = (TextView) findViewById(R.id.vote);
        poster = (ImageView) findViewById(R.id.poster);

        Intent intent = getIntent();

        String mtitle = intent.getStringExtra("title_key");
        String moverview = intent.getStringExtra("overview_key");
        String mrelease = intent.getStringExtra("release_key");
        String mvote = intent.getStringExtra("vote_key");
        String mposter = intent.getStringExtra("url_key");


        title.setText(mtitle);
        overview.setText(moverview);
        release.setText(mrelease);
        vote.setText(mvote);

        Picasso.with(this)
                .load(mposter)
                .into(poster);


    }
}
