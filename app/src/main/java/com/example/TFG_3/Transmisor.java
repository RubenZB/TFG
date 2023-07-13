package com.example.TFG_3;

public class Transmisor {

    private String idBeacon;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String imagen;



    public Transmisor(String idBeacon, String nombre, String descripcion, String ubicacion, String imagen) {
        this.idBeacon = idBeacon;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }
    public Transmisor(String idBeacon, String nombre) {
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
    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}
    @Override
    public String toString() {
        return "Transmisor{" +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
