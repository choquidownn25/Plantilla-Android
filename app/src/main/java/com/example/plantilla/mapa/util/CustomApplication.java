package com.example.plantilla.mapa.util;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.example.plantilla.mapa.generic.VolleySingleton;

public class CustomApplication extends Application{
    private RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        //Patron de diseño singleston
        requestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
    }
    public RequestQueue getVolleyRequestQueue(){
        return requestQueue;
    }
}