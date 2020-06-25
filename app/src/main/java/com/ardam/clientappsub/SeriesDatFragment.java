package com.ardam.clientappsub;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardam.clientappsub.adapter.EpisodeAdapter;
import com.ardam.clientappsub.models.Episode;
import com.ardam.clientappsub.models.Series;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesDatFragment extends Fragment {
    public Series myModel;
    TextView txtSeriesName;
    ImageView image;
    FirebaseFirestore db;
    CollectionReference ref;
    RecyclerView rcvEpList;

    public SeriesDatFragment() {
        // Required empty public constructor
    }

View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         myView = inflater.inflate(R.layout.fragment_series_dat, container, false);
        GoogleAdMob adMob = new GoogleAdMob();
        adMob.loadBanner(myView, getContext(), getActivity());
         txtSeriesName = myView.findViewById(R.id.seriesName);
         image = myView.findViewById(R.id.image);
         rcvEpList = myView.findViewById(R.id.epList);
        if (myModel != null)
        {
         getActivity().setTitle(myModel.getTitle());
        }


         if (myModel != null)
         {
             Glide.with(getContext())
                     .load(myModel.getImageUrl())
                     .into(image);
             txtSeriesName.setText(myModel.getTitle());
             db = FirebaseFirestore.getInstance();
             ref = db.collection(getString(R.string.episodes));
             ref.whereEqualTo("seriesTitle",myModel.getTitle())
                     .orderBy("createdAt")
                     .addSnapshotListener(new EventListener<QuerySnapshot>() {
                         @Override
                         public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                             ArrayList<Episode> episodes = new ArrayList<>();
                             for (DocumentSnapshot snapshot: queryDocumentSnapshots)
                             {
                                 episodes.add(snapshot.toObject(Episode.class));
                             }
                             EpisodeAdapter adapter = new EpisodeAdapter(episodes,getContext(),getFragmentManager());
                             rcvEpList.setAdapter(adapter);
                             rcvEpList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));


                         }

                     });
         }
         return myView;
    }
}
