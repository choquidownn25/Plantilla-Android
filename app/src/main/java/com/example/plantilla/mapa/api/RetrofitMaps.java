package com.example.plantilla.mapa.api;

import com.example.plantilla.mapa.models.Mapa;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Api para consulta a google maps
 */
public interface RetrofitMaps {

    /*
     * Retrofit obtiene anotaciones con nuestra URL
     * Y nuestro método que nos devolverá los detalles
     * @GET("api/directions/json?key=AIzaSyC22GfkHu9FdgT9SwdCWMwKX1a4aohGifM")
     */
    @GET("api/directions/json?key=AIzaSyDatvWUmrz-N76InvqZ2_ov-1B9-htMSxI")
    Call<Mapa> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);

}
