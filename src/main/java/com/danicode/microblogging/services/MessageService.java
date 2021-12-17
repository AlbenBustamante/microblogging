package com.danicode.microblogging.services;

import com.danicode.microblogging.model.dao.implementations.DAOMessageImpl;
import com.danicode.microblogging.model.dao.templates.DAOMessage;
import com.danicode.microblogging.model.domain.Message;

import java.sql.Connection;

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
}
