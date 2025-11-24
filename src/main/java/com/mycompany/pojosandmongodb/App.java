/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pojosandmongodb;

import com.mycompany.pojosandmongodb.config.MongoClientProvider;
import com.mycompany.pojosandmongodb.exception.DaoException;
import com.mycompany.pojosandmongodb.exception.EntityNotFoundException;
import com.mycompany.pojosandmongodb.model.Direccion;
import com.mycompany.pojosandmongodb.model.Usuario;
import com.mycompany.pojosandmongodb.repository.IUsuarioDAO;
import com.mycompany.pojosandmongodb.repository.UsuarioDAO;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public class App {

    public static void main(String[] args) {
        // Inicializa Solo Una Vez
        MongoClientProvider.INSTANCE.init();

        // Usa el DAO
        IUsuarioDAO dao = new UsuarioDAO();
        
//        try{
//
//        } catch (EntityNotFoundException e) {
//            System.err.println("No encontrado: " + e.getMessage());
//        } catch (DaoException e) {
//            System.err.println("Error DAO: " + e.getMessage());
//        }
    }
}
