package com.example.plantilla.webrtc.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import okhttp3.logging.HttpLoggingInterceptor;
public class ApiCliente {
    public static final String BASE_URL = "http://sigti.webcol.net/directv/";


    //Metodo de conexion al servidor
    public static Retrofit getCliente(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //Retofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        return  retrofit;
    }
}
