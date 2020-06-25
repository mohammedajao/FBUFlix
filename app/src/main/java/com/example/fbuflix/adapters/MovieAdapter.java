package com.example.fbuflix.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.fbuflix.MainActivity;
import com.example.fbuflix.R;
import com.example.fbuflix.activities.DisplayActivity;
import com.example.fbuflix.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public static final String TAG = "MovieAdapter";

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating XML data into the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populate the data into the view through the holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get movie at the position
        Movie movie = movies.get(position);
        // Bind the movie data to the holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        ImageView ivPoster;
        TextView tvOverview;
        View movieItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieItem = itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.i(TAG, "Clicked ");
            if(position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Log.i(TAG, "Clicked " + movie.getTitle());
                Intent intent = new Intent(context, DisplayActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            int radius = 30;
            int margin = 10;
            int pholder = R.drawable.flicks_backdrop_placeholder;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
                pholder = R.drawable.flicks_movie_placeholder;
            }

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(pholder)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivPoster);
        }
    }
}
