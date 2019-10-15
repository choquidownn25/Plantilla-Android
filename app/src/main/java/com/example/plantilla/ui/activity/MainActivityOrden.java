package com.example.plantilla.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.plantilla.R;
import com.example.plantilla.ui.adapter.OrdenAdapter;
import com.example.plantilla.ui.models.Orden;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivityOrden extends AppCompatActivity {

    /*
    Declarar instancias globales
   */
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private static final String[] Servicio = {
            "General",
            "Preferencial",
            "Vip"
    };
    private static final String[] TipoServicio = {
            "Moto",
            "Auto",
            "Bus",
            "Avion"
    };
    private Button bntFormularioCondireccion;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ordenes);

        // my_child_toolbar is defined in the layout file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        MaterialSpinner spinners = (MaterialSpinner) findViewById(R.id.spinner);
        spinners.setItems(Servicio);
        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinners);
        spinner.setItems(TipoServicio);
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
        bntFormularioCondireccion = (Button) findViewById(R.id.bntFormularioCondireccion);
        bntFormularioCondireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopup();
            }
        });
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );
    }

    //Sale mensaje de Acetado
    private void callPopup() {
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.custom_layout, null);

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        ((Button) popupView.findViewById(R.id.btnok))
                .setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
                    public void onClick(View arg0) {
                        popupWindow.dismiss();
                    }

                });

    }

}
