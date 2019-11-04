package com.findme.service;

import com.findme.dao.UserDaoInterface;
import com.findme.exception.BadRequestException;
import com.findme.models.User;
import com.findme.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService implements ServiceInterface<User> {

    UserDaoInterface userDao;

    @Autowired
    public UserService(UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    public User get(Long userId) {
        User user = (User) userDao.findById(userId);
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userDao.getAll();
        return users;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(Long id) {
        //todo
    }


    @Override
    @Transactional
    public User save(User user ) {
        Date date = new Date();
        user.setDateRegistered(date);
        user.setDateLastActive(date);
        return (User) userDao.save(user);
    }
}