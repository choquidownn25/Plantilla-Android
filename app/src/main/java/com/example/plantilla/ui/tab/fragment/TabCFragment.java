package com.example.plantilla.ui.tab.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.plantilla.R;
import com.example.plantilla.ui.tab.models.AndroidFlavor;
import com.example.plantilla.ui.tab.models.Persona;
import com.example.plantilla.ui.utilidades.AndroidFlavorAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class TabCFragment extends Fragment {

    private EditText roomEditText;
    private ListView roomListView;
    private ImageButton addFavoriteButton;
    private ArrayList<String> roomList;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPref;
    private String keyprefRoom;
    private String keyprefRoomList;
    private ArrayList<Persona> roomLists;
    private ArrayList<Persona> listapersonas;
    private ArrayAdapter<Persona> adapters;
    private ImageButton add_favorite_chat;
    private static final String TAG = "TabAFragment";
    private AndroidFlavorAdapter flavorAdapter;
    AndroidFlavor[] androidFlavors = {
            new AndroidFlavor("Cali", "1.5", R.drawable.cupcake),
            new AndroidFlavor("Medellin", "1.6", R.drawable.donut),
            new AndroidFlavor("Bogota", "2.0-2.1", R.drawable.eclair),
            new AndroidFlavor("Barraquilla", "2.2-2.2.3", R.drawable.froyo),
            new AndroidFlavor("Manizales", "2.3-2.3.7", R.drawable.gingerbread),
            new AndroidFlavor("Pereira", "3.0-3.2.6", R.drawable.honeycomb),
            new AndroidFlavor("Bucaramanga", "4.0-4.0.4", R.drawable.icecream),
            new AndroidFlavor("Cucuta", "4.1-4.3.1", R.drawable.jellybean),
            new AndroidFlavor("Ibague", "4.4-4.4.4", R.drawable.kitkat),
            new AndroidFlavor("Armenia", "5.0-5.1.1", R.drawable.lollipop)
    };

    public TabCFragment() {
        // Constructor público vacío requerido
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_c, container, false);
        // Inflar el diseño de este fragmento.

        flavorAdapter = new AndroidFlavorAdapter(getActivity(), Arrays.asList(androidFlavors));

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) view.findViewById(R.id.listview_flavor);
        listView.setAdapter(flavorAdapter);
        return view;
    }

}
