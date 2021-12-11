package com.danicode.microblogging.model.dao.templates;

import com.danicode.microblogging.model.domain.Message;

import java.util.List;

public interface DAOMessage extends CRUD<Message> {

    List<Message> findByUsername(int idUser) throws Exception;
}
