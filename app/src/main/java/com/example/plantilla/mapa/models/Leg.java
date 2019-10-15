package com.example.plantilla.mapa.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Leg {

    @SerializedName("distance")
    @Expose
    private Distancia distance;
    @SerializedName("duration")
    @Expose
    private Duracion duration;

    /**
     *
     * @return
     * The distance
     */
    public Distancia getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Distancia distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     * The duration
     */
    public Duracion getDuration() {
        return duration;
    }

    /**
     *
     * @param duration
     * The duration
     */
    public void setDuration(Duracion duration) {
        this.duration = duration;
    }

}
