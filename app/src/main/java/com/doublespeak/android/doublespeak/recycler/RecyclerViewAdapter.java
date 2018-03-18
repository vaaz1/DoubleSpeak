package com.doublespeak.android.doublespeak.recycler;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.doublespeak.android.doublespeak.models.Cell;
import com.example.android.doublespeak.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<Cell> cellList;
    private Context context;
    private View.OnClickListener onClickListener;
    private LayoutInflater layoutInflater;


    public RecyclerViewAdapter(Context context, List<Cell> bookList) {
        this.onClickListener = ((View.OnClickListener) context);
        this.cellList = bookList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = layoutInflater.inflate(R.layout.item_card, parent, false);
        cardView.setOnClickListener(onClickListener);
        return new RecyclerViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Cell bookAtPosition = cellList.get(position);
        switch (bookAtPosition.getModeCell()) {
            case IsImage:
                Glide.with(context).load(bookAtPosition.getImageRes()).into(holder.image);
                break;
            case IsText:
                holder.tvText.setText(bookAtPosition.getAnimal());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

}