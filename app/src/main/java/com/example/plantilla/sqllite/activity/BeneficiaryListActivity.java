package com.example.plantilla.sqllite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.plantilla.R;
import com.example.plantilla.sqllite.adapter.BeneficiaryRecyclerAdapter;
import com.example.plantilla.sqllite.models.Beneficiary;
import com.example.plantilla.sqllite.sql.DatabaseHelper;

import java.util.ArrayList;

public class BeneficiaryListActivity extends AppCompatActivity {

    //region Atributos
    private AppCompatActivity activity = BeneficiaryListActivity.this;
    private Context context = BeneficiaryListActivity.this;
    private RecyclerView recyclerViewBeneficiary;
    private ArrayList<Beneficiary> listBeneficiary;
    private BeneficiaryRecyclerAdapter beneficiaryRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private SearchView searchBox;
    private ArrayList<Beneficiary> filteredList;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        initViews();
        initObjects();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("NAME")) {

            //get all needed extras intent

            int id = getIntent().getExtras().getInt("ID");
            String email = getIntent().getExtras().getString("EMAIL");
            String address = getIntent().getExtras().getString("ADDRESS");
            String country = getIntent().getExtras().getString("COUNTRY");
        }else{

            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();

        }
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );
    }

    /**
     * Este método es para inicializar vistas
     */
    private void initViews() {
        recyclerViewBeneficiary = (RecyclerView) findViewById(R.id.recyclerViewBeneficiary);
    }

    /**
     * Este método es para inicializar los objetos que se utilizarán.
     */
    private void initObjects() {
        listBeneficiary = new ArrayList<>();
        beneficiaryRecyclerAdapter = new BeneficiaryRecyclerAdapter(listBeneficiary, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBeneficiary.setLayoutManager(mLayoutManager);
        recyclerViewBeneficiary.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeneficiary.setHasFixedSize(true);
        recyclerViewBeneficiary.setAdapter(beneficiaryRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);
        getDataFromSQLite();
    }

    /**
     * Este método es buscar todos los registros de usuario de SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask se utiliza para que la operación SQLite no bloquee el subproceso de la
        // interfaz de usuario
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listBeneficiary.clear();
                listBeneficiary.addAll(databaseHelper. getAllBeneficiary());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                beneficiaryRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
