package com.winkhanh.flixster.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String backdropPath;
    String title;
    int id;
    double averageVote;

    public Movie(){}

    String overview;
    public Movie(JSONObject jsonObj) throws JSONException {
        id = jsonObj.getInt("id");
        backdropPath = jsonObj.getString("backdrop_path");
        posterPath = jsonObj.getString("poster_path");
        title = jsonObj.getString("title");
        overview = jsonObj.getString("overview");
        averageVote= jsonObj.getDouble("vote_average");
    }
    public int getId(){return id;}
    public static List<Movie> mapFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Movie> movies= new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath(boolean landscape) {
        return String.format("https://image.tmdb.org/t/p/w342/%s",(!landscape)?posterPath:backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getAverageVote() { return averageVote; }
}
