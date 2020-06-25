package com.ardam.clientappsub.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ardam.clientappsub.R;
import com.ardam.clientappsub.models.Genre;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import project.aamir.sheikh.circletextview.CircleTextView;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyItems> {

    ArrayList<Genre> mArrayList;


    public GenreAdapter(ArrayList<Genre> mArrayList) {

        this.mArrayList = mArrayList;
    }


    @Override
    public MyItems onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent,false);
        MyItems items = new MyItems(v);
        return items;
    }

    @Override
    public void onBindViewHolder(MyItems holder, int position) {
        holder.mTextView.setText(mArrayList.get(position).getName());
        holder.mCircleTextView.setCustomText(mArrayList.get(position).getName());
        holder.mCircleTextView.setSolidColor(position);
        holder.mCircleTextView.setTextColor(Color.WHITE);
        holder.mCircleTextView.setCustomTextSize(18);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference movieRef = db.collection("movies");
        holder.mCircleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyItems extends RecyclerView.ViewHolder {
        TextView mTextView;
        CircleTextView mCircleTextView;

        public MyItems(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
            mCircleTextView = (CircleTextView) itemView.findViewById(R.id.ctv);

        }
    }
}