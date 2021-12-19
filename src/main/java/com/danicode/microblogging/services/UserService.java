package com.danicode.microblogging.services;

import com.danicode.microblogging.model.dao.implementations.DAOUserImpl;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;

import static com.danicode.microblogging.services.ConnectionService.getConnection;

public class UserService {
    private Connection conn;
    private DAOUser userDao;
    private static User userLogged = null;

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
            try {
                if (this.conn != null) {
                    this.conn.rollback();
                }
            } catch (Exception ex1) {
                ex1.printStackTrace(System.out);
            }
        }
        return isRegistered;
    }

    public boolean registerNewUser(User user) {
        var register = false;
        if (!this.isUserRegistered(user)) {
            try {
                this.conn = getConnection();
                this.conn.setAutoCommit(false);
                this.userDao = new DAOUserImpl(this.conn);

                register = this.userDao.create(user) != 0;

                this.conn.commit();
                this.conn.close();
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                try {
                    if (this.conn != null) {
                        this.conn.rollback();
                    }
                } catch (Exception ex1) {
                    ex1.printStackTrace(System.out);
                }
            }
        }
        return register;
    }

    public boolean setUserLogged(User userToLog) {
        if (userLogged == null) {
            try {
                if (this.isUserRegistered(userToLog)) {
                    this.conn = getConnection();
                    this.conn.setAutoCommit(false);
                    this.userDao = new DAOUserImpl(this.conn);

                    var userByEmail = this.userDao.findByEmail(userToLog.getEmail());
                    var userByUsername = this.userDao.findByUsername(userToLog.getUsername());

                    if (this.isDataCorrect(userToLog, userByEmail)) {
                        userLogged = userByEmail;
                    }
                    else if (this.isDataCorrect(userToLog, userByUsername)) {
                        userLogged = userByUsername;
                    }

                    this.conn.commit();
                    this.conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                try {
                    if (this.conn != null) {
                        this.conn.rollback();
                    }
                } catch (Exception ex1) {
                    ex1.printStackTrace(System.out);
                }
            }
        }

        return userLogged != null;
    }

    private boolean isDataCorrect(User userToLog, User userToCompare) {
        if (userToCompare != null) {
            return userToCompare.getPassword().equals(userToLog.getPassword());
        }
        return false;
    }

    public User getUser(String username) {
        User user = null;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.userDao = new DAOUserImpl(this.conn);

            user = this.userDao.findByUsername(username);

            this.conn.commit();
            this.conn.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            try {
                if (this.conn != null) {
                    this.conn.rollback();
                }
            } catch (Exception ex1) {
                ex1.printStackTrace(System.out);
            }
        }

        return user != null ? user : new User(
                "Nombre y ", "apellido no encontrado", "Email no encontrado", "Usuario no encontrado", ""
        );
    }

    public User getUserLogged() { return userLogged; }

    public void resetUserLogged() { userLogged = null; }
}
