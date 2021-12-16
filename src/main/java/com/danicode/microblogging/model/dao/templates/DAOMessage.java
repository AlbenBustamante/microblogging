package com.danicode.microblogging.model.dao.templates;

import com.danicode.microblogging.model.domain.Message;

import java.util.List;

public interface DAOMessage extends CRUD<Message> {

    List<Message> findByUsername(String username) throws Exception;

    List<Message> findByMessage(String message) throws Exception;
}
