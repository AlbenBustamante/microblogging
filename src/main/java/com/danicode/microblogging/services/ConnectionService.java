package com.danicode.microblogging.services;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Se encarga de gestionar la conexión a base de datos MySQL.
 */
public class ConnectionService {
    private static final String URL = "jdbc:mysql://localhost:3306/microblogging";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final int INITIAL_SIZE = 50;
    private static BasicDataSource dataSource = null;

    /**
     * Crea un nuevo {@code DataSource} y establece la configuración sólo si es {@code null}.
     * <p>Es necesario para crear un pool de conexiones.</p>
     * @return Devuelve el {@code DataSource} y su configuración.
     */
    private static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(URL);
            dataSource.setUsername(USERNAME);
            dataSource.setPassword(PASSWORD);
            dataSource.setInitialSize(INITIAL_SIZE);
        }
        return dataSource;
    }

    /**
     * Establece el driver de MySQL y crea una nueva conexión.
     * @return Devuelve la nueva conexión.
     * @throws Exception Puede ser ClassNotFoundException y/o SQLException.
     */
    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        return getDataSource().getConnection();
    }

    /**
     * Cuerra la conexion interna dado caso que no exista la conexion externa.
     * <p>La conexión externa es para manejar transacciones.</p>
     * @param externConnection Conexion externa.
     * @param conn Conexion interna.
     * @throws Exception Puede ser ClassNotFoundException y/o SQLException.
     */
    public static void close(Connection externConnection, Connection conn) throws Exception {
        if (externConnection == null) {
            conn.close();
        }
    }

    /**
     * Cierra primero el {@code PreparedStatement} y posteriormente la conexion.
     * @param externConnection Conexión externa.
     * @param conn Conexión interna.
     * @param stmt Sentencia preparada.
     * @throws Exception Puede ser ClassNotFoundException y/o SQLException.
     */
    public static void close(Connection externConnection, Connection conn, PreparedStatement stmt) throws Exception {
        stmt.close();
        close(externConnection, conn);
    }

    /**
     * Cierra primero el conjunto de resultados, luego la sentencia preparada y posteriormente, la conexión.
     * @param externConnection Conexión externa.
     * @param conn Conexión interna.
     * @param stmt Sentencia Preparada.
     * @param rs Conjunto de resultados.
     * @throws Exception Puede ser ClassNotFoundException y/o SQLException.
     */
    public static void close(Connection externConnection, Connection conn, PreparedStatement stmt,
                             ResultSet rs) throws Exception {
        rs.close();
        close(externConnection, conn, stmt);
    }
}
