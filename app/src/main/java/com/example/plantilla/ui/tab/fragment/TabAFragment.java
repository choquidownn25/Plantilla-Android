package com.example.plantilla.ui.tab.fragment;

import android.R.layout;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.plantilla.R;
import com.example.plantilla.ui.tab.models.AndroidFlavor;
import com.example.plantilla.ui.tab.models.Persona;
import com.example.plantilla.ui.utilidades.AndroidFlavorAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

import static android.R.id.empty;
import static com.example.plantilla.R.layout.simple_list_item_1;

public class TabAFragment extends Fragment {

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
            new AndroidFlavor("Cali", "1500", R.drawable.cupcake),
            new AndroidFlavor("Medellin", "1600", R.drawable.donut),
            new AndroidFlavor("Bogota", "2000", R.drawable.eclair),
            new AndroidFlavor("Barraquilla", "2222", R.drawable.froyo),
            new AndroidFlavor("Manizales", "2370", R.drawable.gingerbread),
            new AndroidFlavor("Pereira", "3032", R.drawable.honeycomb),
            new AndroidFlavor("Bucaramanga", "4040", R.drawable.icecream),
            new AndroidFlavor("Cucuta", "4143", R.drawable.jellybean),
            new AndroidFlavor("Ibague", "4444", R.drawable.kitkat),
            new AndroidFlavor("Armenia", "5051", R.drawable.lollipop)
    };

    public TabAFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tab_a, container, false);
        // Inflar el diseño de este fragmento.

        flavorAdapter = new AndroidFlavorAdapter(getActivity(), Arrays.asList(androidFlavors));

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) view.findViewById(R.id.listview_flavor);
        listView.setAdapter(flavorAdapter);
        return view;
    }







}
