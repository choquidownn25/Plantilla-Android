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
import com.example.plantilla.sqllite.activity.MainActivityPais;
import com.example.plantilla.sqllite.activity.MainActivityProducto;
import com.example.plantilla.sqllite.activity.ModifyPaisActivity;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.database.DBProducto;
import com.example.plantilla.sqllite.models.Pais;
import com.example.plantilla.sqllite.models.Producto;

import java.util.ArrayList;

public class ProductoRecyclerAdapter extends RecyclerView.Adapter<ProductoRecyclerAdapter.ProductoViewHolder> {

    //<editor-fold desc="Atributos">
    private DBProducto dbProducto;
    public ImageView overflow;
    private Context mContext;
    private Context mainActivityProducto;
    private ArrayList<Producto> productos;

    //</editor-fold>

    public ProductoRecyclerAdapter(ArrayList<Producto> productos, Context mContext) {
        this.productos = productos;
        this.mContext = mContext;
    }

    public ProductoRecyclerAdapter(MainActivityProducto mainActivityProducto, ArrayList<Producto> productoArrayList) {
        productos = productoArrayList;
        this.mainActivityProducto = mainActivityProducto;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_anime_card_sqllite_producto, viewGroup, false);
        return new ProductoRecyclerAdapter.ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder productoViewHolder, int i) {

        String datoCodigo = String.valueOf(productos.get(i).getId());
        productoViewHolder.idTextView.setText(datoCodigo);
        productoViewHolder.nombre.setText(productos.get(i).getNombre());
        productoViewHolder.activo.setChecked(productos.get(i).isActivo());
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre;
        public CheckBox activo;
        public TextView idTextView;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            //Campos para mapear
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            activo = (CheckBox) itemView.findViewById(R.id.checkBox);
            idTextView = (TextView) itemView.findViewById(R.id.id);

                itemView.setOnClickListener(new View.OnClickListener()  {

                    @Override
                    public void onClick(View v) {

                        String id = idTextView.getText().toString();
                        String title = nombre.getText().toString();
                        boolean desc = Boolean.parseBoolean(activo.getText().toString());

                        Intent modify_intent = new Intent(v.getContext(), ModifyPaisActivity.class);
                        modify_intent.putExtra("title", title);
                        modify_intent.putExtra("activo", desc);
                        modify_intent.putExtra("id", id);

                        v.getContext().startActivity(modify_intent);
                    }
                });



        }
    }
}
