package com.example.plantilla.Perfil;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.example.plantilla.R;
import com.google.firebase.auth.FirebaseAuth;

public class PerfilActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth; //Clase Firebase
    private TextView textNombreCliente;
    private TextView textEmail;
    private TextView textId;
    private  String Nombre;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textNombreCliente = (TextView)findViewById(R.id.textNombreCliente) ;
        textEmail = (TextView)findViewById(R.id.textEmail);
        textId = (TextView)findViewById(R.id.textId);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Nombre=firebaseAuth.getCurrentUser().getDisplayName();
            textId.setText(firebaseAuth.getCurrentUser().getUid());
            if (Nombre.isEmpty()){
                textNombreCliente.setText(firebaseAuth.getCurrentUser().getUid());
            }else {
                textNombreCliente.setText(Nombre);
            }
            textEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        }
    }


}
