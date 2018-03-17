package com.doublespeak.android.doublespeak.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.doublespeak.android.doublespeak.R;

public class GridAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public GridAdapter(Context context) {
        super(context, R.layout.item_card);

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_card, parent, false);

        }

        Glide
                .with(context)
                .load(R.drawable.putin)
                .into((ImageView) convertView);

        return convertView;
    }
}