package com.example.mohamed.popularmovie.Utilites;

import android.text.TextUtils;
import android.util.Log;

import com.example.mohamed.popularmovie.Model;
import com.example.mohamed.popularmovie.Utils;

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

public final class FindMid  {

    private static String TAG = FindMid.class.getSimpleName();

    public static List<ModelId> fetchinputs(String urlResponce) {



           URL url = createUrl(urlResponce);

            Log.v(TAG, "created url");

            String jsonResponse = readFromStream(url);
            Log.v(TAG, "created stream");

            List<ModelId> trailers = extractJson(jsonResponse);

            Log.v(TAG, "created json");

            return trailers;

    }



    public static URL createUrl(String urlResponce) {
        URL url=null;
        try {
            url=new URL(urlResponce);
            Log.v(TAG, "sucess" + url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

         return  url;
    }


    public static String readFromStream(URL url) {
        String jsonResponse=" ";
        if(url==null){
            Log.v(TAG, "url is null" + url);
            return jsonResponse;

        }

        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try {
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(httpURLConnection.getResponseCode()==200){
                inputStream=httpURLConnection.getInputStream();
                jsonResponse=readStringFromStream(inputStream);
                Log.v(TAG, "url is ok" + httpURLConnection.getResponseCode());

            }
            else{
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



    private static List<ModelId> extractJson(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            Log.v(TAG, "json is null");
            return null;
        }

        List<ModelId> trailers = new ArrayList<>();
        try {
            JSONObject root=new JSONObject(jsonResponse);
            JSONArray resultArray=root.optJSONArray("results");
            for(int i=0 ; i<resultArray.length();i++){

                JSONObject item=resultArray.optJSONObject(i);

                String key=item.optString("key");

                ModelId model=new ModelId(key);

                trailers.add(model);
                Log.v(TAG, "key" + key);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return trailers;
    }



}
