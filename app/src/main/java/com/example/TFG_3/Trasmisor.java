package com.example.TFG_3;

import org.altbeacon.beacon.Beacon;

public class Trasmisor {

    private Beacon beacon;

    private double distancia;
    private String nombre;
    private String descripcion;
    private String ubicacion;

    public Trasmisor(Beacon beacon, String nombre, String descripcion, String ubicacion) {
        this.beacon = beacon;
        this.distancia = beacon.getDistance();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }
    public Trasmisor(Beacon beacon, String nombre) {
        this.beacon = beacon;
        this.distancia = beacon.getDistance();
        this.nombre = nombre;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }

    public double getDistancia(){
        return beacon.getDistance();
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString() {
        return "Trasmisor{" +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
