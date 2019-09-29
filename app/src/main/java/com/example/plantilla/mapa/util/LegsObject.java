package com.example.plantilla.mapa.util;

import java.util.List;
//Leyendas objetos
public class LegsObject {

    private List<StepsObject> steps;
    //Distancia
    private DistanceObject distance;
    //Duración
    private DurationObject duration;

    public LegsObject(DurationObject duration, DistanceObject distance, List<StepsObject> steps) {
        this.duration = duration;
        this.distance = distance;
        this.steps = steps;
    }

    public List<StepsObject> getSteps() {
        return steps;
    }

    public DistanceObject getDistance() {
        return distance;
    }

    public DurationObject getDuration() {
        return duration;
    }
}