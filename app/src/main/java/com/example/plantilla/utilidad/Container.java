package com.example.plantilla.utilidad;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.plantilla.ui.activity.GraficoActivity;
import com.example.plantilla.webrtc.ConnectActivity;
import com.example.plantilla.webrtc.localizacion.MainActivityLocalizacion;

public class Container extends TabActivity {

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        TabHost host = getTabHost();
        host.addTab(host.newTabSpec("one").setIndicator("Video").setContent(new Intent(this, ConnectActivity.class)));
        host.addTab(host.newTabSpec("twoo").setIndicator("Mapa").setContent(new Intent(this, MainActivityLocalizacion.class)));
        host.addTab(host.newTabSpec("three").setIndicator("Grafico").setContent(new Intent(this, GraficoActivity.class)));

    }
}
