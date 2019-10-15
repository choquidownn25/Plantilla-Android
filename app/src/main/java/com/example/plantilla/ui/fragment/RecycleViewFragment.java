package com.example.plantilla.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plantilla.R;
import com.example.plantilla.ui.adapter.SimpleAdapter;

/**
 * @author YanLu
 * @since 17/9/3
 */

public class RecycleViewFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView mRecyclerView;
    private SimpleAdapter mAdapter;
    public RecycleViewFragment() {
    }

    /**
     * Devuelve una nueva instancia de este fragmento para la sección dada
     * numero
     *
     */
    public static RecycleViewFragment newInstance(int sectionNumber) {
        RecycleViewFragment fragment = new RecycleViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //int section = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = inflater.inflate(R.layout.fragment_rv, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new SimpleAdapter();
        mAdapter.add("Cali - Bogota");
        mAdapter.add("Cali - Medellin");
        mAdapter.add("Cali - Popayan");
        mAdapter.add("Cali - Pereira");
        mAdapter.add("Cali - Manizales");
        mAdapter.add("Cali - Barranquilla");
        mAdapter.add("Cali - Cartagenra");
        mAdapter.add("Cali - Sincelejo");
        mAdapter.add("Cali - Riohacha");
        mAdapter.add("Cali - Valledupar");
        mAdapter.add("Cali - Cucuta");
        mAdapter.add("Cali - Bucaramanga");
        mAdapter.add("Cali - Tunja");
        mAdapter.add("Cali - Villavicencio");
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
