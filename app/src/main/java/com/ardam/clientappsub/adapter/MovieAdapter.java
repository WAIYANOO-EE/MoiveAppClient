package com.ardam.clientappsub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ardam.clientappsub.GoogleAdMob;
import com.ardam.clientappsub.MainActivity;
import com.ardam.clientappsub.R;
import com.ardam.clientappsub.VideoDetailFragment;
import com.ardam.clientappsub.models.Movie;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder>{
    ArrayList<Movie> movies = new ArrayList<>();
    Context context;
     ImageView image;

     private InterstitialAd interstitialAd;
     private RewardedVideoAd rewardedVideoAd;
     FragmentManager manager;
    public MovieAdapter(ArrayList<Movie> movies, Context context, FragmentManager manager) {
        this.movies = movies;
        this.context = context;
        this.manager = manager;
        GoogleAdMob adMob = new GoogleAdMob();
        interstitialAd = adMob.loadInterAd(context);
        rewardedVideoAd = adMob.loadRewarded(context);
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        Glide.with(context)
                .load(movies.get(position).getImageUrl())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoDetailFragment fragment = new VideoDetailFragment();
                fragment.movie = movies.get(position);
                setFragment(fragment);
                if (interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                if (rewardedVideoAd.isLoaded())
                {
                    rewardedVideoAd.show();
                }
            }
        });
        if (MainActivity.currentFrag.equals(context.getString(R.string.Home_frag)))
        {
            MainActivity.prevFrag=context.getString(R.string.Home_frag);
        }
        else if (MainActivity.currentFrag.equals(context.getString(R.string.Movie_frag)))
        {
            MainActivity.prevFrag = context.getString(R.string.Movie_frag);
        }
        else if (MainActivity.currentFrag.equals(context.getString(R.string.Search_frag)));
        {
            MainActivity.prevFrag = context.getString(R.string.Search_frag);
        }
        MainActivity.currentFrag = context.getString(R.string.video_det);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class MovieHolder extends RecyclerView.ViewHolder{
        public ImageView image;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);

        }
    }

    public void setFragment(Fragment f)
    {
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.content_fragment, f);
        ft.commit();
    }
    }







