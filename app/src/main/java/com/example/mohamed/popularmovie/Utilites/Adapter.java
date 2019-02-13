package com.example.mohamed.popularmovie.Utilites;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.mohamed.popularmovie.*;


import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<ModelId> {

    private static final String TAG ="Adapter.class" ;

    public Adapter(@NonNull Context context, List<ModelId> list) {
        super(context,0,list);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ModelId model=getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
             }

        TextView text=(TextView) convertView.findViewById(R.id.trailerText);

        String key=model.getmKey();

        text.setText("Trailer"+position);

        Log.v(TAG, "kkkkkkkkkkkkkkk" + model.getmKey());

       return convertView;
    }


}
