package com.danicode.microblogging.model.dao.implementations;

import com.danicode.microblogging.model.dao.templates.DAOMessage;
import com.danicode.microblogging.model.dao.templates.DAOUser;
import com.danicode.microblogging.model.domain.Message;

import java.sql.Connection;
import java.util.List;

public class DAOMessageImpl implements DAOMessage {
    private Connection externConnection;
    private DAOUser userDao;

    public DAOMessageImpl() {
        this.userDao = new DAOUserImpl();
    }

    public DAOMessageImpl(Connection externConnection) {
        this.externConnection = externConnection;
        this.userDao = new DAOUserImpl(externConnection);
    }

    @Override
    public int create(Message element) throws Exception {
        return 0;
    }

    @Override
    public List<Message> list() throws Exception {
        return null;
    }

    @Override
    public int update(Message element) throws Exception {
        return 0;
    }

    @Override
    public int delete(int idElement) throws Exception {
        return 0;
    }

    @Override
    public Message findById(int idElement) throws Exception {
        return null;
    }

    @Override
    public List<Message> findByUsername(int idUser) throws Exception {
        return null;
    }

    @Override
    public List<Message> findByMessage(String message) throws Exception {
        return null;
    }
}
