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
import com.example.plantilla.sqllite.adapter.ProductoRecyclerAdapter;
import com.example.plantilla.sqllite.database.DBArticulo;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.database.DBProducto;
import com.example.plantilla.sqllite.helper.DatabaseHelpers;
import com.example.plantilla.sqllite.helper.DatabaseHelpersProducto;
import com.example.plantilla.sqllite.models.Articulo;
import com.example.plantilla.sqllite.models.Producto;

import java.util.ArrayList;

public class MainActivityProducto extends AppCompatActivity {

    //<editor-fold desc="Atributos">
    /*
    Declarar instancias globales
     */
    private RecyclerView recycler;
    private RecyclerView.LayoutManager lManager;
    private DBManager dbManager;
    private DBArticulo dbArticulo;
    private DBProducto dbProducto;
    final String[] from = new String[] {
            DatabaseHelpersProducto._ID,
            DatabaseHelpersProducto.NOMBRE,
            String.valueOf(DatabaseHelpersProducto.ACTIVO)
            //DatabaseHelpers.ACTIVO
    };

    final int[] to = new int[] { R.id.id, R.id.title, R.id.checkBox };
    final int[] Activo = new int[] { R.id.id, R.id.nombre, R.id.checkBox};
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private ProductoRecyclerAdapter adapter;
    ArrayList<Producto> productoArrayList=new ArrayList<>();
    //</editor-fold>

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_sqllite_producto);

        dbProducto = new DBProducto(this);
        dbProducto.open();
        Cursor cursor = dbProducto.fetch();
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
                Intent intent = new Intent(MainActivityProducto.this, AddProductoActivity.class);
                startActivity(intent);
            }
        });

        //recycler
        recycler= (RecyclerView) findViewById(R.id.card_recycler_view_pais);

        //SET PROPS
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycler.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        adapter=new ProductoRecyclerAdapter(this,productoArrayList);

        //RETRIEVE
        retrieve();
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );

    }

    //<editor-fold desc="Retorna todo">
    private void retrieve()
    {
        productoArrayList.clear();

        DBManager dbs=new DBManager(this);
        DBArticulo dbt=new DBArticulo(this);
        DBProducto db=new DBProducto(this);
        db.open();

        //Recibe el cursor
        Cursor c=db.getAll();

        //
        //Agregar a la Lista de arreglos
        while (c.moveToNext())
        {
            int id=c.getInt(0);
            String nombre=c.getString(1);
            boolean activo= Boolean.parseBoolean(c.getString(2));
            //String datoActivo=c.getString(3);
            Producto producto=new Producto(id,nombre,activo);

            //ADD al array lis del pais
            productoArrayList.add(producto);
        }

        //Comprueba si la lista no esta vacía
        if(!(productoArrayList.size()<1))
        {
            recycler.setAdapter(adapter);
        }

        db.close();;

    }
    //</editor-fold>

}
