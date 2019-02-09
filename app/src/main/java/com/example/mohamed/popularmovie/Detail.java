package com.example.mohamed.popularmovie;

import android.app.Notification;
import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.popularmovie.Review.ReviewAdapter;
import com.example.mohamed.popularmovie.Review.ReviewModel;
import com.squareup.picasso.Picasso;
import com.example.mohamed.popularmovie.Utilites.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.example.mohamed.popularmovie.Room.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Detail extends AppCompatActivity {
    ReviewAdapter reviewAdapter;
    ListView listView;
    TextView title, overview, release, vote;
    ImageView poster;
 public    List<ModelId> keys;

 Button fovorite;

    String url;
    AppDatabase database;

    String mposter ,mtitle,moverview,mrelease,mvote,mid ;

    ListView listReview;
    Adapter adapter;
//    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        database = AppDatabase.getmInstance(getApplicationContext());


        fovorite = (Button) findViewById(R.id.fovorite);

        // setCheckBox();

        listView = (ListView) findViewById(R.id.list);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        release = (TextView) findViewById(R.id.release);
        vote = (TextView) findViewById(R.id.vote);
        poster = (ImageView) findViewById(R.id.poster);

        Intent intent = getIntent();

        mtitle = intent.getStringExtra("title_key");
        moverview = intent.getStringExtra("overview_key");
        mrelease = intent.getStringExtra("release_key");
        mvote = intent.getStringExtra("vote_key");
        mposter = intent.getStringExtra("url_key");
        mid = intent.getStringExtra("pop_id");
        Toast.makeText(Detail.this, "idddddddddddd" + mid, Toast.LENGTH_LONG).show();


        url = "http://api.themoviedb.org/3/movie/" + mid + "/videos?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";

        title.setText(mtitle);
        overview.setText(moverview);
        release.setText(mrelease);
        vote.setText(mvote);

        Picasso.with(this).load(mposter).into(poster);

        new Task().execute(url);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = keys.get(position).getmKey();

                String url = "https://www.youtube.com/watch?v=" + key;

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(intent);

                // ModelId model=parent.getItemAtPosition(position);


            }
        });


        final String reviewUrl = "http://api.themoviedb.org/3/movie/" + mid + "/reviews?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";

        reviewTask task = new reviewTask();
        task.execute(reviewUrl);




        fovorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Model model=new Model(mtitle,mposter,moverview,mvote,mrelease,mid);

                AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        database.daoTask().insertTask(model);
                        Log.v("Detail","id"+mid+mrelease);

                    }
                });



//                database.daoTask().insertTask(model);
//                Toast.makeText(getApplicationContext(),"id"+mid+mrelease,Toast.LENGTH_LONG).show();
//
//                Log.v("Detail","id"+mid+mrelease);


            }
        });


    }

   public class Task extends AsyncTask<String ,Void,List<ModelId>>{



       @Override
       protected List <ModelId> doInBackground(String... urls) {
           if (urls.length < 1 || urls[0] == null) {

               Toast.makeText(getApplicationContext(), "Make sure Internet Is connected", Toast.LENGTH_LONG).show();
               return null;
           } else {
               List<ModelId> result = null;
               result = FindMid.fetchinputs(urls[0]);

               return result;
           }
       }

       @Override
       protected void onPostExecute(List<ModelId> words) {
           super.onPostExecute(words);
           if (words != null && !words.isEmpty()) {
               listView=(ListView) findViewById(R.id.list);
               adapter = new Adapter(getApplicationContext(), words);
             //  int x=words.
               listView.setAdapter(adapter);
               keys=words;


           }
       }
   }

   public  class reviewTask extends AsyncTask<String,Void,List<ReviewModel>>{

       @Override
       protected List<ReviewModel> doInBackground(String... urls) {
           if (urls.length < 1 || urls[0] == null) {

               //Toast.makeText(getApplicationContext(), "Make sure Internet Is connected", Toast.LENGTH_LONG).show();
               return null;
           } else {

               URL url=FindMid.createUrl(urls[0]);
               String readStream=FindMid.readFromStream(url);

               List<ReviewModel> reviews = extractJson(readStream);


               return reviews;
           }

       }

       @Override
       protected void onPostExecute(List<ReviewModel> reviews) {
           super.onPostExecute(reviews);
           reviewAdapter=new ReviewAdapter(getApplicationContext(),reviews);
            listReview=(ListView) findViewById(R.id.reviewList);
           listReview.setAdapter(reviewAdapter);
          // Log.v("Detail","rrrrrrrrrrr"+reviews.get(0));

       }
   }

    private  List<ReviewModel> extractJson(String readStream) {
        List<ReviewModel> urlReviews=new ArrayList<>();


        try {
            JSONObject object=new JSONObject(readStream);
            JSONArray array=object.optJSONArray("results");
            for(int i=0;i<array.length();i++){
                JSONObject item=array.optJSONObject(i);
                String content=item.optString("content");
                String review=item.optString("author");
                ReviewModel model=new ReviewModel(review,content);
                urlReviews.add(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

         return urlReviews;
    }


}
