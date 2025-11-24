/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojosandmongodb.exception;

/**
 *
 * @author martinbl
 */
public class EntityNotFoundException extends DaoException {
    public EntityNotFoundException(String message) { super(message); }
}