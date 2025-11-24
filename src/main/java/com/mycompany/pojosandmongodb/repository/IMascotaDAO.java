/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pojosandmongodb.repository;

import com.mycompany.pojosandmongodb.exception.DaoException;
import com.mycompany.pojosandmongodb.exception.EntityNotFoundException;
import com.mycompany.pojosandmongodb.model.Mascota;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public interface IMascotaDAO {

    ObjectId create(Mascota entity) throws DaoException;

    Optional<Mascota> findById(ObjectId _id) throws DaoException;

    List<Mascota> findAll() throws DaoException;

    boolean update(Mascota entity) throws DaoException, EntityNotFoundException;

    boolean deleteById(ObjectId _id) throws DaoException, EntityNotFoundException;

    long deleteAll() throws DaoException;

    Optional<Mascota> findByNombre(String nombre) throws DaoException;
}
