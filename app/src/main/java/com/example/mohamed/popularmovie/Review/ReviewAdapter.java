package com.example.mohamed.popularmovie.Review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mohamed.popularmovie.R;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<ReviewModel> {


    public ReviewAdapter(@NonNull Context context, List<ReviewModel> reviews) {
        super(context, 0, reviews);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        ReviewModel model = getItem(position);

        TextView review = (TextView) convertView.findViewById(R.id.review);
        TextView content = (TextView) convertView.findViewById(R.id.content);

        review.setText(model.getMreview());
        content.setText(model.getMcontent());

        return convertView;

    }
}