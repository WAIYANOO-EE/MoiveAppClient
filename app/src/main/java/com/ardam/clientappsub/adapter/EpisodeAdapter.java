package com.ardam.clientappsub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ardam.clientappsub.GoogleAdMob;
import com.ardam.clientappsub.MainActivity;
import com.ardam.clientappsub.R;
import com.ardam.clientappsub.VideoDetailFragment;
import com.ardam.clientappsub.models.Episode;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {

    ArrayList<Episode> episodes = new ArrayList<>();
    Context context;
    FragmentManager manager;

    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;
    public EpisodeAdapter(ArrayList<Episode> episodes, Context context, FragmentManager manager) {
        this.episodes = episodes;
        this.context = context;
        this.manager = manager;
        GoogleAdMob adMob = new GoogleAdMob();
        interstitialAd = adMob.loadInterAd(context);
        rewardedVideoAd = adMob.loadRewarded(context);
    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item, parent,false);
        return new EpisodeHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, final int position) {
        holder.txtEpNo.setText(position+1 + "");
        holder.txtEpName.setText(episodes.get(position).getName());
        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                if (rewardedVideoAd.isLoaded())
                {
                rewardedVideoAd.show();
                }
               VideoDetailFragment fragment = new VideoDetailFragment();
               fragment.episode = episodes.get(position);
               setFragment(fragment);


            }
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }


    public class EpisodeHolder extends RecyclerView.ViewHolder{
        TextView txtEpNo, txtEpName;
        RecyclerView rcvEpList;
        ImageView playBtn;

        public EpisodeHolder(@NonNull View itemView) {
            super(itemView);

            txtEpNo = itemView.findViewById(R.id.txtEpNo);
            txtEpName = itemView.findViewById(R.id.txtEpName);
            playBtn = itemView.findViewById(R.id.playBtn);



        }
    }
    public void setFragment(Fragment f){
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.content_fragment, f);
        ft.commit();
        MainActivity.mediumFrag=MainActivity.prevFrag;
        MainActivity.currentFrag = context.getString(R.string.Series_frag);
        MainActivity.prevFrag = context.getString(R.string.video_det);
    }
}