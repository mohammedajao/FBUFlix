package com.example.fbuflix.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.fbuflix.R;
import com.example.fbuflix.databinding.ActivityDisplayBinding;
import com.example.fbuflix.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";
    public static final String MOVIE_API_LINK = "https://api.themoviedb.org/3/movie/";

    TextView tvMDTitle;
    TextView tvMDReleaseDate;
    ImageView ivMDPoster;
    RatingBar rbMovieRating;
    TextView tvMovieDesc;
    Movie movie;
    String movie_yt_key;
    String trailer_yt_trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDisplayBinding binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        Log.i(TAG, "Movie Title: " + movie.getTitle() + movie.getRating());
        tvMDTitle = binding.tvMDTitle;
        tvMDReleaseDate = binding.tvMDReleaseDate;
        rbMovieRating = binding.rbMovieRating;
        tvMovieDesc = binding.tvMovieDesc;
        ivMDPoster = binding.ivMDPoster;

        tvMDTitle.setText(movie.getTitle());
        tvMDReleaseDate.setText(movie.getRelease_date());
        tvMovieDesc.setText(movie.getOverview());
        float rating = (float) movie.getRating() ;
        rbMovieRating.setRating(rating / 2.0f);

        String imageUrl;
        imageUrl = movie.getBackdropPath();

        Glide.with(getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .into(ivMDPoster);

        ivMDPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trailer_yt_trailer != null && !trailer_yt_trailer.isEmpty()) {
                    Log.d(TAG, trailer_yt_trailer);
                    Intent intent = new Intent(DisplayActivity.this, MovieTrailerActivity.class);
                    intent.putExtra(Movie.KEY_INTENT_TRAILER, movie_yt_key);
                    startActivity(intent);
                }
            }
        });

        getTrailer(movie);
    }

    private void setTrailerLink(String key) {
        if(!key.equals("")) {
            movie_yt_key = key;
            trailer_yt_trailer = "https://www.youtube.com/watch?v=" + key;
            Log.d(TAG, trailer_yt_trailer);
        }
    }

    private void getTrailer(final Movie movie) {
        final String API_KEY = getString(R.string.tmdb_api_key);
        final String API_URL = MOVIE_API_LINK + movie.getId() + "/videos?api_key=" + API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d(TAG, API_URL);
        client.get(API_URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    if(results.length() == 0)
                        return;

                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d(TAG, youtubeKey);
                    // Sets trailer link in DisplayActivity and the targeted movie
                    movie.setYTTrailerKey(results);
                    setTrailerLink(movie.getYTTrailerKey());
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Youtube API Call Failed: " + response);
            }
        });
    }
}