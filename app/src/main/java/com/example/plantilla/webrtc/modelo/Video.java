package com.example.plantilla.webrtc.modelo;

public class Video {

   private int id;
   private String nombre;
   private float lat;
   private float lon;

   public Video(int id, String nombre, float lat, float lon) {
      this.id = id;
      this.nombre = nombre;
      this.lat = lat;
      this.lon = lon;
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

   public float getLat() {
      return lat;
   }

   public void setLat(float lat) {
      this.lat = lat;
   }

   public float getLon() {
      return lon;
   }

   public void setLon(float lon) {
      this.lon = lon;
   }
}
