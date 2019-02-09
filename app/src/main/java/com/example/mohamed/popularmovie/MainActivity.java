
package com.example.mohamed.popularmovie;

import android.app.LoaderManager;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.mohamed.popularmovie.Room.AppDatabase;
import com.example.mohamed.popularmovie.Room.MainViewModel;

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
    List<Model> words;
    ConnectivityManager manager;
    NetworkInfo networkInfo;
    AppDatabase database;
    List<Model> tasks;
Model mode;
    List<Model> newModels;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getmInstance(getApplicationContext());

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
            String title = model.getTitle();
            String overview = model.getOverview();
            String release = model.getRelease();
            String vote = model.getVote();
            String id = model.getId();
            Intent intent = new Intent(getApplicationContext(), Detail.class);

            String path = model.getPoster();
            String value = "w185";
            String base_url = "http://image.tmdb.org/t/p/";
            final String full_url = base_url + value + "/" + path;

            intent.putExtra("title_key", title);
            intent.putExtra("url_key", full_url);
            intent.putExtra("overview_key", overview);
            intent.putExtra("release_key", release);
            intent.putExtra("vote_key", vote);
            intent.putExtra("pop_id", id);
            Log.v(TAG, "title is " + title);
            Log.v(TAG, "idddddddddddddddddd " + id);
            // Toast.makeText(MainActivity.this,"idddddddddddd"+id,Toast.LENGTH_LONG).show();
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

        if (id == R.id.top_rated && networkInfo != null && networkInfo.isConnected()) {
            murl = "http://api.themoviedb.org/3/movie/top_rated?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
            posters();

        } else if (id == R.id.populate && networkInfo != null && networkInfo.isConnected()) {

            murl = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
            posters();

        } else if (id == R.id.fovorite) {

            extractFovorite();

//            words=  database.daoTask().loadAllTasks();
//            if (words != null && !words.isEmpty()) {
//                adapter = new MovieAdapter(getApplicationContext(), words, this);
//                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//                recyclerView.setAdapter(adapter);
//                //adapter.setTasks(words);
//
//                Log.v("MainActivity","xxxxxxxxxxx"+words.size());
//
//
//            } else {
//                Log.v("MainActivity","nnnnnnnnnnnn"+words.size());
//
//                // words.clear();
//            }
//

        } else {

            text.setTextSize(30);
            text.setText("network offline");
            text.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), " Disconnected", Toast.LENGTH_LONG).show();

        }

        return true;
    }

    private void extractFovorite() {

        MainViewModel model= ViewModelProviders.of(this).get(MainViewModel.class);
        model.getAllTasks().observe(this, new Observer<List<Model>>() {
            @Override
            public void onChanged(@Nullable List<Model> models) {

                 for(int i=0;i<models.size();i++){
                    Model model=models.get(i);
                    String poster=model.getPoster();
                    String overview=model.getOverview();
                    String title=model.getTitle();
                    String release=model.getRelease();
                    String  vote=model.getVote();
                    String id=model.getId();
                     mode=new Model(title,poster,overview,vote,release,id);
                     models.add(mode);


                     Log.v("MainActivity","mmmmmmmmmmm"+models.size());

                 }
//               adapter=new MovieAdapter(getApplicationContext(),newModels,this);
//               recyclerView.setAdapter(adapter);
//                Log.v("MainActivity","tttttttttttttttt"+models.size());
                adapter.setTasks(models);


            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

       // extractFovorite();
        if (networkInfo != null && networkInfo.isConnected()) {
            murl = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
            posters();
            recyclerView.setAdapter(adapter);

            Toast.makeText(getApplicationContext(), "Internet is connectet", Toast.LENGTH_LONG).show();


        }

    }


    @Override
    public void onItemClick(Model model) {

    }





}