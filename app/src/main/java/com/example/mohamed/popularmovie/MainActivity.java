
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

public class MainActivity extends AppCompatActivity  implements  MovieAdapter.ItemClick {
    List<String> pop;
    public MovieAdapter adapter;
    public static final String TAG = MainActivity.class.getName();

    RecyclerView earthquakeListView;
    private static final String responce = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
    TextView text;
    ArrayList<Model> words;
          Model model;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        earthquakeListView =  findViewById(R.id.list);

               // Utils.pop(pop);

      //  Log.v(TAG,"oooooooooooooo"+pop.get(1));

         words=new ArrayList<>();
        adapter = new MovieAdapter(getApplicationContext(), words,this);


        Eath eath=new Eath();
           eath.execute(responce);

           earthquakeListView.setAdapter(adapter);

//        ConnectivityManager manager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
//
//        // If there is a network connection, fetch data
//        if (networkInfo != null && networkInfo.isConnected()) {
//
//            Eath eath=new Eath();
//            eath.execute(responce);
//
//        }else{
//           // text.setText("there is no connection check internet");
//        }
//


        Log.v(TAG,"added data to adapter");





    }



    @Override
    public void onItemClick(Model model) {
    }



    public class Eath extends AsyncTask<String,Void,List<Model>>implements MovieAdapter.ItemClick  {



        @Override
        protected List<Model> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {

                Log.v(TAG,"url is null");
                return null;
            }

            List<Model> result = null;
            result = Utils.fetchinputs(urls[0]);
            Log.v(TAG,"sssssssssssssssssss do in background"+result.get(0));
            return result;

        }



        @Override
        protected void onPostExecute(List<Model> words) {
            super.onPostExecute(words);
//MovieAdapter.ItemClick c =MainActivity.ItemClick.class;
            adapter = new MovieAdapter(getApplicationContext(), words,this );


            //earthquakeListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //StaggeredGridLayoutManager manager=new G(2,1);

              earthquakeListView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
            earthquakeListView.setAdapter(adapter);

            if (words != null && !words.isEmpty()) {
                //  adapter.add

                Log.v(TAG,"add data to adapter"+words.get(2));

            }


        }

        @Override
        public void onItemClick(Model model) {
           // Context context=MainActivity.;
            String title=model.getMtitle();
            String overview=model.getMoverview();
            String release=model.getMrelease();
            String vote=model.getMvote();
            Intent intent=new Intent(getApplicationContext(),Detail.class);

            String path = model.getMposter();
            String value = "w185";
            String base_url = "http://image.tmdb.org/t/p/";
            final String full_url = base_url + value + "/" + path;

            intent.putExtra("t",title);
            intent.putExtra("p",full_url);
            intent.putExtra("o",overview);
            intent.putExtra("r",release);
            intent.putExtra("v",vote);
           // intent.putExtra("t",title);
            Log.v(TAG,"title is "+title);
            startActivity(intent);


        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        List<String> poppp = null;
//
//        Utils.pop(poppp);
        //Log.v(TAG,"pooooooooooooo"+poppp.get(7));
        int id=item.getItemId();
       if(id==R.id.populate){
          // compute()
         // Toast.makeText(getApplicationContext(),"ccccccc"+poppp.get(0),Toast.LENGTH_LONG).show();
       }

         return super.onOptionsItemSelected(item);
    }




}