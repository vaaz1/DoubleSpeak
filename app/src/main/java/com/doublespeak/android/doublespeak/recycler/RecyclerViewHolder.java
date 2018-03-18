package com.doublespeak.android.doublespeak.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.doublespeak.R;


class RecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView tvText;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.card_image);
        tvText = itemView.findViewById(R.id.tvText);
    }

}