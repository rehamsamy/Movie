
package com.example.mohamed.popularmovie;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.popularmovie.Model;
import com.example.mohamed.popularmovie.MovieAdapter;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClick {

    public MovieAdapter adapter;
    public static final String TAG = MainActivity.class.getName();

    RecyclerView recyclerView;
    private static String murl;

    TextView text;
    ArrayList<Model> words;
    ConnectivityManager manager;
    NetworkInfo networkInfo;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        words = new ArrayList<>();


        text = (TextView) findViewById(R.id.connect);

         manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            murl = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
            posters();
            recyclerView.setAdapter(adapter);

            Toast.makeText(getApplicationContext(), "Internet is connectet", Toast.LENGTH_LONG).show();


        } else {


            text.setText("Internet Disconnected");
            text.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "Make sure that Internet is Connected", Toast.LENGTH_LONG).show();

        }


        Log.v(TAG, "added data to adapter");
    }


    @Override
    public void onItemClick(Model model) {
    }

    public class Eath extends AsyncTask<String, Void, List<Model>> implements MovieAdapter.ItemClick {
        @Override
        protected List<Model> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {

                Log.v(TAG, "url is null");
                Toast.makeText(getApplicationContext(), "Make sure Internet Is connected", Toast.LENGTH_LONG).show();
                return null;
            } else {
                List<Model> result = null;
                result = Utils.fetchinputs(urls[0]);

                return result;
            }

        }


        @Override
        protected void onPostExecute(List<Model> words) {
            super.onPostExecute(words);
            if (words != null && !words.isEmpty()) {
                adapter = new MovieAdapter(getApplicationContext(), words, this);
               recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                recyclerView.setAdapter(adapter);

            } else {
               // words.clear();
            }


        }

        @Override
        public void onItemClick(Model model) {
            String title = model.getMtitle();
            String overview = model.getMoverview();
            String release = model.getMrelease();
            String vote = model.getMvote();
            Intent intent = new Intent(getApplicationContext(), Detail.class);

            String path = model.getMposter();
            String value = "w185";
            String base_url = "http://image.tmdb.org/t/p/";
            final String full_url = base_url + value + "/" + path;

            intent.putExtra("title_key", title);
            intent.putExtra("url_key", full_url);
            intent.putExtra("overview_key", overview);
            intent.putExtra("release_key", release);
            intent.putExtra("vote_key", vote);
            Log.v(TAG, "title is " + title);
            startActivity(intent);

        }

    }


    public void posters() {
        new Eath().execute(murl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.top_rated:
                if (networkInfo != null && networkInfo.isConnected()) {
                    murl = "http://api.themoviedb.org/3/movie/top_rated?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
                    posters();

                }else {
                   text.setText("network offline");
                    text.setVisibility(View.VISIBLE);

                    Toast.makeText(getApplicationContext(), "Intenet is Disconnected", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.populate:
                murl = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
                posters();
                break;
        }

        return true;
    }
}