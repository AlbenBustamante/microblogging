package com.danicode.microblogging.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionService {
    private static final String URL = "jdbc:mysql://localhost:3306/microblogging";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static BasicDataSource dataSource = null;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(URL);
            dataSource.setUsername(USERNAME);
            dataSource.setPassword(PASSWORD);
            dataSource.setInitialSize(50);
        }
        return dataSource;
    }

    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER);
        return getDataSource().getConnection();
    }

    public static void close(Connection externConnection, Connection conn) throws Exception {
        if (externConnection == null) {
            conn.close();
        }
    }

    public static void close(Connection externConnection, Connection conn, PreparedStatement pstmt) throws Exception {
        pstmt.close();
        close(externConnection, conn);
    }

    public static void close(Connection externConnection, Connection conn, PreparedStatement pstmt,
                             ResultSet rs) throws Exception {
        rs.close();
        close(externConnection, conn, pstmt);
    }
}
