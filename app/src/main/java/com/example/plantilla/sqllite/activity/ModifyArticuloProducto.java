package com.example.plantilla.sqllite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.plantilla.R;
import com.example.plantilla.sqllite.database.DBArticulo;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.database.DBProducto;

public class ModifyArticuloProducto extends AppCompatActivity implements View.OnClickListener {

    //<editor-fold desc="Atributos">
    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private CheckBox checkBox3;
    private long _id;
    private DBManager dbManager;
    private DBArticulo dbArticulo;
    private DBProducto dbProducto;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //</editor-fold>


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_record);

        dbArticulo = new DBArticulo(this);
        dbArticulo.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        boolean activo = Boolean.parseBoolean(intent.getStringExtra("activo"));
        //boolean datoAct = Boolean.parseBoolean(intent.getStringExtra("activo"));
        _id = Long.parseLong(id);

        titleText.setText(name);

        checkBox3.setChecked(activo);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_update:
                String title = titleText.getText().toString();

                boolean activo = checkBox3.isChecked();
                dbProducto.update(_id, title, activo);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbArticulo.delete(_id);
                this.returnHome();
                break;
        }
    }

    //Retorno al inicio
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ModifyArticuloProducto.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
