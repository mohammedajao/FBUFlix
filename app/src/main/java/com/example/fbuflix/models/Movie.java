package com.example.fbuflix.models;

import android.os.Parcelable;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbuflix.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    public static final String TAG = "Movie";
    public static final String KEY_INTENT_TRAILER = "TrailerLink";
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    double rating;
    int id;
    String release_date;
    String trailer_link;

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public void setYTTrailerKey(JSONArray mvJsonResultArray) throws JSONException {
        for(int i = 0; i < mvJsonResultArray.length(); i++) {
            JSONObject data = mvJsonResultArray.getJSONObject(i);
            String site = data.getString("site");
            Log.d(TAG, site);
            if(site.equals("YouTube")) {
                trailer_link = data.getString("key");
            }
        }
    }

    public Movie() {}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
        release_date = jsonObject.getString("release_date");
    }

    public String getPosterPath() {
        // Later the goal should be making an API request to configuration API
        // Figuring out what sizes of images are available
        // Append size to the URL
        // Append poster path to the edited URL
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() { return rating; }

    public String getRelease_date() { return release_date; }

    public int getId() { return id; }

    public String getYTTrailerKey() { return trailer_link; }

}
