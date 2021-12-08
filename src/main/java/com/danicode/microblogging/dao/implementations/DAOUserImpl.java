package com.danicode.microblogging.dao.implementations;

import com.danicode.microblogging.dao.templates.DAOUser;
import com.danicode.microblogging.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static com.danicode.microblogging.dao.ConnectionService.*;

public class DAOUserImpl implements DAOUser {
    private Connection externConnection = null;
    private static final String
        SQL_INSERT = "INSERT INTO users(name, last_name, email, username, password) VALUES(?, ?, ?, ?, ?);",
        SQL_SELECT = "SELECT * FROM users;",
        SQL_UPDATE = "UPDATE users SET name=?, last_name=?, email=?, username=?, password=? WHERE user_id=?;",
        SQL_DELETE = "DELETE FROM users WHERE user_id=?;",
        SQL_SELECT_BY_ID = "SELECT name, last_name, email, username, password FROM users WHERE user_id=?;";

    public DAOUserImpl() { }

    public DAOUserImpl(Connection externConnection) {
        this.externConnection = externConnection;
    }

    @Override
    public int create(User user) throws Exception {
        Connection conn = this.externConnection == null ? getConnection() : this.externConnection;
        PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT);
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getLastName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUsername());
        pstmt.setString(5, user.getPassword());

        var rowsUpdated = pstmt.executeUpdate();
        close(this.externConnection, conn, pstmt);

        return rowsUpdated;
    }

    @Override
    public List<User> list() throws Exception {
        return null;
    }

    @Override
    public int update(User user) throws Exception {
        return 0;
    }

    @Override
    public int delete(int idUser) throws Exception {
        return 0;
    }

    @Override
    public User getById(int idUser) throws Exception {
        return null;
    }
}
