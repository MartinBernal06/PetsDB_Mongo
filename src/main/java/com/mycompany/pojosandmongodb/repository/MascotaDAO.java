/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojosandmongodb.repository;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.pojosandmongodb.config.MongoClientProvider;
import com.mycompany.pojosandmongodb.exception.DaoException;
import com.mycompany.pojosandmongodb.exception.EntityNotFoundException;
import com.mycompany.pojosandmongodb.model.Mascota;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public class MascotaDAO implements IMascotaDAO {
     private final MongoCollection<Mascota> col;

    public MascotaDAO() {
        this.col = MongoClientProvider.INSTANCE.getCollection("mascotas", Mascota.class);
    }

    @Override
    public ObjectId create(Mascota entity) throws DaoException {
        try {
            if (entity.getId()== null) entity.setId(new ObjectId());
            entity.setCreadoEn(Instant.now());
            col.insertOne(entity);
            return entity.getId();
        } catch (MongoException e) {
            throw new DaoException("Error insertando mascota", e);
        }
    }

    @Override
    public Optional<Mascota> findById(ObjectId _id) throws DaoException {
         try {
            return Optional.ofNullable(col.find(Filters.eq("_id", _id)).first());
        } catch (MongoException e) {
            throw new DaoException("Error consultando mascota por ID", e);
        }
    }

    @Override
    public List<Mascota> findAll() throws DaoException {
         try {
            return col.find().limit(100).into(new ArrayList<>());
        } catch (MongoException e) {
            throw new DaoException("Error consultando todas las mascotas", e);
        }
    }

    @Override
    public boolean update(Mascota entity) throws DaoException, EntityNotFoundException {
        try {
            var result = col.updateOne(
                Filters.eq("_id", entity.getId()),
                Updates.combine(
                    Updates.set("nombre", entity.getNombre()),
                    Updates.set("tipo", entity.getTipo()),
                    Updates.set("persona_id", entity.getPersona_id())
                )
            );
            if (result.getMatchedCount() == 0)
                throw new EntityNotFoundException("Mascota no encontrado: " + entity.getId());
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            throw new DaoException("Error actualizando mascota", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId _id) throws DaoException, EntityNotFoundException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0)
                throw new EntityNotFoundException("Mascota no encontrado: " + _id);
            return true;
        } catch (MongoException e) {
            throw new DaoException("Error eliminando mascota", e);
        }
    }

    @Override
    public long deleteAll() throws DaoException {
         try {
            return col.deleteMany(Filters.exists("_id")).getDeletedCount();
        } catch (MongoException e) {
            throw new DaoException("Error eliminando todos los mascotas", e);
        }
    }

    @Override
    public Optional<Mascota> findByNombre(String nombre) throws DaoException {
        try {
            return Optional.ofNullable(col.find(Filters.eq("nombre", nombre)).first());
        } catch (MongoException e) {
            throw new DaoException("Error consultando mascota por nombre", e);
        }
    }
}
