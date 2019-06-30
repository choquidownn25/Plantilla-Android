package com.example.plantilla.ui.tab.models;

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
