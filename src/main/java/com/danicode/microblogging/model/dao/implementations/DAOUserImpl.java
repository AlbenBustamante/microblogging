package com.danicode.microblogging.model.dao.implementations;

import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static com.danicode.microblogging.services.ConnectionService.*;

public class DAOUserImpl implements DAOUser {
    private Connection externConnection = null;
    private static final String
        SQL_INSERT = "INSERT INTO users(name, last_name, email, username, password) VALUES(?, ?, ?, ?, ?);",
        SQL_SELECT = "SELECT * FROM users;",
        SQL_UPDATE = "UPDATE users SET name=?, last_name=?, password=? WHERE user_id=?;",
        SQL_DELETE = "DELETE FROM users WHERE user_id=?;",
        SQL_SELECT_BY_ID = "SELECT name, last_name, email, username, password FROM users WHERE user_id=?;",
        SQL_SELECT_BY_USERNAME = "SELECT * FROM users WHERE UPPER(username)=UPPER(?);",
        SQL_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email=?;";

    public DAOUserImpl() { }

    public DAOUserImpl(Connection externConnection) {
        this.externConnection = externConnection;
    }

    @Override
    public int create(User user) throws Exception {
        Connection conn = this.externConnection == null ? getConnection() : this.externConnection;
        PreparedStatement stmt = conn.prepareStatement(SQL_INSERT);
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail().toLowerCase());
        stmt.setString(4, user.getUsername());
        stmt.setString(5, user.getPassword());

        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }

    @Override
    public List<User> list() throws Exception {
        List<User> users = new ArrayList<>();
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_SELECT);
        var rs = stmt.executeQuery();

        while (rs.next()) {
            var user = new User(
                    rs.getInt("user_id"), rs.getString("name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("username"), rs.getString("password")
            );
            users.add(user);
        }

        close(this.externConnection, conn, stmt, rs);
        return users;
    }

    @Override
    public int update(User user) throws Exception {
        var conn = this.externConnection == null ? getConnection() : this.externConnection;
        var stmt = conn.prepareStatement(SQL_UPDATE);
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getPassword());
        stmt.setInt(4, user.getIdUser());

        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }

    @Override
    public int delete(int idUser) throws Exception {
        var conn = this.externConnection != null ? this.externConnection : getConnection();
        var stmt = conn.prepareStatement(SQL_DELETE);
        stmt.setInt(1, idUser);
        var rowsUpdated = stmt.executeUpdate();
        close(this.externConnection, conn, stmt);
        return rowsUpdated;
    }

    @Override
    public User findById(int idUser) throws Exception {
        User user = null;
        var conn = this.externConnection == null ? getConnection() : this.externConnection;
        var stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
        stmt.setInt(1, idUser);
        var rs = stmt.executeQuery();

        if (rs.next()) {
            user = new User(
                    idUser, rs.getString("name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("username"), rs.getString("password")
            );
        }
        close(this.externConnection, conn, stmt, rs);
        return user;
    }

    @Override
    public User findByUsername(String username) throws Exception {
        return this.findByString(SQL_SELECT_BY_USERNAME, username);
    }

    @Override
    public User findByEmail(String email) throws Exception {
        return this.findByString(SQL_SELECT_BY_EMAIL, email);
    }

    private User findByString(String query, String value) throws Exception {
        User user = null;
        var conn = this.externConnection == null ? getConnection() : this.externConnection;
        var stmt = conn.prepareStatement(query);
        stmt.setString(1, value);
        var rs = stmt.executeQuery();

        if (rs.next()) {
            user = new User(
                    rs.getInt("user_id"), rs.getString("name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("username"), rs.getString("password")
            );
        }
        close(this.externConnection, conn, stmt, rs);
        return user;
    }
}
