package com.danicode.microblogging.dao.implementations;

import com.danicode.microblogging.dao.templates.DAOUser;
import com.danicode.microblogging.domain.User;

import java.util.List;

public class DAOUserImpl implements DAOUser {
    private static final String
        SQL_INSERT = "INSERT INTO users(name, last_name, email, username, password) VALUES(?, ?, ?, ?, ?);",
        SQL_SELECT = "SELECT * FROM users;",
        SQL_UPDATE = "UPDATE users SET name=?, last_name=?, email=?, username=?, password=? WHERE user_id=?;",
        SQL_DELETE = "DELETE FROM users WHERE user_id=?;",
        SQL_SELECT_BY_ID = "SELECT name, last_name, email, username, password FROM users WHERE user_id=?;";

    @Override
    public int create(User element) throws Exception {
        return 0;
    }

    @Override
    public List<User> list() throws Exception {
        return null;
    }

    @Override
    public int update(User element) throws Exception {
        return 0;
    }

    @Override
    public int delete(int idElement) throws Exception {
        return 0;
    }

    @Override
    public User getById(int idElement) throws Exception {
        return null;
    }
}
