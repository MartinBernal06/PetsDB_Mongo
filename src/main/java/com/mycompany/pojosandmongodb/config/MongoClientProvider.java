/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojosandmongodb.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author martinbl
 */
/**
 * Ejemplo del patrón Singleton usando un enum en Java.
 * 
 * Esta clase garantiza que solo exista UNA instancia del cliente de MongoDB 
 * en toda la aplicación, y proporciona un punto global de acceso.
 */
public enum MongoClientProvider {
    // ==============================
    // Instancia ÚNICA (Singleton)
    // ==============================
    INSTANCE; // Esta es la única instancia de la clase. Java garantiza que solo exista una.

    // ==============================
    // Atributos de conexión
    // ==============================
    private MongoClient client;            // Objeto principal de conexión a MongoDB
    private String dbName = "PetsDB";  // Nombre de la base de datos
    private String uri = "tu-url-de-conexion"; // URI o cadena de conexión a MongoDB

    // ==============================
    // Inicialización del cliente
    // ==============================
    /**
     * Método para inicializar el cliente de MongoDB.
     * 
     * Se usa "synchronized" para asegurar que solo un hilo lo inicialice,
     * evitando que se creen varias conexiones por error.
     */
    public synchronized void init() {
        if (client == null) { // Solo se crea si no existe
            // Crea la conexión usando una configuración personalizada
            client = MongoClients.create(MongoConfig.buildSettings(this.uri));

            // Hook para cerrar la conexión cuando la app se apaga
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try { 
                    client.close(); 
                } catch (Exception ignored) {}
            }));
        }
    }

    // ==============================
    // Obtener el cliente MongoDB
    // ==============================
    /**
     * Retorna el cliente MongoDB actual.
     * 
     * Si no se ha inicializado, lanza una excepción para obligar 
     * a llamar a "init()" primero.
     */
    public MongoClient client() {
        if (client == null)
            throw new IllegalStateException("MongoClientProvider no inicializado. Llama a init() antes.");
        return client;
    }

    // ==============================
    // Obtener la base de datos
    // ==============================
    /**
     * Devuelve la base de datos configurada (por defecto "UsuariosDB").
     */
    public MongoDatabase database() {
        return client().getDatabase(this.dbName);
    }

    // ==============================
    // Obtener una colección
    // ==============================
    /**
     * Devuelve una colección del tipo indicado.
     * 
     * @param collectionName Nombre de la colección (ej. "usuarios")
     * @param clazz Tipo de documento (ej. Document.class)
     */
    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        if (client == null)
            throw new IllegalStateException("MongoClientProvider no inicializado. Llama a init() antes.");

        MongoDatabase db = client.getDatabase(this.dbName);
        return db.getCollection(collectionName, clazz);
    }
}