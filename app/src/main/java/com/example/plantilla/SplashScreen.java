package com.example.plantilla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.plantilla.account.activity.LoginActivity;

public class SplashScreen extends Activity {

    private Context contexts;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String message = "", url = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Bloquear orientación de pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        contexts = getApplication();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            Intent intent = getIntent();
            message = intent.getStringExtra("message");
            url = intent.getStringExtra("url");
            Log.d("notification Dato", message + url);
        } catch (Exception e) {
            Log.d("error notificacion", e.toString());
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                intent.putExtra("message", message);
                intent.putExtra("url", url);
                startActivity(intent);
                finish();
            }
        });
        thread.start();
    }


}