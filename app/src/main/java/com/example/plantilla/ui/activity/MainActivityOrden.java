package com.example.plantilla.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.plantilla.R;
import com.example.plantilla.ui.adapter.OrdenAdapter;
import com.example.plantilla.ui.models.Orden;

import java.util.ArrayList;
import java.util.List;

public class MainActivityOrden extends AppCompatActivity {

    /*
    Declarar instancias globales
   */
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ordenes);

        // my_child_toolbar is defined in the layout file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Inicializar Animes
        List<Orden> items = new ArrayList<>();

        items.add(new Orden(R.drawable.angel, "Angel Beats", 230));
        items.add(new Orden(R.drawable.death, "Death Note", 456));
        items.add(new Orden(R.drawable.fate, "Fate Stay Night", 342));
        items.add(new Orden(R.drawable.nhk, "Welcome to the NHK", 645));
        items.add(new Orden(R.drawable.suzumiya, "Suzumiya Haruhi", 459));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.card_recycler_view);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new OrdenAdapter(items);
        recycler.setAdapter(adapter);

    }
}
