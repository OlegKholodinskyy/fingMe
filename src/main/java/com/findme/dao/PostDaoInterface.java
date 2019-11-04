package com.findme.dao;

import com.findme.models.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PostDaoInterface<T> extends DaoInterface<T>{
//    public T save(T t);
//
//    T findById(Long id);
//
//    T update(T t);
//
//    void delete(T t) throws DataIntegrityViolationException;
//
//    boolean checkIfPresent(T t);
//
//    List<User> getAll();

 //   T getOne(String phone);

}
