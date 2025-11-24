/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojosandmongodb;

import com.mycompany.pojosandmongodb.config.MongoClientProvider;
import com.mycompany.pojosandmongodb.model.Direccion;
import com.mycompany.pojosandmongodb.model.Mascota;
import com.mycompany.pojosandmongodb.model.Usuario;
import com.mycompany.pojosandmongodb.repository.IMascotaDAO;
import com.mycompany.pojosandmongodb.repository.IUsuarioDAO;
import com.mycompany.pojosandmongodb.repository.MascotaDAO;
import com.mycompany.pojosandmongodb.repository.UsuarioDAO;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author martinbl
 */
public class Seeder {

    public static void main(String[] args) {
        MongoClientProvider.INSTANCE.init();

        IUsuarioDAO usuarioDAO = new UsuarioDAO();
        IMascotaDAO mascotaDAO = new MascotaDAO();

        try {
            // === Crear 30 usuarios ===
            List<Usuario> usuarios = List.of(
                    new Usuario(new ObjectId(), "Martin", 25, new Direccion("Prueba 12", "Obregón", "MEX"), List.of("beta", "premium"), null),
                    new Usuario(new ObjectId(), "Sandra", 30, new Direccion("Morelos 200", "Obregón", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Carlos", 28, new Direccion("Miguel Alemán", "Hermosillo", "MEX"), List.of("vip"), null),
                    new Usuario(new ObjectId(), "Jimena", 26, new Direccion("Rosales", "Navojoa", "MEX"), List.of("premium"), null),
                    new Usuario(new ObjectId(), "Ricardo", 35, new Direccion("Centro", "Guaymas", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Alejandra", 22, new Direccion("Tecnológico", "Obregón", "MEX"), List.of("new"), null),
                    new Usuario(new ObjectId(), "Ernesto", 33, new Direccion("Roma", "CDMX", "MEX"), List.of("vip", "premium"), null),
                    new Usuario(new ObjectId(), "Paola", 21, new Direccion("Juárez", "Monterrey", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Raúl", 27, new Direccion("Sonora", "Hermosillo", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Fernanda", 29, new Direccion("Centro", "Chihuahua", "MEX"), List.of("premium"), null),
                    // 20 más
                    new Usuario(new ObjectId(), "Lucía", 32, new Direccion("Olivos", "CDMX", "MEX"), List.of("vip"), null),
                    new Usuario(new ObjectId(), "Jorge", 24, new Direccion("Chapultepec", "CDMX", "MEX"), List.of("new"), null),
                    new Usuario(new ObjectId(), "María", 31, new Direccion("Sur 88", "Puebla", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Luis", 36, new Direccion("Norte 12", "Toluca", "MEX"), List.of("premium"), null),
                    new Usuario(new ObjectId(), "Esteban", 40, new Direccion("Roma Norte", "CDMX", "MEX"), List.of("vip"), null),
                    new Usuario(new ObjectId(), "Diana", 23, new Direccion("Reforma", "CDMX", "MEX"), List.of("new"), null),
                    new Usuario(new ObjectId(), "Tania", 26, new Direccion("Centro", "Guadalajara", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Miguel", 39, new Direccion("Juárez", "Durango", "MEX"), List.of("vip"), null),
                    new Usuario(new ObjectId(), "Patricia", 28, new Direccion("Mirador", "Cancún", "MEX"), List.of("premium"), null),
                    new Usuario(new ObjectId(), "Hugo", 34, new Direccion("Norte", "Tijuana", "MEX"), List.of("premium"), null),
                    new Usuario(new ObjectId(), "Teresa", 22, new Direccion("Roma", "CDMX", "MEX"), List.of("new"), null),
                    new Usuario(new ObjectId(), "Omar", 27, new Direccion("Centro", "Saltillo", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Nidia", 33, new Direccion("López Mateos", "Obregón", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Ramiro", 38, new Direccion("Valle Verde", "Monterrey", "MEX"), List.of("premium"), null),
                    new Usuario(new ObjectId(), "Elena", 29, new Direccion("Insurgentes", "CDMX", "MEX"), List.of("vip"), null),
                    new Usuario(new ObjectId(), "Gustavo", 41, new Direccion("Obregón 200", "Guadalajara", "MEX"), List.of("basic"), null),
                    new Usuario(new ObjectId(), "Carmen", 26, new Direccion("Centro", "Tabasco", "MEX"), List.of("premium"), null),
                    new Usuario(new ObjectId(), "Fabian", 30, new Direccion("Morelos", "Obregón", "MEX"), List.of("beta"), null),
                    new Usuario(new ObjectId(), "Beatriz", 32, new Direccion("Sur", "Mérida", "MEX"), List.of("vip"), null),
                    new Usuario(new ObjectId(), "Rosa", 24, new Direccion("Norte", "Culiacán", "MEX"), List.of("basic"), null)
            );

            // Guardar usuarios
            for (Usuario u : usuarios) {
                usuarioDAO.create(u);
            }

            System.out.println("Usuarios insertados: " + usuarios.size());

            // === Crear 20 mascotas y ligarlas a usuarios ===
            List<Mascota> mascotas = List.of(
                    new Mascota(new ObjectId(), "Firulais", "Perro", usuarios.get(0).getId()),
                    new Mascota(new ObjectId(), "Michi", "Gato", usuarios.get(1).getId()),
                    new Mascota(new ObjectId(), "Rex", "Perro", usuarios.get(2).getId()),
                    new Mascota(new ObjectId(), "Pelusa", "Gato", usuarios.get(3).getId()),
                    new Mascota(new ObjectId(), "Bobby", "Perro", usuarios.get(4).getId()),
                    new Mascota(new ObjectId(), "Nieve", "Conejo", usuarios.get(5).getId()),
                    new Mascota(new ObjectId(), "Solovino", "Perro", usuarios.get(6).getId()),
                    new Mascota(new ObjectId(), "Copito", "Gato", usuarios.get(7).getId()),
                    new Mascota(new ObjectId(), "Toby", "Perro", usuarios.get(8).getId()),
                    new Mascota(new ObjectId(), "Luna", "Gato", usuarios.get(9).getId()),
                    new Mascota(new ObjectId(), "Chispa", "Perro", usuarios.get(10).getId()),
                    new Mascota(new ObjectId(), "Max", "Perro", usuarios.get(11).getId()),
                    new Mascota(new ObjectId(), "Kiara", "Gata", usuarios.get(12).getId()),
                    new Mascota(new ObjectId(), "Oddie", "Perro", usuarios.get(13).getId()),
                    new Mascota(new ObjectId(), "Pancho", "Perro", usuarios.get(14).getId()),
                    new Mascota(new ObjectId(), "Princesa", "Gata", usuarios.get(15).getId()),
                    new Mascota(new ObjectId(), "Romeo", "Gato", usuarios.get(16).getId()),
                    new Mascota(new ObjectId(), "Julieta", "Gato", usuarios.get(17).getId()),
                    new Mascota(new ObjectId(), "Gordo", "Perro", usuarios.get(18).getId()),
                    new Mascota(new ObjectId(), "Nala", "Gata", usuarios.get(19).getId())
            );

            for (Mascota m : mascotas) {
                mascotaDAO.create(m);
            }

            System.out.println("Mascotas insertadas: " + mascotas.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
