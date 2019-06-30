package com.example.plantilla.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.plantilla.R;
import com.example.plantilla.api.GetDataService;
import com.example.plantilla.api.RetrofitClientInstance;
import com.example.plantilla.ui.adapter.CustomAdapter;
import com.example.plantilla.ui.models.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRecicleview extends AppCompatActivity {

    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carview);

        progressDoalog = new ProgressDialog(MainActivityRecicleview.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Crear un manejador para la interfaz RetrofitInstance*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<RetroPhoto>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {

            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MainActivityRecicleview.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Método para generar una lista de datos utilizando RecyclerView con un adaptador personalizado*/
    private void generateDataList(List<RetroPhoto> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivityRecicleview.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
