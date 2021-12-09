package com.danicode.microblogging.dao.templates;

import java.util.List;

public interface CRUD<E> {

    int create(E element) throws Exception;

    List<E> list() throws Exception;

    int update(E element) throws Exception;

    int delete(int idElement) throws Exception;

    E findById(int idElement) throws Exception;
}
