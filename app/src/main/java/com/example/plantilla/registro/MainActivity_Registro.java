package com.example.plantilla.registro;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.plantilla.R;
import com.example.plantilla.ui.activity.MainActivity;


/**
 * Created by choqu_000 on 11/02/2017.
 */

public class MainActivity_Registro extends Activity {

    //Creacion de la actividad

    protected void  onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_registro_drogeria);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(MainActivity_Registro.this, MainActivity.class);
        startActivity(intent);
    }

}
