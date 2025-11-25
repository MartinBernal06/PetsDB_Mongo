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
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public class MascotaDAO implements IMascotaDAO {

    private final MongoCollection<Mascota> col;
    private final MongoCollection<Document> colDoc;

    public MascotaDAO() {
        this.col = MongoClientProvider.INSTANCE.getCollection("mascotas", Mascota.class);
        this.colDoc = MongoClientProvider.INSTANCE.getCollection("mascotas", Document.class);
    }

    @Override
    public ObjectId create(Mascota entity) throws DaoException {
        try {
            if (entity.getId() == null) {
                entity.setId(new ObjectId());
            }
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
            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Mascota no encontrado: " + entity.getId());
            }
            return result.getModifiedCount() > 0;
        } catch (MongoException e) {
            throw new DaoException("Error actualizando mascota", e);
        }
    }

    @Override
    public boolean deleteById(ObjectId _id) throws DaoException, EntityNotFoundException {
        try {
            var result = col.deleteOne(Filters.eq("_id", _id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Mascota no encontrado: " + _id);
            }
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

    /**
     * Realiza un JOIN entre mascotas y usuarios usando $lookup. Busca el
     * usuario al que pertenece cada mascota comparando: localField: persona_id
     * (en mascotas) foreignField: _id (en usuarios) El resultado se coloca en
     * el campo "duenio". Luego $unwind convierte el arreglo "duenio" en un
     * documento individual.
     *
     * $lookup: permite unir dos colecciones (similar a LEFT JOIN en SQL).
     * $unwind: separa un arreglo en múltiples documentos.
     */
    public List<Document> findMascotasWithUsuario() throws DaoException {
        try {
            List<Document> pipeline = List.of(
                    new Document("$lookup",
                            new Document("from", "usuarios")
                                    .append("localField", "persona_id")
                                    .append("foreignField", "_id")
                                    .append("as", "duenio")
                    ),
                    new Document("$unwind", "$duenio")
            );

            return colDoc.aggregate(pipeline).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error en lookup mascota-usuario", e);
        }
    }

    /**
     * Busca mascotas filtrando por tipo, y ordena los resultados por nombre
     * ascendente.
     *
     * $match: filtra documentos según una condición. $sort: ordena los
     * resultados; 1 = ascendente, -1 = descendente.
     */
    public List<Mascota> findByTipoSorted(String tipo) throws DaoException {
        try {
            List<Document> pipeline = List.of(
                    new Document("$match", new Document("tipo", tipo)),
                    new Document("$sort", new Document("nombre", 1))
            );

            return col.aggregate(pipeline, Mascota.class).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error filtrando por tipo", e);
        }
    }

    /**
     * Agrupa las mascotas por persona_id y cuenta cuántas mascotas tiene cada
     * usuario.
     *
     * $group: agrupa documentos por un campo. _id: representa el campo por el
     * que se agrupa. totalMascotas: usa $sum para incrementar 1 por cada
     * mascota encontrada.
     *
     * Esto devuelve una lista con: { _id: <persona_id>, totalMascotas: N }
     */
    public List<Document> countMascotasPorUsuario() throws DaoException {
        try {
            List<Document> pipeline = List.of(
                    new Document("$group",
                            new Document("_id", "$persona_id")
                                    .append("totalMascotas", new Document("$sum", 1))
                    )
            );

            return colDoc.aggregate(pipeline).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error agrupando mascotas", e);
        }
    }

    /**
     * Pagina los resultados de mascotas. Calcula cuántos documentos saltar
     * según la página: skip = (page - 1) * size
     *
     * $sort: ordena por fecha de creación descendente. $skip: omite una
     * cantidad de documentos. $limit: define cuántos documentos devolver.
     */
    public List<Mascota> paginar(int page, int size) throws DaoException {
        try {
            int skip = (page - 1) * size;

            List<Document> pipeline = List.of(
                    new Document("$sort", new Document("creado_en", -1)),
                    new Document("$skip", skip),
                    new Document("$limit", size)
            );

            return col.aggregate(pipeline, Mascota.class).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error en paginación", e);
        }
    }

    /**
     * Proyecta únicamente los campos nombre y tipo de las mascotas.
     *
     * $project: permite especificar qué campos incluir o excluir. _id: 0
     * significa excluirlo. nombre: 1 significa incluirlo. tipo: 1 significa
     * incluirlo.
     */
    public List<Document> proyectarNombreYTipo() throws DaoException {
        try {
            List<Document> pipeline = List.of(
                    new Document("$project",
                            new Document("_id", 0)
                                    .append("nombre", 1)
                                    .append("tipo", 1)
                    )
            );

            return colDoc.aggregate(pipeline).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error en project", e);
        }
    }

    /**
     * Genera un ranking de usuarios basado en cuántas mascotas tienen.
     *
     * $lookup: une usuarios con mascotas según _id -> persona_id. $project:
     * nombre: 1 para incluir el nombre del usuario. cantidadMascotas: usa $size
     * para determinar cuántos elementos tiene el arreglo "mascotas". $sort:
     * ordena por cantidadMascotas en orden descendente.
     */
    public List<Document> rankingUsuariosPorMascotas() throws DaoException {
        try {
            List<Document> pipeline = List.of(
                    new Document("$lookup",
                            new Document("from", "mascotas")
                                    .append("localField", "_id")
                                    .append("foreignField", "persona_id")
                                    .append("as", "mascotas")
                    ),
                    new Document("$project",
                            new Document("nombre", 1)
                                    .append("cantidadMascotas", new Document("$size", "$mascotas"))
                    ),
                    new Document("$sort", new Document("cantidadMascotas", -1))
            );

            return colDoc.aggregate(pipeline).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error en ranking", e);
        }
    }
}
