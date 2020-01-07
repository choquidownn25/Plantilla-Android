package com.example.plantilla.sqllite.activity;

import android.content.Intent;
import android.database.Cursor;
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
import com.example.plantilla.sqllite.adapter.ArticuloRecyclerAdapter;
import com.example.plantilla.sqllite.adapter.PaisRecyclerAdapter;
import com.example.plantilla.sqllite.database.DBArticulo;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.helper.DatabaseHelper;
import com.example.plantilla.sqllite.helper.DatabaseHelpers;
import com.example.plantilla.sqllite.models.Articulo;
import com.example.plantilla.sqllite.models.Pais;

import java.util.ArrayList;

public class MainActivityArticulo extends AppCompatActivity {

    //<editor-fold desc="Atributos">
    /*
    Declarar instancias globales
     */
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private DBManager dbManager;
    private DBArticulo dbArticulo;
    final String[] from = new String[] {
            DatabaseHelpers._ID,
            DatabaseHelpers.NOMBRE,
            DatabaseHelpers.DESCRIPCION
            //DatabaseHelpers.ACTIVO
    };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc };
    final int[] Activo = new int[] { R.id.id, R.id.nombre, R.id.lblactivo};
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private ArticuloRecyclerAdapter adapter;
    ArrayList<Articulo> articulosArrayList=new ArrayList<>();
    //</editor-fold>


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqllite_articulo);
        dbArticulo = new DBArticulo(this);
        dbArticulo.open();
        Cursor cursor = dbArticulo.fetch();
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
                Intent intent = new Intent(MainActivityArticulo.this, AddArticuloActivity.class);
                startActivity(intent);
            }
        });

        //recycler
        recycler= (RecyclerView) findViewById(R.id.card_recycler_view_pais);

        //SET PROPS
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        adapter=new ArticuloRecyclerAdapter(this,articulosArrayList);

        //RETRIEVE
        retrieve();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );
    }

    //<editor-fold desc="Retorna todo">
    private void retrieve()
    {
        articulosArrayList.clear();

        DBManager dbs=new DBManager(this);
        DBArticulo db=new DBArticulo(this);
        db.open();

        //Recibe el cursor
        Cursor c=db.getAll();

        //
        //Agregar a la Lista de arreglos
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String nombre=c.getString(1);
            String description=c.getString(2);
            //String datoActivo=c.getString(3);
            Articulo articulo=new Articulo(id,nombre,description);

            //ADD al array lis del pais
            articulosArrayList.add(articulo);
        }

        //Comprueba si la lista no esta vacía
        if(!(articulosArrayList.size()<1))
        {
            recycler.setAdapter(adapter);
        }

        db.close();;

    }
    //</editor-fold>
}
