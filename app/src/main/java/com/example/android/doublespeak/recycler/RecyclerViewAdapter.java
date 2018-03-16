package com.example.android.doublespeak.recycler;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.android.doublespeak.R;
import com.example.android.doublespeak.models.Cell;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Cell> cellList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Cell> bookList) {
        this.cellList = bookList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Cell bookAtPosition = cellList.get(position);

        Glide
                .with(context)
                .load(R.mipmap.ic_launcher)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.cellList.size();
    }

}