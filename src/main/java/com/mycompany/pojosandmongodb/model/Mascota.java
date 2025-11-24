/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojosandmongodb.model;

import java.time.Instant;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public class Mascota {

    private ObjectId _id;
    private ObjectId persona_id; // FK para hacer lookup
    private String nombre;
    private String tipo; // perro, gato, etc.
    @BsonProperty("creado_en")
    private Instant creadoEn;
    
    public Mascota() {
    }

    public Mascota(ObjectId persona_id, String nombre, String tipo) {
        this.persona_id = persona_id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Mascota(ObjectId _id, String nombre, String tipo, ObjectId persona_id) {
        this._id = _id;
        this.persona_id = persona_id;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public Instant getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Instant creadoEn) {
        this.creadoEn = creadoEn;
    }
    
    

    public ObjectId getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(ObjectId persona_id) {
        this.persona_id = persona_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Mascota{" + "_id=" + _id + ", persona_id=" + persona_id + ", nombre=" + nombre + ", tipo=" + tipo + ", creadoEn=" + creadoEn + '}';
    }
    
    

}
