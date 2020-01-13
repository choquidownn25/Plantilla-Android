package com.example.plantilla.sqllite.models;

public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private boolean activo;

    public Producto(int id, String nombre,  boolean activo) {
        this.id = id;
        this.nombre = nombre;

        this.activo = activo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
