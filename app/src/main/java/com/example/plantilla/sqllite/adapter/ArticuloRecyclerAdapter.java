package com.example.plantilla.sqllite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.sqllite.activity.MainActivityArticulo;
import com.example.plantilla.sqllite.activity.MainActivityPais;
import com.example.plantilla.sqllite.activity.ModifyArticuloActivity;
import com.example.plantilla.sqllite.activity.ModifyPaisActivity;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.models.Articulo;
import com.example.plantilla.sqllite.models.Pais;

import java.util.ArrayList;

public class ArticuloRecyclerAdapter extends RecyclerView.Adapter<ArticuloRecyclerAdapter.ArticuloViewHolder>  {


    //<editor-fold desc="Atributos">
    private DBManager dbManager;
    public ImageView overflow;
    private Context mContext;
    private Context mainActivityArticulos;
    private ArrayList<Articulo> articulos;

    //</editor-fold>

    //<editor-fold desc="Constructor">
    public ArticuloRecyclerAdapter (ArrayList<Articulo> articulos, Context mContext) {
        this.articulos = articulos;
        this.mContext = mContext;
    }

    public ArticuloRecyclerAdapter(MainActivityArticulo mainActivityArticulo, ArrayList<Articulo> articulosArrayList) {
        articulos = articulosArrayList;
        this.mainActivityArticulos= mainActivityArticulo;
    }
    //</editor-fold>

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_anime_card_sqllite_articulo, viewGroup, false);
        return new ArticuloRecyclerAdapter.ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder articuloViewHolder, int i) {

        String datoCodigo = String.valueOf(articulos.get(i).getId());
        articuloViewHolder.idTextView.setText(datoCodigo);
        articuloViewHolder.nombre.setText(articulos.get(i).getNombre());
        articuloViewHolder.description.setText("Descripcion : "+articulos.get(i).getDescripcion());
        // articuloViewHolder.checkBox3.setChecked(Boolean.parseBoolean(articulos.get(i).getActivo()));
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    public class ArticuloViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView description;
        public TextView idTextView;
        public CheckBox checkBox3;

        public ArticuloViewHolder(@NonNull View itemView) {
            super(itemView);
            //Campos para mapear
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            description = (TextView) itemView.findViewById(R.id.lblactivo);
            idTextView = (TextView) itemView.findViewById(R.id.id);
            itemView.setOnClickListener(new View.OnClickListener()  {

                @Override
                public void onClick(View v) {

                    String id = idTextView.getText().toString();
                    String title = nombre.getText().toString();
                    String desc = description.getText().toString();
                    // boolean activo = checkBox3.isChecked();
                    Intent modify_intent = new Intent(v.getContext(), ModifyArticuloActivity.class);
                    modify_intent.putExtra("title", title);
                    modify_intent.putExtra("desc", desc);
                    modify_intent.putExtra("id", id);
                    // modify_intent.putExtra("activo", activo);
                    v.getContext().startActivity(modify_intent);
                }
            });
        }
    }
}

