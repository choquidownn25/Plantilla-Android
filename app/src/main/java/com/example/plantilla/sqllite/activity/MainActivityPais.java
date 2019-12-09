package com.example.plantilla.sqllite.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.plantilla.R;
import com.example.plantilla.sqllite.adapter.PaisRecyclerAdapter;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.helper.DatabaseHelper;
import com.example.plantilla.sqllite.models.Pais;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPais extends  AppCompatActivity {

    //<editor-fold desc="Atributos">
    /*
    Declarar instancias globales
     */
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private DBManager dbManager;
    final String[] from = new String[] {
            DatabaseHelper._ID,
            DatabaseHelper.SUBJECT,
            DatabaseHelper.DESC
    };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc };
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private PaisRecyclerAdapter adapter;
    ArrayList<Pais> paisArrayList=new ArrayList<>();
    //</editor-fold>

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqllite_pais);
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        // Inicializar Animes

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SHOW INPUT DIALOG
                //showDialog();
                Intent intent = new Intent(MainActivityPais.this, AddPaisActivity.class);
                startActivity(intent);
            }
        });

        //recycler
        recycler= (RecyclerView) findViewById(R.id.card_recycler_view_pais);

        //SET PROPS
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        adapter=new PaisRecyclerAdapter(this,paisArrayList);

        //RETRIEVE
        retrieve();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );
    }
    //RETREIEV
    private void retrieve()
    {
        paisArrayList.clear();

        DBManager db=new DBManager(this);
        db.open();

        //Recibe el cursor
        Cursor c=db.getAll();

        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String nombre=c.getString(1);
            String description=c.getString(2);

            Pais p=new Pais(id,nombre,description);

            //ADD al array lis del pais
            paisArrayList.add(p);
        }

        //Comprueba si la lista no esta vacía
        if(!(paisArrayList.size()<1))
        {
            recycler.setAdapter(adapter);
        }

        db.close();;

    }
}
