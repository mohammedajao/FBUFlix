package com.example.fbuflix.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.fbuflix.MainActivity;
import com.example.fbuflix.R;
import com.example.fbuflix.models.Movie;

import org.parceler.Parcels;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";
    TextView tvMDTitle;
    TextView tvMDReleaseDate;
    RatingBar rbMovieRating;
    TextView tvMovieDesc;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        Log.i(TAG, "Movie Title: " + movie.getTitle() + movie.getRating());
        tvMDTitle = findViewById(R.id.tvMDTitle);
        tvMDReleaseDate = findViewById(R.id.tvMDReleaseDate);
        rbMovieRating = findViewById(R.id.rbMovieRating);
        tvMovieDesc = findViewById(R.id.tvMovieDesc);

        tvMDTitle.setText(movie.getTitle());
        tvMDReleaseDate.setText(movie.getRelease_date());
        tvMovieDesc.setText(movie.getOverview());
        float rating = (float) movie.getRating() ;
        rbMovieRating.setRating(rating / 2.0f);
    }
}