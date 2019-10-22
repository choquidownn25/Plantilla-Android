package com.example.plantilla.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantilla.R;
import com.example.plantilla.camara.Camara;
import com.example.plantilla.sqllite.activity.MainActivityEmpleado;
import com.example.plantilla.sqllite.activity.MainActivitySQLite;
import com.example.plantilla.ui.activity.MainActivity;
import com.example.plantilla.ui.activity.MainActivityOrden;
import com.example.plantilla.ui.activity.MainActivityRecicleview;
import com.example.plantilla.ui.fragment.PlaceholderFragment;
import com.example.plantilla.ui.interfaces.ItemClickListener;

import java.util.ArrayList;

public class GridAdapter extends  RecyclerView.Adapter<GridAdapter.ViewHolder> {

    ArrayList<String> alName;
    ArrayList<Integer> alImage;
    Context context;
    PlaceholderFragment placeholderFragment;

    public GridAdapter(Context context, ArrayList<String> alName, ArrayList<Integer> alImage) {
        super();
        this.context = context;
        this.alName = alName;
        this.alImage = alImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvSpecies.setText(alName.get(i));
        viewHolder.imgThumbnail.setImageResource(alImage.get(i));

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    //Evalua el que escoja
                    if (position == 0) {
                        //Para Sensores
                        context.startActivity(new Intent(context, MainActivityOrden.class));
                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    }else if (position == 1){
                        //Para cita
                        context.startActivity(new Intent(context, MainActivityRecicleview.class));
                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    } else  if (position == 2){
                        //Aca esta el tab layout

                        context.startActivity(new Intent(context, Camara.class));
                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();

                    }else if (position == 3){
                        //Radio grafia  MainActivityVideoYoutube
                        context.startActivity(new Intent(context, MainActivitySQLite.class));
                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    } else if (position== 4){
                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, MainActivityEmpleado.class));
                    } else {
                        Toast.makeText(context, "#" + position + " - " + alName.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return alName.size();
    }


    //Clase
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgThumbnail;
        public TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }


    }


}
