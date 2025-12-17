package com.ncpp.asistenteexpedientes.asistente.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoAsistente {
    private final static String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private final static String URL = "jdbc:postgresql://172.17.104.247/ASISTENTE_SANTA";
    private final static String USERNAME = "ASISTENTESANTA_ADM";
    private final static String PASSWORD = "ADMIN7895123$$#";

    private AccesoAsistente(){
    }

    @SuppressWarnings("deprecation")
	public static Connection getConnection() throws SQLException {
        Connection cn = null;
        try {
            // Paso 1: Cargar el driver a memoria 
            Class.forName(DRIVER_CLASS_NAME).newInstance();
            // Paso 2: Obtener el objeto Connection 
            cn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            throw new SQLException("No se encontro el driver de la base de datos.");
        } catch (Exception e) {
            throw new SQLException("No se puede establecer la conexion con la BD.");
        }
        return cn;
    }
}
