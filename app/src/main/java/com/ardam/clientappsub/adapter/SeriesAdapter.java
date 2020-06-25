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
import com.ardam.clientappsub.SeriesDatFragment;
import com.ardam.clientappsub.VideoDetailFragment;
import com.ardam.clientappsub.models.Series;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesHolder> {
    ArrayList<Series> series = new ArrayList<>();
    Context context;
    public FragmentManager manager;

    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    public SeriesAdapter(ArrayList<Series> series, Context context, FragmentManager manager) {
        this.series = series;
        this.context = context;
        this.manager = manager;
        GoogleAdMob adMob = new GoogleAdMob();
        interstitialAd = adMob.loadInterAd(context);
        rewardedVideoAd = adMob.loadRewarded(context);
    }

    @NonNull
    @Override
    public SeriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new SeriesHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesHolder holder, final int position) {
        Glide.with(context)
                .load(series.get(position).getImageUrl())
                .into(holder.image);


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeriesDatFragment datFragment = new SeriesDatFragment();
                datFragment.myModel = series.get(position);
                setFragment(datFragment, position);
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

    }

    @Override
    public int getItemCount() {
        return series.size();
    }


    public class SeriesHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public SeriesHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }

    public void setFragment(Fragment fragment, int position) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.content_fragment, fragment);
        ft.commit();
        if (MainActivity.currentFrag.equals(context.getString(R.string.Home_frag))) {
            MainActivity.prevFrag = context.getString(R.string.Home_frag);
        } else if (MainActivity.currentFrag.equals(context.getString(R.string.Series_frag))) {
            MainActivity.prevFrag = context.getString(R.string.Series_frag);
        } else if (MainActivity.currentFrag.equals(context.getString(R.string.Search_frag))) {
            MainActivity.prevFrag = context.getString(R.string.Search_frag);
        }
        MainActivity.currentFrag = context.getString(R.string.video_det);

        MainActivity.series = series.get(position);
        MainActivity.prevFrag = context.getString(R.string.Series_frag);
        MainActivity.currentFrag = context.getString(R.string.series_dat);
    }
}
