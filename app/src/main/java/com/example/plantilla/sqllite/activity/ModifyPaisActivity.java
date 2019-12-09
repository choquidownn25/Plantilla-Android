package com.example.plantilla.sqllite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import com.example.plantilla.R;
import com.example.plantilla.sqllite.database.DBManager;

public class ModifyPaisActivity extends AppCompatActivity implements OnClickListener {

    //<editor-fold desc="Atributos">
    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    private long _id;
    private DBManager dbManager;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    //</editor-fold>


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_record);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);

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
        String desc = intent.getStringExtra("desc");

        _id = Long.parseLong(id);

        titleText.setText(name);
        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    //Click
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_update:
                String title = titleText.getText().toString();
                String desc = descText.getText().toString();

                dbManager.update(_id, title, desc);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }
    //Retorno al inicio
    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivityPais.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
