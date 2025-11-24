/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pojosandmongodb.repository;

import com.mycompany.pojosandmongodb.exception.DaoException;
import com.mycompany.pojosandmongodb.exception.EntityNotFoundException;
import com.mycompany.pojosandmongodb.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public interface IUsuarioDAO {
    ObjectId create(Usuario entity) throws DaoException;

    Optional<Usuario> findById(ObjectId _id) throws DaoException;

    List<Usuario> findAll() throws DaoException;

    boolean update(Usuario entity) throws DaoException, EntityNotFoundException;

    boolean deleteById(ObjectId _id) throws DaoException, EntityNotFoundException;

    long deleteAll() throws DaoException;

    Optional<Usuario> findByNombre(String nombre) throws DaoException;

}
