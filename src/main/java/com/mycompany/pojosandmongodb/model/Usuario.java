/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojosandmongodb.model;

import java.time.Instant;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public class Usuario {
    private ObjectId _id;

    private String nombre;
    private Integer edad;

    private Direccion direccion;          // Objeto embebido
    private List<String> tags;            // Lista de strings
    private List<Telefono> telefonos;     // Lista de objetos embebidos

    @BsonProperty("creado_en")
    private Instant creadoEn;

    public Usuario() {
    }

    public Usuario(ObjectId _id, String nombre, Integer edad, Direccion direccion, List<String> tags, List<Telefono> telefonos) {
        this._id = _id;
        this.nombre = nombre;
        this.edad = edad;
        this.direccion = direccion;
        this.tags = tags;
        this.telefonos = telefonos;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public Instant getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Instant creadoEn) {
        this.creadoEn = creadoEn;
    }
    
    
}
