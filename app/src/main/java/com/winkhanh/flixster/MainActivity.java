package com.winkhanh.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.winkhanh.flixster.adapter.MovieAdapter;
import com.winkhanh.flixster.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "https://api.themoviedb.org/3/movie/now_playing";
    public static final String API_KEY="";
    public static final String TAG="MAIN";
    List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies=new ArrayList<>();
        final MovieAdapter movieAdapter= new MovieAdapter(this,movies);

        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key",API_KEY);
        client.get(URL,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG,"onSuccess");
                JSONObject jsonObj = json.jsonObject;
                try{
                    JSONArray result = jsonObj.getJSONArray("results");
                    movies.addAll(Movie.mapFromJsonArray(result));
                    movieAdapter.notifyDataSetChanged();
                }catch(JSONException e){
                    Log.e(TAG,"Json exception",e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG,"onFailure");
            }
        });
    }
}