package com.example.plantilla.webrtc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.plantilla.R;

import com.example.plantilla.youtube.MainActivityDemoYoutube;
import com.example.plantilla.youtube.MainActivityDemoYoutubeDos;
import com.example.plantilla.youtube.MainActivityDemoYoutubeTres;

public class MainActivityVideoYoutube extends AppCompatActivity {

    private Button buttonSendvideouno;
    private Button buttonSendvideodos;
    private Button buttonSendvideotres;
    private Button buttonSendvideocuatro;
    Context context;
    Activity contexts;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityvideo);

        context = getApplicationContext();
        contexts = getParent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setTitle(getResources().getString(R.string.video));
//        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.white));
        }


        buttonSendvideouno = (Button) findViewById(R.id.buttonSendvideouno);
        buttonSendvideodos = (Button) findViewById(R.id.buttonSendvideodos);
        buttonSendvideotres = (Button) findViewById(R.id.buttonSendvideotres);


        buttonSendvideouno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityVideoYoutube.this, MainActivityDemoYoutube.class);
                startActivity(intent);
            }
        });

        buttonSendvideodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityVideoYoutube.this, MainActivityDemoYoutubeDos.class);
                startActivity(intent);
            }
        });


        buttonSendvideotres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityVideoYoutube.this, MainActivityDemoYoutubeTres.class);
                startActivity(intent);
            }
        });



    }

}



