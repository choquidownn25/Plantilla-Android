package com.example.plantilla.ui.tab.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantilla.R;

public class TabCFragment extends Fragment {

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
        // Inflar el diseño de este fragmento.
        return inflater.inflate(R.layout.fragment_tab_c, container, false);
    }

}
