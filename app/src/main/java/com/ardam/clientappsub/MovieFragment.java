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

import com.ardam.clientappsub.adapter.MovieAdapter;
import com.ardam.clientappsub.models.Movie;
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
public class MovieFragment extends Fragment {
    FirebaseFirestore db;
    CollectionReference movieRef;
    RecyclerView rcvMovies;
    RecyclerCoverFlow rcvTenMovies;
    TextView txtMovie;

    public MovieFragment() {
        // Required empty public constructor
    }

    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_movie, container, false);
        GoogleAdMob adMob = new GoogleAdMob();
        adMob.loadBanner(myView, getContext(), getActivity());
        db = FirebaseFirestore.getInstance();
        movieRef = db.collection(getString(R.string.movie_ref));
        rcvTenMovies = myView.findViewById(R.id.rcvTenMovies);
        rcvMovies = myView.findViewById(R.id.rcvMovies);
        txtMovie = myView.findViewById(R.id.txtMovies);
        movieRef.orderBy("createdAt", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                ArrayList<Movie> movies = new ArrayList<>();
                ArrayList<Movie> tenMovies = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    movies.add(snapshot.toObject(Movie.class));
                }
                if (movies.size() >= 10) {
                    Random random = new Random();

                    for (int i = 0; i < 10; i++) {
                        int index = random.nextInt(movies.size());
                        tenMovies.add(movies.get(index));
                    }
                    rcvMovies.setAdapter(new MovieAdapter(movies, getContext(), getFragmentManager()));
                    rcvMovies.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    rcvTenMovies.setAdapter(new MovieAdapter(tenMovies, getContext(), getFragmentManager()));
                    rcvTenMovies.scrollToPosition(tenMovies.size() / 2);
                } else {
                    MovieAdapter adapter = new MovieAdapter(movies, getContext(),getFragmentManager());
                    rcvMovies.setAdapter(adapter);
                    rcvTenMovies.setAdapter(adapter);
                    rcvMovies.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    rcvTenMovies.scrollToPosition(movies.size() / 2);


                }
                txtMovie.setText("All Movies(" + movies.size() + ")");
            }
        });
        return myView;
    }
}
