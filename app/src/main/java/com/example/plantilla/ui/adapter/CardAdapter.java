package com.example.plantilla.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.ui.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements View.OnClickListener {

    private List<Movie> mItems;
    private Listener mListener;
    private Context context;

    public CardAdapter(List<Movie> items, Listener listener) {
        if (items == null) {
            items = new ArrayList<>();
        }
        mItems = items;
        mListener = listener;
    }

    public CardAdapter(List<Movie> items, Context context) {
        super();
        this.mItems=items;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_recycler_view_card_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = mItems.get(i);
        viewHolder.tvMovie.setText(movie.name);
        viewHolder.imgThumbnail.setImageBitmap(movie.imageBitmap);
        if (mListener != null) {
            viewHolder.cardView.setOnClickListener(this);
            viewHolder.cardView.setTag(movie);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            Movie movie = (Movie) v.getTag();
            mListener.onItemClicked(movie);
        }

    }

    public interface Listener {
        void onItemClicked(Movie movie);
    }

    public List<Movie> getItems() {
        return mItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView tvMovie;
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvMovie = (TextView) itemView.findViewById(R.id.tv_movie);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
