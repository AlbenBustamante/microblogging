package com.danicode.microblogging.services;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.model.dao.implementations.DAOMessageImpl;
import com.danicode.microblogging.model.dao.templates.DAOMessage;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.danicode.microblogging.services.ConnectionService.*;

public class MessageService {
    private Connection conn;
    private DAOMessage messageDao;
    private final UserService userService;

    public MessageService() {
        this.userService = new UserService();
    }

    /**
     * Crea e inserta un nuevo mensaje a la base de datos.
     * @param message Mensaje a registrar.
     * @return Devuelve true si la operación fue exitosa.
     */
    public boolean createMessage(Message message) {
        var isCreated = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.messageDao = new DAOMessageImpl(this.conn);

            isCreated = this.messageDao.create(message) != 0;

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
        return isCreated;
    }

    /**
     * Obtiene una lista de mensajes según el filtro indicado.
     * <p>Preferiblemente, usa las constantes de {@code BLogConstants} para filtrar los mensajes.</p>
     * @param filter Filtro de mensajes.
     * @param message Inicializa el username del mensaje si el filtro es {@code LIST_USER_MESSAGES}.
     *                <p>En cambio, usa el username del {@code userLogged} si el filtro es {@code LIST_MY_MESSAGES}.</p>
     *                <p>Inicializa el mensaje si usas {@code LIST_BY_MESSAGE}.</p>
     *                <p>Si sólo deseas obtener TODOS los mensajes, {@code message} puede ser {@code null}.</p>
     */
    public List<Message> getMessages(int filter, Message message) {
        List<Message> messages = null;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.messageDao = new DAOMessageImpl(this.conn);

            if (filter == BlogConstants.LIST_MESSAGES) {
                messages = this.messageDao.list();
            } else if (filter == BlogConstants.LIST_USER_MESSAGES) {
                messages = this.messageDao.findByUsername(message.getUser().getUsername());
            } else if (filter == BlogConstants.LIST_BY_MESSAGE) {
                messages = this.messageDao.findByMessage(message.getMessage());
            } else if (filter == BlogConstants.LIST_MY_MESSAGES) {
                messages = this.messageDao.findByUsername(this.userService.getUserLogged().getUsername());
            }

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
        return messages;
    }

    /**
     * Envía una consulta a la base de datos para obtener al usuario que coincida con el nombre de usuario.
     * @param username Nombre de usuario a buscar.
     * @return Devuelve al usuario encontrado, si no encuentra, devuelve {@code null}.
     */
    public User getAuthor(String username) {
        return this.userService.getUser(username);
    }

    /**
     * Edita un mensaje y guarda los cambios en la base de datos.
     * @param message Mensaje a actualizar. Importante tener el id inicializado.
     * @return Devuelve true si la operación fue exitosa.
     */
    public boolean editMessage(Message message) {
        var edited = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.messageDao = new DAOMessageImpl(this.conn);

            edited = this.messageDao.update(message) != 0;

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
        return edited;
    }

    /**
     * Obtiene un mensaje según la hora y fecha de registro.
     * @param dateTime Fecha y hora a consultar.
     * @return Devuelve el mensaje si fue encontrado, de lo contrario devuelve {@code null}.
     */
    public Message getMessage(String dateTime) {
        Message message = null;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.messageDao = new DAOMessageImpl(this.conn);

            message = this.messageDao.findByDateTime(dateTime);

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
        return message;
    }

    /**
     * Borra un mensaje de la base de datos.
     * @param idMessage id del mensaje a borrar.
     * @return Devuelve true si la operación fue exitosa.
     */
    public boolean deleteMessage(int idMessage) {
        var deleted = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.messageDao = new DAOMessageImpl(this.conn);

            deleted = this.messageDao.delete(idMessage) != 0;

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
     * Borra todos los mensajes del usuario logueado.
     * @return Devuelve true si la operación fue exitosa.
     */
    public boolean deleteUserMessages() {
        var deleted = false;
        try {
            this.conn = getConnection();
            this.conn.setAutoCommit(false);
            this.messageDao = new DAOMessageImpl(this.conn);

            deleted = this.messageDao.deleteUserMessages(this.userService.getUserLogged().getIdUser()) != 0;

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
     * Ordena los mensajes según el orden.
     * <p>Nuevamente, preferiblemente usar las constantes de {@code BlogConstants} para filtrado y ordenamiento.</p>
     * @param order Puede ser {@code ORDER_BY_NEW_MESSAGES} o {@code ORDER_BY_OLD_MESSAGES}.
     * @param filter Ver la documentación de {@code getMessages} para obtener la información.
     * @param message Ver la documentación de {@code getMessages} para obtener la información.
     * @return Devuelve una lista de mensajes según el filtrado y ordenamiento.
     * <p>Devuelve una lista vacía si no encuentra elementos.</p>
     * @see #getMessages(int, Message)
     */
    public List<Message> sortedMessages(String order, int filter, Message message) {
        var messages = this.getMessages(filter, message);

        if (order.equals(BlogConstants.ORDER_BY_NEW_MESSAGES)) {
            Collections.reverse(messages);
        }

        return messages;
    }
}
