package com.ardam.clientappsub;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ardam.clientappsub.adapter.MovieAdapter;
import com.ardam.clientappsub.adapter.SeriesAdapter;
import com.ardam.clientappsub.models.Movie;
import com.ardam.clientappsub.models.Series;
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
public class SearchFragment extends Fragment {
    EditText search;
    TextView txtMovie, txtSeries;
    RecyclerView rcvMovies, rcvSeries;
    FirebaseFirestore db;
    CollectionReference movieRef, seriesRef;


    public SearchFragment() {
        // Required empty public constructor
    }

    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myView = inflater.inflate(R.layout.fragment_search, container, false);
        GoogleAdMob adMob = new GoogleAdMob();
        adMob.loadBanner(myView, getContext(), getActivity());
        search = myView.findViewById(R.id.search);
        txtMovie = myView.findViewById(R.id.txtMovies);
        txtSeries = myView.findViewById(R.id.txtSeries);
        rcvMovies = myView.findViewById(R.id.rcvMovies);
        rcvSeries = myView.findViewById(R.id.rcvSeries);
        db = FirebaseFirestore.getInstance();
        movieRef = db.collection(getString(R.string.movie_ref));
        seriesRef = db.collection(getString(R.string.series_ref));
        loadData();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.getText().toString().trim().equals(""))
                {
                    loadData();
                }
                else {
                    movieRef.orderBy("title")
                            .startAt(search.getText().toString().trim())
                            .endAt(search.getText().toString().trim()+"\uf8ff");
                    movieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            ArrayList<Movie> movies = new ArrayList<>();
                            for (DocumentSnapshot snapshot: queryDocumentSnapshots)
                            {
                                movies.add(snapshot.toObject(Movie.class));
                            }
                            MovieAdapter adapter = new MovieAdapter(movies, getContext(),getFragmentManager());
                            LinearLayoutManager lm = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
                            rcvMovies.setAdapter(adapter);
                            rcvMovies.setLayoutManager(lm);
                            txtMovie.setText(search.getText().toString()+" Movies ("+movies.size()+")");
                        }
                    });
                    seriesRef.orderBy("title")
                            .startAt(search.getText().toString().trim())
                            .endAt(search.getText().toString().trim()+ "\uf8ff");
                    seriesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            ArrayList<Series> series = new ArrayList<>();
                            for (DocumentSnapshot snapshot: queryDocumentSnapshots)
                            {
                                series.add(snapshot.toObject(Series.class));
                            }
                            SeriesAdapter adapter = new SeriesAdapter(series, getContext(),getFragmentManager());
                            LinearLayoutManager lm = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
                            rcvSeries.setAdapter(adapter);
                            rcvSeries.setLayoutManager(lm);
                            txtSeries.setText(search.getText().toString()+" Series ("+series.size()+")");
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return myView;
    }
    public void loadData()
    {
        movieRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<Movie> movies = new ArrayList<>();
                for (DocumentSnapshot snapshot: queryDocumentSnapshots)
                {
                    movies.add(snapshot.toObject(Movie.class));
                }
                MovieAdapter adapter = new MovieAdapter(movies, getContext(),getFragmentManager());
                LinearLayoutManager lm = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false);
                rcvMovies.setAdapter(adapter);
                rcvMovies.setLayoutManager(lm);
                txtMovie.setText("All Movies ("+movies.size()+")");

            }
        });
        rcvSeries = myView.findViewById(R.id.rcvSeries);
        CollectionReference seriesRef = db.collection(getString(R.string.series_ref));
        seriesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<Series> series = new ArrayList<>();
                for (DocumentSnapshot snapshot: queryDocumentSnapshots)
                {
                    series.add(snapshot.toObject(Series.class));
                }
                SeriesAdapter adapter = new SeriesAdapter(series,getContext(),getFragmentManager());
                LinearLayoutManager lm = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                rcvSeries.setAdapter(adapter);
                rcvSeries.setLayoutManager(lm);
                txtSeries.setText("All Series("+series.size()+")");

            }
        });
    }
}
