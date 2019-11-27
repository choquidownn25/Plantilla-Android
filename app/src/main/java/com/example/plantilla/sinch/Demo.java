package com.example.plantilla.sinch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.example.plantilla.R;
import com.example.plantilla.sinch.push.LoginActivity;

public class Demo extends AppCompatActivity {

    private Button chat;
    private Button video;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_sinch);

        chat = (Button) findViewById(R.id.buttonchat);
        video = (Button) findViewById(R.id.buttonvideo);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String action;
                Intent intent = new Intent(Demo.this, com.example.plantilla.sinch.message.ui.activity.LoginActivity.class);
                startActivity(intent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Demo.this, com.example.plantilla.sinch.push.LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}