package com.bixi.bixi.Pojos;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by telynet on 1/5/2017.
 */

public class Oferta {
    private String nombre;
    private String precio;
    private String direccionImagen;
    private String descripcion;

    private String titulo;

    private List<String> images = null;

    public Oferta(String nombre,String precio, String direccionImagen, String titulo,String descripcion)
    {
        this.nombre = nombre;
        this.precio = precio;
        this.direccionImagen = direccionImagen;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDireccionImagen() {
        return direccionImagen;
    }

    public void setDireccionImagen(String direccionImagen) {
        this.direccionImagen = direccionImagen;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


}

