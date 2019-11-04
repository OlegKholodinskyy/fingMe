package com.findme.service;

import com.findme.exception.NotFoundException;
import com.findme.exception.ObjectExistException;
import com.findme.models.User;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by oleg on 19.10.2019.
 */
public interface ServiceInterface<T> {

    public T save(T t);
    public T get (Long id) throws ObjectExistException, NotFoundException;

    public List<T> getAll();
    public T update (T t) throws NotFoundException, ObjectExistException;
    public void delete(Long id) throws ObjectExistException, NotFoundException;
}
