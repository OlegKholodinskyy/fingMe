package com.findme.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DaoInterface<T> {
    public T save(T t);

    T findById(Long id);

    T update(T t);

    void delete(T t) throws DataIntegrityViolationException;

    List<T> getAll();

}
