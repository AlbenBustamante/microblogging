package com.danicode.microblogging.model.dao.implementations;

import com.danicode.microblogging.model.dao.templates.DAOMessage;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.Message;

import java.sql.Connection;
import java.util.List;

public class DAOMessageImpl implements DAOMessage {
    private Connection externConnection;
    private DAOUser userDao;
    private static final String
        SQL_INSERT = "INSERT INTO messages(user_id_pk, date_time, message) VALUES(?, ?, ?);",
        SQL_SELECT = "SELECT message_id, user_id_pk, date_time, message FROM messages;",
        SQL_UPDATE = "UPDATE messages SET message = ? WHERE message_id = ?;",
        SQL_DELETE = "DELETE FROM messages WHERE message_id = ?,",
        SQL_SELECT_BY_ID = "SELECT user_id_pk, date_time, message FROM messages WHERE message_id = ?;";

    public DAOMessageImpl() {
        this.userDao = new DAOUserImpl();
    }

    public DAOMessageImpl(Connection externConnection) {
        this.externConnection = externConnection;
        this.userDao = new DAOUserImpl(externConnection);
    }

    @Override
    public int create(Message message) throws Exception {
        return 0;
    }

    @Override
    public List<Message> list() throws Exception {
        return null;
    }

    @Override
    public int update(Message message) throws Exception {
        return 0;
    }

    @Override
    public int delete(int idMessage) throws Exception {
        return 0;
    }

    @Override
    public Message findById(int idMessage) throws Exception {
        return null;
    }

    @Override
    public List<Message> findByUsername(String username) throws Exception {
        return null;
    }

    @Override
    public List<Message> findByMessage(String message) throws Exception {
        return null;
    }
}
