/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pojosandmongodb;

import com.mycompany.pojosandmongodb.config.MongoClientProvider;
import com.mycompany.pojosandmongodb.exception.DaoException;
import com.mycompany.pojosandmongodb.exception.EntityNotFoundException;
import com.mycompany.pojosandmongodb.model.Direccion;
import com.mycompany.pojosandmongodb.model.Usuario;
import com.mycompany.pojosandmongodb.repository.IMascotaDAO;
import com.mycompany.pojosandmongodb.repository.IUsuarioDAO;
import com.mycompany.pojosandmongodb.repository.MascotaDAO;
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
        MascotaDAO mascotaDao = new MascotaDAO();

        try {
            System.out.println("===== LOOKUP Mascota → Usuario =====");
            mascotaDao.findMascotasWithUsuario()
                    .forEach(doc -> System.out.println(doc.toJson()));

            System.out.println("\n===== Mascotas por tipo y ordenadas =====");
            mascotaDao.findByTipoSorted("Perro")
                    .forEach(System.out::println);

            System.out.println("\n===== Conteo de Mascotas por Usuario =====");
            mascotaDao.countMascotasPorUsuario()
                    .forEach(doc -> System.out.println(doc.toJson()));

            System.out.println("\n===== Paginación de Mascotas =====");
            mascotaDao.paginar(1, 3)
                    .forEach(System.out::println);

            System.out.println("\n===== Proyección (solo nombre y tipo) =====");
            mascotaDao.proyectarNombreYTipo()
                    .forEach(doc -> System.out.println(doc.toJson()));

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
