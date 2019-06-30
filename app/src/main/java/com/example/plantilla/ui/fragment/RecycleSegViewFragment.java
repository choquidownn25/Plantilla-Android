package com.example.plantilla.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.plantilla.R;
import com.example.plantilla.api.GetDataService;
import com.example.plantilla.api.RetrofitClientInstance;
import com.example.plantilla.ui.activity.MainActivityRecicleview;
import com.example.plantilla.ui.adapter.CustomAdapter;
import com.example.plantilla.ui.adapter.SimpleAdapter;
import com.example.plantilla.ui.models.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleSegViewFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView mRecyclerView;
    private CustomAdapter mAdapter;
    ProgressDialog progressDoalog;

    public RecycleSegViewFragment() {
    }

    /**
     * Devuelve una nueva instancia de este fragmento para la sección dada
     * numero
     */
    public static RecycleSegViewFragment newInstance(int sectionNumber) {
        RecycleSegViewFragment fragment = new RecycleSegViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rv, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);

        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        // usa esta configuración para mejorar el rendimiento si sabes que cambia
        // en el contenido no cambia el tamaño del diseño de RecyclerView
        mRecyclerView.setHasFixedSize(true);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {

            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();

                mAdapter = new CustomAdapter(getActivity(),response.body());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        //return super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }




}
