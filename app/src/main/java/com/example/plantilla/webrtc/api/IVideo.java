package com.example.plantilla.webrtc.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IVideo {
    @POST("videoconferencia.php")
    Call<Boolean> ordenBasica(@Field("nombre") String nombre,
                              @Field("lon") String lon,
                              @Field("lat") String lat);
}
