package com.example.plantilla.sqllite.models;

public class Pais {
    private int id;
    private String nombre;
    private String description;

    public Pais(int id, String nombre, String description) {
        this.id = id;
        this.nombre = nombre;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
