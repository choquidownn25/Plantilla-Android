package com.example.plantilla.mapa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Route;

public class Mapa {

    @SerializedName("routes")
    @Expose
    private List<Ruta> routes = new ArrayList<>();

    /**
     *
     * @return
     * The routes
     */
    public List<Ruta> getRoutes() {
        return routes;
    }

    /**
     *
     * @param routes
     * The routes
     */
    public void setRoutes(List<Ruta> routes) {
        this.routes = routes;
    }
}
