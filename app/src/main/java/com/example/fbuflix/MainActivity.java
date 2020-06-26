package com.example.fbuflix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbuflix.activities.DisplayActivity;
import com.example.fbuflix.adapters.MovieAdapter;
import com.example.fbuflix.databinding.ActivityMainBinding;
import com.example.fbuflix.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    private static final String TAG = "MainActivity";

    List<Movie> movies;
    RecyclerView rvMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Using ViewBinding Library to inflate XML and reduce findViewByID
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        movies = new ArrayList<>();

        rvMovies = binding.rvMovies;
        final String API_KEY = getString(R.string.tmdb_api_key);

        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());

        MovieAdapter.OnClickListener onClickListener = new MovieAdapter.OnClickListener() {
            @Override
            public void onItemClicked(Intent i, Context context) {
                context.startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        };

        final MovieAdapter movieAdapter = new MovieAdapter(this, movies, onClickListener);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());

        fetchMovies(movieAdapter);
    }
    // Get data from the TMDB API and add it to our collection

    private void fetchMovies(final MovieAdapter movieAdapter) {
        final String API_KEY = getString(R.string.tmdb_api_key);
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL + API_KEY, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "API GET Request Success");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "JSON Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies Size: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "API GET Request Failure " + statusCode + " " + response);
            }
        });
    }
}