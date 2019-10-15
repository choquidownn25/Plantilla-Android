package com.example.plantilla.mapa.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conecion {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/";
    private Conecion() {}

    //Metodo de conexion al servidor
    public static Retrofit getCliente(){
  //Retofit

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;
    }


    //Local
    public static Retrofit getClienteLocal(){


        //Retofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;
    }

}
