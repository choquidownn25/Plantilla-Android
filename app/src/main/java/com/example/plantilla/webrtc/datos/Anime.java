package com.example.plantilla.webrtc.datos;

/**
 * Created by choqu_000 on 25/05/2016.
 */
public class Anime {
    private int imagen;
    private String nombre;
    private int visitas;

    public Anime(int imagen, String nombre, int visitas) {
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