package com.example.plantilla.webrtc.datos;

/**
 * Created by choqu_000 on 28/09/2017.
 */

public class Persona {

    private String nombre;
    private char genero;

    public Persona(String nombre, char genero) {
        this.setNombre(nombre);
        this.setGenero(genero);
    }

    public String getNombre() {
        return nombre;
    }

    public char getGenero() {
        return genero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }
}
