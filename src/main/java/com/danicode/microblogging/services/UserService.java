package com.danicode.microblogging.services;

import com.danicode.microblogging.model.dao.implementations.DAOUserImpl;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;

import static com.danicode.microblogging.services.ConnectionService.getConnection;

public class UserService {
    Connection conn;
    DAOUser userDao;

    public boolean isUserRegistered(User user) {
        boolean isRegistered = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.userDao = new DAOUserImpl(this.conn);

            isRegistered = this.userDao.findByEmail(user.getEmail()) != null ||
                    this.userDao.findByUsername(user.getUsername()) != null;

            this.conn.commit();
            this.conn.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return isRegistered;
    }
}
