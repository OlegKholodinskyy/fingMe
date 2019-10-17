package com.findme.dao;

import com.findme.models.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserDao implements DaoInterface<User> {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public User save(User user) {
         entityManager.persist(user);
         return user;
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(User user) throws DataIntegrityViolationException {

    }

    @Override
    public boolean checkIfPresent(User user) {
        return false;
    }

}
