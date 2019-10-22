package com.example.plantilla.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.ui.activity.ContainerActivity;
import com.example.plantilla.ui.adapter.GridAdapter;
import com.example.plantilla.ui.adapter.GridViewAdapter;
import com.example.plantilla.ui.tab.fragment.TabsActivity;
import com.github.captain_miao.optroundcardview.OptRoundCardView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Jose Sarria
 * @since 17/9/3
 */

public class PlaceholderFragment extends Fragment {

    //<editor-fold desc="Atributos">
    //Atributos
    /**
     * El argumento del fragmento que representa el número de sección para esto.
     * fragmento.
     */
    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_SECTION_TITLE = "section_number";
    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> alName;
    ArrayList<Integer> alImage;

    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private ImageButton OrdenServicio;
    private ImageButton Auditoria;

    //Dependencias de instancias globales
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private Context context;
    private Activity contexts;
    private Context contexto;
    //</editor-fold>

    public PlaceholderFragment() {
    }

    /**
     * Devuelve una nueva instancia de este fragmento para la sección dada
     * numero
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int section = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        contexts = getActivity();
        OptRoundCardView roundCardView;
        context=getActivity();

        // Ubicar argumento en el text view de section_fragment.xml
        String title = getArguments().getString(ARG_SECTION_TITLE);
        TextView titulo = (TextView) rootView.findViewById(R.id.section_labe2);
        titulo.setText(title);

        switch (section){
            case 1:
                roundCardView = (OptRoundCardView) rootView.findViewById(R.id.bottom_card_view);
                roundCardView.showCorner(false, false, false, false);
                break;
            //Otro botom de las cita sqllite
            case 2:
                roundCardView = (OptRoundCardView) rootView.findViewById(R.id.top_card_view);
                roundCardView.showCorner(false, false, false, false);
                break;
            default:
                break;
        }

        Button button = (Button)rootView.findViewById(R.id.actualizar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, TabsActivity.class));
            }
        });

        alName = new ArrayList<>(Arrays.asList(getString(R.string.seccion1), getString(R.string.seccion2), getString(R.string.camara), getString(R.string.sqllite)));
        alImage = new ArrayList<>(Arrays.asList(R.drawable.histoclinica, R.drawable.examen, R.drawable.disponible, R.drawable.consulta));


        // Llama el RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // Numero de columnas
        mLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter(context, alName, alImage);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
