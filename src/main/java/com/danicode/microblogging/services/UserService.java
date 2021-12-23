package com.danicode.microblogging.services;

import com.danicode.microblogging.model.dao.implementations.DAOUserImpl;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;

import static com.danicode.microblogging.services.ConnectionService.getConnection;

/**
 * Esta clase se encarga de gestionar el manejo de usuarios y de la base de datos.
 */
public class UserService {
    private Connection conn;
    private DAOUser userDao;
    private static User userLogged = null;

    /**
     * Verifica si el usuario fue encontrado según el email o el username.
     * @param user Usuario a consultar.
     * @return Devuelve true si el usuario fue encontrado.
     */
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

    /**
     * Registra un nuevo a la base de datos si éste no existe según {@code isUserRegistered}.
     * @param user Usuario a registrar
     * @return Devuelve true si el usuario fue insertado a la base de datos.
     */
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

    /**
     * Establece al usuario logueado si no hay otro usuario logueado y los datos están correctos.
     * @param userToLog Usuario a loguearse.
     * @return Devuelve true si la operación fue exitosa.
     */
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

    /**
     * Comparara los datos entre el usuario a loguearse y el usuario registrado en la base de datos.
     * @param userToLog Usuario a loguearse.
     * @param userToCompare Usuario registrado en la base de datos, puede ser según el email o el username.
     * @return Devuelve true si la contraseña de ambos usuarios coinciden.
     */
    private boolean isDataCorrect(User userToLog, User userToCompare) {
        if (userToCompare != null) {
            return userToCompare.getPassword().equals(userToLog.getPassword());
        }
        return false;
    }

    /**
     * Encuentra en la base de datos a un usuario según el nombre de usuario.
     * @param username Nombre de usuario a buscar.
     * @return Devuelve {@code null} si no se encuentra, de lo contrario, devuelve al usuario con todos sus atributos.
     */
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

    /**
     * Actualiza los datos de un usuario.
     * @param user Usuario a actualizar. Importante que tenga el id inicializado.
     * @return Devuelve true si la operación fue exitosa.
     */
    public boolean updateUser(User user) {
        var updated = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.userDao = new DAOUserImpl(this.conn);

            updated = this.userDao.update(user) != 0;

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
        return updated;
    }

    /**
     * Borra a un usuario de la base de datos.
     * @param idUser Id del usuario a borrar.
     * @return Devuelve true si la operación fue exitosa.
     */
    public boolean deleteUser(int idUser) {
        var deleted = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.userDao = new DAOUserImpl(this.conn);

            deleted = this.userDao.delete(idUser) != 0;

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
        return deleted;
    }

    /**
     * @return Devuelve al usuario logueado. Si no hay usuario logueado, devuelve {@code null}.
     */
    public User getUserLogged() { return userLogged; }

    /**
     * Reinicia al usuario logueado, por lo que su valor pasa a ser {@code null}.
     */
    public void resetUserLogged() { userLogged = null; }
}
