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
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.plantilla.R;
import com.example.plantilla.sqllite.database.DBArticulo;
import com.example.plantilla.sqllite.database.DBManager;
import com.example.plantilla.sqllite.database.DBProducto;

public class AddProductoActivity  extends AppCompatActivity implements View.OnClickListener {

    //<editor-fold desc="Atributos">
    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private CheckBox checkBox3;
    private DBManager dbManager;
    private DBArticulo dbArticulo;
    private DBProducto dbProducto;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //</editor-fold>


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Porducto");
        setContentView(R.layout.activity_add_record_producto);

        subjectEditText = (EditText) findViewById(R.id.subject_edittext);
        descEditText = (EditText) findViewById(R.id.description_edittext);
        checkBox3  = (CheckBox) findViewById(R.id.checkBox3);
        addTodoBtn = (Button) findViewById(R.id.add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.saludo) );

        dbProducto = new DBProducto(this);
        dbProducto.open();
        addTodoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_record:

                final String name = subjectEditText.getText().toString();
                final String desc = descEditText.getText().toString();
                final boolean activo = checkBox3.isChecked();
                dbProducto.insert(name, activo);

                Intent main = new Intent(AddProductoActivity.this, MainActivityProducto.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
