package com.example.fbuflix.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fbuflix.R;
import com.example.fbuflix.databinding.ActivityMovieTrailerBinding;
import com.example.fbuflix.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieTrailerBinding binding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        // temporary test video id -- TODO replace with movie trailer video id
        final String videoId = getIntent().getStringExtra(Movie.KEY_INTENT_TRAILER);

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) binding.player;

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.ytapiv3_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.setFullscreen(true);
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });
    }
}