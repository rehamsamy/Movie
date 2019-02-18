
package com.example.mohamed.popularmovie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mohamed.popularmovie.Room.AppDatabase;
import com.example.mohamed.popularmovie.Room.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClick {

    private  MovieAdapter adapter;
    public static final String TAG = MainActivity.class.getName();

    RecyclerView recyclerView;
    private static String murl;


   public ArrayList<Model>words;
    ConnectivityManager manager;
    NetworkInfo networkInfo;
    AppDatabase database;
    List<Model> tasks;
    List<Model> newModels;
    int x;

       private static final String KEY="key";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        words = new ArrayList<>();


        database = AppDatabase.getmInstance(getApplicationContext());

        recyclerView = findViewById(R.id.list);
        //adapter.setTasks(words);
//        extractFovorite();
//        recyclerView.setAdapter(adapter);
        Log.v(TAG,"wordsss"+words.size());

        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            murl = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
            posters();

            recyclerView.setAdapter(adapter);

            Toast.makeText(getApplicationContext(), "Internet is connectet", Toast.LENGTH_LONG).show();
           // extractFovorite();

        } else {

              adapter=new MovieAdapter(this,words);
              recyclerView.setAdapter(adapter);
            Toast.makeText(getApplicationContext(), "Make sure that Internet is Connected", Toast.LENGTH_LONG).show();

        }


        Log.v(TAG, "added data to adapter");

        if (savedInstanceState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState((Parcelable) words);
        }

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//       // words=adapter.getTasks();
//
//        outState.putParcelableArrayList(KEY, words);
//
//      // x= adapter.get
//        outState.putInt("index",x);
//        Log.v("INSTANCE_STATE", "save");
//        super.onSaveInstanceState(outState);
//    }

//@Override
//    protected void onSaveInstanceState(Bundle state) {
//        super.onSaveInstanceState(state);
//
//        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
//
//        // Save list state
////     words=recyclerView.getLayoutManager().onSaveInstanceState();
////       // words = layoutManager.onSaveInstanceState();
////        state.putParcelable("key",  words);
//    }


//    protected void onSaveInstanceState(Bundle state) {
//        super.onSaveInstanceState(state);
//
//        // Save list state
//        words = recyclerView.getLayoutManager().onSaveInstanceState();
//        state.putParcelable("key", words);
//    }
//



    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if(state != null)
            words = state.getParcelable("key");
    }



    public  class Eath extends AsyncTask<String, Void, List<Model>> implements MovieAdapter.ItemClick {
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
                adapter = new MovieAdapter(getApplicationContext(), (ArrayList<Model>) words, this);
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
           // int idTable=model.getIdTable();

            //int idTable=model.getIdTable();


            Intent intent = new Intent(getApplicationContext(), Detail.class);

            String path = model.getPoster();
            String value = "w185";
            String base_url = "http://image.tmdb.org/t/p/";
            final String full_url = base_url + value + "/" + path;

            intent.putExtra("title_key", title);
            intent.putExtra("path_key", path);
            intent.putExtra("url_key", full_url);

            intent.putExtra("overview_key", overview);
            intent.putExtra("release_key", release);
            intent.putExtra("vote_key", vote);
            intent.putExtra("pop_id", id);
           // intent.putExtra("idTable_key", idTable);
           // intent.putExtra("id_table",idTable);
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
       } else {


            Toast.makeText(getApplicationContext(), " Disconnected", Toast.LENGTH_LONG).show();

        }

        return true;
    }





    @Override
    public void onItemClick(Model model) {

    }



    private void  extractFovorite() {
        tasks=new ArrayList<>();
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<Model>> fov = viewModel.getAllTasks();
        fov.observe(this, new Observer<List<Model>>() {
            @Override
            public void onChanged(@Nullable List<Model> models) {
                if(models==null){
                    Toast.makeText(getApplicationContext(),"favorite is empty",Toast.LENGTH_LONG).show();

                }else{
                    adapter.setTasks(models);

                }

            }
        });
        Log.v("MainActivity", "tasks is"+words.size());


    }



}



