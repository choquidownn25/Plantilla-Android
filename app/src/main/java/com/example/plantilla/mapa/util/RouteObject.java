package com.example.plantilla.mapa.util;

import java.util.List;
//Ruta por objetos
public class RouteObject {
    private List<LegsObject> legs;
    public RouteObject(List<LegsObject> legs) {
        this.legs = legs;
    }
    public List<LegsObject> getLegs() {
        return legs;
    }
}