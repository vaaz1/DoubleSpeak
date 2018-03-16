package com.example.android.doublespeak.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.android.doublespeak.R;


class RecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView image;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.card_image);
    }

}