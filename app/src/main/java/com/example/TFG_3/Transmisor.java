package com.example.TFG_3;

import java.util.Objects;

public class Transmisor {

    private String idBeacon;
    private String nombre;
    private String descripcion;
    private String info;
    private int piso;


    public Transmisor(String idBeacon, String nombre, String descripcion, String info, int piso) {
        this.idBeacon = idBeacon;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.info = info;
        this.piso = piso;

    }
    public Transmisor(String idBeacon, String nombre) {
        this.idBeacon = idBeacon;
        this.nombre = nombre;
    }

    public String getBeacon() {
        return idBeacon;
    }


    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getInfo() {
        return info;
    }
    public int getPiso() {
        return piso;
    }

    @Override
    public String toString() {
        return "Transmisor{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", Informacion='" + info + '\'' +
                ", piso=" + piso +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transmisor that = (Transmisor) o;
        return Objects.equals(idBeacon, that.idBeacon) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(info, that.info);
    }

}
