package com.example.TFG_3;

import org.altbeacon.beacon.Beacon;

public class Trasmisor {

    private String idBeacon;
    private String nombre;
    private String descripcion;
    private String ubicacion;

    public Trasmisor(String idBeacon, String nombre, String descripcion, String ubicacion) {
        this.idBeacon = idBeacon;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }
    public Trasmisor(String idBeacon, String nombre) {
        this.idBeacon = idBeacon;
        this.nombre = nombre;
    }

    public String getBeacon() {
        return idBeacon;
    }

    public void setBeacon(String idBeacon) {
        this.idBeacon = idBeacon;
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
