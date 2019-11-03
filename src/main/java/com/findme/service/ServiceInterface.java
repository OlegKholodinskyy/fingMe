package com.findme.service;

import com.findme.models.User;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by oleg on 19.10.2019.
 */
public interface ServiceInterface<T> {

    public T save(T t);
    public T get (Long id);

    public List<T> getAll();
}
