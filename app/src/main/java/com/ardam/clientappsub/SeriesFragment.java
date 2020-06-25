package com.ardam.clientappsub;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ardam.clientappsub.adapter.SeriesAdapter;
import com.ardam.clientappsub.models.Movie;
import com.ardam.clientappsub.models.Series;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import recycler.coverflow.RecyclerCoverFlow;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeriesFragment extends Fragment {
    FirebaseFirestore db;
    CollectionReference seriesRef;
    TextView txtSeries;
    RecyclerView rcvSeries;
    RecyclerCoverFlow rcvTenSeries;

    public SeriesFragment() {
        // Required empty public constructor
    }

    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_series, container, false);
        GoogleAdMob adMob = new GoogleAdMob();
        adMob.loadBanner(myView, getContext(), getActivity());
        db = FirebaseFirestore.getInstance();
        seriesRef = db.collection(getString(R.string.series_ref));
        txtSeries = myView.findViewById(R.id.txtMovies);
        rcvSeries = myView.findViewById(R.id.rcvMovies);
        rcvTenSeries = myView.findViewById(R.id.rcvTenMovies);
        seriesRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<Series> series = new ArrayList<>();
                ArrayList<Series> tenSeries = new ArrayList<>();
                for (DocumentSnapshot snapshot: queryDocumentSnapshots)
                {
                 series.add(snapshot.toObject(Series.class));
                }
                if (series.size()>=10)
                {
                    Random random = new Random();

                for (int i=0; i<10; i++)
                {
                 int index = random.nextInt(series.size());
                 tenSeries.add(series.get(index));

                }
                SeriesAdapter adapter = new SeriesAdapter(series, getContext(),getFragmentManager());
                rcvSeries.setAdapter(adapter);
                rcvSeries.setLayoutManager(new GridLayoutManager(getContext(),3));
                rcvTenSeries.setAdapter(new SeriesAdapter(series, getContext(),getFragmentManager()));
                rcvTenSeries.scrollToPosition(tenSeries.size()/2);
                }
                else {
                    SeriesAdapter adapter = new SeriesAdapter(series,getContext(),getFragmentManager());
                    rcvSeries.setAdapter(adapter);
                    rcvTenSeries.setAdapter(adapter);
                    rcvSeries.setLayoutManager(new GridLayoutManager(getContext(),3));
                    rcvTenSeries.scrollToPosition(series.size()/2);
                }
                txtSeries.setText("All Series("+series.size()+")");
            }

        });

        return myView;
    }
}
