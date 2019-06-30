package com.example.plantilla.ui.models;

public class Orden {
    private int imagen;
    private String nombre;
    private int visitas;

    public Orden(int imagen, String nombre, int visitas) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.visitas = visitas;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVisitas() {
        return visitas;
    }

    public int getImagen() {
        return imagen;
    }
}
