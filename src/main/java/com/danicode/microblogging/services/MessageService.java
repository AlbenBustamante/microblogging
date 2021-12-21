package com.danicode.microblogging.services;

import com.danicode.microblogging.constants.BlogConstants;
import com.danicode.microblogging.model.dao.implementations.DAOMessageImpl;
import com.danicode.microblogging.model.dao.templates.DAOMessage;
import com.danicode.microblogging.model.domain.Message;
import com.danicode.microblogging.model.domain.User;

import java.sql.Connection;
import java.util.List;

import static com.danicode.microblogging.services.ConnectionService.*;

public class MessageService {
    private Connection conn;
    private DAOMessage messageDao;

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
     * Mediante este método, puedes obtener una lista de mensajes según lo que requieras.
     * @param filter Usa las constantes de {@code BlogConstants} para obtener el listado deseado.
     * @param message Si el filtro es distinto de {@code LIST_MESSAGES}, puede ser {@code null}.
     *                En cambio, si es por usuario, con sólo indicar el mensaje es suficiente.
     * */
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

    public User getAuthor(String username) {
        var service = new UserService();
        return service.getUser(username);
    }

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
}
