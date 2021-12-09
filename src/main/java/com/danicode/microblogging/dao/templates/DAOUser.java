package com.danicode.microblogging.dao.templates;

import com.danicode.microblogging.domain.User;

public interface DAOUser extends CRUD<User> {

    User findByUsername(String username) throws Exception;

    User findByEmail(String email) throws Exception;
}
