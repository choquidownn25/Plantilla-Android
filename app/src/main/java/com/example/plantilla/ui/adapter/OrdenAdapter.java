package com.example.plantilla.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.plantilla.R;
import com.example.plantilla.ui.models.Orden;

import java.util.List;

public class OrdenAdapter extends RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder> {
    private List<Orden> items;

    public OrdenAdapter(List<Orden> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrdenAdapter.OrdenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_anime_card, viewGroup, false);
        return new OrdenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenAdapter.OrdenViewHolder ordenViewHolder, int i) {
        ordenViewHolder.imagen.setImageResource(items.get(i).getImagen());
        ordenViewHolder.nombre.setText(items.get(i).getNombre());
        ordenViewHolder.visitas.setText("Visitas:"+String.valueOf(items.get(i).getVisitas()));

    }

    @Override
    public int getItemCount() {
        return items.size();

    }

    public class OrdenViewHolder extends RecyclerView.ViewHolder {

        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;

        public OrdenViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            visitas = (TextView) itemView.findViewById(R.id.visitas);

        }
    }
}
