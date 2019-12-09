package com.example.plantilla.sqllite.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.sqllite.activity.MainActivityPais;
import com.example.plantilla.sqllite.activity.ModifyPaisActivity;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.helper.DatabaseHelper;
import com.example.plantilla.sqllite.models.Beneficiary;
import com.example.plantilla.sqllite.models.Pais;

import java.util.ArrayList;

public class PaisRecyclerAdapter extends RecyclerView.Adapter<PaisRecyclerAdapter.PaisViewHolder>  {

    //<editor-fold desc="Atributos">
    private DBManager dbManager;
    public ImageView overflow;
    private Context mContext;
    private Context mainActivityPais;
    private ArrayList<Pais> pais;

    //</editor-fold>

    public PaisRecyclerAdapter(ArrayList<Pais> pais, Context mContext) {
        this.pais = pais;
        this.mContext = mContext;
    }

    public PaisRecyclerAdapter(MainActivityPais mainActivityPais, ArrayList<Pais> paisArrayList) {
        pais = paisArrayList;
        this.mainActivityPais = mainActivityPais;
    }

    @NonNull
    @Override
    public PaisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_anime_card_sqllite_pais, viewGroup, false);
        return new PaisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder paisViewHolder, int i) {
        String datoCodigo = String.valueOf(pais.get(i).getId());
        paisViewHolder.idTextView.setText(datoCodigo);
        paisViewHolder.nombre.setText(pais.get(i).getNombre());
        paisViewHolder.description.setText("Descripcion : "+pais.get(i).getDescription());

    }

    @Override
    public int getItemCount() {
        return pais.size();
    }

    public class PaisViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView description;
        public TextView idTextView;

        public PaisViewHolder(@NonNull View itemView) {
            super(itemView);
            //Campos para mapear
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            description = (TextView) itemView.findViewById(R.id.visitas);
            idTextView = (TextView) itemView.findViewById(R.id.id);
            itemView.setOnClickListener(new View.OnClickListener()  {

                @Override
                public void onClick(View v) {

                    String id = idTextView.getText().toString();
                    String title = nombre.getText().toString();
                    String desc = description.getText().toString();

                    Intent modify_intent = new Intent(v.getContext(), ModifyPaisActivity.class);
                    modify_intent.putExtra("title", title);
                    modify_intent.putExtra("desc", desc);
                    modify_intent.putExtra("id", id);

                    v.getContext().startActivity(modify_intent);
                }
            });

        }
    }
}
