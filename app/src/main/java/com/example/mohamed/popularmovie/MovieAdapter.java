package com.example.mohamed.popularmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {

    private ItemClick mOnClick;


    public List<Movies> words;
    public interface ItemClick {
        void onItemClick(Movies model);

    }


    Context context;



    Model model;

    private static final String TAG = MovieAdapter.class.getSimpleName();


    public MovieAdapter(Context mcontext, ArrayList<Movies> mwords, ItemClick listener) {
        this.context = mcontext;
        this.words = mwords;
       this.mOnClick = listener;


    }

    public void setTasks(List<Movies> words) {
        this.words=words;
        notifyDataSetChanged();

    }


//    Observer<ArrayList<Movies>> observer;
//    public MovieAdapter(Context applicationContext, ArrayList<Model> models, Observer<ArrayList<Model>> mobserver) {
//       this.context=applicationContext;
//       this.words=models;
//        this.observer=mobserver;
//
//
//    }


    @NonNull
    @Override
    public MovieAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.Holder holder, int i) {
        final Movies model = words.get(i);
        String path = model.getPoster();
        // Toast.makeText(context,"xxxx"+model.getMtitle(),Toast.LENGTH_LONG);

        //List<String> x = popMethod(model.getMpopulate());

        String value = "w185";
        String base_url = "http://image.tmdb.org/t/p/";
        final String full_url = base_url + value + "/" + path;


        populateUI();
        Picasso.with(context)
                .load(full_url)
                .into(holder.poster);

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClick.onItemClick(model);


            }
        });
    }


    private List<String> popMethod(List<String> mpopulate) {

        Log.v(TAG, "mmmmmmmmmmmmmmmm" + mpopulate.get(2));
        return mpopulate;
    }


    @Override
    public int getItemCount() {
        return words.size();
    }

    private void populateUI() {
    }



    public  ArrayList<Movies> getTasks(){
        return (ArrayList<Movies>) words;

    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout root;
        ImageView poster;


        public Holder(@NonNull View view) {
            super(view);
            // menu=view.
            poster = view.findViewById(R.id.poster);
            root = view.findViewById(R.id.root);


        }



    }
}