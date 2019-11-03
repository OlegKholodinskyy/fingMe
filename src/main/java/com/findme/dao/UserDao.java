package com.findme.dao;

import com.findme.models.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDao implements UserDaoInterface<User> {

    private String getAllString = "SELECT * FROM USER_TABLE";
    private String getOneString = "SELECT * FROM USER_TABLE WHERE PHONE = ?";
    @PersistenceContext
    private EntityManager entityManager;


    @Override
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

    @Override
    public List<User> getAll() {
        List<User> users = entityManager.createNativeQuery(getAllString, User.class).getResultList();
        return users;
    }

    @Override
    public User getOne(String phone) {
        User user;
        try {
            user = (User) entityManager.createNativeQuery(getOneString, User.class).setParameter(1, phone).getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;
    }

}
