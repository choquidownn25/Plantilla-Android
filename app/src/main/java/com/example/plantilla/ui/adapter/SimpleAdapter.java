package com.example.plantilla.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plantilla.Orden.ActivityOrden;
import com.example.plantilla.R;
import com.example.plantilla.ui.activity.MainActivityOrden;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Internal;

/**
 * @author YanLu
 * @since 17/9/3
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
    private List<String> mData = new ArrayList<>();


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_result, parent, false);
        return new SimpleViewHolder(root);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.tvLabel.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(String data) {
        mData.add(data);
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLabel;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            tvLabel = (TextView) itemView.findViewById(R.id.section_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityOrden.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
