package com.example.mohamed.popularmovie;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class Utils {

    // String urlResoponce = "http://api.themoviedb.org/3/movie/popular?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";
    private static List<String> popp;

    private static String TAG = Utils.class.getSimpleName();

    public static List<Model> fetchinputs(String urlResoponce) {
        URL url = createUrl(urlResoponce);

        Log.v(TAG, "created url");

        String jsonResponce = readStream(url);
        Log.v(TAG, "created stream");

        List<Model> films = extractMovie(jsonResponce);

        Log.v(TAG, "created json");


        List<String> populated = null;

        populated = Utils.pop(popp);
        Log.v(TAG, "pppppppppppppppppppppppppp" + populated);


        // Return the list of {@link Earthquake}s
        return films;

    }

    public static List<Model> extractMovie(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            Log.v(TAG, "json is null");
            return null;
        }

        List<Model> films = new ArrayList<>();
        List<String> populate = new ArrayList<>();


        try {
            JSONObject root = new JSONObject(jsonResponse);


            JSONArray filmsArray = root.optJSONArray("results");
            for (int i = 0; i < filmsArray.length(); i++) {
                JSONObject filmItem = filmsArray.optJSONObject(i);
                String title = filmItem.optString("original_title");
                String poster_image = filmItem.optString("poster_path");
                String overview = filmItem.optString("overview");
                String vote_average = filmItem.optString("vote_average");
                String realse_data = filmItem.optString("release_date");


                String populated = filmItem.optString("popularity");
                populate.add(populated);

                Log.v(TAG, "title overview" + title + overview);

                Model model = new Model(title, poster_image, overview, vote_average, realse_data, populate);


                films.add(model);

            }

            List<String> x = pop(populate);
            Log.v(TAG, "pppppppppppppppp" + x.get(7));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "ssssssssssssssssssss");


        return films;


    }

    public static List<String> pop(List<String> populate) {


        Log.v(TAG, "oooooooooooo" + populate);

        // Log.v(TAG,"oooooooooooo"+populate.get(7));

        return populate;

    }


    public static URL createUrl(String urlResoponce) {

        URL url = null;
        try {
            url = new URL(urlResoponce);
            Log.v(TAG, "sucess" + url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url == null) {
            Log.v(TAG, "url is null" + url);

        }

        Log.v(TAG, "sucess" + url);
        return url;
    }


    public static String readStream(URL url) {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            Log.v(TAG, "url is null" + url);
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readStringFromStream(inputStream);
                Log.v(TAG, "url is ok" + httpURLConnection.getResponseCode());

            } else {
                httpURLConnection.disconnect();
                Log.v(TAG, "url is not found" + httpURLConnection.getResponseCode());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;

    }


    public static String readStringFromStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
                Log.v(TAG, "line not null" + line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }


}
