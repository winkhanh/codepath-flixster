package com.winkhanh.flixster.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkhanh.flixster.R;
import com.winkhanh.flixster.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;
    public static final int POPULAR=1;
    public static final int NON_POPULAR=0;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView;
        if (viewType==NON_POPULAR)
            movieView=LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        else
            movieView=LayoutInflater.from(context).inflate(R.layout.item_popular_movie,parent,false);

        return new ViewHolder(movieView);
    }

    @Override
    public int getItemViewType(int position){
        if (movies.get(position).getAverageVote()>=6) return POPULAR;else return NON_POPULAR;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie=movies.get(position);

        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverView;
        ImageView ivPoster;
        TextView tvRating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverView = itemView.findViewById(R.id.tvOverView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverView.setText(movie.getOverview());
            tvRating.setText(String.format("Rating : %.1f",movie.getAverageVote()));
            boolean landscape = (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
            boolean popular = (movie.getAverageVote()>=6);
            Glide.with(context).load(movie.getPosterPath(landscape||popular)).placeholder(R.drawable.movie_placeholder).into(ivPoster);
        }
    }
}
